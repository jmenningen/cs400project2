// --== CS400 File Header Information ==--
// Name: Humza Ayub
// Email: hayub@wisc.edu 
// Team: Blue 
// Role: Frontend Developer
// TA: Dan Kiel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

// TODO finish tests

public class FrontEndDeveloperTests {

	private List<Item> dummyMenuList = new LinkedList<>(Arrays.asList(new Item("Hamburger", 50, 50, 50, 50),
																	  new Item("Hot Dog", 100,100,100,100),
																	  new Item("Donut", 150,150,150,150),
																	  new Item("French Fries", 50, 100, 150, 75)));
	private static boolean setUpDone = false;

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
			String input = "1" + System.lineSeparator() + "x" + System.lineSeparator() + "2"
							+ System.lineSeparator() + "x" + System.lineSeparator() + "3" + System.lineSeparator() + "x"
							+ System.lineSeparator() + "4" + System.lineSeparator() + "x" + System.lineSeparator() + "x";
			InputStream inputStreamSimulator = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStreamSimulator);
			ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
			// set the output to the stream captor to read the output of the front end
			System.setOut(new PrintStream(outputStreamCaptor));
			// instantiate when front end is implemented
			Object frontend = new Frontend();
			((Frontend)frontend).run(new CalorieWatchBackend(this.dummyMenuList));
			System.setOut(standardOut);
			System.setIn(standardIn);
			// if we made it here test passed!
		} catch (Exception e) {
			// make sure stdin and stdout are set correctly after we get exception in test
			System.setOut(standardOut);
			System.setIn(standardIn);
			e.printStackTrace();
			fail("This test has failed.");
		}
	}

	/*
	 * This test will search for a calorie value and checks if it will return 
	 * three exact matches. If it returns 3 exact matches, then the test passes,
	 * fails otherwise.
	 * */
	@Test
	public void testExactCalorieSearch() {fail("This test has not been implemented.");}

	/*
	 * This test will search for a calorie value and checks
	 * it it returns 3 matches (a mix of exact and close match).
	 * If it correctly returns the correct mix of exact and close
	 * matches then the test passes, fails otherwise.
	 */
	@Test
	public void testMixedCalorieSearch() {fail("This test has not been implemented.");}
	
	/*
	 * This test will search fora calorie value and checks if it will return
	 * 3 close matches. If it correctly returns the 3 closest values then
	 * the test passes, fails otherwise.
	 */
	@Test
	public void testCloseCalorieSearch() {fail("This test has not been implemented.");}

	/*
	 * This test will search for a range of values that
	 * include the entire tree (a full order traversal).
	 * If it correctly returns the entire tree, then the
	 * test passes, fails otherwise.
	 */
	@Test
	public void testFullRange() {fail("This test has not been implemented.");}

	/*
	 * This test will search for a range of values that
	 * includes some values within the tree. If it
	 * correctly returns values within the range
	 * test passes, fails otherwise.
	 */
	@Test
	public void testRange() {fail("This test has not been implemented.");}

	/*
	 * This test will search for a range that includes no
	 * values within tree. If it correctly returns the
	 * error message, then the test pases, fails otherwise.
	 */
	@Test
	public void testBadRange() {fail("This test has not been implemented.");}
}

