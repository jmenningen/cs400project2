import java.util.ArrayList;
import java.util.List;

public interface BackendInterface {

    public enum RequestType {
        CALORIES,
        FATS,
        CARBS,
        PROTEINS
    }

    public void setRanges(Nutr nutr, double min, double max);

    public List<Item> getSelectedMenu(int start);

    public void generateMenu();

    public ArrayList<Item> getClosestMenu(Nutr nutr, double val);
}
