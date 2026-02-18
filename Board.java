import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;

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

    public List<int[]> getPiecePositionsByColor(Piece.Color color) {
    List<int[]> positions = new ArrayList<>();
    
    for(int row = 0; row < 4; row++) {
        for(int col = 0; col < 4; col++) {
            Piece piece = grid[row][col];
            if(piece != null && piece.color == color) {
                positions.add(new int[]{row, col});
            }
        }
    }
    
    return positions;
}

    public boolean isSquareValid(int row, int col){return ((0 <= row && row <= 3) && (0 <= col && col <= 3));}

    public boolean isSquareEmpty(int row, int col){return (grid[row][col] == null);}

    public boolean isStatusOkay(Piece thisPiece){return (thisPiece.status == Piece.Status.IN_LOBBY);}

    public boolean validMove(int srcRow, int srcCol, int dstRow, int dstCol){

        Piece.Type pieceType;

        pieceType = grid[srcRow][srcCol].type;
        int rowDiff = dstRow - srcRow;
        int colDiff = dstCol - srcCol;

        if (pieceType == Piece.Type.ROOK){
            if((((srcRow == dstRow) && (colDiff == 0)) || (srcCol == dstCol) && (rowDiff == 0))){
                System.out.println("Rook cannot move here");
                return false;
            }
            return true;
            // obstruction checking will be implemented in the future. until then, the tester will have to make sure of no obstructions
        }

        if(pieceType == Piece.Type.KNIGHT){
            if(!(((Math.abs(rowDiff) == 2) && (Math.abs(colDiff)==1)) || ((Math.abs(rowDiff)==1) && (Math.abs(colDiff)==2)))){
                return false;
            }
            return true;
        }

        if(pieceType == Piece.Type.PAWN){
            if(!(Math.abs(rowDiff) <= 1 && (Math.abs(colDiff) <= 1))){
                return false;
            }
            return true;
        }

        return false;
    }

}
