package SocialMediaAnalyzer3;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler {
    private String csvFilePath;

    public CSVHandler(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public void writePosts(List<Post> posts) {
        try (FileWriter writer = new FileWriter(csvFilePath)) {
            for (Post post : posts) {
                writer.write(post.getId() + "," +
                             post.getContent() + "," +
                             post.getAuthor() + "," +
                             post.getLikes() + "," +
                             post.getShares() + "," +
                             post.getDateTime() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Post> readPostsFromCSV() {
        List<Post> posts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean isFirstLine = true; // To skip the header line

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }

                String[] data = line.split(",");
                if (data.length >= 6) {
                    int id = Integer.parseInt(data[0]);
                    String content = data[1];
                    String author = data[2];
                    int likes = Integer.parseInt(data[3]);
                    int shares = Integer.parseInt(data[4]);
                    String dateTime = data[5];
                    posts.add(new Post(id, content, author, likes, shares, dateTime));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return posts;
    }
}
