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

    public void placeAPiece(Piece thisPiece, int a, int b){

        if(isStatusOkay(thisPiece) && isSquareValid(a,b) && isSquareEmpty(a,b)){
            grid[a][b] = thisPiece;
            thisPiece.status = Piece.Status.ON_BOARD;
        }

    }

    public boolean isSquareValid(int row, int col){return ((0 <= row && row <= 3) && (0 <= col && col <= 3));}

    public boolean isSquareEmpty(int row, int col){return (grid[row][col] == null);}

    public boolean isStatusOkay(Piece thisPiece){return (thisPiece.status == Piece.Status.IN_LOBBY);}

}
