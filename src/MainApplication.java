import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import SocialMediaAnalyzer3.SocialMediaAnalyzer;
import gui.SignInView;


public class MainApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize your application
        SocialMediaAnalyzer socialMediaAnalyzer = new SocialMediaAnalyzer(null, null);

        // Create the sign-in view
        SignInView signInView = new SignInView();

        // Create the scene
        Scene scene = new Scene(signInView.createSignInScene(socialMediaAnalyzer), 800, 600);

        // Set the stage
        primaryStage.setTitle("Social Media Analyzer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
