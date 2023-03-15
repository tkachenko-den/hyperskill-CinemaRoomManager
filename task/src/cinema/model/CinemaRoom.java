package cinema.model;

import java.util.Arrays;

public class CinemaRoom {
    Seat[][] seats;
    int rows;
    int seatsInRow;

    public CinemaRoom(int rows, int seatsInRow) {
        this.rows = rows;
        this.seatsInRow = seatsInRow;
        seats = new Seat[rows][seatsInRow];

        for (int ii = 0; ii < rows; ii++) {
            for (int jj = 0; jj < seatsInRow; jj++) {
                seats[ii][jj] = new Seat(ii+1,jj+1,seatPrice(ii+1,jj+1));
            }
        }
    }

    protected double seatPrice(int row, int seat) {
        return rows*seatsInRow<=60 ?
                10.0 :
                row<=rows/2 ? 10.0 : 8.0;
    }

    public double maxProfit() {
        double result=0;
        for (int ii = 0; ii < rows; ii++) {
            for (int jj = 0; jj < seatsInRow; jj++) {
                result+= seats[ii][jj].getPrice();
            }
        }
        return result;
    }

    public int getRows() {
        return rows;
    }

    public int getSeatsInRow() {
        return seatsInRow;
    }

    public Seat[] getRow(int rowNum) {
        return seats[rowNum-1];
    }

    public Seat getSeat(int row, int seat) {
        return seats[row-1][seat-1];
    }

    public int getTotalSeatsNumber() {
        return rows*seatsInRow;
    }

    public int getSoldSeatsNumber() {
        return (int) Arrays.stream(seats)
                .flatMap(Arrays::stream)
                .filter(s->s.getState().equals("B"))
                .count();
    }

    public double getSoldSeatsPrice() {
        return Arrays.stream(seats)
                .flatMap(Arrays::stream)
                .filter(s->s.getState().equals("B"))
                .mapToDouble(Seat::getPrice)
                .sum();
    }

}
