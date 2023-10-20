package gui;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.List;
import model.Post;  // Correct the import statement

public class DataVisualizationView {
    public void showDataVisualization(List<Post> posts) {
        // Create a PieChart and configure it
        List<PieChart.Data> pieChartData = generatePieChartData(posts);
        PieChart pieChart = new PieChart(FXCollections.observableArrayList(pieChartData));
        
        // Create a button to generate the chart
        Button generateChartButton = new Button("Generate Chart");
        generateChartButton.setOnAction(event -> showChart(pieChart));
        
        // Create a layout for the view
        VBox layout = new VBox(pieChart, generateChartButton);
        
        // Create a scene and stage for the view
        Scene scene = new Scene(layout, 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Data Visualization");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    private List<PieChart.Data> generatePieChartData(List<Post> posts) {
        List<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Post post : posts) {
            pieChartData.add(new PieChart.Data(post.getCategory(), post.getShares()));
        }

        return pieChartData;
    }

    private void showChart(PieChart pieChart) {
        Stage chartStage = new Stage();
        chartStage.setTitle("Post Distribution Chart");
        Scene chartScene = new Scene(pieChart);
        chartStage.setScene(chartScene);
        chartStage.show();
    }
}
