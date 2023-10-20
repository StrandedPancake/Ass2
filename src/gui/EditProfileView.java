package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import usermanagement.User;
import usermanagement.UserProfileManager;  // Import UserProfileManager

public class EditProfileView {
    private UserProfileManager userProfileManager;

    public EditProfileView(UserProfileManager userProfileManager) {
        this.userProfileManager = userProfileManager;
    }

    public void showEditProfile(User user) {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        // Create input fields for editing profile
        TextField firstNameField = new TextField(user.getFirstName());
        TextField lastNameField = new TextField(user.getLastName());
        PasswordField passwordField = new PasswordField();

        // Label for the input fields
        Label firstNameLabel = new Label("First Name:");
        Label lastNameLabel = new Label("Last Name:");
        Label passwordLabel = new Label("New Password:");

        layout.getChildren().addAll(firstNameLabel, firstNameField, lastNameLabel, lastNameField, passwordLabel, passwordField);

        // Edit Profile button
        Button editProfileButton = new Button("Edit Profile");
        editProfileButton.setOnAction(e -> {
            // Retrieve input from the text fields
            String newFirstName = firstNameField.getText();
            String newLastName = lastNameField.getText();
            String newPassword = passwordField.getText();

            // Call your usermanagement functionality to edit the user's profile
            userProfileManager.editUserProfile(user, newFirstName, newLastName, newPassword);

            // Optionally, show a confirmation message or perform other actions
            // Then close the stage
            stage.close();
        });

        layout.getChildren().add(editProfileButton);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }
}
