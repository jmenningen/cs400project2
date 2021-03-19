// --== CS400 File Header Information ==--
// Name: Xin Cai
// Email: xcai72@wisc.edu
// Team: DF blue
// Role: Backend developer
// TA: Dan
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class CalorieWatchBackend implements BackendInterface{
  
  /* data fields: Five Red Black Trees to store data */
  private HashMap<String, Item> menuMap;
  
  /* [0] calo  [1] fat  [2] carbs  [3] protein */
  public ArrayList<RedBlackTree<Item>> trees = new ArrayList<>(4);
  private double[] fixMax = new double[4];  // max values from database
  private double[] fixMin = new double[4];  // min values from database
  private double[] usrMax = new double[4];  // upper bound from user
  private double[] usrMin = new double[4];  // lower bound from user
  
  private List<Item> database = new LinkedList<>();  // store all items when initializing
  private List<Item> result = new ArrayList<>();     // store selected items for user
  private final int MENUSIZE;                        // size of menu for user
  private int nutrType = -1;                         // current nutrition type
  
  
  /** constructor with args: a list of Items */
  public CalorieWatchBackend(List<Item> itemList) {
    if (itemList == null) 
      throw new IllegalArgumentException("error: null args");
    
    MENUSIZE = itemList.size();
    database = itemList;
    menuMap = new HashMap<>(database.size());
    
    // proprocessing fixMin
    IntStream.range(0, 4).forEach(i -> fixMin[i] = Integer.MAX_VALUE);
    
    // create new RedBlackTrees
    Stream.of(Nutr.values()).forEach(nutr -> {
      trees.add(new RedBlackTree<Item>(
          (c1, c2) -> (c1.get(nutr)).compareTo(c2.get(nutr))));  // define comparator
    });
    
    // load data
    database.stream().forEach(item -> {
      // put(name, item) into HashMap 
      menuMap.put(item.getName(), item);
      
      Stream.of(Nutr.values()).forEach(nutr -> {
        trees.get(nutr.ordinal()).insert(item); // insert item into each RedBlackTree
        initializeRanges(nutr, item);           // trace min/max values
      });
    });
    
    // set usrMin/msrMax to fixMin/fixMax
    resetRanges();
  }
  
  /**
   * updates the min/max values of each nutrition in database
   * @param nutr specifies a nutrition
   * @param item specifies an item
   */
  private void initializeRanges(Nutr nutr, Item item) {
    int i = nutr.ordinal();
    fixMin[i] = Math.min(fixMin[i], item.get(nutr));
    fixMax[i] = Math.max(fixMax[i], item.get(nutr));
  }
  
  /**
   * this methods reset user's input to the original states
   */
  public void resetRanges() {
    IntStream.range(0, 4).forEach(i -> {
      usrMax[i] = fixMax[i];
      usrMin[i] = fixMin[i];
    });
  }
  
  /**
   * @param nutr specifies a nutrition
   * @return the min value of that nutrition in database
   */
  public double getMin(Nutr nutr) {
    return fixMin[nutr.ordinal()];
  }
  
  /**
   * @param nutr specifies a nutrition
   * @return the max value of that nutrition in database
   */
  public double getMax(Nutr nutr) {
    return fixMax[nutr.ordinal()];
  }
  
  /**
   * @param nutr user's selected nutrition
   * @param min  lower bound of user's searching range 
   * @param max  upper bound of user's searching range
   */
  public void setRanges(Nutr nutr, double min, double max) {
    nutrType = nutr.ordinal();
    usrMin[nutrType] = min;
    usrMax[nutrType] = max;
  }
  
  /**
   * generate a list of menu that matches user's input
   */
  public void generateMenu() {
    int i = nutrType;
    Item min = createDummyItem(usrMin[i]);
    Item max = createDummyItem(usrMax[i]);
    result = trees.get(i).getRange(min, max);
  }
  
  /** create a dummy item holds a min/max value */
  private Item createDummyItem(double value) {
    return new Item("*", value, value, value, value);
  }
  
  /**
   * @return the menu's size
   */
  public int getMenuSize() {
    return result.size();
  }
  
  /**
   * To get the whole list, front end should call getSelectedMenu(0);
   * @return a sublist of selected items 
   */
  public List<Item> getSelectedMenu(int start) {
    int from = Math.min(start,            result.size());
    int to   = Math.min(start + MENUSIZE, result.size());
    return result.subList(from, to);
  }
    
  /**
   * @param name The name of an item in the menu
   * @return The first MenuItemInterface with a matching name. 
   *         If none exist, this method returns null
   */
  public Item getSpecificMenuItem(String name) {
    return menuMap.containsKey(name) ? 
           menuMap.get(name) : null;
  }
}
