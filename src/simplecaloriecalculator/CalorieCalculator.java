package simplecaloriecalculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;

public class CalorieCalculator extends Application {
    // Temporarily stores calorie entries
	// Will be cleared when
	// Changing users or resetting
    private ArrayList<Double> calorieEntries = new ArrayList<>();
    
    // Stores user entries
    // userLists contains the User class in a HashMap
    // userEntries is used for GUI
    private HashMap<String, User> userLists = new HashMap<>();
    private ComboBox<String> userEntries = new ComboBox<>();
    
    // Stores food log GUI
    private ListView<Food> foodLog = new ListView<>();
    
    // Optional 4-4-9 rule and showing other nutrient
    private CheckBox rule = new CheckBox("4-4-9 Rule?");
    private CheckBox other = new CheckBox("Show others?");
    
    // Choice to add calories, users, or food
    private ToggleGroup group = new ToggleGroup();
    private RadioButton c1 = new RadioButton("Calories");
    private RadioButton c2 = new RadioButton("Food");
    private RadioButton c3 = new RadioButton("User");
    
    // UI components
    private Label resultLabel;
    private Label resultProtein; // optional
    private Label resultCarbs; // optional
    private Label resultFats; // optional
    
    // Calories
    private TextField calorieInput = new TextField();
    // Food
    private TextField nameFoodInput = new TextField(); // optional
    private TextField calorieFoodInput = new TextField();
    private TextField proteinFoodInput = new TextField(); // optional
    private TextField carbsFoodInput = new TextField(); // optional
    private TextField fatsFoodInput = new TextField(); // optional
    // User
    private TextField nameUserInput = new TextField(); 
    private TextField maxUserInput = new TextField(); // optional
    
    // The panels
    private VBox addCalories = new VBox(10);
    private VBox addFood = new VBox(10);
    private VBox addUser = new VBox(10);

