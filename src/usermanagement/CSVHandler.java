package usermanagement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler {
    private String csvFilePath;

    public CSVHandler(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public void writeUsersToCSV(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new File(csvFilePath))) {
            StringBuilder sb = new StringBuilder();
            sb.append("Username,Password,FirstName,LastName,UserType\n");

            for (User user : users) {
                sb.append(user.getUsername()).append(",");
                sb.append(user.getPassword()).append(",");
                sb.append(user.getFirstName()).append(",");
                sb.append(user.getLastName()).append(",");
                sb.append(user.getUserType()).append("\n");
            }

            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<User> readUsersFromCSV() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue; // Skip the header line
                }

                String[] data = line.split(",");
                if (data.length == 5) {
                    String username = data[0];
                    String password = data[1];
                    String firstName = data[2];
                    String lastName = data[3];
                    UserType userType = UserType.valueOf(data[4]);

                    User user = new User(username, password, firstName, lastName, userType);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void addUserToCSV(User newUser) {
        try (FileWriter fileWriter = new FileWriter(csvFilePath, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            StringBuilder sb = new StringBuilder();
            sb.append(newUser.getUsername()).append(",");
            sb.append(newUser.getPassword()).append(",");
            sb.append(newUser.getFirstName()).append(",");
            sb.append(newUser.getLastName()).append(",");
            sb.append(newUser.getUserType()).append("\n");

            bufferedWriter.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}