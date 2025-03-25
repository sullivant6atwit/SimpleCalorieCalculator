package simplecaloriecalculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

public class CalorieCalculator extends Application {
    // Stores calorie entries
    private ArrayList<Double> calorieEntries = new ArrayList<>();
    
    // UI components
    private Label resultLabel;
    private TextField calorieInput;

    @Override
    public void start(Stage primaryStage) {
        // Initialize calorie input field
        calorieInput = new TextField();
        calorieInput.setPromptText("Enter calories");

        // Create add calories button
        Button addButton = new Button("Add Calories");
        addButton.setOnAction(event -> addCalories());

        // Create reset tracker button
        Button resetButton = new Button("Reset Tracker");
        resetButton.setOnAction(event -> resetTracker());

        // Initialize result label
        resultLabel = new Label("Total Calories: 0");

        // Create layout and add components
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
            new Label("Calorie Tracker"),
            calorieInput, 
            addButton, 
            resetButton,
            resultLabel
        );

        // Set up and show the stage
        Scene scene = new Scene(layout, 250, 300);
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

    private void resetTracker() {
        // Clear all calorie entries
        calorieEntries.clear();
        updateTotalCalories();
    }

    private void updateTotalCalories() {
        // Calculate total calories
        double total = 0;
        for (Double calories : calorieEntries) {
            total += calories;
        }
        
        // Update result label with formatted total
        resultLabel.setText(String.format("Total Calories: %.2f", total));
    }

    public static void main(String[] args) {
        launch(args);
    }
}