// --== CS400 File Header Information ==--
// Name: Raea Freund
// Email: rfreund2@wisc.edu
// Team: DF
// TA: Dan Kiel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MealReader implements MealDataReaderInterface {

	// list containing meal objects
	private LinkedList<Item> mealList;

	/*
	 * Constructor calls readDataSet() to construct mealList. For this application,
	 * the menu string will be: "ians_menu_table.txt"
	 */
	public MealReader(String menu) {
		mealList = readDataSet(menu);
	}

	/*
	 * Returns meal list
	 */
	public LinkedList<Item> getMealList() {
		return mealList;
	}

	/*
	 * Reads a text file to construct an html string that is parsed into various
	 * fields such as menu name, calories, carbs, etc. These fields are used to
	 * create and return a list of menu item objects.
	 */
	public LinkedList<Item> readDataSet(String menu) {
		try {
			// scan in provided menu
			Scanner scanner = new Scanner(new File(menu));
			String menuString = scanner.nextLine();
			scanner.close();
			Document doc = Jsoup.parse(menuString);

			Elements titles = doc.getElementsByClass("text-right"); // gets name of meal
			Elements grams = doc.select("td"); // gets number of grams in each category

			// lists to help make meal object
			LinkedList<String> mealName = new LinkedList<String>(); // list to store meal names
			LinkedList<String> mealGrams = new LinkedList<String>(); // list to store grams
			// list of meal objects to be returned
			LinkedList<Item> mealList = new LinkedList<>();

			// put each name in list mealName
			for (Element paragraph : titles) {
				mealName.add(paragraph.text());
			}

			// put each gram in list mealGrams
			for (Element paragraph1 : grams) {
				if (!paragraph1.hasText()) {
					mealGrams.add("0.0g");
				} else {
					mealGrams.add(paragraph1.text());
				}
			}

			// insert new meal objects in to list
			int countName = 0;
			int countGrams = 0;
			while (countName < mealName.size()) {
				mealList.add(new Item(mealName.get(countName++),
					  extractGramsValue(mealGrams.get(countGrams)),
            extractGramsValue(mealGrams.get(countGrams + 1)),
            extractGramsValue(mealGrams.get(countGrams + 3)),
            extractGramsValue(mealGrams.get(countGrams + 4))));
				countGrams = countGrams + 10;
			}

			return mealList;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

	private double extractGramsValue(String str){
	    String trimmed = (str.charAt(str.length()-1) == 'g') ? str.substring(0, str.length()-2) : str;
	    return (trimmed.length() > 0) ? Double.parseDouble(trimmed) : 0.0;
  }

	/*
	 * Prints out the list of meal objects and corresponding data. Since only
	 * calories, carbs, fats, and protein is used for this application, only that
	 * info will be printed.
	 */
	/*
	public void displayList() {
		System.out.println("***Menu Items and corresponding nutritional values for given menu***");
		System.out.println("    (Number of Fats, Carbs, and Proteins are displayed in grams)    \n");
		for (int i = 0; i < mealList.size(); i++) {
			System.out.println((i + 1) + ") Meal Name:          " + mealList.get(i).getName());
			System.out.println("    Number of Calories: " + mealList.get(i).getCals());
			System.out.println("    Number of Fats:     " + mealList.get(i).getFats());
			System.out.println("    Number of Carbs:    " + mealList.get(i).getCarbs());
			System.out.println("    Number of Proteins: " + mealList.get(i).getProtein());
			System.out.println("");
		}
		System.out.println("End of menu.");
	}
	*/
}
