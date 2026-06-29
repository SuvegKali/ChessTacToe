public class Player {
    public Piece rook;
    public Piece knight;
    public Piece pawn;

    public Piece.Color color;

    public Player(Piece.Color color){
        this.color = color;
        this.rook = new Piece(Piece.Type.ROOK, color);
        this.knight = new Piece(Piece.Type.KNIGHT, color);
        this.pawn = new Piece(Piece.Type.PAWN, color);
    }

    public Piece[] getAllPieces() {
        return new Piece[]{rook, knight, pawn};
    }
}