package service;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class InformationSingleton {

    private static InformationSingleton informationSingleton;
    private int numberOfCardsHorizontal;
    private int numberOfCardsVertical;
    private IntegerProperty countErrors;
    private IntegerProperty score;
    private long startTime;
    private long endTime;

    private InformationSingleton() {
        numberOfCardsHorizontal = 2;
        numberOfCardsVertical = 2;
        countErrors = new SimpleIntegerProperty(0);
        score = new SimpleIntegerProperty(0);
    }

    public static InformationSingleton getInformationSingleton() {
        if(informationSingleton == null) {
            informationSingleton = new InformationSingleton();
        }

        return informationSingleton;
    }

    public void incrementCountErrors() {
        countErrors.set(countErrors.get() + 1);
    }

    public void resetCountErrors() {
        countErrors.set(0);
    }

    public IntegerProperty getCountErrorsProperty() {
        return countErrors;
    }

    public void setNumberOfCards(int numberOfCardsHorizontal, int numberOfCardsVertical) {
        this.numberOfCardsHorizontal = numberOfCardsHorizontal;
        this.numberOfCardsVertical = numberOfCardsVertical;
    }

    public int getNumberOfCardsHorizontal() {
        return numberOfCardsHorizontal;
    }

    public int getNumberOfCardsVertical() {
        return numberOfCardsVertical;
    }

    public void incrementScore() {
        score.set(score.get() + 1);
    }

    public IntegerProperty getScoreProperty() {
        return score;
    }

    public void resetScore() {
        score.set(0);
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
