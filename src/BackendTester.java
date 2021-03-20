// --== CS400 File Header Information ==--
// Name: Xin Cai
// Email: xcai72@wisc.edu
// Team: DF blue
// Role: Backend developer
// TA: Dan
// Lecturer: Gary Dahl
// Notes to Grader: None

import org.junit.*;
import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.List;

public class BackendTester {

  @Before
  public void setUp() throws Exception { }
  
  @Test
  public void testGetMin() {
    CalorieWatchBackend backend = new CalorieWatchBackend(readData());
    double epsilon = 0.00001;
    
    double diffCalorie = backend.getMin(Nutr.CALORIE) - 64.0;
    assertTrue(Math.abs(diffCalorie) < epsilon);
    
    double diffFat = backend.getMin(Nutr.FAT) - 0.0;
    assertTrue(Math.abs(diffFat) < epsilon);
  }
  
  @Test
  public void testGetMax() {
    CalorieWatchBackend backend = new CalorieWatchBackend(readData());
    double epsilon = 0.00001;
    
    double diffCarb = backend.getMax(Nutr.CARB) - 120.7;
    assertTrue(Math.abs(diffCarb) < epsilon);
    
    double diffFat = backend.getMax(Nutr.FAT) - 0.5;
    assertTrue(Math.abs(diffFat) < epsilon);
  }
  
  @Test
  public void testGetMenuSize() {
    CalorieWatchBackend backend = new CalorieWatchBackend(readData());
    backend.setRanges(Nutr.CALORIE, 60.0, 65.0);
    assertEquals(backend.getMenuSize(), 0);
    
    backend.generateMenu();
    assertEquals(backend.getMenuSize(), 3);
  }
  
  @Test
  public void testGetSpecificMenuItem() {
    CalorieWatchBackend backend = new CalorieWatchBackend(readData());
    
    assertTrue(backend.getSpecificMenuItem("Bread").getName().equals("Bread"));
    assertNull(backend.getSpecificMenuItem("Super Big Biscuit"));
  }
  
  @Test
  public void testGetSelectedMenu() {
    CalorieWatchBackend backend = new CalorieWatchBackend(readData());
    
    // set user's input as protein 5-11
    backend.setRanges(Nutr.PROTEIN, 5, 11);
    backend.generateMenu();
    
    assertEquals(backend.getSelectedMenu(0).size(), 6);  // check size
    
    List<Item> retMenu = backend.getSelectedMenu(0);
    for (int i = 0; i < retMenu.size(); ++i) {
      // check items
      assertTrue(retMenu.get(i).getName().equals("food" + (i + 5)));
    }
  }
  
  @Test
  public void testgetClosetMenu() {
    CalorieWatchBackend backend = new CalorieWatchBackend(readData());
    
    // In the given data, there are:
    // 2 Items with FAT = 0.3
    // 1 Item  with FAT = 0.2
    // 5 Items with FAT = 0.4
    
    /* case 1: key = 0.3
       explanation: since there are only 2 items(FAT = 0.3)
           and 0.3 - 0.2 = 0.4 - 0.3, so all items(FAT = 0.2 | 0.4)
           should be added too. */
    assertEquals(backend.getClosestMenu(Nutr.FAT, 0.3).size(), 8);
    
    /* case 2: key = 0.28
    explanation: the most closest are items(FAT = 0.3)
                 the next closest is item(FAT = 0.2)
                 list is enough, done. */
    assertEquals(backend.getClosestMenu(Nutr.FAT, 0.28).size(), 3);
    
    /* case 3: key = 0.31
    explanation: the most closest are items(FAT = 0.3)
                 the next closest are items(FAT = 0.4)
                 list is enough, done. */
    assertEquals(backend.getClosestMenu(Nutr.FAT, 0.31).size(), 7);
  }
  
  
  /** dummy data of 16 items for testing */
  public List<Item> readData() {
    Item[] foods = new Item[16];
    foods[0] = new Item("Bread",       427,  0.3, 120.7,  14.3);
    foods[1] = new Item("Panko",        70,  0.4, 118.7,     3);
    foods[2] = new Item("Gluten free",  70,  0.1, 115.7,     1);
    foods[3] = new Item("Black",        65,  0.3, 110.7,   2.2);
    foods[4] = new Item("Italian",      70,  0.4, 105.7,     2);
    
    foods[5] = new Item("food5",       440,  0.1, 102.7,   5.2);
    foods[6] = new Item("food6",        70,  0.4,  99.7,   6.2);
    foods[7] = new Item("food7",        70,    0,  96.7,   7.2);
    foods[8] = new Item("food8",        64,  0.4,  95.7,   8.2);
    foods[9] = new Item("food9",        70,  0.1,  91.7,   9.2);
    
    foods[10] = new Item("food10",     406,  0.5,  87.7,  10.2);
    foods[11] = new Item("food11",      70,  0.5,  84.7,  11.2);
    foods[12] = new Item("food12",      70,    0,  76.7,  12.2);
    foods[13] = new Item("food13",      65,  0.2,  72.7,  13.2);
    foods[14] = new Item("food14",      70,  0.4,  64.7,  14.2);
    foods[15] = new Item("food15",      70,  0.1,  61.7,  15.2);
    
    return new LinkedList<Item>(java.util.Arrays.asList(foods));
  }
  
}
