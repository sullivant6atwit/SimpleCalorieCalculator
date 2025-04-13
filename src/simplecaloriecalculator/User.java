package simplecaloriecalculator;

import java.util.ArrayList;
import java.util.Iterator;

public class User extends Nutrition{
	private ArrayList<Food> log = new ArrayList<>(); // ArrayList of food class
	private final double MAX_CALORIES;               // max calorie intake that the user has set
	
	// No max calories: defaults to 2000
	public User(String name) {
		super(name);
		MAX_CALORIES = 2000;
	}
	
	public User(String name, double max) {
		super(name);
		MAX_CALORIES = max;
	}
	
	// getter methods
	public double getMax() {
		return MAX_CALORIES;
	}
	
	public ArrayList<Food> getLog() {
		return log;
	}
	
	// add food object to user's log
	// also adds onto their nutrition
	public <T extends Food> void addTo(T o) {
		log.add(o);
		this.setCalories(this.getCalories() + o.getCalories());
		this.setProteins(this.getProteins() + o.getProteins());
		this.setFats(this.getFats() + o.getFats());
		this.setCarbs(this.getCarbs() + o.getCarbs());
	}
	
	public void clearLog() {
		log.clear();
		this.setCalories(0);
		this.setProteins(0);
		this.setFats(0);
		this.setCarbs(0);
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append('[');
		Iterator<Food> i = log.iterator();
		while(i.hasNext()) {
			b.append(i.next());
			if(i.hasNext()) {
				b.append(String.format("%n"));
			}
		}
		b.append(']');
		return String.format("Type: User, Name: %s, Calories: %.2f/%.2f, Proteins = %.2f, Fats = %.2f, Carbohydrates = %.2f%nFood Log: %s",
				getName(), getCalories(), MAX_CALORIES, getProteins(), getFats(), getCarbs(), b.toString());
	}
}
