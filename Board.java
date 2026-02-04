import java.util.Scanner;

public class Board {
    Piece grid[][];
    private Scanner scanner;

    public Board(){
        grid = new Piece[4][4];
        scanner = new Scanner(System.in);
    }
    public void display(){
        Piece funcObj = new Piece(); // helper object for piece name display
        System.out.println("here is the board");
        System.out.println("   0   1    2   3");
        for(int i = 0 ; i < 4 ; i++){
            System.out.print(i + " ");
            for(int j = 0 ; j < 4 ; j++){
                if(!(grid[i][j] == null)){
                    System.out.print(" [" + funcObj.pieceName(grid[i][j]) + "] ");
                }
                else{
                    System.out.print(" [] ");
                }
                
            }
            System.out.println();
        }
    }

    public void placeAPiece(){

        //take user input for piece placement
        System.out.println("Which piece ? 1 = ROOK   2 = KNIGHT   3 = PAWN");
        int pieceInput =  scanner.nextInt();

        System.out.println("Which color ? 1 = WHITE   2 = BLACK");
        int colorInput =  scanner.nextInt();

        System.out.println("Which row ? (0-3)");
        int rowInput = scanner.nextInt();

        System.out.println("Which column ? (0-3)");
        int colInput = scanner.nextInt();

        Piece pieceType;
        if(pieceInput == 1){
            pieceType = Piece.Type ROOK;
        }
        else if(pieceInput == 2){
            pieceType = Piece.Type KNIGHT;
        }
        else if(pieceInput == 3){
            pieceType = Piece.Type PAWN;
        }

        

        int row = rowInput;
        int col = colInput;
        Piece piece = pieceType;
        Piece color = colorType;
        //checking for out of bounds squares
        if(!((0<= row && row<=3) && (0<= col && col<=3))){
            System.out.println("Squares are out of the board");
        }
        //checking for square being empty
        if(grid[row][col] == null){
            grid[row][col] =  new Piece(piece , color);
        }
        else{
            System.out.println("Square is already occupied");
        }
    }
}
