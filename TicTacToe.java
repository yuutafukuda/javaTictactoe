import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        char[][] gameBoard = buildGameBoard();
        char[][] gameData = buildGameData();
        printGameBoard(gameBoard);   
        clearGame(gameBoard, gameData);
        playGame(scan, gameBoard, gameData);

        System.out.println();
        System.out.println("Thank you for playing!");
        scan.close();
    }


    // PRINTS
    public static void printGameBoard(char[][] gameBoard) {
        for (char[] row : gameBoard) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println();
    }


    // BUILDERS AND INITIALIZERS
    public static char[][] buildGameBoard() {
        char[][] gameBoard = {
            {' ', '|', ' ', '|', ' '}, 
            {'-', '+', '-', '+', '-'}, 
            {' ', '|', ' ', '|', ' '}, 
            {'-', '+', '-', '+', '-'}, 
            {' ', '|', ' ', '|', ' '}
        };
        char[][] gamePlacement = {
            {'1','2','3'}, 
            {'4','5','6'},
            {'7','8','9'},
        };

        int row;
        int col;
        for (int i = 0; i < gamePlacement.length; i++) {
            row = i;
            row += i;
            for (int j = 0; j < gamePlacement[i].length; j++) {
                col = j;
                col += j;
                gameBoard[row][col] = gamePlacement[i][j]; 
            }
        }

        return gameBoard;
    }


    public static char[][] buildGameData() {
        char[][] gameData = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '},
        };

        return gameData;
    }


    public static char selectPlayer(Scanner scan) {

        System.out.println("Which player are you? (X or O): ");
        char player = scan.next().charAt(0);
        while (player != 'X' && player != 'O') {
            System.out.println("Not a valid player! Try again..");
            System.out.print("Which player are you? (X or O): ");
            player = scan.next().charAt(0);
        }

        return player;
    }


    // CLEAR
    public static void clearGame(char[][] gameBoard, char[][] gameData) {
        int[] positions = {0, 2, 4};
        for (int row : positions) {
            for (int col : positions) {
                gameBoard[row][col] = ' ';
            } 
        }

        int i = 0;
        for (char[] row : gameData) {
            int j = 0;
            for (char elem : row) {
                gameData[i][j] = ' ';
                j++;
            }
            i++;
        }
    }


    // GAME LOGICS
    public static void playGame(Scanner scan, char[][] gameBoard, char[][] gameData) {
        char player = selectPlayer(scan);

        char winner;
        int round = 0;
        while (round < 9) {
            System.out.println("Player " + player + ", enter your placement (1-9): ");
            int pos = scan.nextInt();
    
            int[] arrPos = convertPosition(pos);

            if (judgePlacement(arrPos, gameData)) {
                round++;
                placePiece(gameBoard, gameData, arrPos, player);
                printGameBoard(gameBoard);
    
                if (player == 'X') { player = 'O'; } 
                else { player = 'X'; }
    
                winner = checkWinner(gameData);
                if (round >= 9 && winner == 'n') {
                    winner = 'd';
                }
                if (endGame(winner)) {
                    break;
                }
            }

            else {
                System.out.println("Invalid position! Try again ...");
            }
        }

        boolean playAgain = playAgain(scan);

        if (playAgain) {
            clearGame(gameBoard, gameData);
            playGame(scan, gameBoard, gameData);   
        }
    }

    public static boolean playAgain(Scanner scan) {
        char playAgain;
        while (true) {
            System.out.println("Play again? (y/n) ");
            playAgain = scan.next().charAt(0);
            if (playAgain == 'y') {
                return true;
            }
            if (playAgain == 'n') {
                return false;
            }
            System.out.println("Enter y to play again or n to end ...");
        }
    }


    public static int[] convertPosition(int pos) {
        int row = (pos - 1) / 3;
        int col = (pos - 1) % 3;

        int[] arrPos = {row, col};
        return arrPos;
    }


    public static boolean judgePlacement(int[] arrPos, char[][] gameData) {
        if (gameData[arrPos[0]][arrPos[1]] == ' ') {
            return true;
        }
        return false;
    }

    public static void placePiece(char[][] gameBoard, char[][] gameData, int[] arrPos, char user) {
        int[] positions = {0, 2, 4};
        int row = positions[arrPos[0]];
        int col = positions[arrPos[1]];
        
        gameBoard[row][col] = user;
        gameData[arrPos[0]][arrPos[1]] = user;
    }


    public static char checkWinner(char[][] gameData) {
        char[] topRow = gameData[0];
        char[] midRow = gameData[1];
        char[] bottomRow = gameData[2];
        char[] leftCol = {topRow[0], midRow[0], bottomRow[0]};
        char[] midCol = {topRow[1], midRow[1], bottomRow[1]};
        char[] rightCol = {topRow[2], midRow[2], bottomRow[2]};
        char[] leftRightDiagonal = {topRow[0], midRow[1], bottomRow[2]};
        char[] rightLeftDiagonal = {topRow[2], midRow[1], bottomRow[0]};

        char[][] possibleWins = {topRow, midRow, bottomRow, rightCol, midCol, leftCol, leftRightDiagonal, rightLeftDiagonal};

        char winner = 'n';
        for (char[] line : possibleWins) {
            if (line[0] != ' ' && checkEqualElem(line)) {
                winner = line[0];
                break;
            }
        }

        return winner;
    }

 
    public static boolean endGame(char winner) {
        if (winner == 'd') {
            System.out.print("DRAW!");
            return true;
        }
        if (winner != 'n') {
            System.out.println(winner + " WINS!");
            return true;
        }
        return false;
    }


    // AUXILIARIES METHODS
    public static boolean checkEqualElem(char[] arr) {
        char comparison = arr[0];
        for (char elem : arr) {
            if (elem != comparison) {
                return false;
            }
        }
        return true;
    }
}