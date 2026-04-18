import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class GameGUI {

    // ── Palette ──────────────────────────────────────────────────────────────
    private static final Color LIGHT_SQUARE   = new Color(0xF0D9B5);
    private static final Color DARK_SQUARE    = new Color(0xB58863);
    private static final Color SELECTED_COLOR = new Color(0x7FC97F);
    private static final Color BG             = new Color(0xF0E6CB);
    private static final Color PANEL_BG       = new Color(0x16213E);
    private static final Color ACCENT         = new Color(0xE94560);
    private static final Color TEXT_LIGHT     = new Color(0xEEEEEE);
    private static final Color TEXT_DIM       = new Color(0x888888);
    private static final Color WHITE_PIECE_FG = new Color(0xFFFAF0);
    private static final Color BLACK_PIECE_FG = new Color(0x1A1A1A);
    private static final Color WHITE_PIECE_SHADOW = new Color(0x999999);
    private static final Color BLACK_PIECE_SHADOW = new Color(0x000000, true);

    // ── Chess Unicode symbols ─────────────────────────────────────────────────
    // index: [color 0=white,1=black][type 0=rook,1=knight,2=pawn]
    private static final String[][] PIECE_SYMBOLS = {
        { "\u2656", "\u2658", "\u2659" },  // white: ♖ ♘ ♙
        { "\u265C", "\u265E", "\u265F" }   // black: ♜ ♞ ♟
    };

    // ── State ─────────────────────────────────────────────────────────────────
    private boolean gameOver       = false;
    private boolean turnInProgress = false;
    private Game    game;

    private JFrame      frame;
    private SquarePanel[][] boardSquares;
    private PieceButton[]   whiteButtons;
    private PieceButton[]   blackButtons;

    private Piece selectedPiece = null;
    private int   selectedRow   = -1;
    private int   selectedCol   = -1;

    private JLabel statusLabel;
    private JLabel cdLabel;

    // ─────────────────────────────────────────────────────────────────────────
    public GameGUI() {
        game = new Game();

        frame = new JFrame("ChessTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(860, 560);
        frame.setMinimumSize(new Dimension(780, 500));
        frame.setLayout(new BorderLayout(0, 0));
        frame.getContentPane().setBackground(BG);

        frame.add(buildTopBar(),    BorderLayout.NORTH);
        frame.add(buildSidePanel(Piece.Color.WHITE), BorderLayout.WEST);
        frame.add(buildBoard(),     BorderLayout.CENTER);
        frame.add(buildSidePanel(Piece.Color.BLACK), BorderLayout.EAST);
        frame.add(buildStatusBar(), BorderLayout.SOUTH);

        refreshUI();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ── Top bar ───────────────────────────────────────────────────────────────
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        bar.setBackground(BG);
        bar.setBorder(new EmptyBorder(8, 0, 0, 0));

        JLabel title = new JLabel("CHESS TAC TOE");
        title.setFont(new Font("TF2 BUILD", Font.BOLD , 28));
        title.setForeground(ACCENT);
        bar.add(title);
        return bar;
    }

    // ── Board ─────────────────────────────────────────────────────────────────
    private JPanel buildBoard() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BG);

        JPanel board = new JPanel(new GridLayout(4, 4, 2, 2));
        board.setBackground(new Color(0x333333));
        board.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(0x555555), 2),
            new EmptyBorder(2, 2, 2, 2)
        ));

        boardSquares = new SquarePanel[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                boolean isLight = (i + j) % 2 == 0;
                SquarePanel sq = new SquarePanel(i, j, isLight);
                boardSquares[i][j] = sq;
                board.add(sq);
            }
        }

        board.setPreferredSize(new Dimension(360, 360));
        wrapper.add(board);
        return wrapper;
    }

    // ── Side panel (lobby) ────────────────────────────────────────────────────
    private JPanel buildSidePanel(Piece.Color color) {
        boolean isWhite = (color == Piece.Color.WHITE);

        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG);
        outer.setBorder(new EmptyBorder(0, isWhite ? 20 : 0, 0, isWhite ? 0 : 20));

        JPanel inner = new JPanel();
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBackground(PANEL_BG);
        inner.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(0x333355), 1),
            new EmptyBorder(12, 10, 12, 10)
        ));

        JLabel title = new JLabel(isWhite ? "WHITE" : "BLACK");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Monospaced", Font.BOLD, 11));
        title.setForeground(isWhite ? new Color(0xCCCCCC) : new Color(0x888888));
        title.setBorder(new EmptyBorder(0, 0, 10, 0));
        inner.add(title);

        Player player = isWhite ? game.getWhitePlayer() : game.getBlackPlayer();
        Piece[] pieces = player.getAllPieces();

        PieceButton[] buttons = new PieceButton[3];
        for (int i = 0; i < 3; i++) {
            PieceButton btn = new PieceButton(pieces[i]);
            buttons[i] = btn;
            int idx = i;
            btn.addActionListener(e -> selectPiece(pieces[idx]));
            inner.add(btn);
            if (i < 2) inner.add(Box.createVerticalStrut(8));
        }

        if (isWhite) whiteButtons = buttons;
        else         blackButtons = buttons;

        outer.add(inner);
        return outer;
    }

    // ── Status bar ────────────────────────────────────────────────────────────
    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(0x0F0F1A));
        bar.setBorder(new EmptyBorder(8, 20, 8, 20));

        statusLabel = new JLabel("WHITE'S TURN");
        statusLabel.setFont(new Font("Monospaced", Font.BOLD, 13));
        statusLabel.setForeground(TEXT_LIGHT);

        cdLabel = new JLabel("");
        cdLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
        cdLabel.setForeground(ACCENT);

        bar.add(statusLabel, BorderLayout.WEST);
        bar.add(cdLabel,     BorderLayout.EAST);
        return bar;
    }

    // ── Selection logic ───────────────────────────────────────────────────────
    private void selectPiece(Piece piece) {
        if (gameOver) return;
        if (piece.color != game.getCurrentPlayer().rook.color) return;
        if (piece.status == Piece.Status.ON_BOARD) return;

        selectedPiece = piece;
        selectedRow   = -1;
        selectedCol   = -1;
        refreshUI();
    }

    private void handleBoardClick(int row, int col) {
        if (gameOver) return;
        if (turnInProgress) return;
        turnInProgress = true;

        // ── PLACEMENT ────────────────────────────────────────────────────────
        if (selectedPiece != null && selectedPiece.status == Piece.Status.IN_LOBBY) {
            refreshUI();
            if (game.placePiece(selectedPiece, row, col)) {
                if (game.checkWin(game.getCurrentPlayer())) {
                    showWin();
                    return;
                }
                selectedPiece = null;
                selectedRow   = -1;
                selectedCol   = -1;
                game.switchPlayer();
            }
            refreshUI();
            turnInProgress = false;
            return;
        }

        // ── SELECT ───────────────────────────────────────────────────────────
        if (selectedRow == -1) {
            Piece p = game.getBoard().grid[row][col];
            if (p != null && p.color == game.getCurrentPlayer().rook.color) {
                selectedPiece = p;
                selectedRow   = row;
                selectedCol   = col;
            }
            refreshUI();
            turnInProgress = false;
            return;
        }

        // ── MOVE / CAPTURE ───────────────────────────────────────────────────
        boolean moved = game.movePiece(selectedRow, selectedCol, row, col);

        if (!moved) {
            int cd = game.getCurrentCooldown();
            showError(cd > 0
                ? "Capture on cooldown! (" + cd + " turn" + (cd > 1 ? "s" : "") + " remaining)"
                : "Invalid move for this piece.");
            selectedPiece = null;
            selectedRow   = -1;
            selectedCol   = -1;
            refreshUI();
        } else {
            refreshUI();
            if (game.checkWin(game.getCurrentPlayer())) {
                showWin();
                return;
            }
            selectedPiece = null;
            selectedRow   = -1;
            selectedCol   = -1;
            game.switchPlayer();
        }

        refreshUI();
        turnInProgress = false;
    }

    // ── UI refresh ────────────────────────────────────────────────────────────
    private void refreshUI() {
        Board b = game.getBoard();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Piece p = b.grid[i][j];
                boolean sel = (i == selectedRow && j == selectedCol);
                boardSquares[i][j].update(p, sel);
            }
        }

        updateSidePanel(game.getWhitePlayer(), whiteButtons);
        updateSidePanel(game.getBlackPlayer(), blackButtons);

        boolean isWhite = (game.getCurrentPlayer() == game.getWhitePlayer());
        statusLabel.setText((isWhite ? "WHITE" : "BLACK") + "'S TURN");
        statusLabel.setForeground(isWhite ? new Color(0xF0D9B5) : new Color(0xB58863));

        int cd = game.getCurrentCooldown();
        cdLabel.setText(cd > 0 ? "⚔ COOLDOWN: " + cd : "");
    }

    private void updateSidePanel(Player player, PieceButton[] buttons) {
        Piece[] pieces = player.getAllPieces();
        boolean isCurrentPlayer = (player == game.getCurrentPlayer());

        for (int i = 0; i < 3; i++) {
            boolean onBoard  = pieces[i].status == Piece.Status.ON_BOARD;
            boolean selected = (pieces[i] == selectedPiece);
            buttons[i].setEnabled(!onBoard && isCurrentPlayer);
            buttons[i].setSelected(selected);
            buttons[i].repaint();
        }
    }

    // ── Dialogs ───────────────────────────────────────────────────────────────
    private void showWin() {
        String winner = (game.getCurrentPlayer() == game.getWhitePlayer()) ? "White" : "Black";
        gameOver = true;

        JOptionPane.showMessageDialog(frame,
            winner + " wins! Three in a row!",
            "Game Over",
            JOptionPane.INFORMATION_MESSAGE);

        turnInProgress = false;
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Invalid Action", JOptionPane.WARNING_MESSAGE);
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Inner: SquarePanel — one cell of the 4×4 board
    // ═════════════════════════════════════════════════════════════════════════
    private class SquarePanel extends JPanel {
        private final boolean isLight;
        private Piece piece;
        private boolean selected;

        SquarePanel(int row, int col, boolean isLight) {
            this.isLight = isLight;
            setPreferredSize(new Dimension(88, 88));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) {
                    handleBoardClick(row, col);
                }
                @Override public void mouseEntered(MouseEvent e) { repaint(); }
                @Override public void mouseExited(MouseEvent e)  { repaint(); }
            });
        }

        void update(Piece p, boolean sel) {
            this.piece    = p;
            this.selected = sel;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

            int w = getWidth(), h = getHeight();

            // Square background
            Color base = selected ? SELECTED_COLOR : (isLight ? LIGHT_SQUARE : DARK_SQUARE);
            g2.setColor(base);
            g2.fillRect(0, 0, w, h);

            // Subtle inner highlight on light squares
            if (isLight && !selected) {
                g2.setColor(new Color(255, 255, 255, 30));
                g2.fillRect(0, 0, w, 3);
            }

            // Piece symbol
            if (piece != null) {
                int colorIdx = (piece.color == Piece.Color.WHITE) ? 0 : 1;
                int typeIdx  = (piece.type  == Piece.Type.ROOK)   ? 0
                             : (piece.type  == Piece.Type.KNIGHT)  ? 1 : 2;
                String sym = PIECE_SYMBOLS[colorIdx][typeIdx];

                // Shadow
                Font pieceFont = new Font("Serif", Font.PLAIN, 48);
                g2.setFont(pieceFont);
                FontMetrics fm = g2.getFontMetrics();
                int tx = (w - fm.stringWidth(sym)) / 2;
                int ty = (h - fm.getHeight()) / 2 + fm.getAscent();

                g2.setColor(piece.color == Piece.Color.WHITE
                    ? WHITE_PIECE_SHADOW : BLACK_PIECE_SHADOW);
                g2.drawString(sym, tx + 2, ty + 2);

                // Piece
                g2.setColor(piece.color == Piece.Color.WHITE
                    ? WHITE_PIECE_FG : BLACK_PIECE_FG);
                g2.drawString(sym, tx, ty);
            }

            g2.dispose();
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    // Inner: PieceButton — lobby piece selector
    // ═════════════════════════════════════════════════════════════════════════
    private class PieceButton extends JButton {
        private final Piece piece;
        private boolean selected;

        PieceButton(Piece piece) {
            this.piece = piece;
            setPreferredSize(new Dimension(80, 80));
            setMaximumSize(new Dimension(80, 80));
            setAlignmentX(Component.CENTER_ALIGNMENT);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        public void setSelected(boolean sel) { this.selected = sel; }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

            int w = getWidth(), h = getHeight();
            boolean isWhite = piece.color == Piece.Color.WHITE;
            boolean onBoard = piece.status == Piece.Status.ON_BOARD;

            // Background circle
            Color bgColor;
            if (onBoard) {
                bgColor = new Color(0x2A2A3A);
            } else if (selected) {
                bgColor = new Color(0x4A7A4A);
            } else if (!isEnabled()) {
                bgColor = new Color(0x2A2A3A);
            } else {
                bgColor = isWhite ? new Color(0x3A3A2A) : new Color(0x2A2A2A);
            }

            g2.setColor(bgColor);
            g2.fillRoundRect(4, 4, w - 8, h - 8, 16, 16);

            // Border
            Color borderColor = selected ? SELECTED_COLOR
                : (onBoard ? new Color(0x444444) : new Color(0x555555));
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(onBoard ? 1f : 1.5f));
            g2.drawRoundRect(4, 4, w - 8, h - 8, 16, 16);

            // Symbol
            int colorIdx = isWhite ? 0 : 1;
            int typeIdx  = (piece.type == Piece.Type.ROOK)   ? 0
                         : (piece.type == Piece.Type.KNIGHT)  ? 1 : 2;
            String sym = PIECE_SYMBOLS[colorIdx][typeIdx];

            Font f = new Font("Serif", Font.PLAIN, onBoard ? 28 : 36);
            g2.setFont(f);
            FontMetrics fm = g2.getFontMetrics();
            int tx = (w - fm.stringWidth(sym)) / 2;
            int ty = (h - fm.getHeight()) / 2 + fm.getAscent();

            if (onBoard) {
                g2.setColor(new Color(255, 255, 255, 40));
            } else {
                g2.setColor(isWhite ? WHITE_PIECE_FG : new Color(0xAAAAAA));
            }
            g2.drawString(sym, tx, ty);

            // "ON BOARD" badge
            if (onBoard) {
                g2.setFont(new Font("Monospaced", Font.PLAIN, 8));
                g2.setColor(new Color(0x666666));
                String badge = "ON BOARD";
                g2.drawString(badge, (w - g2.getFontMetrics().stringWidth(badge)) / 2, h - 6);
            }

            g2.dispose();
        }
    }

    // ── Entry point ───────────────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameGUI::new);
    }
}