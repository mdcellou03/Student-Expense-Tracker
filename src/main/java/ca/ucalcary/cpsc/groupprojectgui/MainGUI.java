package ca.ucalcary.cpsc.groupprojectgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        MainController cont = fxmlLoader.getController();
        if (file!=null){
            cont.load(file);
        }
        stage.setTitle("StudentExpense Tracker");
        stage.setScene(scene);
        stage.show();
    }
    private static File file = null;
    public static void main(String[] args) {
        if (args.length>2){
            System.out.println("Expected one command line argument");
        }
        if (args.length == 1){
            String filename = args[0];
            file = new File(filename);
            if (!file.exists()|| !file.canRead()){
                System.err.println("cannot load from file");
                System.exit(1);
            }
        }
        launch();
    }
}