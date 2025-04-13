package simplecaloriecalculator;

public class Food extends Nutrition{
	private String foodGroup; //needed?
	
	// No name: defaults to "Food"
	public Food(double c, int choice) {
		super("Food");
		setCalories(c);
		switch(choice) {
			case 0: foodGroup = "Fruits"; break;
			case 1: foodGroup = "Vegetables"; break;
			case 2: foodGroup = "Protein"; break;
			case 3: foodGroup = "Grains"; break;
			case 4: foodGroup = "Dairy"; break;
			default: foodGroup = "Unknown";
		}
	}
	
	public Food(String n, double c, int choice) {
		super(n);
		setCalories(c);
		switch(choice) {
			case 0: foodGroup = "Fruits"; break;
			case 1: foodGroup = "Vegetables"; break;
			case 2: foodGroup = "Protein"; break;
			case 3: foodGroup = "Grains"; break;
			case 4: foodGroup = "Dairy"; break;
			default: foodGroup = "Unknown";
		}
	}
	
	public String getGroup() {
		return foodGroup;
	}
	
	@Override
	public String toString() {
		return String.format("%s, %.2fkcal, %.2fg proteins, %.2fg carbs, %.2fg fats, %s",
							getName(), getCalories(), getProteins(), getCarbs(), getFats(), getGroup());
	}
}
