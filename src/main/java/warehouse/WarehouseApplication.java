package warehouse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import warehouse.configuration.DataBaseSession;

import java.io.IOException;
import java.util.Objects;


public class WarehouseApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent rootScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/userInterface/main.fxml")));
        stage.setTitle("Warehouse");
//            Scene scene = new Scene(rootScene);
//            scene.getStylesheets().add(getClass().getResource("src/warehouse.css").toExternalForm());   Error NullPointerException
        Image iconImage = new Image("warehouse.png");
        stage.getIcons().add(iconImage);
        stage.setScene(new Scene(rootScene));
        stage.show();

    }



    public static void main(String[] args) {
        DataBaseSession.getInstance();
        launch(args);
        DataBaseSession.shutdown();

    }


}
