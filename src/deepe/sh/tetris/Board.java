package deepe.sh.tetris;

import java.util.Random;

import javax.swing.JComponent;

public class Board {
    private static JComponent boardComponent;
    private static int width, height;

    private static boolean[][] board;
    private static Coordinate[] piece = new Coordinate[4];
    private static PieceType pieceType;

    public static void init(int width, int height, JComponent boardComponent) {
        Board.width = width;
        Board.height = height;
        Board.boardComponent = boardComponent;
        board = new boolean[height][width];
    }

    public static void addNewPiece() {
        int midx = width / 2;

        // choose a random piece
        PieceType[] oneEach = {
            PieceType.SQUARE,
            PieceType.LINE_HORIZONTAL,
            PieceType.T_DOWN,
            PieceType.L1_DOWN,
            PieceType.L2_DOWN,
            PieceType.FOUR1_HORIZONTAL,
            PieceType.FOUR2_HORIZONTAL
        };
        pieceType = oneEach[new Random().nextInt(7)];
        
        // construct the choosen piece
        switch (pieceType) {
            case SQUARE:
                piece[0] = new Coordinate(midx    , 1);
                piece[1] = new Coordinate(midx + 1, 1);
                piece[2] = new Coordinate(midx    , 2);
                piece[3] = new Coordinate(midx + 1, 2);
                break;

            case LINE_HORIZONTAL:
                piece[0] = new Coordinate(midx - 1, 1);
                piece[1] = new Coordinate(midx    , 1);
                piece[2] = new Coordinate(midx + 1, 1);
                piece[3] = new Coordinate(midx + 2, 1);
                break;

            case T_DOWN:
                piece[0] = new Coordinate(midx - 1, 1);
                piece[1] = new Coordinate(midx    , 1);
                piece[2] = new Coordinate(midx + 1, 1);
                piece[3] = new Coordinate(midx,     2);
                break;

            case L1_DOWN:
                piece[0] = new Coordinate(midx - 1, 1);
                piece[1] = new Coordinate(midx    , 1);
                piece[2] = new Coordinate(midx + 1, 1);
                piece[3] = new Coordinate(midx - 1, 2);
                break;

            case L2_DOWN:
                piece[0] = new Coordinate(midx - 1, 1);
                piece[1] = new Coordinate(midx    , 1);
                piece[2] = new Coordinate(midx + 1, 1);
                piece[3] = new Coordinate(midx + 1, 2);
                break;

            case FOUR1_HORIZONTAL:
                piece[0] = new Coordinate(midx + 1, 1);
                piece[1] = new Coordinate(midx    , 1);
                piece[2] = new Coordinate(midx    , 2);
                piece[3] = new Coordinate(midx - 1, 2);
                break;

            case FOUR2_HORIZONTAL:
                piece[0] = new Coordinate(midx - 1, 1);
                piece[1] = new Coordinate(midx    , 1);
                piece[2] = new Coordinate(midx    , 2);
                piece[3] = new Coordinate(midx + 1, 2);
                break;
            
            default:
                // this label will never be reached, but it prevents
                // compiler warnings about incomplete switch statement
        }
        
        // rotate the piece randomly (1-4 times)
        int r = new Random().nextInt(4);
        for (int i = 0; i < r; i++)
            CurrentPiece.rotate();

        // draw the piece
        occupy(piece);
        boardComponent.repaint();
    }

    public static void checkBottomRow() {
        for (boolean b : board[height - 1])
            if (b == false)
                return;

        for (int x = width - 1; x > 0; x--)
            for (int y = height - 2; y > 0; y--)
                board[y+1][x] = board[y][x];
    }

    public static boolean isOccupied(int x, int y) {
        if (x < 0 || x >= width || y >= height)
            return true;
            
        return board[y][x];
    }

    public static boolean isOccupied(Coordinate[] piece) {
        for (Coordinate c : piece)
            if (isOccupied(c.x, c.y))
                return true;

        return false;
    }

    public static void occupy(Coordinate[] piece) {
        for (Coordinate c : piece)
            board[c.y][c.x] = true;
    }

    public static void unOccupy(Coordinate[] piece) {
        for (Coordinate c : piece)
            board[c.y][c.x] = false;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void repaintBoard() {
        boardComponent.repaint();
    }

    public static class CurrentPiece {

        public static boolean moveDown() {
            unOccupy(piece);

            boolean cannotMoveMore = false;
            for (Coordinate c : piece) {
                if (isOccupied(c.x, c.y + 1)) {
                    cannotMoveMore = true;
                    break;
                }
            }

            if (cannotMoveMore) {
                occupy(piece);
                return false;
            }

            for(int i = 0; i < piece.length; i++)
                piece[i].y += 1;
            occupy(piece);

            repaintBoard();
            return true;
        }

        public static boolean moveRight() {
            unOccupy(piece);

            boolean cannotMoveMore = false;
            for (Coordinate c : piece) {
                if (isOccupied(c.x + 1, c.y)) {
                    cannotMoveMore = true;
                    break;
                }
            }

            if (cannotMoveMore)
                return false;

            for(int i = 0; i < piece.length; i++)
                piece[i].x += 1;
            occupy(piece);

            repaintBoard();
            return true;
        }

        public static boolean moveLeft() {
            unOccupy(piece);

            boolean cannotMoveMore = false;
            for (Coordinate c : piece) {
                if (isOccupied(c.x - 1, c.y)) {
                    cannotMoveMore = true;
                    break;
                }
            }

            if (cannotMoveMore)
                return false;

            for(int i = 0; i < piece.length; i++)
                piece[i].x -= 1;
            occupy(piece);

            repaintBoard();
            return true;
        }

        public static void rotate() {
            unOccupy(piece);
            
            Coordinate[] newPiece = null;
            PieceType newPieceType = null;

            switch (pieceType) {
                case SQUARE:
                    return;

                case LINE_HORIZONTAL:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x + 1, piece[0].y - 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x - 1, piece[2].y + 1),
                        new Coordinate(piece[3].x - 2, piece[3].y + 2)
                    };
                    newPieceType = PieceType.LINE_VERTICAL;
                    break;

                case LINE_VERTICAL:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x - 1, piece[0].y + 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x + 1, piece[2].y - 1),
                        new Coordinate(piece[3].x + 2, piece[3].y - 2)
                    };
                    newPieceType = PieceType.LINE_HORIZONTAL;
                    break;
                
