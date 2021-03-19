// --== CS400 File Header Information ==--
// Name: Raea Freund
// Email: rfreund2@wisc.edu
// Team: DF
// TA: Dan Kiel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Testing class for MealReader.java and associated files.
 */
public class DataWranglerTests {

	/*
	 * This test reads in 52 meals and tests whether the list of meals returned is of size 52. It 
	 * fails if the size is not 52 or if an exception occurs while reading in the meals.
	 */
	@Test
	public void testReaderNumberOfMeals() {
		MealReader readerToTest = new MealReader("ians_menu_table.txt");
		try {
			LinkedList<Item> mealList = readerToTest.getMealList();
			assertEquals(52, mealList.size(), "Test reader number of meals failed.");
		} catch (Exception e) {
			e.printStackTrace();
			// test failed
			fail("Unexpected exception thrown!");
		}
		
	}

	/*
	 * This test reads in 3 meals and tests whether the list of meals returned contains the correct meals. It 
	 * fails if the meal is not contained or if an exception occurs while reading in the meals.
	 */
	@Test
	public void testReaderMealNames() {
		MealReader readerToTest = new MealReader("ians_menu_table.txt");
		LinkedList<Item> mealList = readerToTest.getMealList();
		
		String name1 = "Bread Crumbs";
		String name2 = "Gluten Free Panko Breadcrumbs";
		String name3 = "Bread";
		
		assertEquals(name1, mealList.get(0).getName(), "Test reader meal names failed.");
		assertEquals(name2, mealList.get(2).getName(), "Test reader meal names failed.");
		assertEquals(name3, mealList.get(3).getName(), "Test reader meal names failed.");
	
	}

	/*
	 * This tests whether the meal interface methods return the correct values. It fails if the
	 * methods do not function correctly or if an exception occurs while reading in the meals.
	 */
	/*
	@Test
	public void testReaderInternalMethods() {
		
		boolean passed = true;
		Meal meal1 = new Meal("Cheese pizza", "557", "32.0g", "12.0g", "7345.4g", "3.0g", "3.33g", "3.56g", "6.0g", "5.0g", "12.0g");
	
		if (meal1.getName() != "Cheese pizza") {
			System.out.println("Incorrect name.");
			passed = false;
		}
		if (meal1.getCals() != 557) {
			System.out.println("Incorrect calorie count.");
			passed = false;
		} 	
		if (meal1.getFats() != 32.0) {
			System.out.println("Incorrect fat count.");
			passed = false;
		} 	
		if (meal1.getSugar() != 12.0) {
			System.out.println("Incorrect sugar count.");
			passed = false;
		} 	 
		if (meal1.getProtein() != 3.0) {
			System.out.println("Incorrect protein count.");
			passed = false;
		} 	
		assertEquals(true, passed, "Test reader internal methods failed.");
	}
	*/

	/*
	 * This tests whether the menu reader works with other menus, not just "ians_menu_table.txt"
	 * Other menus can be tested at the website https://secretmenus.com
	 */
	@Test
	public void testOtherMenu() {
		MealReader readerToTest = new MealReader("taco_bell.txt");
		LinkedList<Item> mealList = readerToTest.getMealList();
		//readerToTest.displayList(); // --> displays correctly
		assertEquals(87, mealList.size(), "Incorrect size for taco bell menu.");
	}	
	
	/*
	 * This test reads in 4 meals and tests whether the list of meals returned is in the expected order.
	 * It fails if it is not in order or if an exception occurs while reading in the meals.
	 */
	/*
	@Test
	public void testReaderMealOrder() {
		MealReader readerToTest = new MealReader("ians_menu_table.txt");
		LinkedList<Item> mealList = readerToTest.getMealList();
		
		LinkedList<Item> testList = new LinkedList<MealInterface>();
		testList.add(new Meal("Bread Crumbs", "427.0", "5.72g", "0.0g", "77.74g", "14.42g", "0.0g","0.0g","0.0g","0.0g","0.0g"));
		testList.add(new Meal("Panko Breadcrumbs � Whole Wheat Style", "70.0", "0.5g", "0.0g", "14.0g", "3.0g", "0.0g","0.0g","0.0g","0.0g","0.0g"));
		testList.add(new Meal("Gluten Free Panko Breadcrumbs", "70.0", "0.0g", "0.0g", "16.0g", "1.0g", "0.0g","0.0g","0.0g","0.0g","0.0g"));
		testList.add(new Meal("Bread", "70.0", "0.0g", "0.0g", "16.0g", "1.0g", "0.0g","0.0g","0.0g","0.0g","0.0g"));

		if (!mealList.get(0).getName().equals(testList.get(0).getName())) {
			fail("Meals are not in correct order.");
		}
		if (!mealList.get(2).getName().equals(testList.get(2).getName())) {
			fail("Meals are not in correct order.");
		}
		if (!mealList.get(3).getName().equals(testList.get(3).getName())) {
			fail("Meals are not in correct order.");
		}
		

	}
	*/

}//end testing

