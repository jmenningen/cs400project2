// --== CS400 File Header Information ==--
// Name: Humza Ayub
// Email: hayub@wisc.edu
// Team: Blue
// Role: Frontend Developer
// TA: Daniel Kiel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.*;
import java.util.List;

public class BackendDummy implements BackendInterface {

    private List<Item> dummyMenuList = new LinkedList<>(Arrays.asList(new Item("Hamburger", 50, 50, 50, 50),
                                                                      new Item("Hot Dog", 100,100,100,100),
                                                                      new Item("Donut", 150,150,150,150),
                                                                      new Item("French Fries", 50, 100, 150, 75)));

    private List<Item> userMenuList = new ArrayList<>();
    private double min;
    private double max;
    private Nutr nutr;

    @Override
    public void setRanges(Nutr nutr, double min, double max) {
        this.min = min;
        this.max = max;
        this.nutr = nutr;
    }

    @Override
    public List<Item> getSelectedMenu(int start) {
        return this.userMenuList;
    }

    @Override
    public void generateMenu() {
        this.userMenuList.clear();

        for (Item item : dummyMenuList) {
            if (item.get(nutr) >= this.min && item.get(nutr) <= this.max) {
                this.userMenuList.add(item);
            }
        }

        if (this.min == this.max && this.userMenuList.size() < 3) {
            List<Item> dummyList = new LinkedList<>();
            for (Item item : dummyMenuList) {
                if (!this.userMenuList.contains(item)) {
                    dummyList.add(item);
                }
            }

            Collections.sort(dummyList, (o1,o2) -> {
                Double x = Math.abs(this.max - o1.get(nutr));
                Double y = Math.abs(this.max - o2.get(nutr));
                return x.compareTo(y);
            });

            int i = 0;
            while (this.userMenuList.size() < 3) {
                this.userMenuList.add(dummyList.get(i++));
            }

        }
    }

    @Override
    public ArrayList<Item> getClosestMenu(Nutr nutr, double val) {
        this.setRanges(nutr, val, val);
        this.generateMenu();
        return (ArrayList<Item>) getSelectedMenu(0);
    }
}
