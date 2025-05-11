import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class pack {

    Queue<card> deck = new LinkedList<card>();

    public pack() {
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 13; j++) {

                String shape = null;
                switch (i) {
                    case 0:
                        shape = "Hearts";
                        break;
                    case 1:
                        shape = "Diamonds";
                        break;
                    case 2:
                        shape = "Clubs";
                        break;
                    case 3:
                        shape = "Spades";
                        break;
                }
                if (j==1)
                    deck.add(new card(14, shape));
                else 
                    deck.add(new card(j, shape));
            }
        }
    }

    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle((List<?>) deck);
    }

    public card draw() {
        card drawnCard = deck.poll();
        deck.add(drawnCard);
        return drawnCard;

    }

    public static void main(String[] args) {
        pack p = new pack();
        for (int i = 0; i < 53; i++) {
            card c = p.draw();
            System.out.println(c.toString());

        }
        System.out.println();
        System.out.println();

        for (int i = 0; i < 53; i++) {
            card c = p.draw();
            System.out.println(c.toString());

        }
    }
}
