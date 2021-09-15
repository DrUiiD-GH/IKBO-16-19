package model;

import javafx.scene.image.Image;

import java.util.UUID;

public class Card extends PlayField {
    private int cardId;
    private Image imageFile;
    private boolean isFound;
    private UUID cardUuid;

    public Card(int cardId, Image imageFile) {
        this.cardId = cardId;
        this.imageFile = imageFile;
        this.isFound = false;
        cardUuid = UUID.randomUUID();
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public UUID getCardUuid() {
        return cardUuid;
    }

    public Image getImageFile() {
        return imageFile;
    }

    public void setImageFile(Image imageFile) {
        this.imageFile = imageFile;
    }

    public boolean isFound() {
        return isFound;
    }

    public void setFound(boolean found) {
        isFound = found;
    }
}
