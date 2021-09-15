import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import service.WindowsManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        WindowsManager.showMainMenuWindow();
        //WindowsManager.showPlayWindow();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

