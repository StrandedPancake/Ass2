package gui;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;  // Import IOException
import java.io.PrintWriter;  // Import PrintWriter
import model.Post;  // Correct the import statement

public class DataExportView {
    public void exportPostToFile(Post post) {
        Stage stage = new Stage();

        // Create a FileChooser for selecting the folder and filename for export
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        // Display a Save dialog for choosing the export location and filename
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            // Implement the export operation here, e.g., writing the post data to the selected file
            // You can use FileWriter, PrintWriter, or any other method to write the post data to the file

            // Example:
            try {
                PrintWriter writer = new PrintWriter(file);
                writer.println("Post ID: " + post.getId());
                writer.println("Content: " + post.getContent());
                // Add more post properties as needed
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}