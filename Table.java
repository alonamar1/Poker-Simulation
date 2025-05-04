

public class Table {    
    
    private static final int ENTRANCE_CHIPS = 100; // Assuming a maximum of 4 players
    private int numberOfPlayers;
    private player[] players;
    private card[] tableCards;
    private pack deck;
    private int pot;

    public Table(int numberOfPlayers) {
        this.players = new player[9]; // Assuming a maximum of 4 players
        this.tableCards = new card[5]; // Assuming a maximum of 5 community cards
        this.deck = new pack();
        this.pot = 0;
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfPlayers = numberOfPlayers;
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new player(ENTRANCE_CHIPS);
        }
    }

    public void setUpTable() {
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i].setUp();
        }
        rotateTurn();
    }

    public void addPlayer(player newPlayer) {
        if (numberOfPlayers < 9) {
            players[numberOfPlayers] = newPlayer;
            numberOfPlayers++;
        } else {
            System.out.println("Table is full. Cannot add more players.");
        }
    }

    public void removePlayer(String playerName) {
        if (playerIndex >= 0) {
            int playerIndex = -1;
            boolean playerFound = false;
            for (int i = 0; i < numberOfPlayers; i++) {
                if (players[i].getName().equals(playerName)){
                    player[i]=players[i+1];
                    playerIndex = i+1;
                    playerFound = true;
                    break;
                }
            }
            for (int i = playerIndex; i < numberOfPlayers - 1; i++) {
                players[i] = players[i + 1];
            }
            numberOfPlayers--;
        } else {
            System.out.println("player not found.");
        }
    }

    public void dealCards() {
        deck.shuffleDeck();
        for (int j = 1; j < 3; j++) {
            for (int i = 0; i < numberOfPlayers; i++) {
                players[i].setHomepair(j, deck.draw());
            }
        }
    }

    public void dealFlop() {
        deck.draw(); // Burn a card
        for (int i = 0; i < 3; i++) {
            tableCards[i] = deck.draw();
            for (int j = 0; j < numberOfPlayers; j++) {
                players[j].setHand(tableCards[i], i+2);
            }
        }
    }

    public void dealTurn() {
        deck.draw(); // Burn a card
        tableCards[3] = deck.draw();
    }


    public void dealRiver() {
        deck.draw(); // Burn a card
        tableCards[4] = deck.draw();
    }

    public void placeBet(player player, int betAmount) {
        if (betAmount+player.getBet() > player.getChips()) {
            System.out.println("Not enough chips to place the bet.");
        } else {
            player.placeBet(betAmount);
            pot += betAmount;
        }
    }

    public void showTableCards() {
        System.out.println("Table Cards: ");
        for (int i = 0; i < tableCards.length; i++) {
            if (tableCards[i] != null) {
                System.out.print(tableCards[i].toString() + " ");
            }
        }
        System.out.println();
    }

    public void showPot() {
        System.out.println("Current Pot: " + pot);
    }

    public void rotateTurn() {
        player temp=player[0];
        for (int i = 0; i < numberOfPlayers; i++) {
            player secTemp = players[(i + 1) % numberOfPlayers];
            players[(i + 1) % numberOfPlayers] = temp;
            temp=secTemp;
            }
        }
    }

    

