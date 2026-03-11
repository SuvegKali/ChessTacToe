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

        // System.out.println("\nHere is the board");

        // Column numbers
        System.out.print("    ");
        for(int c = 0; c < 4; c++){
            System.out.printf("  %d   ", c);
        }
        System.out.println();

        // Top border
        System.out.println("   +-----+-----+-----+-----+");

        for(int i = 0; i < 4; i++){

            // Row number
            System.out.print(i + "  |");

            for(int j = 0; j < 4; j++){

                String cell;

                if(grid[i][j] == null){
                    cell = " ";
                } 
                else{
                    cell = grid[i][j].pieceName(grid[i][j]);
                }

                System.out.printf(" %-3s |", cell);
            }

            System.out.println();
            System.out.println("   +-----+-----+-----+-----+");
        }

        System.out.println();
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
            if(grid[dstRow][dstCol] == null){
                if( (Math.abs(rowDiff) == 1 && colDiff == 0) ||
                    (Math.abs(colDiff) == 1 && rowDiff == 0) ){
                    return true;
                }
                return false;
            }
            if(grid[dstRow][dstCol] != null){
                if(!(Math.abs(rowDiff) <= 1 && (Math.abs(colDiff) <= 1))){
                return false;
                }
                return true;
            }
            
        }

        return false;
    }

    public List<int[]> whitePieceList(){
        // returns a list of all the pieces currently placed on the board by the white player (with coordinates)
        List<int[]> whiteList = new ArrayList<>();

        whiteList = getPiecePositionsByColor(Piece.Color.WHITE);

        return whiteList;
    }

    public List<int[]> blackPieceList(){
        // returns a list of all the pieces currently placed on the board by the white player (with coordinates)
        List<int[]> blackList = new ArrayList<>();

        blackList = getPiecePositionsByColor(Piece.Color.BLACK);

        return blackList;
    }
}
