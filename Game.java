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
    int whitePiecesPlaced = 0;
    int blackPiecesPlaced = 0;
    int piecesPlaced = 0;
    int roundHelper = 0;

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
        if(currentPlayer == whitePlayer){
            System.out.println("White");
        }
        else if(currentPlayer == blackPlayer){
            System.out.println("Black");
        }
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

        if(currentPlayer == whitePlayer){
            if(whitePiecesPlaced < 2){
                board.placeAPiece(temp, pieceRow, pieceCol);
                whitePiecesPlaced++;
            }
            List<int[]> whiteCoordinates = board.whitePieceList();
            if(whiteCoordinates.size() >= 2){
                int tempRX, tempKX, tempPX, tempRY, tempKY, tempPY;

                tempRX = whiteCoordinates.get(0)[0];
                tempKX = whiteCoordinates.get(1)[0];

                tempRY = whiteCoordinates.get(0)[1];
                tempKY = whiteCoordinates.get(1)[1];

                tempPX = pieceRow;
                tempPY = pieceCol;

                if(!checkWinningCondition(tempRX, tempRY, tempKX, tempKY, tempPX, tempPY)){
                    board.placeAPiece(temp, pieceRow, pieceCol);
                } else {
                    System.out.println("Cannot place here, game will end !");
                }
            }
            
        }
        else if(currentPlayer == blackPlayer){
            if(blackPiecesPlaced < 2){
                board.placeAPiece(temp, pieceRow, pieceCol);
                blackPiecesPlaced++;
            }
            List<int[]> blackCoordinates = board.blackPieceList();
            if(blackCoordinates.size() >= 2){
                int tempRX, tempKX, tempPX, tempRY, tempKY, tempPY;
            
                tempRX = blackCoordinates.get(0)[0];
                tempKX = blackCoordinates.get(1)[0];
                // tempPX = blackCoordinates.get(2)[0];
                tempRY = blackCoordinates.get(0)[1];
                tempKY = blackCoordinates.get(1)[1];
                // tempPY = whiteCoordinates.get(2)[1];
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

        // if(piecesPlaced < 2){
        //     board.placeAPiece(temp, pieceRow,pieceCol);
        //     piecesPlaced++;
        // }
        // else{
            
        //     List<int[]> coordinates = board.getPiecePositionsByColor(currentPlayer.knight.color);
        //     int tempRX, tempKX, tempPX, tempRY, tempKY, tempPY;
            
        //     tempRX = coordinates.get(0)[0];
        //     tempKX = coordinates.get(1)[0];
        //     // tempPX = coordinates.get(2)[0];
        //     tempRY = coordinates.get(0)[1];
        //     tempKY = coordinates.get(1)[1];
        //     // tempPY = coordinates.get(2)[1];
        //     tempPX = pieceRow;
        //     tempPY = pieceCol;



        //     if(!checkWinningCondition(tempRX, tempRY, tempKX, tempKY, tempPX, tempPY)){
        //         board.placeAPiece(temp, pieceRow, pieceCol);

        //     }
        //     else{
        //         System.out.println("Cannot place here, game will end !");
        //     }
        
        
        

        


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
        // checking for diagonal 
        else if( (minRow + 1 == midRow && midRow + 1 == maxRow) && (minCol + 1 == midCol && midCol + 1 == maxCol) ){
            return true;
        }
        return false;
        
    }

    public void moveAPiece(){
        // taking user input ffor source coords
        int srcRow, srcCol;
        System.out.println("Enter the piece you wish to move");
        System.out.println("Enter piece row");
        srcRow = sc.nextInt();
        System.out.println("Enter piece column");
        srcCol = sc.nextInt();

        // validating user input
        // checking if piece coords are in range
        if(! (board.isSquareValid(srcRow, srcCol))){
            System.out.println("Piece coordinates out of range ");
            return;
        }

        //checking if the entered cell is not empty
        if((board.grid[srcRow][srcCol] == null)){
            System.out.println("There exists no piece at the entered cell");
            return;
        }

        //checking if piece belongs to the current player
        Piece currentPiece = board.grid[srcRow][srcCol];
        // if(!(currentPlayer == whitePlayer && currentPiece.color == Piece.Color.WHITE)){
        //     System.out.println("Please select your piece 1");
        // }
        // else if(!(currentPlayer == blackPlayer && currentPiece.color == Piece.Color.BLACK)){
        //     System.out.println("Please select your piece 2");
        // }

        // taking user input for destination coords
        int dstRow, dstCol;
        System.out.println("Enter the destination you want to move to");
        System.out.println("Enter destination row");
        dstRow = sc.nextInt();
        System.out.println("Enter destination column");
        dstCol = sc.nextInt();

        //checking if destination coords are in range
        if(!(board.isSquareValid(dstRow, dstCol))){
            System.out.println("Please enter destination coords in range");
            return;
        }

        // checking behaviour of pieces 
        if(! board.validMove(srcRow,srcCol, dstRow, dstCol)){
            System.out.println("This piece cannot move here as per the law of chess");
            return;
        }

        if(board.grid[dstRow][dstCol] == null){
            board.grid[dstRow][dstCol] = currentPiece;
            board.grid[srcRow][srcCol] = null;
        }
        else{
            board.grid[dstRow][dstCol].status = Piece.Status.IN_LOBBY;
            board.grid[dstRow][dstCol] = currentPiece;
            board.grid[srcRow][srcCol] = null;
        }

    }

    public void play(){
    //     // helper();
        board.display();
        piecePlacer();
        switchPlayer();
        board.display();
        piecePlacer();
        switchPlayer();
        board.display();
        piecePlacer();
        switchPlayer();
        board.display();
        piecePlacer();
        switchPlayer();
        board.display();
        piecePlacer();
        switchPlayer();
        board.display();
        moveAPiece();
        board.display();
    }

    public boolean checkWin(Player tempPlayer){
        Piece r,k,p;
        
        r = tempPlayer.rook; k = tempPlayer.knight; p = tempPlayer.pawn;
        int rx, ry, kx, ky, px, py;
        List<int[]> rCoords =  board.getPiecePositionsByColor(r.color); 
        List<int[]> kCoords =  board.getPiecePositionsByColor(k.color); 
        List<int[]> pCoords =  board.getPiecePositionsByColor(p.color); 

        rx = rCoords.get(0)[0];
        ry = rCoords.get(0)[1];
        kx = kCoords.get(0)[0];
        ky = kCoords.get(0)[1];
        px = pCoords.get(0)[0];
        py = pCoords.get(0)[1];

        if(checkWinningCondition(rx,ry,kx,ky,px,py)){
            return true;
        }




        return false;
    }
    // implementing game loop
    public void gameLoop(){
        board.display();
        if(!completedPlacementRound()){
            placementRound();
        }
        while(!checkWin(currentPlayer)){

            switchPlayer();
        }
    }

    public void switchPlayer(){
        if(currentPlayer == whitePlayer){ currentPlayer = blackPlayer ;}
        else if(currentPlayer == blackPlayer){ currentPlayer = whitePlayer ;}
    }
    
    public void placementRound(){
        while(roundHelper < 5){
            if(currentPlayer == whitePlayer){
                System.out.println("White");
            }
            else if(currentPlayer == blackPlayer){
                System.out.println("Black");
            }
            piecePlacer();
            
            
            board.display();
            switchPlayer();
            roundHelper++;
        }
    }

    public boolean completedPlacementRound(){return(roundHelper == 4);}

}
