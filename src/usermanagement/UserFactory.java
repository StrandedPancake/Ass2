package usermanagement;

public class UserFactory {
    public User createAdminUser() {
        return new User("admin123", "adminpassword", "Admin", "User", UserType.ADMIN);
    }

    public User createStandardUser() {
        return new User("user123", "userpassword", "Standard", "User", UserType.STANDARD);
    }
}
