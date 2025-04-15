package simplecaloriecalculator;

public class Food extends Nutrition{
	
	// No name: defaults to "Food"
	public Food(double c) {
		super("Food");
		setCalories(c);
	}
	
	public Food(String n, double c) {
		super(n);
		setCalories(c);
	}
	
	@Override
	public String toString() {
		return String.format("%s, %.2fkcal, %.2fg proteins, %.2fg carbs, %.2fg fats",
							getName(), getCalories(), getProteins(), getCarbs(), getFats());
	}
}
