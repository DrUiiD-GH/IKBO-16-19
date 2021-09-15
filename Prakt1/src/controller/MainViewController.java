package controller;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Card;
import model.PlayField;
import service.InformationSingleton;
import service.WindowsManager;

import java.util.*;

import static javafx.beans.binding.Bindings.format;

public class MainViewController extends FinishGameController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label errorsCountLabel;

    @FXML
    private Label timeLabel;

    private GridPane fieldGridPane;
    private PlayField playField;
    private List<Card> cards;
    private Map<Card, ImageView> cardImageViewMap = new HashMap<>();
    private InformationSingleton informationSingleton;
    private Timer timer;


    @FXML
    private void initialize() {
        informationSingleton = InformationSingleton.getInformationSingleton();
        playField = new PlayField(informationSingleton.getNumberOfCardsHorizontal(), informationSingleton.getNumberOfCardsVertical());
        cards = playField.getCards();
        timer = new Timer();

        errorsCountLabel.textProperty().bind(format("%d", informationSingleton.getCountErrorsProperty()));
        scoreLabel.textProperty().bind(
                format("%d/%d", informationSingleton.getScoreProperty(),
                        informationSingleton.getNumberOfCardsHorizontal() * informationSingleton.getNumberOfCardsVertical() / 2));

        Platform.runLater(() -> {
            Stage stage = (Stage) scoreLabel.getScene().getWindow();
            stage.setOnCloseRequest((event) -> timer.cancel());
            createGrid(playField, mainBorderPane.getScene().widthProperty(), mainBorderPane.getScene().heightProperty());
        });
        startGame();
    }

    private void startGame() {
        informationSingleton.resetCountErrors();
        informationSingleton.resetScore();
        informationSingleton.setStartTime(System.currentTimeMillis() / 1000);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    informationSingleton.setEndTime(System.currentTimeMillis() / 1000);
                    timeLabel.setText(String.valueOf(informationSingleton.getEndTime() - informationSingleton.getStartTime()));
                });
            }
        }, 0, 1000);
    }

    private void stopGame() {
        Stage stage = (Stage)scoreLabel.getScene().getWindow();
        stage.close();
        timer.cancel();
        WindowsManager.showFinishGameWindow();
    }

    private void createGrid(PlayField playField, ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty) {
        fieldGridPane = new GridPane();
        fieldGridPane.setPadding(new Insets(10));
        fieldGridPane.setStyle("-fx-background-color: #006400");

        int numberOfCardsH = playField.getNumberOfCardsHorizontal();
        int numberOfCardsV = playField.getNumberOfCardsVertical();

        int index = 0;
        for (int i = 0; i < numberOfCardsH; i++) {
            for(int k = 0; k < numberOfCardsV; k++) {
                ImageView imageView = new ImageView(playField.getHiddenCardImage());
                imageView.fitWidthProperty().bind(heightProperty.divide(numberOfCardsV + 1));
                imageView.fitHeightProperty().bind(heightProperty.divide(numberOfCardsV + 1));

                cardImageViewMap.put(cards.get(index), imageView);
                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, clickOnCardHandler(cards.get(index), imageView));

                GridPane.setMargin(imageView, new Insets(10));
                fieldGridPane.add(imageView, i, k);

                index++;
            }
        }

        fieldGridPane.setAlignment(Pos.CENTER);
        mainBorderPane.setCenter(fieldGridPane);
    }

    private EventHandler clickOnCardHandler(Card card, ImageView imageView) {
        return e -> {
            playField.openCard(card);
            imageView.setImage(card.getImageFile());

            new Thread(() -> {
                if(playField.getLastSelectedCard() == null) {
                    fieldGridPane.setDisable(true);

                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    for(Card cardMap : cardImageViewMap.keySet()) {
                        if(!cardMap.isFound()) {
                            cardImageViewMap.get(cardMap).setImage(playField.getHiddenCardImage());
                        }
                    }
                    fieldGridPane.setDisable(false);
                }
            }).start();

            if(playField.isFinish()) {
                stopGame();
            }
        };
    }
}
