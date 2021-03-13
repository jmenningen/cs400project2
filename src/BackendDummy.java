import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class BackendDummy implements BackendInterface {

    public enum RequestType {
        CALORIES,
        FATS,
        CARBS,
        PROTEINS
    }

    private List <MenuItemInterface> dummyMenuList;

    public BackendDummy() {
        this.dummyMenuList = new LinkedList<MenuItemInterface>();
        this.dummyMenuList.add(new MenuItemDummy("Hamburger", 100, 100, 100, 100));
        this.dummyMenuList.add(new MenuItemDummy("Hot Dog", 100,100,100,100));
        this.dummyMenuList.add(new MenuItemDummy("Donut", 100,100,100,100));
    }

    @Override
    public List<MenuItemInterface> getClosestMenuItems(BackendInterface.RequestType type, float target, int numberOfResults) {
        return dummyMenuList;
    }

    @Override
    public int getMenuSize() {
        return 0;
    }

    @Override
    public MenuItemInterface getSpecificMenuItem(String name) {
        return null;
    }

}
