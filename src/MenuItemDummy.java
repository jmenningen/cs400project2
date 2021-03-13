public class MenuItemDummy implements MenuItemInterface {
    private String name;
    private float calories;
    private float fats;
    private float carbs;
    private float proteins;

    public MenuItemDummy(String name, float calories, float fats, float carbs, float proteins) {
        this.name = name;
        this.calories = calories;
        this.fats = fats;
        this.carbs = carbs;
        this.proteins = proteins;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getCalories() {
        return this.calories;
    }

    @Override
    public float getFats() {
        return this.fats;
    }

    @Override
    public float getCarbs() {
        return this.carbs;
    }

    @Override
    public float getProteins() {
        return this.proteins;
    }
}
