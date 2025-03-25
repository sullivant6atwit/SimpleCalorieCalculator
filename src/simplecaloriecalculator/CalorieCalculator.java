package simplecaloriecalculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CalorieCalculator extends Application {
    private double totalCalories = 0;
    private Label resultLabel;
    private TextField calorieInput;

    @Override
    public void start(Stage primaryStage) {
        // Create input field
        calorieInput = new TextField();
        calorieInput.setPromptText("Enter calories");

        // Create add button
        Button addButton = new Button("Add Calories");
        addButton.setOnAction(e -> addCalories());

        // Create result label
        resultLabel = new Label("Total Calories: 0");

        // Create layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
            new Label("Calorie Calculator"),
            calorieInput, 
            addButton, 
            resultLabel
        );

        // Create scene
        Scene scene = new Scene(layout, 250, 250);

        // Set up stage
        primaryStage.setTitle("Calorie Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addCalories() {
        try {
            // calories from input
            double calories = Double.parseDouble(calorieInput.getText());
            
            // Add to total calories
            totalCalories += calories;
            
            // Update
            resultLabel.setText("Total Calories: " + totalCalories);
            
            // Clear input
            calorieInput.clear();
        } catch (NumberFormatException e) {
            // invalid input
            resultLabel.setText("Please enter a valid number");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
