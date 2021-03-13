import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Frontend {

    private BackendDummy backend = null;
    private final String[][] nutritionMenu;
    private final int maxLength = 50;

    /**
     * This is the main method and automatically runs the application when the file is ran
     * @param args Contains filepath if specified in command line TODO may or may not be needed
     */
    public static void main(String[] args) {
        // May or not be needed depending on how we implement the data reader
        //String filePath = "test.csv";
        //if (args.length > 0)
        //    filePath = args[0];
        try {
            run();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * This method is responsible for running the entire application that the user interacts with in order to
     * keep track of any nutritional values they might be interested in
     *
     * @throws Exception temporary for testing purposes TODO possibly remove
     */
    public static void run() throws Exception {
        // Initialize the backend and frontend
        BackendDummy backend = new BackendDummy();
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
        String[] nutritionCategories = {"CALORIES", "FAT", "CARBS", "PROTEIN"};
        this.nutritionMenu = new String[nutritionCategories.length][2];
        for (int i = 0; i < nutritionMenu.length; i++) {
            this.nutritionMenu[i][0] = (i < 9) ? ("0" + (i + 1) + ".") : ((i+1) + ".");
            this.nutritionMenu[i][1] = nutritionCategories[i] + " ".repeat(12 - nutritionCategories[i].length());
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
        System.out.println(" ".repeat(13) + "WELCOME TO CALORIE WATCH" + " ".repeat(this.maxLength));
        System.out.println("=".repeat(this.maxLength));
    }

    /**
     * Simple method displays an exit message when the user exits the application
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
     * 2*n matrix, where n is the number of nutritional categories, in this application n = 4
     */
    private void showNutritionMenu() {
        System.out.println("\nNutrition Categories: ");
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
     * @param category is a string of the nutritional category that the user selected
     */
    private void showSelectionMenu(String category) {
        System.out.println();
        String message = "[ Welcome to " + category.toUpperCase(Locale.ROOT) +  " MODE";
        System.out.println(message + " ".repeat(this.maxLength - message.length() - 1) + "]");
        message = "[ Please enter your desired value as an integer";
        System.out.println(message + " ".repeat(this.maxLength - message.length() - 1) + "]");
        message = "[ A range can be selected by entering 'int-int'";
        System.out.println(message + " ".repeat(this.maxLength - message.length() - 1) + "]");
        message = "[ Enter 'h' to show this message again";
        System.out.println(message + " ".repeat(this.maxLength - message.length() - 1) + "]");
        message = "[ Enter 'x' to exit selection mode";
        System.out.println(message + " ".repeat(this.maxLength - message.length() - 1) + "]");
    }

    /**
     * This method implements the selection mode that the user inputted
     * @param i is an int that corresponds to the category the user chose
     * @param scnr is a Scanner passed from the run() method
     * @throws Exception temporary that will be edited for more specific exceptions TODO
     */
    private void selectionMode(int i, Scanner scnr) throws Exception {
        // Display the menu to the user and get their enum category
        showSelectionMenu(this.nutritionMenu[i - 1][1].trim());
        BackendInterface.RequestType category = this.getCategory(i - 1);

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
                    this.showSelectionMenu(this.nutritionMenu[i-1][1].trim());
                } else {
                    // Try-catch block to see if user entered 'int' or 'int1-int2' properly
                    try {
                        // if it contains '-' we have to split the string, while making sure that it is explicitly
                        // 'int-int' and not something like 'int-int-'
                        if (cmd.contains("-")) {
                            String[] range = cmd.split("-", 2);
                            int minRange = Integer.parseInt(range[0]);
                            int maxRange = Integer.parseInt(range[1]);
                            // if they entered a range where the second number is less than the first
                            // tell the user how to properly enter their range
                            if (minRange > maxRange) {
                                System.out.println("Please enter range as 'x-y', where x < y!");
                                throw new NumberFormatException();
                            }
                            this.showSelections(this.backend.getClosestMenuItems(category, minRange, 0)); // TODO change this to work with corresponding method in backend
                        } else if (Integer.parseInt(cmd) >= 0) // if they didn't input range they possibly inputted an int
                            this.showSelections(this.backend.getClosestMenuItems(category, Integer.parseInt(cmd),1)); // TODO change this to work with corresponding method in backend
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
    private void showSelections(List<MenuItemInterface> menuList) {
        // If list is empty, then tell user there is no menu items that they want
        if (menuList.isEmpty()) {
            System.out.println("No menu items within that range!");
        } else {
            // for every menuItem in the list display it to the user in a nicely formatted way
            String message;
            for (MenuItemInterface menuItem : menuList) {
                System.out.println("\n" + menuItem.getName());
                message = "Calories: ";
                System.out.println(".".repeat(15 - message.length()) + message + menuItem.getCalories());
                message = "Fats: ";
                System.out.println(".".repeat(15 - message.length()) + message  + menuItem.getFats());
                message = "Carbs: ";
                System.out.println(".".repeat(15 - message.length()) + message  + menuItem.getCarbs());
                message = "Proteins: ";
                System.out.println(".".repeat(15 - message.length()) + message  + menuItem.getProteins());
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
    private BackendInterface.RequestType getCategory(int i) {
        if (i == 0) {
            return BackendInterface.RequestType.CALORIES;
        } else if (i == 1) {
            return BackendInterface.RequestType.FATS;
        } else if (i == 2) {
            return BackendInterface.RequestType.CARBS;
        } else {
            return BackendInterface.RequestType.PROTEINS;
        }
    }
}