                case L1_UP:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x - 1, piece[0].y + 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x + 1, piece[2].y - 1),
                        new Coordinate(piece[3].x    , piece[3].y + 2)
                    };
                    newPieceType = PieceType.L1_RIGHT;
                    break;

                case L1_DOWN:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x + 1, piece[0].y - 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x - 1, piece[2].y + 1),
                        new Coordinate(piece[3].x    , piece[3].y - 2)
                    };
                    newPieceType = PieceType.L1_LEFT;
                    break;

                case L1_LEFT:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x + 1, piece[0].y + 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x - 1, piece[2].y - 1),
                        new Coordinate(piece[3].x + 2, piece[3].y    )
                    };
                    newPieceType = PieceType.L1_UP;
                    break;

                case L1_RIGHT:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x - 1, piece[0].y - 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x + 1, piece[2].y + 1),
                        new Coordinate(piece[3].x - 2, piece[3].y    )
                    };
                    newPieceType = PieceType.L1_DOWN;
                    break;

                case L2_UP:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x - 1, piece[0].y + 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x + 1, piece[2].y - 1),
                        new Coordinate(piece[3].x + 2, piece[3].y    ),
                    };
                    newPieceType = PieceType.L2_RIGHT;
                    break;

                case L2_DOWN:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x + 1, piece[0].y - 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x - 1, piece[2].y + 1),
                        new Coordinate(piece[3].x - 2, piece[3].y    ),
                    };
                    newPieceType = PieceType.L2_LEFT;
                    break;

                case L2_LEFT:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x + 1, piece[0].y + 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x - 1, piece[2].y - 1),
                        new Coordinate(piece[3].x    , piece[3].y - 2)
                    };
                    newPieceType = PieceType.L2_UP;
                    break;

                case L2_RIGHT:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x - 1, piece[0].y - 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x + 1, piece[2].y + 1),
                        new Coordinate(piece[3].x    , piece[3].y + 2)
                    };
                    newPieceType = PieceType.L2_DOWN;
                    break;

                case T_UP:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x - 1, piece[0].y + 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x + 1, piece[2].y - 1),
                        new Coordinate(piece[3].x + 1, piece[3].y + 1)
                    };
                    newPieceType = PieceType.T_RIGHT;
                    break;

                case T_DOWN:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x + 1, piece[0].y - 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x - 1, piece[2].y + 1),
                        new Coordinate(piece[3].x - 1, piece[3].y - 1)
                    };
                    newPieceType = PieceType.T_LEFT;
                    break;

                case T_LEFT:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x + 1, piece[0].y + 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x - 1, piece[2].y - 1),
                        new Coordinate(piece[3].x + 1, piece[3].y - 1)
                    };
                    newPieceType = PieceType.T_UP;
                    break;

                case T_RIGHT:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x - 1, piece[0].y - 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x + 1, piece[2].y + 1),
                        new Coordinate(piece[3].x - 1, piece[3].y + 1)
                    };
                    newPieceType = PieceType.T_DOWN;
                    break;

                case FOUR1_HORIZONTAL:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x - 1, piece[0].y - 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x + 1, piece[2].y - 1),
                        new Coordinate(piece[3].x + 2, piece[3].y    )
                    };
                    newPieceType = PieceType.FOUR1_VERTICAL;
                    break;

                case FOUR1_VERTICAL:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x + 1, piece[0].y + 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x - 1, piece[2].y + 1),
                        new Coordinate(piece[3].x - 2, piece[3].y    )
                    };
                    newPieceType = PieceType.FOUR1_HORIZONTAL;
                    break;

                case FOUR2_HORIZONTAL:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x + 1, piece[0].y - 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x - 1, piece[2].y - 1),
                        new Coordinate(piece[3].x - 2, piece[3].y    )
                    };
                    newPieceType = PieceType.FOUR2_VERTICAL;
                    break;

                case FOUR2_VERTICAL:
                    newPiece = new Coordinate[] {
                        new Coordinate(piece[0].x - 1, piece[0].y + 1),
                        new Coordinate(piece[1].x    , piece[1].y    ),
                        new Coordinate(piece[2].x + 1, piece[2].y + 1),
                        new Coordinate(piece[3].x + 2, piece[3].y    )
                    };
                    newPieceType = PieceType.FOUR2_HORIZONTAL;
                    break;
            }

            // if the new piece does not overlap any existing cells,
            // set it as the current piece, otherwise do nothing
            unOccupy(piece);
            if(!isOccupied(newPiece)) {
                piece = newPiece;
                pieceType = newPieceType;
            }
            occupy(piece);

            repaintBoard();
        }
    }
}
