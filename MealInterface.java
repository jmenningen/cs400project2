// --== CS400 File Header Information ==--
// Name: Raea Freund
// Email: rfreund2@wisc.edu
// Team: DF
// TA: Dan Kiel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

public interface MealInterface extends Comparable<MealInterface> {

	/*
	 * Returns the name of meal object
	 */
	public String getName();

	/*
	 * Returns the number of calories of meal object
	 */
	public float getCals();	

	/*
	 * Returns the number of fats of meal object
	 */
	public float getFats();
	
	/*
	 * Returns the number of sugars of meal object
	 */
	public float getSugar();
	
	/* 
	 * Returns the number of carbs of meal object
	 */
	public float getCarbs();

	/*
	 * Returns the number of proteins of meal object
	 */
	public float getProtein();
	
	/*
	 * Returns the number of saturated fats of meal object
	 */
	public float getSatFat();

	/*
	 * Returns the number of trans fats of meal object
	 */
	public float getTransFat();

	/*
	 * Returns the number of cholesterol of meal object
	 */
	public float getCholesterol();

	/*
	 * Returns the number of sodium of meal object
	 */
	public float getSodium();
	
	/*
	 * Returns the number of fiber of meal object
	 */
	public float getFiber();

} // end MealInterface
