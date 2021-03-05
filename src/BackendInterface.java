import java.util.List;

public interface BackendInterface {

    public enum RequestType {
        CALORIES,
        FATS,
        CARBS,
        PROTEINS
    }

    /**
     * Returns a list of the closest MenuItemInterfaces to a target value of a particular type of nutritional data
     *
     * @param type The nutritional value to search by
     * @param target The desired nutritional value
     * @param numberOfResults The number of results to display
     * @return A list of the closest MenuItemInterfaces to a target value of a particular type of nutritional data
     */
    public List<MenuItemInterface> getClosestMenuItems(RequestType type, float target, int numberOfResults);

    /**
     * @return The number of MenuItemInterfaces in the full menu
     */
    public int getMenuSize();

    /**
     * @param name The name of an item in the menu
     * @return The first MenuItemInterface with a matching name. If none exist, this method returns null
     */
    public MenuItemInterface getSpecificMenuItem(String name);
}
