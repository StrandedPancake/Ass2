package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import usermanagement.User;
import usermanagement.VIPManager;

public class VIPUpgradeView {
    public void showVIPUpgrade(User user, VIPManager vipManager) {
        // Display the VIP upgrade prompt and subscription message
        Label upgradeLabel = new Label("Upgrade to VIP for premium features.");
        Label subscriptionLabel = new Label("Subscribe now and enjoy exclusive benefits!");

        // Add an "Upgrade to VIP" button
        Button upgradeButton = new Button("Upgrade to VIP");
        upgradeButton.setOnAction(event -> upgradeToVIP(user, vipManager));

        // Create a layout for the view
        VBox layout = new VBox(upgradeLabel, subscriptionLabel, upgradeButton);

        // Create a scene and stage for the view
        Scene scene = new Scene(layout, 400, 300);
        Stage stage = new Stage();
        stage.setTitle("VIP Upgrade");
        stage.setScene(scene);
        stage.show();
    }

    private void upgradeToVIP(User user, VIPManager vipManager) {
        // Implement event handling to upgrade a user to VIP using your usermanagement classes
        vipManager.upgradeToVIP(user);
        System.out.println("Congratulations! You are now a VIP member.");
    }
}
