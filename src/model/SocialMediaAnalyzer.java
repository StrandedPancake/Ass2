package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import usermanagement.CSVHandler;

public class SocialMediaAnalyzer {
    private Map<Integer, Post> posts;
    private CSVHandler csvHandler;
    private String csvFilePath; // Store the file path

    public SocialMediaAnalyzer(List<Post> initialPosts, String csvFilePath) {
        posts = new HashMap<>();

        // Add the initial posts to the posts map
        for (Post post : initialPosts) {
            posts.put(post.getId(), post);
        }

        this.csvFilePath = csvFilePath; // Initialize the file path
        csvHandler = new CSVHandler(csvFilePath);
    }

    public void addPost(Post post) {
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

    public Post retrievePost(int postId) {
        return posts.get(postId);
    }

    public List<Post> retrieveTopNPostsByLikes(int n) {
        List<Post> allPosts = new ArrayList<>(posts.values());
        Collections.sort(allPosts, new PostComparator(true));
        return allPosts.subList(0, Math.min(n, allPosts.size()));
    }

    public List<Post> retrieveTopNPostsByShares(int n) {
        List<Post> allPosts = new ArrayList<>(posts.values());
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
}
