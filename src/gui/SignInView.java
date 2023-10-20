package gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import usermanagement.User;
import usermanagement.UserProfileManager;

public class SignInView {
    private UserProfileManager userProfileManager;

    public SignInView(UserProfileManager userProfileManager) {
        this.userProfileManager = userProfileManager;
    }

    public Parent createSignInScene() {
        // Create your scene content and return the root node
        VBox root = new VBox(10);
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        Button signInButton = new Button("Sign In");

        // Set up event handling for the Sign In button using the UserProfileManager
        signInButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            User loggedInUser = userProfileManager.loginUser(username, password);

            if (loggedInUser != null) {
                // Successful login
                System.out.println("Successfully logged in as: " + loggedInUser.getUsername());
            } else {
                // Failed login
                System.out.println("Login failed. Invalid credentials.");
            }
        });

        root.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, signInButton);

        Scene scene = new Scene(root, 300, 200);
        return root;
    }
}