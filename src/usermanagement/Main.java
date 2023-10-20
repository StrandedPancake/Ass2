package usermanagement;

import usermanagement.UserProfileManager;
import usermanagement.VIPManager;
import usermanagement.UserFactory;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String csvFilePath = "C:/Users/samla/eclipse-workspace/AdvancedProgramming/SocialMediaAnalyzer3/posts.csv";

        UserProfileManager userProfileManager = new UserProfileManager();
        VIPManager vipManager = new VIPManager(userProfileManager);
        UserFactory userFactory = new UserFactory();

        // Load user information from the CSV file when the program starts
        CSVHandler csvHandler = new CSVHandler(csvFilePath);
        List<User> users = csvHandler.readUsersFromCSV();
        userProfileManager.setUsers(users);

        int choice;

        do {
            System.out.println(" ");
            System.out.println("User Management Menu");
            System.out.println("1) Register a new user");
            System.out.println("2) Login as a user");
            System.out.println("3) Edit user profile");
            System.out.println("4) Upgrade to VIP");
            System.out.println("5) Exit");
            System.out.print("Please select: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registerUser(userProfileManager, userFactory, scanner, csvHandler); // Pass CSVHandler to the registerUser method
                    break;
                case 2:
                    loginUser(userProfileManager, scanner);
                    break;
                case 3:
                    editUserProfile(userProfileManager, scanner); // Pass CSVHandler to the editUserProfile method
                    break;
                case 4:
                    upgradeToVIP(vipManager, userProfileManager, scanner);
                    break;
                case 5:
                    System.out.println("Thanks for using User Management System!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void registerUser(UserProfileManager userProfileManager, UserFactory userFactory, Scanner scanner, CSVHandler csvHandler) {
        System.out.println("Registering a new user...");
        System.out.print("Enter a username: ");
        String username = scanner.next();
        System.out.print("Enter a password: ");
        String password = scanner.next();
        System.out.print("Enter first name: ");
        String firstName = scanner.next();
        System.out.print("Enter last name: ");
        String lastName = scanner.next();
        System.out.print("Select user type (1 for ADMIN, 2 for STANDARD): ");
        int userTypeChoice = scanner.nextInt();

        UserType userType = (userTypeChoice == 1) ? UserType.ADMIN : UserType.STANDARD;

        User newUser = new User(username, password, firstName, lastName, userType);
        userProfileManager.registerUser(newUser);
        
        System.out.println("User registered successfully.");

        // Add the new user to the CSV
        csvHandler.addUserToCSV(newUser);
        // Let's print the user data to confirm it's correct
        System.out.println("New user data:");
        System.out.println("Username: " + newUser.getUsername());
        System.out.println("Password: " + newUser.getPassword());
        System.out.println("First Name: " + newUser.getFirstName());
        System.out.println("Last Name: " + newUser.getLastName());
        System.out.println("User Type: " + newUser.getUserType());
    }
	 private static void loginUser(UserProfileManager userProfileManager, Scanner scanner) {
		System.out.println("Login as a user...");
		System.out.print("Enter username: ");
		String username = scanner.next();
		System.out.print("Enter password: ");
		String password = scanner.next();
		User loggedInUser = userProfileManager.loginUser(username, password);
		if (loggedInUser != null) {
			System.out.println("Logged in as: " + loggedInUser.getUsername());
		} else {
			System.out.println("Login failed. Invalid credentials.");
		}
	}

	private static void editUserProfile(UserProfileManager userProfileManager, Scanner scanner) {
		System.out.println("Edit user profile...");
		System.out.print("Enter username: ");
		String username = scanner.next();
		User user = userProfileManager.getUserByUsername(username);
		if (user != null) {
			System.out.print("Enter new first name: ");
			String firstName = scanner.next();
			System.out.print("Enter new last name: ");
			String lastName = scanner.next();
			System.out.print("Enter new password: ");
			String newPassword = scanner.next();
			userProfileManager.editUserProfile(user, firstName, lastName, newPassword);
			System.out.println("User profile updated.");
		} else {
			System.out.println("User not found.");
		}
	}

	private static void upgradeToVIP(VIPManager vipManager, UserProfileManager userProfileManager, Scanner scanner) {
		System.out.println("Upgrading to VIP...");
		System.out.print("Enter username: ");
		String username = scanner.next();
		User user = userProfileManager.getUserByUsername(username);
		if (user != null) {
			vipManager.upgradeToVIP(user);
			System.out.println("User upgraded to VIP.");
		} else {
			System.out.println("User not found.");
		}
	}
}