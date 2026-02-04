public class Piece {
    public enum Type {ROOK, KNIGHT, PAWN};
    public enum Color {WHITE, BLACK};
    public enum Status {ON_BOARD, IN_LOBBY};

    public Type type;
    public Color color;
    public Status status;

    

    public Piece(){
        System.out.println();
    }

    public Piece(Type type, Color color){
        this.type = type;
        this.color = color;
        this.status = Status.IN_LOBBY;

    }
    // @Override
    // public String toString() {
    //     String colorChar = (color == Color.WHITE) ? "W" : "B";
    //     String typeChar;
    //     switch(type) {
    //         case ROOK: typeChar = "R"; break;
    //         case KNIGHT: typeChar = "N"; break;
    //         case PAWN: typeChar = "P"; break;
    //         default: typeChar = "?";
    //     }
    //     return colorChar + typeChar;
    // }


    public String pieceName(Piece temp){
        String colorChar;
        String typeChar;
        if(temp.color == Color.WHITE){
            colorChar = "W"; 
        }
        else{
            colorChar = "B";
        }

        if(temp.type == Type.ROOK){
            typeChar = "R";
        }
        else if(temp.type == Type.KNIGHT){
            typeChar = "K";
        }
        else{ // if(temp.type == Type.PAWN){
            typeChar = "P";
        }
        return colorChar + typeChar;

    }

    


}
