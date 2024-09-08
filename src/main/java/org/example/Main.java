package org.example;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private enum Move {
        ROCK, PAPER, SCISSORS
    }

    private static Random random = new Random();

    private static Move player1Move;
    private static Move player2Move;

    private static final CountDownLatch latch = new CountDownLatch(2);

    public static Move makeMove() {
        int move = random.nextInt(3);
        return Move.values()[move];
    }

    private static void determineWinner() {
        if (player1Move == player2Move) {
            System.out.println("It's a tie!");
        } else if ((player1Move == Move.ROCK && player2Move == Move.SCISSORS) ||
                (player1Move == Move.PAPER && player2Move == Move.ROCK) ||
                (player1Move == Move.SCISSORS && player2Move == Move.PAPER)) {
            System.out.println("Player 1 wins!");
        } else {
            System.out.println("Player 2 wins!");
        }
    }

    public static void main(String[] args) {

        Thread player1 = new Thread(() -> {
            player1Move = makeMove();
            System.out.println("Player one chose " + player1Move);
            latch.countDown();
        });

        Thread player2 = new Thread(() -> {
            player2Move = makeMove();
            System.out.println("Player two chose " + player2Move);
            latch.countDown();
        });

        player1.start();
        player2.start();

        try {
            latch.await();
            determineWinner();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}