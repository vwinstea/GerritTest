import java.util.Scanner;
public abstract class Board
{
    //Variables
    private Piece[][] board;
    private boolean player1Turn;
    private boolean withComputer;
    private boolean gameOver;

    //Constructors
    public Board (int size, boolean computer) {
        this.board = new Piece[size][size];
        resetBoard();
        this.player1Turn = true;
        this.withComputer = computer;
    }

    public Board (int row, int col, boolean computer) {
        this.board = new Piece[row][col];
        resetBoard();
        this.player1Turn = true;
        this.withComputer = computer;
        this.gameOver = false;
    }
    
    //Normal Methods
    public boolean takeTurn() { //Takes One Player Turn
        printBoard();
        if (player1Turn) {
            System.out.println("Player 1 " + getPlayer1Char());
        } else {
            System.out.println("Player 2 " + getPlayer2Char());
        }
        Scanner input = new Scanner(System.in);
        int row;
        int col;
        do {
            System.out.print("row: ");
            row = input.nextInt() - 1;
            System.out.print("col: ");
            col = input.nextInt() - 1;
            if(!canPlayHere(row, col)) {
                System.out.println("Out of bounds");
            }
            if(row == 98 || col == 98) {
                gameOver = true;
            }
        } while(!canPlayHere(row, col));
        if(this.canPlayHere(row, col)) {
            board[row][col] = makePiece();
            player1Turn ^= true;
            return true;
        } else {
            return false;
        }
    }

    public boolean compTakeTurn() { //Takes One Computer Turn
        printBoard();
        System.out.println("Comp " + getPlayer2Char());
        int row;
        int col;
        do {
            row = (int) (Math.random()*board.length);
            col = (int) (Math.random()*board[0].length);
        } while(!canPlayHere(row, col));
        System.out.println("row: " + row);
        System.out.println("col: " + col);
        if(this.canPlayHere(row, col)) {
            board[row][col] = makePiece();
            player1Turn ^= true;
            return true;
        } else {
            return false;
        }
    }

    public boolean tied () { //Tie Conditions
        boolean done = true;
        for (int r = 0; r < getBoard().length; r++) {
            for (int c = 0; c < getBoard()[0].length; c++) {
                if (getPiece(r, c) == '*') {
                    done = false;
                }
            }
        }
        return (done && !gameOver());
    }

    public void printBoard () { //Prints out current Board
        if( makePiece() instanceof Connect4Piece ||  makePiece() instanceof OthelloPiece) { //row labels
            if(makePiece() instanceof OthelloPiece) { //spacing for col labels
                System.out.print("  ");
            }
            for (int c = 0; c < board[0].length; c++) {
                System.out.print((c + 1) + "  ");
            }
            System.out.println();
        }
        for (int r = 0; r < board.length; r++) {
            if(makePiece() instanceof OthelloPiece) { //col labels
                System.out.print(r + 1 + " ");
            }
            for (int c = 0; c < board[0].length; c++) {
                board[r][c].printPiece();
                System.out.print("  ");
            }
            System.out.println();
        }
    }

    public void resetBoard () { //Creates Default Board
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c <board[0].length; c++) {
                board[r][c] = makeBlankPiece();
            }
        }
        gameOver = false;
    }
    
    //Board Getters
    public boolean getWithComputer () {
        return this.withComputer;
    }
    
    public boolean getPlayer1Turn () {
        return this.player1Turn;
    }
    
    public char getPiece (int row, int col) {
        return board[row][col].getPiece();
    }
    
    public Piece[][] getBoard() {
        return board;
    }
    
    public boolean getPlayer1Turn(int row, int col) { //returns whose piece it is
        return board[row][col].isPlayer1Piece();
    }
    
    public boolean getGameOver () {
        return gameOver;
    }
    
    public char getPlayer1Char () {
        return makePiece().getPlayer1Char();
    }
    
    public char getPlayer2Char () {
        return makePiece().getPlayer2Char();
    }
    
    public boolean getEmpty(int row, int col) {
        return board[row][col].getEmpty();
    }

    //Board Setters
    public void setPlayer1Turn () {
        player1Turn ^= true;
    }

    public void setPiece (int row, int col) {
        board[row][col] = makePiece();
    }
    
    public void switchVal (int r, int c) {
        board[r][c].switchVal();
    }
    
    public void setGameOver (boolean b) {
        gameOver = b;
    }
    
    //Abstract Methods
    public abstract boolean play(); //Loops until GameOver
    public abstract boolean canPlayHere (int row, int col); //Move conditions
    public abstract boolean gameOver(); //Win conditions
    public abstract Piece makePiece(); //With Specific Board Piece (For specific Game)
    public abstract Piece makeBlankPiece(); //Used in resetBoard
}
