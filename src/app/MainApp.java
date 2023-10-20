package app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import usermanagement.*;

public class MainApp extends Application {
    private UserProfileManager userProfileManager;
    private VIPManager vipManager;
    private UserFactory userFactory;
    private CSVHandler csvHandler;
    private Stage primaryStage;
    private Label responseLabel; // Label to display responses in the GUI
    private TextField usernameField; // TextField for username

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        userProfileManager = new UserProfileManager();
        vipManager = new VIPManager(userProfileManager);
        userFactory = new UserFactory();

        // Load user information from the CSV file when the program starts
        csvHandler = new CSVHandler("C:/Users/samla/eclipse-workspace/AdvancedProgramming/SocialMediaAnalyzer3/posts.csv");
        csvHandler.readUsersFromCSV(); // Load users from CSV into userProfileManager

        primaryStage.initStyle(StageStyle.UTILITY); // Set the stage style to UTILITY

        responseLabel = new Label(); // Create a label for responses
        showMainMenu();
    }

    private void showMainMenu() {
        StackPane menu = new StackPane(); // Use StackPane to center content

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

        // Set alignment of buttonBox to center
        buttonBox.setAlignment(Pos.CENTER);

        // Create a VBox to hold both the buttonBox and responseLabel
        VBox contentBox = new VBox(20);
        contentBox.getChildren().addAll(buttonBox, responseLabel);

        // Center the content in the StackPane
        menu.getChildren().add(contentBox);

        Scene scene = new Scene(menu, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showLoginView() {
        // Create a dialog for user login
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Login as a User");
        dialog.setHeaderText("Please enter your credentials");

        // Create text input fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // Create buttons for OK and Cancel
        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Set the content of the dialog
        dialog.getDialogPane().setContent(new VBox(usernameField, passwordField));

        // Wait for the user to close the dialog
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                String username = usernameField.getText();
                String password = passwordField.getText(); // Get the entered password

                User loggedInUser = userProfileManager.loginUser(username, password);

                if (loggedInUser != null) {
                    return loggedInUser; // Valid login
                } else {
                    responseLabel.setText("User not found."); // User not found
                }
            }
            return null;
        });

        // Show the dialog and handle the result
        dialog.showAndWait().ifPresent(loggedInUser -> {
            responseLabel.setText("Logged in as: " + loggedInUser.getUsername());
        });
    }


	private void showRegisterUserView() {
        // Create a dialog for user registration
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Register a New User");
        dialog.setHeaderText("Please enter user details");

        // Create text input fields
        TextField usernameField = new TextField(); // Added this line
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        // Create buttons for OK and Cancel
        ButtonType registerButtonType = new ButtonType("Register", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);

        // Set the content of the dialog
        dialog.getDialogPane().setContent(new VBox(usernameField, passwordField, firstNameField, lastNameField));

        // Wait for the user to close the dialog
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == registerButtonType) {
                return new User(usernameField.getText(), passwordField.getText(), firstNameField.getText(), lastNameField.getText(), UserType.STANDARD);
            }
            return null;
        });

        // Show the dialog and handle the result
        dialog.showAndWait().ifPresent(newUser -> {
            userProfileManager.registerUser(newUser);
            csvHandler.addUserToCSV(newUser);
            responseLabel.setText("User registered successfully.");
        });
    }

	private void showEditProfileView() {
	    // Create a dialog for editing user profile
	    Dialog<User> dialog = new Dialog<>();
	    dialog.setTitle("Edit User Profile");
	    dialog.setHeaderText("Please enter new user details");

	    // Create text input fields
	    usernameField = new TextField(); // Initialize it here
	    usernameField.setPromptText("Username");
	    TextField firstNameField = new TextField();
	    firstNameField.setPromptText("First Name");
	    TextField lastNameField = new TextField();
	    lastNameField.setPromptText("Last Name");
	    PasswordField passwordField = new PasswordField();
	    passwordField.setPromptText("New Password");

	    // Create buttons for OK and Cancel
	    ButtonType editButtonType = new ButtonType("Save Changes", ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

	    // Set the content of the dialog
	    dialog.getDialogPane().setContent(new VBox(usernameField, firstNameField, lastNameField, passwordField));

	    // Wait for the user to close the dialog
	    dialog.setResultConverter(dialogButton -> {
	        if (dialogButton == editButtonType) {
	            String username = usernameField.getText(); // Get the username from the input field
	            User currentUser = userProfileManager.getUserByUsername(username);
	            if (currentUser != null) {
	                return new User(username, passwordField.getText(), firstNameField.getText(), lastNameField.getText(), UserType.STANDARD);
	            } else {
	                responseLabel.setText("User not found.");
	            }
	        }
	        return null;
	    });

	    // Show the dialog and handle the result
	    dialog.showAndWait().ifPresent(updatedUser -> {
	        if (updatedUser != null) {
	            userProfileManager.editUserProfile(updatedUser, updatedUser.getFirstName(), updatedUser.getLastName(), updatedUser.getPassword());
	            responseLabel.setText("User profile updated.");
	        }
	    });
	}

    private void showVIPUpgradeView() {
        // Create a dialog for VIP upgrade
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Upgrade to VIP");
        dialog.setHeaderText("Upgrade to VIP for premium features");

        // Create text input field for username
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        // Create buttons for Upgrade and Cancel
        ButtonType upgradeButtonType = new ButtonType("Upgrade", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(upgradeButtonType, ButtonType.CANCEL);

        // Set the content of the dialog
        dialog.getDialogPane().setContent(new VBox(new Label("Enter your username: "), usernameField));

        // Wait for the user to close the dialog
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == upgradeButtonType) {
                String username = usernameField.getText();
                User currentUser = userProfileManager.getUserByUsername(username);
                if (currentUser != null) {
                    return upgradeButtonType; // Continue with the VIP upgrade
                } else {
                    responseLabel.setText("User not found.");
                }
            }
            return null;
        });

        // Show the dialog and handle the result
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