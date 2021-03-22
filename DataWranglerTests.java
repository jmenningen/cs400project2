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
		readerToTest.displayList();
	}

	/*
	 * This tests whether the meal interface methods return the correct values. It fails if the
	 * methods do not function correctly or if an exception occurs while reading in the meals.
	 */
	@Test
	public void testReaderInternalMethods() {
		
		boolean passed = true; // name, cals, fat, carbs, protein
		Item item1 = new Item("Cheese pizza", 305, 9.0, 33.4, 4.3);
	
		if (item1.getName() != "Cheese pizza") {
			System.out.println("Incorrect name.");
			passed = false;
		}
		if (item1.get(Nutr.CALORIE) != 305) {
			System.out.println("Incorrect calorie count.");
			passed = false;
		} 	
		if (item1.get(Nutr.FAT) != 9.0) {
			System.out.println("Incorrect fat count.");
			passed = false;
		} 	
		if (item1.get(Nutr.CARB) != 33.4) {
			System.out.println("Incorrect carb count.");
			passed = false;
		} 	 
		if (item1.get(Nutr.PROTEIN) != 4.3) {
			System.out.println("Incorrect protein count.");
			passed = false;
		} 	
		assertEquals(true, passed, "Test reader internal methods failed.");
	
	}
	
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
	 * This test reads in several meals and tests whether the list of meals returned is in the expected order.
	 * It fails if it is not in order or if an exception occurs while reading in the meals.
	 */
	@Test
	public void testReaderMealOrder() {
		MealReader readerToTest = new MealReader("ians_menu_table.txt");
		LinkedList<Item> mealList = readerToTest.getMealList();
		
		LinkedList<Item> testList = new LinkedList<Item>();
		testList.add(new Item("Bread Crumbs", 427.0, 5.7, 77.7, 14.4));
		testList.add(new Item("Panko Breadcrumbs Whole Wheat Style", 70.0, 0.5, 14.0, 3.0));
		testList.add(new Item("Gluten Free Panko Breadcrumbs", 70.0, 0.0, 1.0, 0.0));
		testList.add(new Item("Bread", 69.0, .8, 13.1, 1.9));

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

}//end testing



