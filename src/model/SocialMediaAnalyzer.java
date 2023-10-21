package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import usermanagement.CSVHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TextField;

public class SocialMediaAnalyzer {
    private Map<Integer, SocialMediaPost> posts;
    private CSVHandler csvHandler;
    private String csvFilePath;

    public SocialMediaAnalyzer(List<SocialMediaPost> initialPosts, String csvFilePath) {
        posts = new HashMap<>();
        for (SocialMediaPost post : initialPosts) {
            posts.put(post.getId(), post);
        }
        this.csvFilePath = csvFilePath;
        csvHandler = new CSVHandler(csvFilePath);
    }

    public void addPost(SocialMediaPost post) {
        posts.put(post.getId(), post);
        csvHandler.writePosts(new ArrayList<>(posts.values()));
    }

    public boolean removePost(int postId) {
        if (posts.remove(postId) != null) {
            csvHandler.writePosts(new ArrayList<>(posts.values()));
            return true;
        }
        return false;
    }

    public SocialMediaPost retrievePost(int postId) {
        return posts.get(postId);
    }

    public List<SocialMediaPost> retrieveTopNPostsByLikes(int n) {
        List<SocialMediaPost> allPosts = new ArrayList<>(posts.values());
        Collections.sort(allPosts, new PostComparator(true));
        return allPosts.subList(0, Math.min(n, allPosts.size()));
    }

    public List<SocialMediaPost> retrieveTopNPostsByShares(int n) {
        List<SocialMediaPost> allPosts = new ArrayList<>(posts.values());
        Collections.sort(allPosts, new PostComparator(false));
        return allPosts.subList(0, Math.min(n, allPosts.size()));
    }

    public String getCsvFilePath() {
        return csvFilePath;
    }

    public void setCsvFilePath(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        csvHandler = new CSVHandler(csvFilePath);
    }

    public void addPostWithDialog(TextField postIdTextField, TextField contentTextField, TextField authorTextField,
            TextField likesTextField, TextField sharesTextField) {
        try {
            int id = Integer.parseInt(postIdTextField.getText());
            int likes = Integer.parseInt(likesTextField.getText());
            int shares = Integer.parseInt(sharesTextField.getText());

            if (isInputValid(id, likes, shares)) {
                String content = contentTextField.getText();
                String author = authorTextField.getText();
                SocialMediaPost newPost = new SocialMediaPost(id, content, author, likes, shares);
                addPost(newPost);
                // Optionally, update your GUI to show the new post
            } else {
                // Provide feedback to the user that the input is not valid
                showErrorMessage("Invalid Input", "Input is not valid. Please check your input values.");
            }
        } catch (NumberFormatException e) {
            // Handle the case where parsing fails (non-integer input)
            showErrorMessage("Invalid Input", "Please enter valid numeric values for ID, Likes, and Shares.");
        }
    }

    private boolean isInputValid(int id, int likes, int shares) {
        // Implement your validation logic here, e.g., check for uniqueness, numeric likes and shares, etc.
        return true; // Return true if input is valid, otherwise false
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
