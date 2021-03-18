// --== CS400 File Header Information ==--
// Name: Raea Freund
// Email: rfreund2@wisc.edu
// Team: DF
// TA: Dan Kiel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

/*
 * Meal object that contains various fields corresponding to nutritional information.
 * For this application, only the mealName, cals, fat, carbs, and protein fields will be used.
 */
public class Meal implements MealInterface {

	private String mealName;
	private float cals;
	private float fat;
	private float sugar;
	private float carbs;
	private float protein;
	private float satFat;
	private float transFat;
	private float cholesterol;
	private float sodium;
	private float fiber;

	/*
	 * Each field will be interpreted as grams. All the grams of each menu item from
	 * the html are passed in to this constructor, even though we are only using
	 * carbs, fats, and protein. All other fields will be 0g for this application.
	 * This project can therefore be generalized to all menus from the website
	 * https://secretmenus.com/ians/nutrition-info/#pizza, other than Ian's pizza.
	 */
	public Meal(String mealName, String cals, String fat, String sugar, String carbs, String protein, String satFat,
			String transFat, String cholesterol, String sodium, String fiber) {
		this.mealName = mealName;
		// substring removes the 'g' at the end.
		this.cals = Float.parseFloat(cals);
		this.fat = Float.parseFloat(fat.substring(0, fat.length() - 1));
		this.sugar = Float.parseFloat(sugar.substring(0, sugar.length() - 1));
		this.carbs = Float.parseFloat(carbs.substring(0, carbs.length() - 1));
		this.protein = Float.parseFloat(protein.substring(0, protein.length() - 1));
		this.satFat = Float.parseFloat(satFat.substring(0, satFat.length() - 1));
		this.transFat = Float.parseFloat(transFat.substring(0, transFat.length() - 1));
		this.cholesterol = Float.parseFloat(cholesterol.substring(0, cholesterol.length() - 1));
		this.sodium = Float.parseFloat(sodium.substring(0, sodium.length() - 1));
		this.fiber = Float.parseFloat(fiber.substring(0, fiber.length() - 1));
	}

	/*
	 * Compares two meal objects based on calories.
	 */
	@Override
	public int compareTo(MealInterface meal) throws NullPointerException{
		if (this.getCals() < meal.getCals()) {
			// return -1 if meal compared to passed in meal has less calories
			return -1;
		} else if (this.getCals() > meal.getCals()) {
			// return 1 if meal compared to passed in meal has more calories
			return 1;
		} else {
			// return 0 if cals are equal
			return 0;
		}
	}

	/*
	 * Returns the name of meal object
	 */
	@Override
	public String getName() {
		return mealName;
	}

	/*
	 * Returns the number of calories of meal object
	 */
	@Override
	public float getCals() {
		return cals;
	}

	/*
	 * Returns the number of fats of meal object
	 */
	@Override
	public float getFats() {
		return fat;
	}

	/*
	 * Returns the number of sugar of meal object
	 */
	@Override
	public float getSugar() {
		return sugar;
	}

	/*
	 * Returns the number of carbs of meal object
	 */
	@Override
	public float getCarbs() {
		return carbs;
	}

	/*
	 * Returns the number of protein of meal object
	 */
	@Override
	public float getProtein() {
		return protein;
	}

	/*
	 * Returns the number of saturated fats of meal object
	 */
	@Override
	public float getSatFat() {
		return satFat;
	}

	/*
	 * Returns the number of trans fats of meal object
	 */
	@Override
	public float getTransFat() {
		return transFat;
	}

	/*
	 * Returns the number of cholesterol of meal object
	 */
	@Override
	public float getCholesterol() {
		return cholesterol;
	}

	/*
	 * Returns the number of sodium of meal object
	 */
	@Override
	public float getSodium() {
		return sodium;
	}

	/*
	 * Returns the number of fiber of meal object
	 */
	@Override
	public float getFiber() {
		return fiber;
	}

}
