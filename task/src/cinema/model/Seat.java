package cinema.model;

public class Seat {
    int row;
    int seatNum;
    String state;
    double price;

    public Seat(int row, int seatNum, double price) {
        this.row = row;
        this.seatNum = seatNum;
        this.price = price;
        state = "S";
    }

    public double getPrice() {
        return price;
    }

    public String getState() {
        return state;
    }


    public boolean sell() {
        if (state.equals("S")) {
            state = "B";
            return true;
        } else
            return false;
    }
}
