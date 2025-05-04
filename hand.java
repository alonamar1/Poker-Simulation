
public class hand {

    public card firstCard;
    public card secondCard;
    
    public hand() {
        this.firstCard = new card();
        this.secondCard = new card();
    }

    public hand(card firstCard, card secondCard) {
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }

    public card getFirstCard() {
        return firstCard;
    }

    public void setCard(int firOrSec, card firstCard) {
        if (firOrSec == 1) {
            this.firstCard = firstCard;
        } else if (firOrSec == 2) {
            this.secondCard = firstCard;
        }
    }

    public card getSecondCard() {
        return secondCard;
    }

    public String toString() {
        return "Hand: " + firstCard.toString() + ", " + secondCard.toString();
    }

    public static void main(String[] args) {
    }
}

