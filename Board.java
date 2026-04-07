import java.util.ArrayList;
import java.util.List;

public class Board {
    Piece grid[][];

    public Board(){
        grid = new Piece[4][4];
    }

    public void display(){
        System.out.print("    ");
        for(int c = 0; c < 4; c++){
            System.out.printf("  %d   ", c);
        }
        System.out.println();

        System.out.println("   +-----+-----+-----+-----+");

        for(int i = 0; i < 4; i++){
            System.out.print(i + "  |");

            for(int j = 0; j < 4; j++){
                String cell;

                if(grid[i][j] == null){
                    cell = " ";
                } 
                else{
                    cell = grid[i][j].toString();
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

    public boolean isSquareValid(int row, int col){
        return ((0 <= row && row <= 3) && (0 <= col && col <= 3));
    }

    public boolean isSquareEmpty(int row, int col){
        return (grid[row][col] == null);
    }

    public boolean isStatusOkay(Piece thisPiece){
        return (thisPiece.status == Piece.Status.IN_LOBBY);
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

public List<int[]> whitePieceList(){
    return getPiecePositionsByColor(Piece.Color.WHITE);
}

public List<int[]> blackPieceList(){
    return getPiecePositionsByColor(Piece.Color.BLACK);
}

    public boolean validMove(int srcRow, int srcCol, int dstRow, int dstCol){
        Piece.Type pieceType = grid[srcRow][srcCol].type;

        int rowDiff = dstRow - srcRow;
        int colDiff = dstCol - srcCol;

        if (pieceType == Piece.Type.ROOK){

    // must move in straight line
    if(!(srcRow == dstRow || srcCol == dstCol)){
        return false;
    }

    // check obstruction
    if(srcRow == dstRow){ // horizontal
        int step = (dstCol > srcCol) ? 1 : -1;

        for(int c = srcCol + step; c != dstCol; c += step){
            if(grid[srcRow][c] != null){
                return false;
            }
        }
    }
    else{ // vertical
        int step = (dstRow > srcRow) ? 1 : -1;

        for(int r = srcRow + step; r != dstRow; r += step){
            if(grid[r][srcCol] != null){
                return false;
            }
        }
    }

    return true;
}

        if(pieceType == Piece.Type.KNIGHT){
            return ((Math.abs(rowDiff) == 2 && Math.abs(colDiff)==1) ||
                    (Math.abs(rowDiff)==1 && Math.abs(colDiff)==2));
        }

        if(pieceType == Piece.Type.PAWN){
            if(pieceType == Piece.Type.PAWN){

    // movement (no capture)
    if(grid[dstRow][dstCol] == null){
        return (
            (Math.abs(rowDiff) == 1 && colDiff == 0) ||
            (Math.abs(colDiff) == 1 && rowDiff == 0)
        );
    }

    // capture (diagonal only)
    if(grid[dstRow][dstCol] != null){
        return (Math.abs(rowDiff) == 1 && Math.abs(colDiff) == 1);
    }
}
        }

        return false;
    }
}