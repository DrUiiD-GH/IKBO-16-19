package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import service.InformationSingleton;
import service.WindowsManager;

public class FinishGameController {

    @FXML
    private Button mainMenuBtn;

    @FXML
    private Label resultErrorsLabel;

    @FXML
    private Label resultTimeLabel;

    @FXML
    private Button playAgainBtn;

    private InformationSingleton informationSingleton;

    @FXML
    private void initialize() {
        informationSingleton = InformationSingleton.getInformationSingleton();
        resultErrorsLabel.setText(informationSingleton.getCountErrorsProperty().getValue().toString());
        resultTimeLabel.setText(String.format("%d seconds", informationSingleton.getEndTime() - informationSingleton.getStartTime()));
    }

    @FXML
    private void playAgainBtnOnAction(ActionEvent event) {
        Stage stage = (Stage) playAgainBtn.getScene().getWindow();
        stage.close();
        WindowsManager.showPlayWindow();
    }

    @FXML
    private void mainMenuBtnOnAction(ActionEvent event) {
        Stage stage = (Stage) mainMenuBtn.getScene().getWindow();
        stage.close();
        WindowsManager.showMainMenuWindow();
    }
}
