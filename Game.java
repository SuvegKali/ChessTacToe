public class Game  {
    // some attributes 
    private Board board;
    private Piece item;
    private Player whitePlayer;
    private Player blackPlayer;

    public  Game(){
        
        this.board = new Board();    // creating a new empty board
        this.whitePlayer = new Player(Piece.Color.WHITE);
        this.blackPlayer = new Player(Piece.Color.BLACK);

    }

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

    public void play(){
        // helper();
        board.display();
        board.placeAPiece();
        board.display();
    }

    
}
