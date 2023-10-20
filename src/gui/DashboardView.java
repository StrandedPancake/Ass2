package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import usermanagement.User;
import usermanagement.UserProfileManager;  // Import UserProfileManager

public class DashboardView {
    private UserProfileManager userProfileManager; // Store the UserProfileManager as an instance variable

    public DashboardView(UserProfileManager userProfileManager) {
        this.userProfileManager = userProfileManager;  // Initialize the UserProfileManager
    }

    public void showDashboard(User user) {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        // Display user data
        Label welcomeLabel = new Label("Welcome, " + user.getFirstName() + "!");
        Label usernameLabel = new Label("Username: " + user.getUsername());
        Label userTypeLabel = new Label("User Type: " + user.getUserType());

        layout.getChildren().addAll(welcomeLabel, usernameLabel, userTypeLabel);

        // Navigation buttons
        Button addPostButton = new Button("Add Post");
        addPostButton.setOnAction(e -> {
            // Pass the UserProfileManager to AddPostView
            AddPostView addPostView = new AddPostView(userProfileManager);
            addPostView.showAddPost(user);
        });

        Button editProfileButton = new Button("Edit Profile");
        editProfileButton.setOnAction(e -> {
            // Pass the UserProfileManager to EditProfileView
            EditProfileView editProfileView = new EditProfileView(userProfileManager);
            editProfileView.showEditProfile(user);
        });

        layout.getChildren().addAll(addPostButton, editProfileButton);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }
}
