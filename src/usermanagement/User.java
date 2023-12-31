package usermanagement;

import model.Post;
import model.SocialMediaPost;

public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private UserType userType;

    public User(String username, String password, String firstName, String lastName, UserType userType) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
    }

    // Getters and setters for all fields
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

	public void addPost(Post newPost) {
		// TODO Auto-generated method stub
		
	}

	public String getFullName() {
	    return firstName + " " + lastName;
	}

	public void addSocialMediaPost(SocialMediaPost newPost) {
		// TODO Auto-generated method stub
		
	}
}