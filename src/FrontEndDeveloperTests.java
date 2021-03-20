// --== CS400 File Header Information ==--
// Name: Humza Ayub
// Email: hayub@wisc.edu 
// Team: Blue 
// Role: Frontend Developer
// TA: Daniel Kiel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FrontEndDeveloperTests {

	private final List<Item> dummyMenuList = new LinkedList<>(Arrays.asList(new Item("Hamburger", 50, 50, 50, 50),
																	  new Item("Hot Dog", 50,100,100,100),
																	  new Item("Donut", 150,150,150,150),
																	  new Item("French Fries", 50, 100, 150, 75)));

	/*
	 * This test will check if the application can switch between different modes
	 * and exit the application. If the application correctly switches between all
	 * of the different modes and exits the application it passes, fails otherwise.
	 * If it does not exit the application then the test will run indefinitely.
	 */
	@Test
	public void testDifferentModes() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;

		try {
			// set the input stream to our input of switching between all the modes then exiting program
			// go between all modes and then exit
			String input = "1" + System.lineSeparator() + "x" + System.lineSeparator() + "2"
							+ System.lineSeparator() + "x" + System.lineSeparator() + "3" + System.lineSeparator() + "x"
							+ System.lineSeparator() + "4" + System.lineSeparator() + "x" + System.lineSeparator() + "x";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			// set the output to the stream captor to read the output of the front end
			System.setOut(new PrintStream(outputStreamCaptor));
			// instantiate when front end is implemented
			Frontend.run(new CalorieWatchBackend(this.dummyMenuList));
			System.setOut(standardOut);
			System.setIn(standardIn);
			String appOutput = outputStreamCaptor.toString();
			// Check if it went through all the modes
			Assertions.assertTrue(appOutput.contains("CALORIES MODE")); Assertions.assertTrue(appOutput.contains("FAT MODE"));
			Assertions.assertTrue(appOutput.contains("CARBS MODE")); Assertions.assertTrue(appOutput.contains("PROTEIN MODE"));
		} catch (Exception e) {
			// make sure stdin and stdout are set correctly after we get exception in test
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			Assertions.fail("This test has failed.");
		}
	}

	/*
	 * This test will search for a calorie value and checks if it will return
	 * three exact matches. If it returns 3 exact matches, then the test passes,
	 * fails otherwise.
	 * */
	@Test
	public void testExactCalorieSearch() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;

		try {
			// set the input stream to our input of switching between all the modes then exiting program
			// check 50 kcal
			String input = "1" + System.lineSeparator() + "50" + System.lineSeparator() + "x"
					+ System.lineSeparator() + "x";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			// set the output to the stream captor to read the output of the front end
			System.setOut(new PrintStream(outputStreamCaptor));
			// instantiate when front end is implemented
			Frontend.run(new CalorieWatchBackend(this.dummyMenuList));
			System.setOut(standardOut);
			System.setIn(standardIn);
			String appOutput = outputStreamCaptor.toString();
			// Check output should return Hot Dog, Hamburger, and French Fries, but not Donut
			Assertions.assertTrue(appOutput.contains("Hot Dog")); Assertions.assertTrue(appOutput.contains("Hamburger"));
			Assertions.assertTrue(appOutput.contains("French Fries")); Assertions.assertFalse(appOutput.contains("Donut"));
		} catch (Exception e) {
			// make sure stdin and stdout are set correctly after we get exception in test
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			Assertions.fail("This test has failed.");
		}
	}

	/*
	 * This test will search for a fat value and checks
	 * it it returns 3 matches (a mix of exact and close match).
	 * If it correctly returns the correct mix of exact and close
	 * matches then the test passes, fails otherwise.
	 */
	@Test
	public void testMixedFatSearch() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;

		try {
			// set the input stream to our input of switching between all the modes then exiting program
			// Check 50g of fat
			String input = "2" + System.lineSeparator() + "50" + System.lineSeparator() + "x"
					+ System.lineSeparator() + "x";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			// set the output to the stream captor to read the output of the front end
			System.setOut(new PrintStream(outputStreamCaptor));
			// instantiate when front end is implemented
			Frontend.run(new CalorieWatchBackend(this.dummyMenuList));
			System.setOut(standardOut);
			System.setIn(standardIn);
			String appOutput = outputStreamCaptor.toString();
			// Check output should return Hot Dog, Hamburger, and French Fries, but not Donut
			Assertions.assertTrue(appOutput.contains("Hot Dog")); Assertions.assertTrue(appOutput.contains("Hamburger"));
			Assertions.assertTrue(appOutput.contains("French Fries")); Assertions.assertFalse(appOutput.contains("Donut"));
		} catch (Exception e) {
			// make sure stdin and stdout are set correctly after we get exception in test
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			Assertions.fail("This test has failed.");
		}
	}
	
	/*
	 * This test will search for a carbs value and checks if it will return at least
	 * 3 close matches. If it correctly returns the 3 closest values then
	 * the test passes, fails otherwise.
	 */
	@Test
	public void testCloseCarbsSearch() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;

		try {
			// set the input stream to our input of switching between all the modes then exiting program
			// Check 125g of carbs
			String input = "3" + System.lineSeparator() + "125" + System.lineSeparator() + "x"
					+ System.lineSeparator() + "x";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			// set the output to the stream captor to read the output of the front end
			System.setOut(new PrintStream(outputStreamCaptor));
			// instantiate when front end is implemented
			Frontend.run(new CalorieWatchBackend(this.dummyMenuList));
			System.setOut(standardOut);
			System.setIn(standardIn);
			String appOutput = outputStreamCaptor.toString();
			// Check output should return Hot Dog, French Fries, and Donut, but not Hamburger
			// since closest values are 100 and 150 with 2 items containing 100g of carbs
			Assertions.assertTrue(appOutput.contains("Hot Dog")); Assertions.assertFalse(appOutput.contains("Hamburger"));
			Assertions.assertTrue(appOutput.contains("French Fries")); Assertions.assertTrue(appOutput.contains("Donut"));
		} catch (Exception e) {
			// make sure stdin and stdout are set correctly after we get exception in test
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			Assertions.fail("This test has failed.");
		}
	}

	/*
	 * This test will search for a range of values that
	 * include the entire tree (a full order traversal).
	 * If it correctly returns the entire tree, then the
	 * test passes, fails otherwise.
	 */
	@Test
	public void testFullRange() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;

		try {
			// set the input stream to our input of switching between all the modes then exiting program
			// Check full range of proteins
			String input = "4" + System.lineSeparator() + "0-150" + System.lineSeparator() + "x"
					+ System.lineSeparator() + "x";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			// set the output to the stream captor to read the output of the front end
			System.setOut(new PrintStream(outputStreamCaptor));
			// instantiate when front end is implemented
			Frontend.run(new CalorieWatchBackend(this.dummyMenuList));
			System.setOut(standardOut);
			System.setIn(standardIn);
			String appOutput = outputStreamCaptor.toString();
			// Check output should return all items in the list
			Assertions.assertTrue(appOutput.contains("Hot Dog")); Assertions.assertTrue(appOutput.contains("Hamburger"));
			Assertions.assertTrue(appOutput.contains("French Fries")); Assertions.assertTrue(appOutput.contains("Donut"));
		} catch (Exception e) {
			// make sure stdin and stdout are set correctly after we get exception in test
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			Assertions.fail("This test has failed.");
		}
	}

	/*
	 * This test will search for a range of values that
	 * includes some values within the tree. If it
	 * correctly returns values within the range
	 * test passes, fails otherwise.
	 */
	@Test
	public void testRange() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;

		try {
			// set the input stream to our input of switching between all the modes then exiting program
			// Check 60g to 120g of protein
			String input = "4" + System.lineSeparator() + "60-120" + System.lineSeparator() + "x"
					+ System.lineSeparator() + "x";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			// set the output to the stream captor to read the output of the front end
			System.setOut(new PrintStream(outputStreamCaptor));
			// instantiate when front end is implemented
			Frontend.run(new CalorieWatchBackend(this.dummyMenuList));
			System.setOut(standardOut);
			System.setIn(standardIn);
			String appOutput = outputStreamCaptor.toString();
			// Check output should return Hot Dog and French Fries, but not Hamburger or Donut
			Assertions.assertTrue(appOutput.contains("Hot Dog")); Assertions.assertFalse(appOutput.contains("Hamburger"));
			Assertions.assertTrue(appOutput.contains("French Fries")); Assertions.assertFalse(appOutput.contains("Donut"));
		} catch (Exception e) {
			// make sure stdin and stdout are set correctly after we get exception in test
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			Assertions.fail("This test has failed.");
		}
	}

	/*
	 * This test will search for a range that includes no
	 * values within tree. If it correctly returns the
	 * error message, then the test passes, fails otherwise.
	 */
	@Test
	public void testBadRange() {
		PrintStream standardOut = System.out;
		InputStream standardIn = System.in;

		try {
			// set the input stream to our input of switching between all the modes then exiting program
			// Check 5g to 10g of protein
			String input = "4" + System.lineSeparator() + "5-10" + System.lineSeparator() + "x"
					+ System.lineSeparator() + "x";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			// set the output to the stream captor to read the output of the front end
			System.setOut(new PrintStream(outputStreamCaptor));
			// instantiate when front end is implemented
			Frontend.run(new CalorieWatchBackend(this.dummyMenuList));
			System.setOut(standardOut);
			System.setIn(standardIn);
			String appOutput = outputStreamCaptor.toString();
			// Should return none of the items and should return the error message
			Assertions.assertFalse(appOutput.contains("Hot Dog")); Assertions.assertFalse(appOutput.contains("Hamburger"));
			Assertions.assertFalse(appOutput.contains("French Fries")); Assertions.assertFalse(appOutput.contains("Donut"));
			Assertions.assertTrue(appOutput.contains("No menu items within that range!"));
		} catch (Exception e) {
			// make sure stdin and stdout are set correctly after we get exception in test
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			Assertions.fail("This test has failed.");
		}
	}
}

