package dev.michalrelich.tablebase.code.three_piece;

import dev.michalrelich.engine.backend.helper.Check;
import dev.michalrelich.engine.backend.positioncheck.PositionCheck;
import dev.michalrelich.tablebase.code.Helpers;
import dev.michalrelich.tablebase.code.MoveGenerator;

import java.nio.file.Path;
import java.util.Arrays;

public class ThreePieceAlgorithm {


    public static void KXKLoop(int pieceInt) {
        int index = 0;
        int allPositions = ThreePieceConstants.KXKPositions(pieceInt);

        String name = Helpers.getFileNameThreePiece(pieceInt);
        assert name != null;
        Path path = Path.of("data", "three_piece", name, name + ".bin");

        boolean firstIteration = true;

        while (index < allPositions) {
            for (int turn = 1; turn <= 2; turn++) {
                for (int kingOne = 0; kingOne <= 63; kingOne++) {

                    boolean proceed = pieceInt == 5 ? Helpers.isLeftHalfKingPosition(kingOne) :
                            Helpers.isEighthKingPosition(kingOne);

                    if (!proceed) continue;
                    for (int kingTwo = 0; kingTwo <= 63; kingTwo++) {
                        for (int piece = 0; piece <= 63; piece++) {
                            int[] pieces = {turn, 8, kingOne, kingTwo, 1, pieceInt * 100 + piece};

                            if (PositionCheck.checkPosition(pieces)) {
                                write(pieces, index, path, firstIteration);
                                index++;
                            }
                        }
                    }
                }
            }

            firstIteration = false;
        }
    }

    public static void write(int[] pieces, int index, Path path, boolean firstIteration) {
        int[][] moves = MoveGenerator.generateMoves(pieces);

        if (isACheckmateOrStalemate(moves)) return; // would mean the position itself is a checkmate or stalemate

        if (firstIteration) {
            for (int[] move : moves) {
                if (move[0] == 0) break;
                int[][] movesOfMoves = MoveGenerator.generateMoves(move); // we get 2 ply deep here

                if (isACheckmateOrStalemate(movesOfMoves) && Check.isInCheck(move) != 0) { // runs isInCheck twice incl. in checkPosition
                    byte value = (byte) (pieces[0] == 1 ? 1 : -1);
                    Helpers.rewriteByteForIntIndex(path, index, value);

                    System.out.println("Found a checkmate in 1 for position: " + Arrays.toString(pieces));
                    return;
                }
            }
        }
    }

    public static boolean isACheckmateOrStalemate(int[][] moves) {
        return moves[0][0] == 0;
    }

    public static int[] getPositionByIndex(int index) {
        return null;
    }
}
