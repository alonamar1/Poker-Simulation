import java.util.Arrays;
import java.util.Comparator;

public class player {
    private String name;
    private hand homepair;
    private int chips;
    private int bet;
    private int highestCard;
    private int kicker;
    private card[] hand;
    private card[] sides;

    boolean highCard;
    boolean pair;
    boolean twoPair;
    boolean threeOfAKind;
    boolean straight;
    boolean flush;
    boolean fullHouse;
    boolean fourOfAKind;
    boolean straightFlush;
    boolean royalFlush;
    boolean isFolded;
    
    public player(int chips) {
        this.chips = chips;
        this.pair = false;
        this.twoPair = false;
        this.threeOfAKind = false;
        this.straight = false;
        this.flush = false;
        this.fullHouse = false;
        this.fourOfAKind = false;
        this.straightFlush = false;
        this.royalFlush = false;
        this.isFolded = false;
        this.homepair = new hand();
        this.highestCard = 0;
        this.bet = 0;
        this.name = null;
        this.hand = new card[5];
        this.sides = new card[2];
        this.highCard = false;
        this.kicker = 0;
    }

    public player(String name, int chips) {
        this.name = name;
        this.chips = chips;
        this.pair = false;
        this.twoPair = false;
        this.threeOfAKind = false;
        this.straight = false;
        this.flush = false;
        this.fullHouse = false;
        this.fourOfAKind = false;
        this.straightFlush = false;
        this.royalFlush = false;
        this.homepair = new hand();
        this.bet = 0;
        this.highestCard = 0;
        this.isFolded = false;
        this.hand = new card[5];
        this.sides = new card[2];
        this.highCard = false;
        this.kicker = 0;
    }

