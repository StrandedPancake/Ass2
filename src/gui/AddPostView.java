package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import usermanagement.User;
import usermanagement.UserProfileManager;  // Import UserProfileManager

public class AddPostView {
    private UserProfileManager userProfileManager;

    public AddPostView(UserProfileManager userProfileManager) {
        this.userProfileManager = userProfileManager;
    }

    public void showAddPost(User user) {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        // Create input fields for post details
        TextField contentField = new TextField();
        TextField authorField = new TextField();

        // Label for the input fields
        Label contentLabel = new Label("Content:");
        Label authorLabel = new Label("Author:");

        layout.getChildren().addAll(contentLabel, contentField, authorLabel, authorField);

        // Add Post button
        Button addPostButton = new Button("Add Post");
        addPostButton.setOnAction(e -> {
            // Retrieve input from the text fields
            String content = contentField.getText();
            String author = authorField.getText();

            // Call your usermanagement functionality to add a post using the provided UserProfileManager
            userProfileManager.addPost(user, content, author);

            // Optionally, show a confirmation message or perform other actions
            // Then close the stage
            stage.close();
        });

        layout.getChildren().add(addPostButton);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }
}
