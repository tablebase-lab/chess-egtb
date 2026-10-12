package dev.michalrelich.tablebase.code.three_piece;

import dev.michalrelich.engine.backend.helper.Check;
import dev.michalrelich.engine.backend.positioncheck.PositionCheck;
import dev.michalrelich.tablebase.code.Helpers;
import dev.michalrelich.tablebase.code.MoveGenerator;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class ThreePieceAlgorithm {


    public static void KXKLoop(int pieceInt) {
        int allPositions = ThreePieceConstants.KXKPositions(pieceInt);

        String name = Helpers.getFileNameThreePiece(pieceInt);
        assert name != null;
        Path path = Path.of("data", "three_piece", name, name + ".bin");

        MappedByteBuffer mappedByteBuffer = null;

        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5 * 1024 * 1024); // 5MBs at a time
        } catch (IOException e) {
            e.printStackTrace();
        }

        firstIteration(allPositions, pieceInt, mappedByteBuffer);
        mainIteration(pieceInt, mappedByteBuffer);
    }

    public static void mainIteration(int pieceInt, MappedByteBuffer mappedByteBuffer) {

        for (int mateInXPlies = 2; mateInXPlies <= 127; mateInXPlies++) {
            System.out.println("Now doing mate in " + mateInXPlies + " plies");
            int index = 0;
            for (int turn = 1; turn <= 2; turn++) {
                for (int kingOne = 0; kingOne <= 63; kingOne++) {
                    boolean proceed = pieceInt == 5 ? Helpers.isLeftHalfKingPosition(kingOne) :
                            Helpers.isEighthKingPosition(kingOne);

                    System.out.println("Now checking for white king at: " + kingOne);

                    if (!proceed) continue;

                    for (int kingTwo = 0; kingTwo <= 63; kingTwo++) {
                        for (int piece = 0; piece <= 63; piece++) {
                            int[] pieces = {turn, 8, kingOne, kingTwo, 1, pieceInt * 100 + piece};
                            if (PositionCheck.checkPosition(pieces)) {
                                if (searchForSolvedPlies(mateInXPlies, pieces, pieceInt)) {
                                    assert mappedByteBuffer != null;
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

    public static boolean searchForSolvedPlies(int mateInXPlies, int[] pieces, int pieceInt) {
        int[][] pieceMoves = MoveGenerator.generateMoves(pieces);

        boolean isEven = mateInXPlies % 2 == 0;

        if (isEven) {
            for (int[] movePos : pieceMoves) {
                if (retrieveValueByPosition(movePos, pieceInt) != mateInXPlies) return false;
            }
            return true;
        } else {
            for (int[] movePos : pieceMoves) {
                if (retrieveValueByPosition(movePos, pieceInt) == mateInXPlies) return true;
            }
            return false;
        }
    }



    public static void firstIteration(int allPositions, int pieceInt, MappedByteBuffer mappedByteBuffer) {
        int index = 0;

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
                                writerForFirstIteration(pieces, index, mappedByteBuffer);
                                index++;
                            }
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

            if (isACheckmateOrStalemate(movesOfMoves) && Check.isInCheck(move) != 0) { // runs isInCheck twice incl. in checkPosition
                byte value = (byte) (pieces[0] == 1 ? 1 : -1);
                mappedByteBuffer.put(index, value);

                return;
            }
        }
    }

    public static boolean isACheckmateOrStalemate(int[][] moves) {
        return moves[0][0] == 0;
    }

    public static int retrieveValueByPosition(int[] position, int pieceInt) {
        int index = 0;
        for (int turn = 1; turn <= 2; turn++) {
            for (int kingOne = 0; kingOne <= 63; kingOne++) {
                boolean proceed = pieceInt == 5 ? Helpers.isLeftHalfKingPosition(kingOne) :
                        Helpers.isEighthKingPosition(kingOne);

                if (!proceed) continue;

                for (int kingTwo = 0; kingTwo <= 63; kingTwo++) {
                    for (int piece = 0; piece <= 63; piece++) {
                        int[] arr = {turn, 8, kingOne, kingTwo, 1, pieceInt * 100 + piece};
                        if (PositionCheck.checkPosition(arr)) {
                            if (Arrays.equals(position, arr)) {
                                return retrieveValueByIndex(index, pieceInt);
                            }
                            index++;
                        }
                    }
                }
            }
        }

        return 0;
    }

    public static int retrieveValueByIndex(int index, int pieceInt) {
        assert Helpers.getFileNameThreePiece(pieceInt) != null;

        String filePath = Path.of("data", "three_piece", Helpers.getFileNameThreePiece(pieceInt),
                Helpers.getFileNameThreePiece(pieceInt) + ".bin").toString();

        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {

            file.seek(index);

            return file.readByte();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