    @Override
    public void start(Stage primaryStage) {
    	// Initialize ToggleGroup
    	c1.setToggleGroup(group);
    	c1.setSelected(true);
    	c2.setToggleGroup(group);
    	c3.setToggleGroup(group);
    	
        // Initialize input field
        calorieInput.setPromptText("Enter calories*");
        
        nameFoodInput.setPromptText("Enter food name");
        calorieFoodInput.setPromptText("Enter food calories*");
        proteinFoodInput.setPromptText("Enter food proteins");
        carbsFoodInput.setPromptText("Enter food carbohydrates");
        fatsFoodInput.setPromptText("Enter food fats");
        
        nameUserInput.setPromptText("Enter user's name*");
        maxUserInput.setPromptText("Enter user's max calories intake");

        // Add default user
        userLists.put("Default", new User("Default"));
        userEntries.getItems().add("Default");
        userEntries.setValue("Default");
        // Add function to the ComboBox
        userEntries.setOnAction(event -> changeUser());
        
        // Create add buttons
        Button addButton = new Button("Add Calories");
        Button addButton1 = new Button("Add Food");
        Button addButton2 = new Button("Add User");
        addButton.setOnAction(event -> addCalories());
        addButton1.setOnAction(event -> addFood());
        addButton2.setOnAction(event -> addUser());

        // Create reset tracker button
        Button resetButton = new Button("Reset Tracker");
        resetButton.setOnAction(event -> resetTracker());

        // Initialize result label
        resultLabel = new Label("Total Calories: 0.00/2000.00");
        resultProtein = new Label("Total Protein: 0.00");
        resultCarbs = new Label("Total Carbs: 0.00");
        resultFats = new Label("Total Fats: 0.00");

        // Add functions to ToggleGroup
        c1.setUserData("Calories");
        c2.setUserData("Food");
        c3.setUserData("User");
        group.selectedToggleProperty().addListener(event -> changeSide());
        
        // Add function to rule and other
        rule.setOnAction(event -> updateTotalCalories());
        other.setOnAction(event -> showOthers());
        
        
        // Create layout and add components
        HBox root = new HBox(10);
        VBox GUILog = new VBox(10);
        VBox side = new VBox(10);
        
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        // Add Food Log GUI to the left
        GUILog.getChildren().addAll(
        	new Label("Food Log"),
        	foodLog
        );
        
        // Add side to the right
        side.getChildren().addAll(
        	new Label("Calorie Tracker"),
        	addCalories,
        	addFood,		// hidden
        	addUser,		// hidden
        	resetButton,	
        	resultLabel,
        	resultProtein,	// hidden
        	resultCarbs,	// hidden
        	resultFats,		// hidden
        	other,
        	rule,
            c1, c2, c3,
            userEntries
        );
        
        // Set up the calories panel
        addCalories.getChildren().addAll(
            calorieInput, 
            addButton
        );
        
        // Set up the food panel
        addFood.getChildren().addAll(
    		nameFoodInput,
    		calorieFoodInput,
    		proteinFoodInput,
    		carbsFoodInput,
    		fatsFoodInput,
    		addButton1
        );
        
        // Set up the user panel
        addUser.getChildren().addAll(
        	nameUserInput,
    		maxUserInput,
    		addButton2
		);
        
        // Hide the other panels and labels
        addFood.setVisible(false); addFood.setManaged(false);
        addUser.setVisible(false); addUser.setManaged(false);
        resultProtein.setVisible(false); resultProtein.setManaged(false);
        resultCarbs.setVisible(false); resultCarbs.setManaged(false);
        resultFats.setVisible(false); resultFats.setManaged(false);
        
        // Put the rest into the root
        root.getChildren().addAll(GUILog, side);

        // Set up and show the stage
        Scene scene = new Scene(root, 600, 700);
        primaryStage.setTitle("Calorie Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addCalories() {
        try {
            // Parse input calories
            double calories = Double.parseDouble(calorieInput.getText());
            
            // Add to entries list
            calorieEntries.add(calories);
            
            // Update total calories display
            updateTotalCalories();
            
            // Clear input field
            calorieInput.clear();
        } catch (NumberFormatException e) {
            // Handle invalid input
            resultLabel.setText("Please enter a valid number");
        }
    }
    
    private void addFood() {
    	try {
    		// Parse input calories
    		double calories = Double.parseDouble(calorieFoodInput.getText());
    		
    		// Check if calories is not negative
    		if (calories >= 0) {
    			// Create new Food class
    			Food f;
    			// Food name is optional
    			// Calls different constructor depending if
    			// the name field is filled in
    			if (nameFoodInput.getText().isEmpty()) {
    				f = new Food(calories, 5);
    			} else {
    				f = new Food(nameFoodInput.getText(), calories, 5);
    			}
    			
    			// Give protein values to food if provided and greater than zero
    			if (!proteinFoodInput.getText().isEmpty() && Double.parseDouble(proteinFoodInput.getText()) > 0) {
    				f.setProteins(Double.parseDouble(proteinFoodInput.getText()));
    			}
    			// Give carb values to food if provided and greater than zero
    			if (!carbsFoodInput.getText().isEmpty() && Double.parseDouble(carbsFoodInput.getText()) > 0) {
    				f.setCarbs(Double.parseDouble(carbsFoodInput.getText()));
    			}
    			// Give fats values to food if provided and greater than zero
    			if (!fatsFoodInput.getText().isEmpty() && Double.parseDouble(fatsFoodInput.getText()) > 0) {
    				f.setFats(Double.parseDouble(fatsFoodInput.getText()));
    			}
    			
    			// Clear input fields
    			nameFoodInput.clear();
    			calorieFoodInput.clear();
    			proteinFoodInput.clear();
    			carbsFoodInput.clear();
    			fatsFoodInput.clear();
    			
    			// Add food to user's log
    			userLists.get(userEntries.getValue()).addTo(f);
    			// Update calories and others
    			updateLog();
    			
    		} else {
    			resultLabel.setText("Please enter a non-negative number");
    		}
    		
    	} catch (NumberFormatException e) {
            // Handle invalid input
            resultLabel.setText("Please enter a valid number");
        }
    }
    
    private void addUser() {
    	// Checks if user already exists and not empty
    	if (userLists.containsKey(nameUserInput.getText())) {
    		resultLabel.setText("User already exists");
    	} else if (nameUserInput.getText().isEmpty() || nameUserInput.getText().isBlank()) {
    		resultLabel.setText("User name must not be empty");
    	} else {
    		try {
    			// Give max calories if provided and greater than zero
    			if (!maxUserInput.getText().isEmpty() && Double.parseDouble(maxUserInput.getText()) > 0) {
    				userLists.put(nameUserInput.getText(), new User(nameUserInput.getText(), Double.parseDouble(maxUserInput.getText())));
    			} else {
    				userLists.put(nameUserInput.getText(), new User(nameUserInput.getText()));
    			}
    			// Add user to userEntries GUI and set its value to the recently added user
        		userEntries.getItems().add(nameUserInput.getText());
        		userEntries.setValue(nameUserInput.getText());
        		
        		// Clear input fields
        		nameUserInput.clear();
        		maxUserInput.clear();
    		} catch (NumberFormatException e) {
    			resultLabel.setText("Please enter a valid number");
    		}
    	}
    }

    private void resetTracker() {
        // Clear all calorie entries
        calorieEntries.clear();
        // Reset current user's log
        userLists.get(userEntries.getValue()).clearLog();
        // Update food log GUI to reflect resetting
        updateLog();
    }
    
    private void changeUser() {
    	// Clear all calories entries
    	calorieEntries.clear();
    	// Update food log GUI to reflect user change
    	updateLog();
    }
    
    private void updateLog() {
    	// Clear food log GUI then update it and the total nutrition values
    	foodLog.getItems().clear();
    	// Iterate through the user's log
    	for (Food f : userLists.get(userEntries.getValue()).getLog()) {
    		foodLog.getItems().add(f);
    	}
    	// Update nutrition values to reflect on the GUI update
    	updateTotalCalories();
        updateOthers();
    }

    private void updateTotalCalories() {
        // Calculate total calories (and adjusted total if 4-4-9 Rule is enabled)
    	// Adjusted total will account for temporary calories added/subtracted
        double total = 0;
        double ruleTotal = 0;
        for (Double calories : calorieEntries) {
            total += calories;
            ruleTotal += calories;
        }
        // Total calories from the food log
        total += userLists.get(userEntries.getValue()).getCalories();
        
        // Check if 4-4-9 Rule is enabled
        // Food calories will not be added to adjusted total
        if(rule.isSelected()) {
        	double p = userLists.get(userEntries.getValue()).getProteins();
        	double c = userLists.get(userEntries.getValue()).getCarbs();
        	double f = userLists.get(userEntries.getValue()).getFats();
        	ruleTotal += 4 * (p + c) + 9 * f;
        	// Update result label with formatted total and adjusted total from the 4-4-9 rule
        	resultLabel.setText(String.format("Total Calories: %.2f/%.2f%n(adjusted: %.2f)", total, userLists.get(userEntries.getValue()).getMax(), ruleTotal));
        } else {
        	// Update result label with formatted total
        	resultLabel.setText(String.format("Total Calories: %.2f/%.2f", total, userLists.get(userEntries.getValue()).getMax()));
        }
    }
    
    private void updateOthers() {
    	// Calculate total protein, carbs, and fats
    	double protein = 0;
    	double carbs = 0;
    	double fats = 0;
    	for (Food f : foodLog.getItems()) {
        	protein += f.getProteins();
        	carbs += f.getCarbs();
        	fats += f.getFats();
        }
    	// Update label
    	resultProtein.setText(String.format("Total Protein: %.2f", protein));
    	resultCarbs.setText(String.format("Total Carbs: %.2f", carbs));
    	resultFats.setText(String.format("Total Fats: %.2f", fats));
    }
    
    private void showOthers() {
    	// Show number for other nutrients if user wishes to
    	if(other.isSelected()) {
    		resultProtein.setVisible(true); resultProtein.setManaged(true);
            resultCarbs.setVisible(true); resultCarbs.setManaged(true);
            resultFats.setVisible(true); resultFats.setManaged(true);
    	} else {
    		resultProtein.setVisible(false); resultProtein.setManaged(false);
            resultCarbs.setVisible(false); resultCarbs.setManaged(false);
            resultFats.setVisible(false); resultFats.setManaged(false);
    	}
    }
    
    private void changeSide() {
    	// Check selected options to show certain panels
    	switch (group.getSelectedToggle().getUserData().toString()) {
    		case "Food":
    			addCalories.setVisible(false); addCalories.setManaged(false);
    			addFood.setVisible(true); addFood.setManaged(true);
    			addUser.setVisible(false); addUser.setManaged(false);
    			break;
    		case "User":
    			addCalories.setVisible(false); addCalories.setManaged(false);
    			addFood.setVisible(false); addFood.setManaged(false);
    			addUser.setVisible(true); addUser.setManaged(true);
    			break;
    		default:
    			addCalories.setVisible(true); addCalories.setManaged(true);
    			addFood.setVisible(false); addFood.setManaged(false);
    			addUser.setVisible(false); addUser.setManaged(false);
    	}
    }

    public static void main(String[] args) {
        launch(args);
    }
}