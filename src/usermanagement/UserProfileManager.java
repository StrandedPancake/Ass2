package usermanagement;

import java.util.ArrayList;
import java.util.List;

public class UserProfileManager {
	private List<User> users; // Declare the 'users' list as an instance variable
	
    public UserProfileManager() {
        users = new ArrayList<>();
    }

    // Methods for user registration, login, and profile management
    public void registerUser(User user) {
        // Register a new user
    	users.add(user); // Add the user to the list
    }

    public User loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user; // Return the user if username and password match
            }
        }
        return null; // Return null if no matching user is found
    }

    public void editUserProfile(User user, String firstName, String lastName, String newPassword) {
        // Edit user profile
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(newPassword);
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
