package dev.michalrelich.tablebase.backend;

public class Constants {

    // board constants
    public static final int BOARD_LENGTH = 8;
    public static final int MAX_NON_KING_PIECES = 3;

    // arrays location constants
    public static final int TURN_INDEX = 0;
    public static final int EN_PASSANT_INDEX = 1;
    public static final int WHITE_KING_INDEX= 2;
    public static final int BLACK_KING_INDEX = 3;
    public static final int DELIMITER_INDEX = 4;
    public static final int LEAST_POSSIBLE_ARRAY_SIZE = 5;

    // gauss number location constants, counts the end as not included! (exclusive)
    public static final int TURN_BEGIN = 0;
    public static final int TURN_END = 1;
    public static final int EN_PASSANT_BEGIN = 1;
    public static final int EN_PASSANT_END = 2;
    public static final int WHITE_KING_BEGIN = 2;
    public static final int WHITE_KING_END = 4;
    public static final int BLACK_KING_BEGIN = 4;
    public static final int BLACK_KING_END = 6;
    public static final int DELIMITER_BEGIN = 6;
    public static final int DELIMITER_END = 7;

    // additional piece constants
    public static final int ADDITIONAL_PIECE_LENGTH = 3; // piece type + position

    // turn constants
    public static final int WHITE_TURN = 1;
    public static final int BLACK_TURN = 2;

    // en passant default (for where there is no en passant)
    public static final int ENP_DEFAULT = 8;

    public static boolean skipNonPositionIndex(int index) {
        return index <= EN_PASSANT_INDEX || index == DELIMITER_INDEX;
    }
}
