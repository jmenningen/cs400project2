// --== CS400 File Header Information ==--
// Name: Humza Ayub
// Email: hayub@wisc.edu
// Team: Blue
// Role: Frontend Developer
// TA: Daniel Kiel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.FileNotFoundException;
import java.util.*;

public class Frontend {

    private BackendInterface backend = null;
    private final String[][] nutritionMenu;
    private final int maxLength = 50;
    private final String[] units = {"kcal", "g", "g", "g"};
    private final String[] nutritionCategories = {"CALORIES", "FAT", "CARBS", "PROTEIN"};

    /**
     * This is the main method and automatically runs the application when the file is ran
     * @param args
     */
    public static void main(String[] args) {
        try {

            MealReader mealReader = new MealReader("ians_menu_table.txt");
            run(new CalorieWatchBackend(mealReader.getMealList()));
        }  catch (FileNotFoundException e1) {
            System.out.println("File was not found!");
        } catch (IllegalArgumentException e2) {
            System.out.println("File not read correctly.");
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    /**
     * This method is responsible for running the entire application that the user interacts with in order to
     * keep track of any nutritional values they might be interested in
     *
     * @throws Exception any exception encountered will trigger a printStackTrace()
     */
    public static void run(BackendInterface backend) throws Exception {
        // Initialize the backend and frontend
        Frontend frontend = new Frontend();
        frontend.backend = backend;

        // Initialize the application by welcoming the user and showing the interactive menu
        frontend.showWelcomeMessage();
        frontend.showBaseMode();

        // To read user inputs
        Scanner scnr = new Scanner(System.in);
        String cmd = null; // command user enters in

        // Loop that keeps application running
        while(true) {
            System.out.print("\nEnter command here: ");
            if (scnr.hasNextLine())
                cmd = scnr.nextLine().trim().toLowerCase(Locale.ROOT);

            // Try-catch block to read what the user inputs
            try {
                if (cmd == null || cmd.isEmpty()) {
                    frontend.showErrorMessage();
                } else if (cmd.equals("x")) { // x means exit the application by breaking the loop
                    frontend.showExitMessage();
                    break;
                } else if (cmd.equals("h")) { // help command for the user to display the menu again
                    frontend.showBaseMode();
                } else {
                    // Try-catch block to see if user entered in a valid number
                    try {
                        // if integer is valid show them the respective selection mode
                        // after they exit selection mode greet them with base mode again
                        if (Integer.parseInt(cmd) >= 1 && Integer.parseInt(cmd) <= frontend.nutritionMenu.length) {
                            frontend.selectionMode(Integer.parseInt(cmd), scnr);
                            frontend.showBaseMode();
                        } else {
                            frontend.showErrorMessage();
                        }
                    } catch (NumberFormatException e1) { // parseInt() throws NumberFormatException if there is no int
                        frontend.showErrorMessage();
                    }

                }
            } catch (Exception e1) {
                throw new Exception();
            }
        }
    }

    /**
     * Constructor that builds the nutrition menu for use in the application
     */
    public Frontend() {
        this.nutritionMenu = new String[this.nutritionCategories.length][2];
        for (int i = 0; i < nutritionMenu.length; i++) {
            this.nutritionMenu[i][0] = (i < 9) ? ("0" + (i + 1) + ".") : ((i+1) + ".");
            this.nutritionMenu[i][1] = this.nutritionCategories[i] + " ".repeat(12 - this.nutritionCategories[i].length());
        }
    }

    /**
     * This method is the 'default' mode of the application and serves as the main menu of the application
     * that the user will interact with to reach their desired nutrition category
     */
    private void showBaseMode() {
        this.showNutritionMenu();
        System.out.println("\n" + "-".repeat(this.maxLength));
        String message = "[ BASE MODE";
        System.out.println(message + " ".repeat(this.maxLength - message.length() - 1) + "]");
        message = "[ - Enter an integer to select category";
        System.out.println(message + " ".repeat(this.maxLength - message.length() - 1) + "]");
        message = "[ - Enter 'h' to show this message again";
        System.out.println(message + " ".repeat(this.maxLength - message.length() - 1) + "]");
        message = "[ - Enter 'x' to exit";
        System.out.println(message + " ".repeat(this.maxLength - message.length() - 1) + "]");
    }

    /**
     * Simple method that displays a welcome message to the user on startup of the application
     */
    private void showWelcomeMessage() {
        System.out.println("=".repeat(this.maxLength));
        String message = "WELCOME TO CALORIE WATCH";
        System.out.println( " ".repeat((this.maxLength - message.length())/2) + message + " ".repeat((this.maxLength - message.length())/2));
        message = "This application lets you watch";
        System.out.println( " ".repeat((this.maxLength - message.length())/2) + message + " ".repeat((this.maxLength - message.length())/2));
        message = "for different types of nutrition.";
        System.out.println( " ".repeat((this.maxLength - message.length())/2) + message + " ".repeat((this.maxLength - message.length())/2));
        message = "You can search for foods with a";
        System.out.println( " ".repeat((this.maxLength - message.length())/2) + message + " ".repeat((this.maxLength - message.length())/2));
        message = "specific value of nutrition you";
        System.out.println( " ".repeat((this.maxLength - message.length())/2) + message + " ".repeat((this.maxLength - message.length())/2));
        message = "want and it will return the three";
        System.out.println( " ".repeat((this.maxLength - message.length())/2) + message + " ".repeat((this.maxLength - message.length())/2));
        message = "closest foods. You can also search";
        System.out.println( " ".repeat((this.maxLength - message.length())/2) + message + " ".repeat((this.maxLength - message.length())/2));
        message = "a specific range that you want.";
        System.out.println( " ".repeat((this.maxLength - message.length())/2) + message + " ".repeat((this.maxLength - message.length())/2));
        System.out.println("=".repeat(this.maxLength));
    }

    /**
     * Simple method that displays an exit message when the user exits the application
     */
    private void showExitMessage() {
        System.out.println("=".repeat(this.maxLength));
        String message = "THANKS FOR USING CALORIE WATCH";
        System.out.println(" ".repeat((this.maxLength - message.length()) / 2) + message +
                " ".repeat((this.maxLength - message.length()) / 2));
        System.out.println("=".repeat(this.maxLength));
    }

    /**
     * Simple method that displays an error message when the user does not enter a valid input
     */
    private void showErrorMessage() {
        System.out.println("Invalid input!");
    }

    /**
     * This method displays the nutrition menu formatted such that the categories are displayed in a
     * 2x2 matrix
     */
    private void showNutritionMenu() {
        System.out.println("\nNutrition Categories:");
        int half;

        if (this.nutritionMenu.length % 2 == 0)
            half = this.nutritionMenu.length / 2;
        else
            half = (this.nutritionMenu.length / 2) + 1;

        for (int i = 0; i < half; i++) {
            System.out.print(this.nutritionMenu[i][0] + " " +
                             this.nutritionMenu[i][1]);

            if (i + half < this.nutritionMenu.length) {
                System.out.print(this.nutritionMenu[i + half][0] + " " +
                                 this.nutritionMenu[i + half][1]);
            }
            System.out.println();
        }
    }

    /**
     * This method displays a menu to the user that they are now in their selected
     * nutritional category mode
     *
     * @param i is an int corresponding to chosen category
     */
    private void showSelectionMenu(int i) {
        System.out.println();
        String message = "[ Welcome to " + this.nutritionCategories[i] +  " MODE";
        System.out.println(message + " ".repeat(this.maxLength - message.length() - 1) + "]");
        message = "[ " + this.nutritionCategories[i] + " MODE uses units of (" + this.units[i] + ")";
        System.out.println(message + " ".repeat(this.maxLength - message.length() - 1) + "]");
        message = "[ - Please enter value as an int or float";
        System.out.println(message + " ".repeat(this.maxLength - message.length() - 1) + "]");
        message = "[ - Select range by entering 'num1-num2'";
        System.out.println(message + " ".repeat(this.maxLength - message.length() - 1) + "]");
        message = "[ - Enter 'h' to show this message again";
        System.out.println(message + " ".repeat(this.maxLength - message.length() - 1) + "]");
        message = "[ - Enter 'x' to exit selection mode";
        System.out.println(message + " ".repeat(this.maxLength - message.length() - 1) + "]");
    }

    /**
     * This method implements the selection mode that the user inputted
     * @param i is an int that corresponds to the category the user chose
     * @param scnr is a Scanner passed from the run() method
     * @throws Exception any exception encountered will trigger a printStackTrace()
     */
    private void selectionMode(int i, Scanner scnr) throws Exception {
        // Display the menu to the user and get their enum category
        showSelectionMenu(i - 1);
        Nutr category = this.getCategory(i - 1);

        String cmd = null; // command that the user enters
        // loop that runs the selection mode
        while (true) {
            System.out.print("\nEnter command here: ");
            if (scnr.hasNextLine())
                cmd = scnr.nextLine().trim().toLowerCase(Locale.ROOT);

            // try-catch block for detecting user input
            try {
                if (cmd == null || cmd.isEmpty()) { // no command = error message
                    this.showErrorMessage();
                } else if (cmd.equals("x")) { // x = exit selection mode by breaking the loop
                    break;
                } else if (cmd.equals("h")) { // displays menu again to help the user
                    this.showSelectionMenu(i - 1);
                } else {
                    // Try-catch block to see if user entered 'int' or 'int1-int2' properly
                    try {
                        // if it contains '-' we have to split the string, while making sure that it is explicitly
                        // 'int-int' and not something like 'int-int-'
                        if (cmd.contains("-")) {
                            String[] range = cmd.split("-", 2);
                            double minRange = Double.parseDouble(range[0]);
                            double maxRange = Double.parseDouble((range[1]));
                            // if they entered a range where the second number is less than the first
                            // tell the user how to properly enter their range
                            if (minRange >= maxRange) {
                                System.out.println("Please enter range as 'x-y', where x < y!");
                                throw new NumberFormatException();
                            }
                            this.backend.setRanges(category, minRange, maxRange); // set the range for the backend
                            this.backend.generateMenu();
                            this.showSelections(this.backend.getSelectedMenu(0));
                        } else if (Double.parseDouble(cmd) >= 0) {
                            // if they didn't input range they possibly inputted an int
                            this.showSelections(this.backend.getClosestMenu(category, Double.parseDouble(cmd)));
                        }
                        // generate the menu in the backend, and then pass the menu to showSelections()
                    } catch (NumberFormatException e1) { // catch any invalid formatting from the user and display error message
                        this.showErrorMessage();
                    }

                }
            } catch (Exception e1) {
                throw new Exception();
            }
        }
    }

    /**
     * This method displays the selections the menu items that correspond to what the
     * user inputted by taking in a list that was fetched from the backend
     *
     * @param menuList is a list that contains all the menu items that were fetched from the backend
     */
    private void showSelections(List<Item> menuList) {
        // If list is empty, then tell user there is no menu items that they want
        if (menuList.isEmpty()) {
            System.out.println("No menu items within that range!");
        } else {
            String message;
            menuList.sort(Comparator.comparing(Item::getName)); // Sort the list in alphabetical order
            // for every menuItem in the list display it to the user in a nicely formatted way
            for (Item menuItem : menuList) {
                System.out.println("\n" + menuItem.getName());
                message = "Calories (kcal): ";
                System.out.println(".".repeat(20 - message.length()) + message + menuItem.get(Nutr.CALORIE));
                message = "Fats (g): ";
                System.out.println(".".repeat(20 - message.length()) + message  + menuItem.get(Nutr.FAT));
                message = "Carbs (g): ";
                System.out.println(".".repeat(20 - message.length()) + message  + menuItem.get(Nutr.CARB));
                message = "Proteins (g): ";
                System.out.println(".".repeat(20 - message.length()) + message  + menuItem.get(Nutr.PROTEIN));
            }
        }
    }

    /**
     * This method fetches the category that the user wants to search through in order for it to eventually be sent
     * to the backend
     *
     * @param i is an int that corresponds to the category the user selected
     * @return the RequestType that the user selected
     */
    private Nutr getCategory(int i) {
        if (i == 0) {
            return Nutr.CALORIE;
        } else if (i == 1) {
            return Nutr.FAT;
        } else if (i == 2) {
            return Nutr.CARB;
        } else {
            return Nutr.PROTEIN;
        }
    }
}
