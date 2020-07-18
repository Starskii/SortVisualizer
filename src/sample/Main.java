package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jdk.jfr.Event;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    Button sortBtn = new Button();

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Creating Bar Graph
        int size = 100;
        int maxValue = 100;
        int windowX = 1920;
        int windowY = 1020;

        int[] unsortedArray = createUnsortedArray(size, maxValue);

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Position");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value");

        XYChart.Series<Number, Number> graph = new XYChart.Series<>();
        for(int i = 0; i < size; i++){
            graph.getData().add(new XYChart.Data<>(i, unsortedArray[i]));
        }

        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("Unsorted Array");

        scatterChart.getData().addAll(graph);
        scatterChart.setPrefSize(windowX-100,windowY);
        //End Creating Bar Graph
        primaryStage.setTitle("Sort Visualizer");
        GridPane layout = new GridPane();
        layout.getChildren().add(0,scatterChart);
        sortBtn = new Button("Sort");
        layout.getChildren().add(1,sortBtn);
        Scene scene = new Scene(layout, windowX,windowY);
        primaryStage.setScene(scene);
        primaryStage.show();

            sortBtn.setOnAction(actionEvent -> {
                try {
                    insertionSort(graph,unsortedArray);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }

    public void insertionSort(XYChart.Series<Number, Number> graph, int[] unsortedArray) throws InterruptedException {
        int lowPos = 0;
        int swappedValue = 0;
        for(int i = 0; i < unsortedArray.length; i++) {
            lowPos = i;
            for (int j = i; j < unsortedArray.length; j++) {
                if(unsortedArray[j] < unsortedArray[lowPos]){
                    lowPos = j;
                }
            }
            //Swap lowPos value with i
            swappedValue = unsortedArray[i];
            unsortedArray[i] = unsortedArray[lowPos];
            unsortedArray[lowPos] = swappedValue;
            updateGraph(graph, unsortedArray);
        }
    }

    public void updateGraph( XYChart.Series<Number, Number> graph, int[] updatedArray){
            graph.getData().clear();
            for(int i = 0; i < updatedArray.length; i++){
                graph.getData().add(new XYChart.Data<>(i, updatedArray[i]));
            }
        }

    int[] createUnsortedArray(int size, int maxValue){
        int[] unsortedArray = new int[size];
        Random randy = new Random();
        for(int i = 0; i < size; i++){
            unsortedArray[i] = Math.abs(randy.nextInt() % maxValue);
        }
        return unsortedArray;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