    public String getName() {
        if (name == null) {
            return name;
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public hand getHomepair() {
        return homepair;
    }

    public void setHomepair(int firOrSec, card card) {
        this.homepair.setCard(firOrSec, card);
        hand[firOrSec-1] = card;
        if (firOrSec==2){
            if (homepair.getFirstCard().getNumber() == homepair.getSecondCard().getNumber()) {
                this.pair = true;
            }
            this.highestCard = Math.max(homepair.getFirstCard().getNumber(), homepair.getSecondCard().getNumber());
            highCard = true;
        }

    }

    public void winner(int winningChips) {
        chips += winningChips;
    }

    public void loser() {
        chips -= bet;
    }

    public void placeBet(int bet) {
        this.bet += bet;
    }

    public int getBet() {
        return bet;
    }

    public void fold() {
        isFolded = true;
        loser();
    }


    public void setUp() {
        bet = 0;
        homepair = new hand();        
        this.isFolded = false;
        clearHand();
    }

    public int getBestHand() {
        if (royalFlush) {
            return 9;
        } else if (straightFlush) {
            return 8;
        } else if (fourOfAKind) {
            return 7;
        } else if (fullHouse) {
            return 6;
        } else if (flush) {
            return 5;
        } else if (straight) {
            return 4;
        } else if (threeOfAKind) {
            return 3;
        } else if (twoPair) {
            return 2;
        } else if (pair) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getHighCard() {
        return highestCard;
    }

    public void clearHand() {
        this.pair = false;
        this.twoPair = false;
        this.threeOfAKind = false;
        this.straight = false;
        this.flush = false;
        this.fullHouse = false;
        this.fourOfAKind = false;
        this.straightFlush = false;
        this.royalFlush = false;
        this.highestCard = 0;
        this.highCard = false;
        this.kicker = 0;
    }

    public void checkAfterFlop(){
        checkSimplesAfterFlop();
        checkDifAfterFlop();
    }

    public void checkAfterTurn(card turn){
        int bestSoFar = getBestHand();
        int changed = -1;
        Arrays.sort(hand, Comparator.comparingInt(card -> card.getNumber()));
        for (int i = 0; i < hand.length; i++) {
            clearHand();
            card temp = hand[i];
            hand[i] = turn;
            checkSimplesAfterFlop();
            checkDifAfterFlop();
            if (getBestHand() > bestSoFar) {
                bestSoFar = getBestHand();
                changed = i;
            }
            for (int j = 0; j < hand.length; j++) {
                if (hand[j] == turn) {
                    hand[j] = temp;
                    break;
                }
            }
            
        }
        clearHand();
        Arrays.sort(hand, Comparator.comparingInt(card -> card.getNumber()));
        if (changed != -1) {
            sides[0] = hand[changed];
            hand[changed] = turn;
        }
        else {
            sides[0] = turn;
        }
        checkSimplesAfterFlop();
        checkDifAfterFlop();
    
    }

    public int checkAfterRiver(card river){
        int bestSoFar1 = getBestHand();
        Arrays.sort(hand, Comparator.comparingInt(card -> card.getNumber()));
        int changed = -1;
        for (int i = 0; i < hand.length; i++) {
            clearHand();
            card temp = hand[i];
            hand[i] = river;
            checkSimplesAfterFlop();
            checkDifAfterFlop();
            if (getBestHand() > bestSoFar1) {
                bestSoFar1 = getBestHand();
                changed = i;
            }
            for (int j = 0; j < hand.length; j++) {
                if (hand[j] == river) {
                    hand[j] = temp;
                    break;
                }
            }
        }

        sides[1] = river;
        int bestSoFar2 = getBestHand();
        int changed1 = -1, changed2 = -1;
        Arrays.sort(hand, Comparator.comparingInt(card -> card.getNumber()));
        for (int i = 0; i < 5; i++) {
            for (int j = i+1; j < 5; j++) {
                clearHand();
                card temp1 = hand[0];
                card temp2 = hand[1];
                hand[i] = sides[0];
                hand[j] = sides[1];
                checkSimplesAfterFlop();
                checkDifAfterFlop();
                if (getBestHand() > bestSoFar2) {
                    bestSoFar2 = getBestHand();
                    changed1 = i;
                    changed2 = j;
                }
                for (int k = 0; k < hand.length; k++) {
                    if (hand[k] == sides[0]) {
                        hand[k] = temp1;
                    }if (hand[k] == sides[1]) {
                        hand[k] = temp2;
                    }
                }
            }
        }
        clearHand();
        Arrays.sort(hand, Comparator.comparingInt(card -> card.getNumber()));

        if (bestSoFar1 > bestSoFar2) {
            if (changed != -1) {
                card temp = hand[changed];
                hand[changed]= sides[1];
                sides[1] = temp;
            } 
        } 
        else {
            if (changed1 != -1) {
                card temp1 = hand[changed1];
                hand[changed1]= sides[0];
                sides[0] = temp1;
            } 
            if (changed2 != -1) {
                card temp2 = hand[changed1];
                hand[changed1]= sides[1];
                sides[1] = temp2;
            }
        }
        checkSimplesAfterFlop();
        checkDifAfterFlop();

        return getBestHand();

    }




    public void checkSimplesAfterFlop()
    {
        Arrays.sort(hand, Comparator.comparingInt(card -> card.getNumber()));

        int countA = 1, number=0;
        for (int i=0; i<4; i++)
        {
            if (hand[i].getNumber()==hand[i+1].getNumber()){
                countA++;
                number=i;
            }
        }

        switch (countA) {
            case 1:
                highCard = true;
                highestCard = hand[4].getNumber();
                kicker = hand[3].getNumber();
                break;
            case 2:
                if (number==3){
                    highestCard = hand[4].getNumber();
                    kicker = hand[2].getNumber();
                }
                else {
                    highestCard = hand[number].getNumber();
                    kicker = hand[4].getNumber();
                }
                pair=true;    
                break;

            case 3:
                if (hand[0].getNumber()==hand[1].getNumber()){
                    if (hand[2].getNumber()==hand[1].getNumber()){
                        threeOfAKind = true;
                        highestCard = hand[0].getNumber();
                        kicker = hand[4].getNumber();
                    }
                    else{
                            twoPair = true;
                            highestCard = hand[3].getNumber();
                            kicker = hand[0].getNumber();
                    }     
                }
                else{
                    if (hand[2].getNumber()==hand[1].getNumber()){
                        if (hand[2].getNumber()==hand[3].getNumber()){
                            threeOfAKind = true;
                            highestCard = hand[1].getNumber();
                            kicker = hand[4].getNumber();
                        }
                        else{
                            twoPair = true;
                            highestCard = hand[4].getNumber();
                            kicker = hand[1].getNumber();
                         }
                    }
                    else {
                        threeOfAKind = true;
                        highestCard = hand[2].getNumber();
                        kicker = hand[1].getNumber();
                    }  
                }  
                break;
                
            case 4:
                if (hand[0].getNumber()==hand[3].getNumber()){
                    fourOfAKind = true;
                    highestCard = hand[3].getNumber();
                    kicker = hand[4].getNumber();
                }
                else{
                    if (hand[1].getNumber()==hand[4].getNumber()){
                        fourOfAKind = true;
                        highestCard = hand[4].getNumber();
                        kicker = hand[0].getNumber();
                    }
                    else {
                        if (hand[0].getNumber()==hand[2].getNumber()){
                            fullHouse = true;
                            highestCard = hand[2].getNumber();
                            kicker = hand[3].getNumber();
                        }
                        else {
                            fullHouse = true;
                            highestCard = hand[4].getNumber();
                            kicker = hand[1].getNumber();
                        }
                    }
                }
                break;      
        }
    }

    public void checkDifAfterFlop()
    {
        Arrays.sort(hand, Comparator.comparingInt(card -> card.getNumber()));
        boolean isStraight = true, isFlush = true;
        for (int i=0; i < hand.length-1; i++) {
            if (isStraight && hand[i+1].getNumber() - hand[i].getNumber() != 1) {
                isStraight = false;
            }
            if (isFlush && !hand[i+1].getSuit().equals(hand[i].getSuit())) {
                isFlush = false;
            }
        }
        
        if (isStraight){
            straight = true;
            if (isFlush)
                straightFlush = true;
        }
        else {
            if (hand[4].getNumber() == 13 && hand[3].getNumber() == 12 && hand[2].getNumber() == 11 && hand[1].getNumber() == 10 && hand[0].getNumber() == 1) {
                straight = true;
                if (isFlush) {
                    royalFlush = true;
                }
            }
            else {
                if (isFlush) {
                    flush = true;
                }
            }
        }
    }
    public void setFlop(card first, card second, card third) {
        hand[2] = first;
        hand[3] = second;
        hand[4] = third;
    }

     


public static void main(String[] args) {
    pack p = new pack();
    p.shaffle();
    player pl = new player("Player1", 1000);
    card c=new card(6, "Hearts");
    pl.setHomepair(1, c);
    System.out.println(c.toString());
    c=new card(2, "Hearts");
    pl.setHomepair(2, c);
    System.out.println(c.toString());
    card c1= new card(7, "clubs");
    card c2=new card(8, "Hearts");
    card c3=new card(9, "Hearts");
    System.out.println(c1.toString());
    System.out.println(c2.toString());
    System.out.println(c3.toString());
    pl.setFlop(c1, c2, c3);
    card c4= new card(5, "clubs");
    System.out.println(c4.toString());
    pl.checkAfterTurn(c4);
    System.out.println("Best hand: " + pl.getBestHand());

    card c5= new card(11, "Hearts");
    System.out.println(c5.toString());
    pl.checkAfterRiver(c5);


    System.out.println("Best hand: " + pl.getBestHand());

} 
}


/*
 * 
    public void checkSimplesAfterFlop()
    {
        int first=1, second=1;
        if (hasSame(hand[2].getNumber(), hand[3].getNumber(), hand[4].getNumber())==3) {
            if (hand[2]==homepair.getFirstCard().getNumber()|| hand[2]==homepair.getSecondCard().getNumber())
            {
                fourOfAKind = true;
                highestCard = hand[2].getNumber();
            }
            else 
                if (pair){
                    fullHouse = true;
                    highestCard = hand[2].getNumber();
                    kicker=homepair.getSecondCard().getNumber();
                }
                else{
                    kicker=highestCard;
                    highestCard = hand[2].getNumber();
                    threeOfAKind = true;
                }
                    
        }
        else {
            if (hasSame(hand[2].getNumber(), hand[3].getNumber(), hand[4].getNumber())==2) {
                int temp;
                if (hand[2].getNumber()==hand[3].getNumber()){
                    temp = hand[2].getNumber();
                else{
                    temp = hand[3].getNumber();
                    if (temp==homepair.getFirstCard().getNumber()||temp==homepair.getSecondCard().getNumber()){
                        if (pair)
                        {
                            fourOfAKind = true;
                            highestCard = temp;
                        }
                        else {
                            threeOfAKind = true;
                            kicker = highestCard;
                            highestCard = temp;
                        }
                    }
                    else 
                        if (pair){
                            twoPair = true;
                            temp = Math.max(temp, homepair.getFirstCard().getNumber());
                            highestCard = temp;
                            kicker = Math.min(temp, homepair.getFirstCard().getNumber());
                        }
                        else {
                            pair = true;
                            kicker = highestCard;
                            highestCard = temp;
                        }
                    }
                }
                    
            }
            else 
            {
                for (int i = 2; i < hand.length; i++) {
                {
                        if (hand[i].getNumber()==homepair.getFirstCard().getNumber())
                            first++;
                        if (hand[i].getNumber()==homepair.getSecondCard().getNumber())
                            second++;
                }
                if (first==2)
                    if (second==2)
                        if (pair){
                            threeOfAKind = true;
                            highestCard = homepair.getFirstCard().getNumber();
                        }
                        else{
                                twoPair = true;
                                highestCard = Math.max(homepair.getFirstCard().getNumber(), homepair.getSecondCard().getNumber());
                                kicker = Math.min(homepair.getFirstCard().getNumber(), homepair.getSecondCard().getNumber());
                        }
                    else{
                        if (second==3){
                            foolHouse = true;
                            highestCard = homepair.getSecondCard().getNumber();
                            kicker = homepair.getFirstCard().getNumber();
                        }
                        else{
                            pair = true;
                            kicker = highestCard;
                            highestCard = homepair.getFirstCard().getNumber();
                        }
                    }
                else if (first==3)
                {
                    if (second==2){
                        foolHouse = true;
                        highestCard = homepair.getFirstCard().getNumber();
                        kicker = homepair.getSecondCard().getNumber();
                    }
                    else
                    {
                        threeOfAKind = true;
                        kicker = highestCard;
                        highestCard = homepair.getFirstCard().getNumber();
                    }
                }
                else if (first==1)
                    if (second==2){
                        pair = true;
                        kicker = highestCard;
                        highestCard = homepair.getSecondCard().getNumber();
                    }
                    else if (second==3){
                        threeOfAKind = true;
                        kicker = highestCard;
                        highestCard = homepair.getSecondCard().getNumber();
                    } 
            }
        }
             
    }
 */
