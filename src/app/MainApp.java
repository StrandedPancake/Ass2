package app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import controller.SocialMediaAnalyzerController;

import usermanagement.*;
import model.SocialMediaAnalyzer;
import model.SocialMediaPost;

public class MainApp extends Application {
    private UserProfileManager userProfileManager;
    private VIPManager vipManager;
    private UserFactory userFactory;
    private SocialMediaAnalyzer socialMediaAnalyzer;
    private CSVHandler csvHandler;
    private Stage primaryStage;
    private Label responseLabel;
    private TextField usernameField;
   
    private TextField postIdTextField; // TextField for post ID
    private TextField contentTextField; // TextField for post content
    private TextField authorTextField; // TextField for post author
    private TextField likesTextField; // TextField for post likes
    private TextField sharesTextField; // TextField for post shares
    private DatePicker dateTimePicker; // DatePicker for date-time

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        socialMediaAnalyzer = new SocialMediaAnalyzer(new ArrayList<>(), "C:/Users/samla/eclipse-workspace/AdvancedProgramming/SocialMediaAnalyzer3/posts.csv");
        userProfileManager = new UserProfileManager();
        vipManager = new VIPManager(userProfileManager);
        userFactory = new UserFactory();
        csvHandler = new CSVHandler("C:/Users/samla/eclipse-workspace/AdvancedProgramming/SocialMediaAnalyzer3/posts.csv");

        csvHandler.readUsersFromCSV();
        primaryStage.initStyle(StageStyle.UTILITY);
        responseLabel = new Label();
        showMainMenu();
        
        // Load the FXML file and set the controller (added code)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("your_main_window.fxml"));
        AnchorPane rootLayout;
        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Get the controller instance (added code)
        SocialMediaAnalyzerController controller = loader.getController();

