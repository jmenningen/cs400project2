// --== CS400 File Header Information ==--
// Name: Xin Cai
// Email: xcai72@wisc.edu
// Team: DF blue
// Role: Backend developer
// TA: Dan
// Lecturer: Gary Dahl
// Notes to Grader: None

/** 
 * Dummy Item used for backend. 
 */
public class Item implements Comparable<Item> {

  private String name;
  private Double calories;
  private Double fat;
  private Double carbs;
  private Double protein;
    
  public Item(String name, double calories, double fat, double carbs, double protein) {
    this.name = name;
    this.calories = calories;
    this.fat = fat;
    this.carbs = carbs;
    this.protein = protein;
  }
  
  public String getName() {
    return name;
  }
  
  /** return values corresponding with args */
  public Double get(Nutr nutr) {
    switch (nutr) {
      case CALORIE: return calories;
      case FAT    : return fat;
      case CARB   : return carbs;
      case PROTEIN: return protein;
      default     : return -1.0;
    }
  }
  
  @Override
  public String toString() {
    return name + ": cal(" + calories + 
                 "), fat(" + fat +
               "), carbs(" + carbs +
             "), protein(" + protein + ")";
  }
  
  @Override
  public int compareTo(Item item) {
    return this.getName().compareTo(item.getName());
  }
}
