package SocialMediaAnalyzer3;
import java.util.Scanner;
import java.util.List;
import usermanagement.*;
import gui.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String csvFilePath = "C:/Users/samla/eclipse-workspace/AdvancedProgramming/SocialMediaAnalyzer2/posts.csv";

        CSVHandler csvHandler = new CSVHandler(csvFilePath);
        List<Post> initialPosts = csvHandler.readPostsFromCSV();
        SocialMediaAnalyzer analyzer = new SocialMediaAnalyzer(initialPosts, csvFilePath);

        int choice;

        do {
            System.out.println(" ");
            System.out.println("---------------------------------");
            System.out.println("Welcome to Social Media Analyzer!");
            System.out.println("---------------------------------");
            System.out.println("1) Add a social media post");
            System.out.println("2) Delete an existing social media post");
            System.out.println("3) Retrieve a social media post");
            System.out.println("4) Retrieve the top N posts with most likes");
            System.out.println("5) Retrieve the top N posts with most shares");
            System.out.println("6) Exit");
            System.out.print("Please select: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addPost(analyzer, csvHandler, scanner);
                    break;
                case 2:
                    deletePost(analyzer, scanner);
                    break;
                case 3:
                    retrievePost(analyzer, scanner);
                    break;
                case 4:
                    retrieveTopNPostsByLikes(analyzer, scanner);
                    break;
                case 5:
                    retrieveTopNPostsByShares(analyzer, scanner);
                    break;
                case 6:
                    System.out.println("Thanks for using Social Media Analyzer!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (choice != 6);

        scanner.close();
    }

    private static void addPost(SocialMediaAnalyzer analyzer, CSVHandler csvHandler, Scanner scanner) {
        System.out.println("Please provide the post ID:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()

        System.out.println("Please provide the post content:");
        String content = scanner.nextLine();

        System.out.println("Please provide the post author:");
        String author = scanner.nextLine();

        System.out.println("Please provide the number of likes of the post:");
        int likes = scanner.nextInt();

        System.out.println("Please provide the number of shares of the post:");
        int shares = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()

        System.out.println("Please provide the date and time of the post in the format of DD/MM/YYYY HH:MM:");
        String dateTime = scanner.nextLine();

        Post post = new Post(id, content, author, likes, shares, dateTime);
        analyzer.addPost(post);
        System.out.println("The post has been added to the collection!");
    }

    private static void retrievePost(SocialMediaAnalyzer analyzer, Scanner scanner) {
        System.out.println("Please provide the post ID to retrieve:");
        int idToRetrieve = scanner.nextInt();

        Post post = analyzer.retrievePost(idToRetrieve);
        if (post != null) {
            System.out.println("Post details:");
            System.out.println("ID: " + post.getId());
            System.out.println("Content: " + post.getContent());
            System.out.println("Author: " + post.getAuthor());
            System.out.println("Likes: " + post.getLikes());
            System.out.println("Shares: " + post.getShares());
            System.out.println("Date and Time: " + post.getDateTime());
        } else {
            System.out.println("Post not found!");
        }
    }

    private static void deletePost(SocialMediaAnalyzer analyzer, Scanner scanner) {
        System.out.println("Please provide the post ID to delete:");
        int idToDelete = scanner.nextInt();

        if (analyzer.removePost(idToDelete)) {
            System.out.println("Post with ID " + idToDelete + " has been deleted.");
        } else {
            System.out.println("Post not found!");
        }
    }

    private static void retrieveTopNPostsByLikes(SocialMediaAnalyzer analyzer, Scanner scanner) {
        System.out.print("Please specify the number of posts to retrieve (N): ");
        int n = scanner.nextInt();
        List<Post> topPosts = analyzer.retrieveTopNPostsByLikes(n);

        System.out.println("The top-" + n + " posts with the most likes are:");
        for (Post post : topPosts) {
            System.out.println(post);
        }
    }

    private static void retrieveTopNPostsByShares(SocialMediaAnalyzer analyzer, Scanner scanner) {
        System.out.print("Please specify the number of posts to retrieve (N): ");
        int n = scanner.nextInt();
        List<Post> topPosts = analyzer.retrieveTopNPostsByShares(n);

        System.out.println("The top-" + n + " posts with the most shares are:");
        for (Post post : topPosts) {
            System.out.println(post);
        }
    }
}
