package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class MainWindow extends Application {
    public static Stage myStage;
    public static DatagramSocket datagramSocket;
    public static InetAddress inetAddress;
    @Override
    public void start(Stage stage) throws IOException {
        datagramSocket = new DatagramSocket();
        inetAddress = InetAddress.getByName("localhost");

        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        Image icon = new Image("C:\\Users\\nikit\\IdeaProjects\\AppFoCoursesFX\\source\\icon.png");
        stage.getIcons().add(icon);

        stage.setTitle("Courser!");
        stage.setScene(scene);
        stage.show();
        myStage = stage;
    }
    public static void main(String[] args) {
        launch(args);
    }
}