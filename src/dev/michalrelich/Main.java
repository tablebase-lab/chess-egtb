package dev.michalrelich;

import dev.michalrelich.engine.backend.Constants;
import dev.michalrelich.engine.backend.positioncheck.PositionCheck;
import dev.michalrelich.engine.frontend.Board;
import dev.michalrelich.engine.frontend.Piece;
import dev.michalrelich.tablebase.code.Helpers;
import dev.michalrelich.tablebase.code.three_piece.ThreePieceAlgorithm;
import dev.michalrelich.tablebase.code.three_piece.ThreePieceConstants;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Random;

import static dev.michalrelich.engine.frontend.Piece.PieceColor.BLACK;
import static dev.michalrelich.engine.frontend.Piece.PieceColor.WHITE;
import static dev.michalrelich.engine.frontend.Piece.PieceType.KING;

public class Main {

    private static final Random random = new Random();

    // todo: limit conversions from int[] to long OR (ideally) use bit-packing
    // todo: pass a position that has checkmate in one to ThreePieceAlgorithm

    static void main() throws InterruptedException {

//        FileZeroPopulator.fullThreePiece();
//        ThreePieceAlgorithm.KXKLoop(2);

        int res = ThreePieceAlgorithm.retrieveIndexByPositionOld(2, new int[]{1,8,18,2,1,219}); // 19417
        System.out.println(res);

        String filePath = Path.of("data", "three_piece", Helpers.getFileNameThreePiece(2),
                Helpers.getFileNameThreePiece(2) + ".bin").toString();

        for (int i = 0; i <= 50000; i++) {
            try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {

                file.seek(i);

                byte signedIndex = file.readByte();

                if (signedIndex == 1) {
                    System.out.println("Found checkmate in 1 moves at " + i + ": " + signedIndex);
                }

//                if (i == 19417) {
//                    System.out.println("Found checkmate in 2? " + signedIndex);
//                }

//                System.out.println("Signed byte at index " + i + ": " + signedIndex);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void methodMeasure() {
        System.out.println(ThreePieceConstants.returnIndexFromArray(2, 2, 12, 209));

        long start = System.nanoTime();

        System.out.println(ThreePieceConstants.returnIndexFromArray(2, 1, 53, 230));

        long end = System.nanoTime();

        double seconds = (end - start) / 1_000_000_000.0;

        System.out.println("Elapsed time: " + seconds + " seconds");


        long start2 = System.nanoTime();

        int pieceInt = 2;
        int count = 0;
        boolean flag = true;
        for (int turn = 1; turn <= 2; turn++) {
            for (int kingOne = 0; kingOne <= 63; kingOne++) {
                boolean proceed = pieceInt == 5 ? Helpers.isLeftHalfKingPosition(kingOne) :
                        Helpers.isEighthKingPosition(kingOne);

                if (!proceed) continue;

                for (int kingTwo = 0; kingTwo <= 63; kingTwo++) {
                    for (int piece = 0; piece <= 63; piece++) {
                        int[] arr = {turn, 8, kingOne, kingTwo, 1, pieceInt * 100 + piece};
                        if (PositionCheck.checkPosition(arr)) {
                            if (turn == 2 && kingTwo == 12 && flag) {
                                flag = false;
                                System.out.println(count);
                            }
                            count++;
                        }
                    }
                }
            }
        }

        long end2 = System.nanoTime();

        double seconds2 = (end2 - start2) / 1_000_000_000.0;

        System.out.println("Elapsed time: " + seconds2 + " seconds");

    }

    public static void addRandomPieces(Board board) {
        for (int i = 0; i < Constants.MAX_NON_KING_PIECES; i++) {
            Piece piece = new Piece(
                    Piece.PieceType.values()[random.nextInt(1, Piece.PieceType.values().length)],
                    Piece.PieceColor.values()[random.nextInt(2)]);
            int random1 = random.nextInt(Constants.BOARD_LENGTH) + 1;
            int random2 = random.nextInt(Constants.BOARD_LENGTH) + 1;
            System.out.println(piece + " row: " + random1 + ", col: " + random2);

            board.addToBoard(piece, random1, random2);
        }
    }

    public static void addRandomKings(Board board) {
        for (int i = 0; i <= 1; i++) {
            Piece.PieceColor color = i == 0 ? WHITE : BLACK;
            int row = random.nextInt(8) + 1;
            int col = random.nextInt(8) + 1;
            Piece piece = new Piece(KING, color);

            System.out.println(piece + ", row: " + row + ", col: " + col);
            board.addToBoard(piece, row, col);
        }
    }
}
