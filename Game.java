import java.util.Scanner;
import java.lang.Math;
import java.util.List;

public class Game  {
    // some attributes 
    private Board board;
    private Piece item;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player currentPlayer;
    int piecesPlaced = 0;

    public  Game(){
        
        board = new Board();    // creating a new empty board
        whitePlayer = new Player(Piece.Color.WHITE);
        blackPlayer = new Player(Piece.Color.BLACK);
        currentPlayer = whitePlayer;

    }
    Scanner sc = new Scanner(System.in);

    //  public void helper(){
    //     item = new Piece(Piece.Type.KNIGHT, Piece.Color.BLACK);
    //     board.grid[3][0] = item;
    // }

    // public void pieceDisplayer(){
    //     System.out.println("Pieces owned by white player :");
    //     for(Piece.Type t : Piece.Type.values()){
    //         if(Piece.Color  == Piece.Color.WHITE){
    //             System.out.println("")
    //         }
    //     }
        
    //     System.out.println()
    // }

    public void piecePlacer(){
        System.out.println("Which piece ? : 1 = ROOK, 2 = KNIGHT, 3 = PAWN");
        int pieceType = sc.nextInt();
        
        

        Piece temp = null;
        switch(pieceType){
            case 1: temp = currentPlayer.rook; break;
            case 2: temp = currentPlayer.knight; break;
            case 3: temp = currentPlayer.pawn; break;
        }

        System.out.println("Which row ? 0 - 3");
        int pieceRow = sc.nextInt();

        System.out.println("Which col ? 0 - 3");
        int pieceCol = sc.nextInt();

        if(temp.status == Piece.Status.ON_BOARD){
            System.out.println("Already placed this piece");
            return;
        }

        if(piecesPlaced < 2){
            board.placeAPiece(temp, pieceRow,pieceCol);
            piecesPlaced++;
        }
        else{
            
            List<int[]> coordinates = board.getPiecePositionsByColor(currentPlayer.knight.color);
            int tempRX, tempKX, tempPX, tempRY, tempKY, tempPY;
            
            tempRX = coordinates.get(0)[0];
            tempKX = coordinates.get(1)[0];
            // tempPX = coordinates.get(2)[0];
            tempRY = coordinates.get(0)[1];
            tempKY = coordinates.get(1)[1];
            // tempPY = coordinates.get(2)[1];
            tempPX = pieceRow;
            tempPY = pieceCol;



            if(!checkWinningCondition(tempRX, tempRY, tempKX, tempKY, tempPX, tempPY)){
                board.placeAPiece(temp, pieceRow, pieceCol);

            }
            else{
                System.out.println("Cannot place here, game will end !");
            }
        }
        
        

        


    }

    public boolean checkWinningCondition(int rx, int ry, int kx, int ky, int px, int py){
        int minRow = Math.min(rx,Math.min(kx,px));
        int maxRow = Math.max(rx,Math.max(kx,px));
        int midRow = rx + kx + px - minRow - maxRow;
        int minCol = Math.min(ry,Math.min(ky,py));
        int maxCol = Math.max(ry,Math.max(ky,py));
        int midCol = ry + ky + py - minCol - maxCol;
        if(rx == kx && kx == px){
            if(minCol + 1 == midCol && midCol + 1 == maxCol){
                return true;
            }
        }
        else if(ry == ky && ky == py){
            if(minRow + 1 == midRow && midRow + 1 == maxRow){
                return true;
            }
        }
        return false;
        // else if ()
    }

    public void play(){
        // helper();
        board.display();
        piecePlacer();
        board.display();
        piecePlacer();
        board.display();
        piecePlacer();
        board.display();
        
    }

    
}
