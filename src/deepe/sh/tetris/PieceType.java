package deepe.sh.tetris;

public enum PieceType {
    //  [][]
    //  [][]
    SQUARE,

    //  [][][]
    //  []
    L1_DOWN,

    //  [][]
    //    []
    //    []
    L1_LEFT,

    //      []
    //  [][][]
    L1_UP,
    
    //  []
    //  []
    //  [][]
    L1_RIGHT,

    //  [][][]
    //      []
    L2_DOWN,

    //    []
    //    []
    //  [][]
    L2_LEFT,

    //  []
    //  [][][]
    L2_UP,
    
    //  [][]
    //  []
    //  []
    L2_RIGHT,

    //  [][][]
    //    []
    T_DOWN,

    //    []
    //  [][]
    //    []
    T_LEFT,

    //    []
    //  [][][]
    T_UP,

    //  []
    //  [][]
    //  []
    T_RIGHT,

    //  [][][][]
    LINE_HORIZONTAL,

    //  []
    //  []
    //  []
    //  []
    LINE_VERTICAL,

    //    [][]
    //  [][]
    FOUR1_HORIZONTAL,

    //  []
    //  [][]
    //    []
    FOUR1_VERTICAL,

    //  [][]
    //    [][]
    FOUR2_HORIZONTAL,

    //    []
    //  [][]
    //  []
    FOUR2_VERTICAL;
}
