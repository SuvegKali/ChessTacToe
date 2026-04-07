public class Piece {
    public enum Type {ROOK, KNIGHT, PAWN};
    public enum Color {WHITE, BLACK};
    public enum Status {ON_BOARD, IN_LOBBY};

    public Type type;
    public Color color;
    public Status status;

    public Piece(){}

    public Piece(Type type, Color color){
        this.type = type;
        this.color = color;
        this.status = Status.IN_LOBBY;
    }

    @Override
    public String toString() {
        String colorChar = (color == Color.WHITE) ? "W" : "B";
        String typeChar;

        switch(type) {
            case ROOK: typeChar = "R"; break;
            case KNIGHT: typeChar = "N"; break;
            case PAWN: typeChar = "P"; break;
            default: typeChar = "?";
        }

        return colorChar + typeChar;
    }
}