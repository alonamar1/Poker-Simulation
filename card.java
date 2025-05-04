
public class card {
    
    int number;
    String shape;

    public card() {
        this.number = 0;
        this.shape = null;
    }

    public card(int number, String shape) {
        this.number = number;
        this.shape = shape;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSuit() {
        return shape;
    }

    public void setSuit(String shape) {
        this.shape = shape;
    }

    public String toString() {
        return "Card: " + number + " of " + shape;
    }

    
}