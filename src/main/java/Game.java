import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class Game {

    private final int boardSize; // kasutaja valitud mängulaua suurus
    // muutujad, mis on antud klassis vajalikud
    private int[][] boardMatrix; // mängulaual asuvad laevad
    private final Random random = new Random(); // juhuslikkus, kasutatakse laevade paigutamiseks mängulaual
    private final int[] ships = {4, 3, 3, 2, 2, 2, 1}; // Laeva pikkused ja kokku 7 laeva ehk 17 ruutu
    private int shipCounter = 0; // laevu kokku (lõpuks kokku 17)
    private int clickCounter = 0; // mitu klick on laevade otsimisel
    private final ArrayList<Point> points = new ArrayList<>(); // Laevade paigutamisel vajalik

    public Game(int boardSize) {
        this.boardSize = boardSize;
        this.boardMatrix = new int[boardSize][boardSize]; // kogu info pannekse mudelis algselt paika. laua suurus on vaikimisi olemas
        // TEST EESMÄRGIL
        //setupNewGame();
        //showGameBoardMatrix();
    }

    public void setupNewGame() {
        boardMatrix = new int[boardSize][boardSize]; // algava mängu laua suurus
        int shipsTotal = ships.length; // laevu kokku
        int shipsPlaced = 0; // mitu laeva on algselt paigutatud

        while (shipsPlaced < shipsTotal) {
            int row = random.nextInt(boardSize);
            int col = random.nextInt(boardSize);
            boolean vertical = random.nextBoolean();
            boolean placed; // kas laev läks paika

            if (vertical) { // proovib paigutada vertikaalselt
                placed = checkVerticalPlace(row, col, ships[shipsPlaced]);
            } else { // proovib paigutada horisontaalselt
                placed = checkHorizontalPlace(row, col, ships[shipsPlaced]);

            }
            // Kas laev paigas?
            if (placed) {
                shipsPlaced++; // Võta järgmine laev
            }
        }
        replaceNineToZero();
    }

    private boolean checkVerticalPlace(int row, int col, int length) {
        points.clear();
        boolean placed = false; // laev pole veel paigas
        if (row + length < boardSize) { // kontroll, kas laeva saab lauale paigutada
            for (int i = row; i < row + length; i++) {
                if (boardMatrix[i][col] == 0) {
                    points.add(new Point(i, col)); // Laeva üks kast paigas
                } else {
                    points.clear();
                    points.add(new Point(-1, -1));
                }
            }
        } else {
            points.add(new Point(-1, -1));
        }
        if (points.get(0).getRow() != -1) { // saime laeva paika
            for (int i = row; i < row + length; i++) {
                boardMatrix[i][col] = length;
            }
            placed = true;
            fillVerticalProtection(row, col, length);
        }
        return placed;
    }

    private boolean checkHorizontalPlace(int row, int col, int length) {
        points.clear();
        boolean placed = false;
        if (col + length < boardSize) {
            for (int i = col; i < col + length; i++) {
                if (boardMatrix[row][i] == 0) {
                    points.add(new Point(row, i)); // Laeva üks kast paigas
                } else {
                    points.clear();
                    points.add(new Point(-1, -1));
                }
            }
        } else {
            points.add(new Point(-1, -1));
        }
        if (points.get(0).getCol() != -1) {
            for (int i = col; i < col + length; i++) {
                boardMatrix[row][i] = length;
            }
            placed = true;
            fillHorizontalProtection(row, col, length);
        }
        return placed;
    }

    private void fillVerticalProtection(int row, int col, int length) {
        // Vasakule ja paremale kaitse
        for (int i = row; i < row + length; i++) {
            if (col - 1 >= 0) {
                boardMatrix[i][col - 1] = 9; // Kaitse
            }
            if (col + 1 < boardSize) {
                boardMatrix[i][col + 1] = 9;  // Laeva pikkuses
            }
        }
        // Ülemine rida
        if (row - 1 >= 0) {
            boardMatrix[row - 1][col] = 9; // Laeva nina
            if (col - 1 >= 0) {
                boardMatrix[row - 1][col - 1] = 9; // Ülemine vasak nurk
            }
            if (col + 1 < boardSize) {
                boardMatrix[row - 1][col + 1] = 9; // Ülemine parem nurk
            }
        }
        // Alumine rida
        if (row + length < boardSize) {
            boardMatrix[row + length][col] = 9; // laeva alumine nina
            if (col - 1 >= 0) {
                boardMatrix[row + length][col - 1] = 9; // Alumine vasak nurk
            }
            if (col + 1 < boardSize) {
                boardMatrix[row + length][col + 1] = 9; // Alumine parem nurk
            }
        }
    }

    private void fillHorizontalProtection(int row, int col, int length) {
        for (int i = col; i < col + length; i++) {
            if (row - 1 >= 0) {
                boardMatrix[row - 1][i] = 9;
            }
            if (row + 1 < boardSize) {
                boardMatrix[row + 1][i] = 9; // alumine rida
            }
        }
        // Vasak ots
        if (col - 1 >= 0) {
            boardMatrix[row][col - 1] = 9; // nina
            if (row - 1 >= 0) {
                boardMatrix[row - 1][col - 1] = 9;
            }
            if (row + 1 < boardSize) {
                boardMatrix[row + 1][col - 1] = 9;
            }
        }
        //Parem ots
        if (col + length < boardSize) {
            boardMatrix[row][col + length] = 9;
            if (row - 1 >= 0) {
                boardMatrix[row - 1][col + length] = 9;
            }
            if (row + 1 < boardSize) {
                boardMatrix[row + 1][col + length] = 9;
            }
        }
    }

    public void showGameBoardMatrix() {
        System.out.println(); // reavahetus
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                System.out.print(boardMatrix[row][col] + " ");
            }
            System.out.println(); // reavahetus peale rea lõppi
        }

    }

    private void replaceNineToZero() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (boardMatrix[row][col] == 9) {
                    boardMatrix[row][col] = 0;
                }
            }
        }

    }

    public int[][] getBoardMatrix() {
        return boardMatrix;
    }

    public int getShipCounter() {
        return shipCounter;
    }

    public void setShipCounter(int shipCounter) {
        this.shipCounter += shipCounter;
    }

    public int getClickCounter() {
        return clickCounter;
    }

    public void setClickCounter(int clickCounter) {
        this.clickCounter += clickCounter;
    }

    public void resetClickCounter() {
        this.clickCounter = 0;
    } // resetib clickcounteri O

    public int getShipsParts() {
        return IntStream.of(ships).sum();
    }

    public void setUserClick(int row, int col, int what) {
        if (what == 7) {
            boardMatrix[row][col] = 7; // Saadi pihta
        } else {
            boardMatrix[row][col] = 8; // Läks mööda
        }
    }

    public boolean isGameOver() {
        return getShipsParts() == getShipCounter(); // kui on võrdne, siis on true ja mäng on läbi
    }

    public void setGameOver() {
        shipCounter = getShipsParts();

    }

}
