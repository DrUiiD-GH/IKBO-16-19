package model;

import controller.MainViewController;
import javafx.scene.image.Image;
import service.ImageOpener;
import service.InformationSingleton;
import service.SoundPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayField extends MainViewController {

    private int numberOfCardsHorizontal;
    private int numberOfCardsVertical;

    private ImageOpener imageOpener;
    private InformationSingleton informationSingleton;
    private SoundPlayer soundPlayer;

    private List<Card> cards;
    private Card lastSelectedCard;

    public PlayField(int numberOfCardsHorizontal, int numberOfCardsVertical) {
        this.numberOfCardsHorizontal = numberOfCardsHorizontal;
        this.numberOfCardsVertical = numberOfCardsVertical;

        informationSingleton = InformationSingleton.getInformationSingleton();
        imageOpener = new ImageOpener();
        soundPlayer = new SoundPlayer();
        cards = createCards();
    }

    public PlayField() {
    }

    private List<Card> createCards() {
        List<Image> images = imageOpener.getCardImages();
        List<Card> cards = new ArrayList<>();

        for(int i = 0; i < (numberOfCardsHorizontal * numberOfCardsVertical)/2; i++) {
            Image image = images.get(i);
            cards.add(new Card(i, image));
            cards.add(new Card(i, image));
        }

        Collections.shuffle(cards);

        return cards;
    }

    public void openCard(Card card) {
        if(!cards.contains(card) || card.isFound())
            return;

        if(lastSelectedCard == null) {
            lastSelectedCard = card;
            soundPlayer.playFlipCard();
            return;
        }

        if(card.getCardUuid().equals(lastSelectedCard.getCardUuid()))
            return;

        soundPlayer.playFlipCard();
        if(card.getCardId() == lastSelectedCard.getCardId()) {
            card.setFound(true);
            lastSelectedCard.setFound(true);
            lastSelectedCard = null;
            informationSingleton.incrementScore();
            soundPlayer.playSuccess();
        } else {
            lastSelectedCard = null;
            soundPlayer.playFail();
            informationSingleton.incrementCountErrors();
        }
    }

    public boolean isFinish() {
        return cards.stream().allMatch(Card::isFound);
    }

    public int getNumberOfCardsHorizontal() {
        return numberOfCardsHorizontal;
    }

    public int getNumberOfCardsVertical() {
        return numberOfCardsVertical;
    }

    public Card getLastSelectedCard() {
        return lastSelectedCard;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Image getHiddenCardImage() {
        return imageOpener.getHiddenCardImage();
    }
}