        // Initialize the scene
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showMainMenu() {
        StackPane menu = new StackPane();

        VBox buttonBox = new VBox(10);
        Button registerButton = new Button("Register a new user");
        Button loginButton = new Button("Login as a user");
        Button editButton = new Button("Edit user profile");
        Button upgradeButton = new Button("Upgrade to VIP");
        Button exitButton = new Button("Exit");

        registerButton.setOnAction(e -> showRegisterUserView());
        loginButton.setOnAction(e -> showLoginView());
        editButton.setOnAction(e -> showEditProfileView());
        upgradeButton.setOnAction(e -> showVIPUpgradeView());
        exitButton.setOnAction(e -> primaryStage.close());

        buttonBox.getChildren().addAll(registerButton, loginButton, editButton, upgradeButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox contentBox = new VBox(20);
        contentBox.getChildren().addAll(buttonBox, responseLabel);

        menu.getChildren().add(contentBox);

        Scene scene = new Scene(menu, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showLoginView() {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Login as a User");
        dialog.setHeaderText("Please enter your credentials");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(new VBox(usernameField, passwordField));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return userProfileManager.loginUser(usernameField.getText(), passwordField.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(loggedInUser -> {
            responseLabel.setText("Logged in as: " + loggedInUser.getUsername());
            showUserDashboard(loggedInUser);
        });
    }

    private void showUserDashboard(User user) {
        // Create the dashboard scene
        StackPane dashboard = new StackPane();

        // Create a button labeled "Social Media Analyzer" instead of "Add Social Media Post"
        Button analyzerButton = new Button("Social Media Analyzer");
        analyzerButton.setOnAction(e -> showSocialMediaAnalyzer(user)); // Pass the logged-in user

        // Create a button to go back to the main menu
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> showMainMenu()); // Set the action to show the main menu

        // Add the buttons to the dashboard StackPane
        VBox buttonBox = new VBox(20);
        buttonBox.getChildren().addAll(analyzerButton, backButton);

        dashboard.getChildren().addAll(buttonBox);

        Scene dashboardScene = new Scene(dashboard, 400, 400);
        primaryStage.setScene(dashboardScene);
    }

    
    private void showSocialMediaAnalyzer(User user) {
        VBox menuBox = new VBox(10);

        Label titleLabel = new Label("Social Media Analyzer");
        titleLabel.setFont(Font.font(20));

        Button addPostButton = new Button("Add a social media post");
        addPostButton.setOnAction(e -> addSocialMediaPost(user));

        Button deletePostButton = new Button("Delete an existing social media post");
        deletePostButton.setOnAction(e -> deleteSocialMediaPost(user));

        Button retrievePostButton = new Button("Retrieve a social media post");
        retrievePostButton.setOnAction(e -> retrieveSocialMediaPost(user));

        Button retrieveTopLikesButton = new Button("Retrieve the top N posts with most likes");
        retrieveTopLikesButton.setOnAction(e -> retrieveTopNPostsByLikes(user));

        Button retrieveTopSharesButton = new Button("Retrieve the top N posts with most shares");
        retrieveTopSharesButton.setOnAction(e -> retrieveTopNPostsByShares(user));

        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> showUserDashboard(user));

        menuBox.getChildren().addAll(titleLabel, addPostButton, deletePostButton, retrievePostButton, retrieveTopLikesButton, retrieveTopSharesButton, backButton);
        menuBox.setAlignment(Pos.CENTER);

        Scene analyzerScene = new Scene(menuBox, 400, 400);
        primaryStage.setScene(analyzerScene);
    }

    private void retrieveTopNPostsByShares(User user) {
        // Prompt the user to enter the number of top posts to retrieve
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Retrieve Top N Posts by Shares");
        dialog.setHeaderText("Enter the number of top posts to retrieve by shares:");
        dialog.setContentText("Number of posts:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nStr -> {
            try {
                int n = Integer.parseInt(nStr);
                // Call your retrieve method here
                List<SocialMediaPost> topPosts = socialMediaAnalyzer.retrieveTopNPostsByShares(n);
                if (!topPosts.isEmpty()) {
                    // Posts retrieved successfully
                    // Display the top posts to the user
                    StringBuilder postDetails = new StringBuilder("Top " + n + " Posts by Shares:\n");
                    for (SocialMediaPost post : topPosts) {
                        postDetails.append("Post ID: ").append(post.getId()).append("\n")
                                .append("Author: ").append(post.getAuthor()).append("\n")
                                .append("Content: ").append(post.getContent()).append("\n")
                                .append("Likes: ").append(post.getLikes()).append("\n")
                                .append("Shares: ").append(post.getShares()).append("\n\n");
                    }
                    // Display postDetails to the user (e.g., in a message box)
                } else {
                    // Handle the case where there are no posts to display
                    // You may want to show an error message
                }
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid number
                // You may want to show an error message
            }
        });
    }


	private void retrieveTopNPostsByLikes(User user) {
        // Prompt the user to enter the number of top posts to retrieve
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Retrieve Top N Posts by Likes");
        dialog.setHeaderText("Enter the number of top posts to retrieve by likes:");
        dialog.setContentText("Number of posts:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nStr -> {
            try {
                int n = Integer.parseInt(nStr);
                // Call your retrieve method here
                List<SocialMediaPost> topPosts = socialMediaAnalyzer.retrieveTopNPostsByLikes(n);
                if (!topPosts.isEmpty()) {
                    // Posts retrieved successfully
                    // Display the top posts to the user
                    StringBuilder postDetails = new StringBuilder("Top " + n + " Posts by Likes:\n");
                    for (SocialMediaPost post : topPosts) {
                        postDetails.append("Post ID: ").append(post.getId()).append("\n")
                                .append("Author: ").append(post.getAuthor()).append("\n")
                                .append("Content: ").append(post.getContent()).append("\n")
                                .append("Likes: ").append(post.getLikes()).append("\n")
                                .append("Shares: ").append(post.getShares()).append("\n\n");
                    }
                    // Display postDetails to the user (e.g., in a message box)
                } else {
                    // Handle the case where there are no posts to display
                    // You may want to show an error message
                }
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid number
                // You may want to show an error message
            }
        });
    }
	private void retrieveSocialMediaPost(User user) {
        // Prompt the user to enter the ID of the post to retrieve
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Retrieve a Social Media Post");
        dialog.setHeaderText("Enter the ID of the post to retrieve:");
        dialog.setContentText("Post ID:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(id -> {
            try {
                int postId = Integer.parseInt(id);
                // Call your retrieve method here
                SocialMediaPost retrievedPost = socialMediaAnalyzer.retrievePost(postId);
                if (retrievedPost != null) {
                    // Post retrieved successfully
                    // Display the post details to the user
                    String postDetails = "Post ID: " + retrievedPost.getId() + "\n"
                            + "Author: " + retrievedPost.getAuthor() + "\n"
                            + "Content: " + retrievedPost.getContent() + "\n"
                            + "Likes: " + retrievedPost.getLikes() + "\n"
                            + "Shares: " + retrievedPost.getShares();
                    // Display postDetails to the user (e.g., in a message box)
                } else {
                    // Handle the case where the post was not found
                    // You may want to show an error message
                }
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid number
                // You may want to show an error message
            }
        });
    }

	private void deleteSocialMediaPost(User user) {
        // Prompt the user to enter the ID of the post to delete
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete a Social Media Post");
        dialog.setHeaderText("Enter the ID of the post to delete:");
        dialog.setContentText("Post ID:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(id -> {
            try {
                int postId = Integer.parseInt(id);
                // Call your delete method here
                if (socialMediaAnalyzer.removePost(postId)) {
                    // Post deleted successfully
                    // You may want to update the GUI or display a message
                } else {
                    // Handle the case where the post was not found
                    // You may want to show an error message
                }
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid number
                // You may want to show an error message
            }
        });
    }

	private void showRegisterUserView() {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Register a New User");
        dialog.setHeaderText("Please enter user details");

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        ButtonType registerButtonType = new ButtonType("Register", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(new VBox(usernameField, passwordField, firstNameField, lastNameField));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == registerButtonType) {
                return new User(usernameField.getText(), passwordField.getText(), firstNameField.getText(), lastNameField.getText(), UserType.STANDARD);
            }
            return null;
        });

        dialog.showAndWait().ifPresent(newUser -> {
            userProfileManager.registerUser(newUser);
            csvHandler.addUserToCSV(newUser);
            responseLabel.setText("User registered successfully.");
        });
    }

    private void showEditProfileView() {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Edit User Profile");
        dialog.setHeaderText("Please enter new user details");

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("New Password");

        ButtonType editButtonType = new ButtonType("Save Changes", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(new VBox(usernameField, firstNameField, lastNameField, passwordField));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == editButtonType) {
                String username = usernameField.getText();
                User currentUser = userProfileManager.getUserByUsername(username);
                if (currentUser != null) {
                    String newFirstName = firstNameField.getText();
                    String newLastName = lastNameField.getText();
                    String newPassword = passwordField.getText();
                    currentUser.setFirstName(newFirstName);
                    currentUser.setLastName(newLastName);
                    if (!newPassword.isEmpty()) {
                        currentUser.setPassword(newPassword);
                    }
                    return currentUser;
                } else {
                    responseLabel.setText("User not found.");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(updatedUser -> {
            if (updatedUser != null) {
                responseLabel.setText("User profile updated.");
            }
        });
    }

    private void addSocialMediaPost(User user) {
        // Assuming you have a form or dialog to get post details
        String idText = postIdTextField.getText();
        String content = contentTextField.getText();
        String author = authorTextField.getText();
        String likesText = likesTextField.getText();
        String sharesText = sharesTextField.getText();
        String dateTime = dateTimePicker.getValue().toString();

        try {
            int id = Integer.parseInt(idText);
            int likes = Integer.parseInt(likesText);
            int shares = Integer.parseInt(sharesText);

            // Validate the input
            if (isInputValid(id, likes, shares, dateTime)) {
                SocialMediaPost newPost = new SocialMediaPost(id, content, author, likes, shares);
                user.addSocialMediaPost(newPost); // Add the post to the user's collection
                // Optionally, update your GUI to show the new post
            } else {
                // Provide feedback to the user that the input is not valid
                // Display an error message or alert
            }
        } catch (NumberFormatException e) {
            // Handle the case where parsing fails (non-integer input)
            // Display an error message or alert
        }
    }

    private boolean isInputValid(int id, int likes, int shares, String dateTime) {
        // Implement your validation logic here, e.g., check for uniqueness, numeric likes and shares, valid date-time format, etc.
        return true; // Return true if input is valid, otherwise false
    }

    private void showVIPUpgradeView() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Upgrade to VIP");
        dialog.setHeaderText("Upgrade to VIP for premium features");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        ButtonType upgradeButtonType = new ButtonType("Upgrade", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(upgradeButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(new VBox(new Label("Enter your username: "), usernameField));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == upgradeButtonType) {
                String username = usernameField.getText();
                User currentUser = userProfileManager.getUserByUsername(username);
                if (currentUser != null) {
                    return upgradeButtonType;
                } else {
                    responseLabel.setText("User not found.");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            if (result == upgradeButtonType) {
                String username = usernameField.getText();
                User currentUser = userProfileManager.getUserByUsername(username);
                if (currentUser != null) {
                    vipManager.upgradeToVIP(currentUser);
                    responseLabel.setText("Congratulations! You are now a VIP member.");
                }
            }
        });
    }
}
