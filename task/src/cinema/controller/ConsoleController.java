package cinema.controller;

import cinema.model.CinemaRoom;
import cinema.model.Seat;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ConsoleController {

    Scanner scanner = new Scanner(System.in);
    CinemaRoom room;
    int emptyLinesAfterOperation = 0;

    public void init() {
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        room = new CinemaRoom(rows, seats);
        showEmptyLine(emptyLinesAfterOperation);
    }

    public void showCinemaRoom() {
        StringBuilder out =
                new StringBuilder("Cinema:\n"
                        + "  "
                        + IntStream.rangeClosed(1, room.getSeatsInRow())
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(" ")) + "\n");
        for (int ii = 1; ii <= room.getRows(); ii++)
            out.append(ii)
                    .append(" ")
                    .append(Arrays.stream(room.getRow(ii))
                            .map(Seat::getState)
                            .collect(Collectors.joining(" ")))
                    .append(ii == room.getRows() ? "" : "\n");

        System.out.println(out);
        showEmptyLine(emptyLinesAfterOperation);
    }

    public void showSeatPrice(Seat seat) {
        System.out.println("Ticket price: $" + (int) seat.getPrice());
        showEmptyLine(emptyLinesAfterOperation);
    }

    public void sellSeat() {
        while (true) {
            System.out.println("Enter a row number:");
            int row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seatNum = scanner.nextInt();

            // Проверка координат
            if (row > room.getRows() || seatNum > room.getSeatsInRow()) {
                showMessage("Wrong input!");
                continue;
            }
            Seat seat = room.getSeat(row, seatNum);
            if (seat.sell()) {
                showSeatPrice(seat);
                break;
            } else
                showMessage("That ticket has already been purchased!");
        }
    }

    public int chooseMenuItem() {
        System.out.println("""
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit""");
        int res = scanner.nextInt();
        showEmptyLine(emptyLinesAfterOperation);
        return res;
    }

    public void showStatistics() {
        String pattern = """
                Number of purchased tickets: %d
                Percentage: %.2f%%
                Current income: $%.0f
                Total income: $%.0f%n""";
        int sold = room.getSoldSeatsNumber();
        int total = room.getTotalSeatsNumber();

        System.out.printf(pattern, sold, (double) sold / total * 100.0, room.getSoldSeatsPrice(), room.maxProfit());
        showEmptyLine(emptyLinesAfterOperation);
    }

    public void showMessage(String msg) {
        showEmptyLine(emptyLinesAfterOperation);
        System.out.println(msg);
        showEmptyLine(emptyLinesAfterOperation);
    }

    public static void stage5Scenario() {
        ConsoleController controller = new ConsoleController();
        controller.emptyLinesAfterOperation = 1;
        controller.init();

        loop:
        while (true) {
            switch (controller.chooseMenuItem()) {
                case 0:
                    break loop;
                case 1:
                    controller.showCinemaRoom();
                    break;
                case 2:
                    controller.sellSeat();
                    break;
                case 3:
                    controller.showStatistics();
                    break;
            }
        }

    }

    public static void showEmptyLine(int cnt) {
        IntStream.rangeClosed(1, cnt).forEach(ii -> System.out.println());
    }


}
