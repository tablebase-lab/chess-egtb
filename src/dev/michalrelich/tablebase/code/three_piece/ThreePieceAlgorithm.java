package dev.michalrelich.tablebase.code.three_piece;

import dev.michalrelich.engine.backend.Constants;
import dev.michalrelich.engine.backend.helper.Check;
import dev.michalrelich.engine.backend.positioncheck.PositionCheck;
import dev.michalrelich.tablebase.code.Helpers;
import dev.michalrelich.tablebase.code.MoveGenerator;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class ThreePieceAlgorithm {

    public static int methodCalls = 0;

    public static void KXKLoop(int pieceInt) {
        MappedByteBuffer mappedByteBuffer = initializeMBB(pieceInt);

        firstIteration(pieceInt, mappedByteBuffer); // is PROVEN to work for ALL rook checkmates in the eighth
        mainIteration(pieceInt, mappedByteBuffer);
    }

    public static void mainIteration(int pieceInt, MappedByteBuffer mappedByteBuffer) {

        for (int mateInXPlies = 2; mateInXPlies <= 127; mateInXPlies++) {
            System.out.println("Now doing mate in " + mateInXPlies + " plies");
            int index = 0;
            for (int turn = 1; turn <= 2; turn++) {
                for (int kingTwo = 0; kingTwo <= 63; kingTwo++) {
                    for (int piece = 0; piece <= 63; piece++) {
                        for (int kingOne = 0; kingOne <= 63; kingOne++) { // king has to be here cuz of cached indexes /
                            boolean proceed = pieceInt == 5 ? Helpers.isLeftHalfKingPosition(kingOne) :
                                    Helpers.isEighthKingPosition(kingOne);

                            if (!proceed) continue;
                            int[] pieces = {turn, 8, kingOne, kingTwo, 1, pieceInt * 100 + piece};
                            if (PositionCheck.checkPosition(pieces)) {
                                if (mappedByteBuffer.get(index) != 0) {
                                    index++;
                                    continue;
                                }

                                if (searchForSolvedPlies(mateInXPlies, pieces, pieceInt, mappedByteBuffer)) {
                                    if (mateInXPlies == 16) {
                                        System.out.println("MATE IN 16 PLIES: " + Arrays.toString(pieces));
                                    }

                                    mappedByteBuffer.put(index, (byte) ((mateInXPlies + 1) / 2));
                                }

                                // at the end
                                index++;
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean searchForSolvedPlies(int mateInXPlies, int[] pieces, int pieceInt, MappedByteBuffer mappedByteBuffer) {
        int[][] pieceMoves = MoveGenerator.generateMoves(pieces);
        if (isACheckmateOrStalemate(pieceMoves)) return false;

        boolean isEven = mateInXPlies % 2 == 0;

        // the method calls for isOdd are about 100x more than for is even, around 6 million per ply
        if (isEven) {
            for (int[] movePos : pieceMoves) {
                if (movePos[0] == 0) break;

                int result = retrieveValueByPosition(movePos, pieceInt, mappedByteBuffer);
                if (result == -1) continue;

                boolean cantContinue = result == 0; // can't be even one 0 move we don't know
                if (cantContinue) return false;
            }
            return true;

        } else {
            for (int[] movePos : pieceMoves) {
                if (movePos[0] == 0) break;

                int result = retrieveValueByPosition(movePos, pieceInt, mappedByteBuffer);
                if (result == -1) continue;
                if (result == (mateInXPlies + 1) / 2 - 1) return true; // fault here
            }
            return false;
        }
    }

    public static int retrieveValueByPosition(int[] position, int pieceInt, MappedByteBuffer mappedByteBuffer) {

        if (position[0] == 0) return 0; // stalemate, checkmate

        if (position.length <= 5) return 0; //  only two kings

        int turn = position[Constants.TURN_INDEX];
        int blackKingPos = position[Constants.BLACK_KING_INDEX];

        int piecePos = position[Constants.DELIMITER_INDEX + 1] % 100;

        int index = ThreePieceConstants.returnIndexFromArray(pieceInt, turn, blackKingPos, piecePos);
        for (int kingOne = 0; kingOne <= 63; kingOne++) {
            boolean proceed = pieceInt == 5 ? Helpers.isLeftHalfKingPosition(kingOne) :
                    Helpers.isEighthKingPosition(kingOne);

            if (!proceed) continue;

            int[] arr = {turn, 8, kingOne, blackKingPos, 1, pieceInt * 100 + piecePos};
            if (PositionCheck.checkPosition(arr)) {
                if (Arrays.equals(position, arr)) {
                    return retrieveValueByIndex(index, pieceInt, mappedByteBuffer);
                }
                index++;
            }
        }

        return 0;
    }

    public static int retrieveValueByIndex(int index, int pieceInt, MappedByteBuffer mappedByteBuffer) {
        assert Helpers.getFileNameThreePiece(pieceInt) != null;

//        methodCalls++;
//        if (methodCalls % 10000 == 0) {
//            System.out.println("Called " + methodCalls);
//        }

        return mappedByteBuffer.get(index);
    }

    public static void firstIteration(int pieceInt, MappedByteBuffer mappedByteBuffer) {
        int index = 0;

        for (int turn = 1; turn <= 2; turn++) {
            for (int kingTwo = 0; kingTwo <= 63; kingTwo++) {
                for (int piece = 0; piece <= 63; piece++) {
                    for (int kingOne = 0; kingOne <= 63; kingOne++) {

                        boolean proceed = pieceInt == 5 ? Helpers.isLeftHalfKingPosition(kingOne) :
                                Helpers.isEighthKingPosition(kingOne);
                        if (!proceed) continue;

                        int[] pieces = {turn, 8, kingOne, kingTwo, 1, pieceInt * 100 + piece};
                        if (PositionCheck.checkPosition(pieces)) {
                            writerForFirstIteration(pieces, index, mappedByteBuffer);
                            index++;
                        }
                    }
                }
            }

        }
    }

    public static void writerForFirstIteration(int[] pieces, int index, MappedByteBuffer mappedByteBuffer) {
        int[][] moves = MoveGenerator.generateMoves(pieces);

        if (isACheckmateOrStalemate(moves)) return; // would mean the position itself is a checkmate or stalemate

        for (int[] move : moves) {
            if (move[0] == 0) break;
            int[][] movesOfMoves = MoveGenerator.generateMoves(move); // we get 2 ply deep here

            if (isACheckmateOrStalemate(movesOfMoves) && Check.isInCheck(move) >= 1) { // runs isInCheck twice incl. in checkPosition
                byte value = (byte) (pieces[0] == 1 ? 1 : -1);
                mappedByteBuffer.put(index, value);
                return;
            }
        }
    }

    public static boolean isACheckmateOrStalemate(int[][] moves) {
        return moves[0][0] == 0;
    }

    public static int retrieveIndexByPositionOld(int pieceInt, int[] position) {
        int count = 0;
        for (int turn = 1; turn <= 2; turn++) {
            for (int kingOne = 0; kingOne <= 63; kingOne++) {
                boolean proceed = pieceInt == 5 ? Helpers.isLeftHalfKingPosition(kingOne) :
                        Helpers.isEighthKingPosition(kingOne);

                if (!proceed) continue;

                for (int kingTwo = 0; kingTwo <= 63; kingTwo++) {
                    for (int piece = 0; piece <= 63; piece++) {
                        int[] arr = {turn, 8, kingOne, kingTwo, 1, pieceInt * 100 + piece};
                        if (Arrays.equals(arr, position)) return count;
                        if (PositionCheck.checkPosition(arr)) count++;
                    }
                }
            }
        }

        return -1;
    }

    public static int[] retrievePositionByIndexOld(int pieceInt, int index) {
        int count = 0;
        for (int turn = 1; turn <= 2; turn++) {
            for (int kingOne = 0; kingOne <= 63; kingOne++) {
                boolean proceed = pieceInt == 5 ? Helpers.isLeftHalfKingPosition(kingOne) :
                        Helpers.isEighthKingPosition(kingOne);

                if (!proceed) continue;

                for (int kingTwo = 0; kingTwo <= 63; kingTwo++) {
                    for (int piece = 0; piece <= 63; piece++) {
                        int[] arr = {turn, 8, kingOne, kingTwo, 1, pieceInt * 100 + piece};
                        if (count == index) return arr;
                        if (PositionCheck.checkPosition(arr)) count++;
                    }
                }
            }
        }

        return null;
    }

    public static MappedByteBuffer initializeMBB(int pieceInt) {
        String name = Helpers.getFileNameThreePiece(pieceInt);
        assert name != null;
        Path path = Path.of("data", "three_piece", name, name + ".bin");

        MappedByteBuffer mappedByteBuffer = null;

        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5 * 1024 * 1024); // 5MBs at a time
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mappedByteBuffer;
    }
}
