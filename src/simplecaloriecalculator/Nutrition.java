package simplecaloriecalculator;

public abstract class Nutrition {
	private double calories; // calories
	private double proteins; // proteins
	private double fats;     // fats
	private double carbs;    // carbs
	private String name;     // name of user/foods
	
	public Nutrition(String name) {
		this.name = name;
	}
	
	// getters and setters
	public double getCalories() {
		return calories;
	}
	public void setCalories(double c) {
		calories = c;
	}
	
	public double getProteins() {
		return proteins;
	}
	public void setProteins(double p) {
		proteins = p;
	}
	
	public double getFats() {
		return fats;
	}
	public void setFats(double f) {
		fats = f;
	}
	
	public double getCarbs() {
		return carbs;
	}
	public void setCarbs(double c) {
		carbs = c;
	}
	
	public String getName() {
		return name;
	}
}
