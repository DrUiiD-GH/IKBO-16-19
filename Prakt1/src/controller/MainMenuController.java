package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import service.InformationSingleton;
import service.WindowsManager;

public class MainMenuController extends MainViewController {
    @FXML
    private Button playGameBtn;

    @FXML
    private ChoiceBox<Integer> numCardsHorChoiceBox;

    @FXML
    private ChoiceBox<Integer> numCardsVerChoiceBox;

    @FXML
    private Label errorLabel;

    private int numberOfCardsHorizontal;
    private int numberOfCardsVertical;

    @FXML
    private void initialize() {
        numCardsVerChoiceBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4));
        numCardsVerChoiceBox.getSelectionModel().select(1);

        numCardsHorChoiceBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        numCardsHorChoiceBox.getSelectionModel().select(1);

        numberOfCardsHorizontal = numCardsHorChoiceBox.getValue();
        numberOfCardsVertical = numCardsVerChoiceBox.getValue();

        numCardsVerChoiceBox.setOnAction(event -> {
            numberOfCardsHorizontal = numCardsHorChoiceBox.getValue();
            numberOfCardsVertical = numCardsVerChoiceBox.getValue();

            errorLabel.setText("");
            playGameBtn.setDisable(false);
            if(!checkConfiguration(numberOfCardsHorizontal, numberOfCardsVertical)) {
                playGameBtn.setDisable(true);
                errorLabel.setText("*This configuration is not possible");
            }
        });
        numCardsHorChoiceBox.setOnAction(numCardsVerChoiceBox.getOnAction());
    }

    @FXML
    private void playGameBtnAction(ActionEvent event) {
        InformationSingleton informationSingleton = InformationSingleton.getInformationSingleton();
        informationSingleton.setNumberOfCards(numberOfCardsHorizontal, numberOfCardsVertical);

        Stage stage = (Stage) playGameBtn.getScene().getWindow();
        stage.close();
        WindowsManager.showPlayWindow();
    }

    public boolean checkConfiguration(int horizontal, int vertical) {
        return (horizontal * vertical) % 2 == 0;
    }
}
