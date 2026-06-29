public class Game {

    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player currentPlayer;
    private int whiteCooldown = 0;
    private int blackCooldown = 0;

    private int roundHelper = 0;

    public Game(){
        board = new Board();
        whitePlayer = new Player(Piece.Color.WHITE);
        blackPlayer = new Player(Piece.Color.BLACK);
        currentPlayer = whitePlayer;
    }

    public Board getBoard(){
        return board;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public Player getWhitePlayer(){
        return whitePlayer;
    }

    public Player getBlackPlayer(){
        return blackPlayer;
    }

    public void switchPlayer() {
    // ✅ only decrement the current player's cooldown before switching
    if (currentPlayer == whitePlayer) {
        if (whiteCooldown > 0) whiteCooldown--;
    } else {
        if (blackCooldown > 0) blackCooldown--;
    }

    currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
}
    public boolean isPlacementPhase(){
        return roundHelper < 4;
    }

    public int getCurrentCooldown(){
    return (currentPlayer == whitePlayer) ? whiteCooldown : blackCooldown;
}

    public boolean placePiece(Piece piece, int row, int col){

        if(piece.status == Piece.Status.ON_BOARD) return false;
        if(!board.isSquareValid(row,col)) return false;
        if(!board.isSquareEmpty(row,col)) return false;

        int size = (piece.color == Piece.Color.WHITE) ?
                board.whitePieceList().size() :
                board.blackPieceList().size();

        if(size >= 2){
            int[][] coords = getFirstTwoCoords(piece.color);

            if(coords == null) return false;

            if(checkWinningCondition(
                    coords[0][0], coords[0][1],
                    coords[1][0], coords[1][1],
                    row, col)){
                return false;
            }
        }

        board.placeAPiece(piece, row, col);

        if(roundHelper < 4){
            roundHelper++;
        }

        return true;
    }

    private int[][] getFirstTwoCoords(Piece.Color color){
        java.util.List<int[]> list = (color == Piece.Color.WHITE)
                ? board.whitePieceList()
                : board.blackPieceList();

        if(list.size() < 2) return null;

        return new int[][]{list.get(0), list.get(1)};
    }

   public boolean movePiece(int srcRow, int srcCol, int dstRow, int dstCol){

    // 🔹 basic validation
    if(!board.isSquareValid(srcRow,srcCol) || !board.isSquareValid(dstRow,dstCol)){
        return false;
    }

    Piece currentPiece = board.grid[srcRow][srcCol];

    if(currentPiece == null) return false;

    // 🔹 ensure correct player piece
    if(currentPiece.color != currentPlayer.rook.color){
        return false;
    }

    Piece target = board.grid[dstRow][dstCol];

    // 🔹 prevent capturing your own piece
    if(target != null && target.color == currentPiece.color){
        return false;
    }

    // 🔥 CHECK COOLDOWN FIRST (CRITICAL FIX)
    if(target != null){
        int cooldown = (currentPiece.color == Piece.Color.WHITE) ? whiteCooldown : blackCooldown;

        System.out.println("Cooldown before move: " + cooldown);

        if(cooldown > 0){
            System.out.println("Capture blocked due to cooldown");
            return false;
        }
    }

    // 🔹 NOW check movement rules
    if(!board.validMove(srcRow,srcCol,dstRow,dstCol)){
        return false;
    }

    // 🔥 EXECUTE CAPTURE
    // 🔥 EXECUTE CAPTURE
if(target != null){
    target.status = Piece.Status.IN_LOBBY;

    // ✅ FIX: Set to 2 so after one switchPlayer() call it becomes 1,
    //         and the player is correctly blocked for their next turn
    if(currentPiece.color == Piece.Color.WHITE){
        whiteCooldown = 2;
    } else {
        blackCooldown = 2;
    }
}

    // 🔹 perform move
    board.grid[dstRow][dstCol] = currentPiece;
    board.grid[srcRow][srcCol] = null;

    return true;
}
    public boolean checkWin(Player player){

    int count = 0;
    int[][] coords = new int[3][2];

    for(int i=0;i<4;i++){
        for(int j=0;j<4;j++){
            Piece p = board.grid[i][j];

            if(p != null && p.color == player.rook.color){
                if(count < 3){
                    coords[count][0] = i;
                    coords[count][1] = j;
                }
                count++;
            }
        }
    }

    if(count < 3) return false;

    return checkWinningCondition(
        coords[0][0], coords[0][1],
        coords[1][0], coords[1][1],
        coords[2][0], coords[2][1]
    );
}

    public boolean checkWinningCondition(int rx, int ry, int kx, int ky, int px, int py){
        int minRow = Math.min(rx,Math.min(kx,px));
        int maxRow = Math.max(rx,Math.max(kx,px));
        int midRow = rx + kx + px - minRow - maxRow;

        int minCol = Math.min(ry,Math.min(ky,py));
        int maxCol = Math.max(ry,Math.max(ky,py));
        int midCol = ry + ky + py - minCol - maxCol;

        if(rx == kx && kx == px){
            return (minCol + 1 == midCol && midCol + 1 == maxCol);
        }
        else if(ry == ky && ky == py){
            return (minRow + 1 == midRow && midRow + 1 == maxRow);
        }
        else if((minRow + 1 == midRow && midRow + 1 == maxRow) &&
                (minCol + 1 == midCol && midCol + 1 == maxCol)){
            return true;
        }

        return false;
    }
}