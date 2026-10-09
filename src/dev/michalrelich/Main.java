package dev.michalrelich;

import dev.michalrelich.engine.backend.Constants;
import dev.michalrelich.engine.frontend.Board;
import dev.michalrelich.engine.frontend.Piece;
import dev.michalrelich.tablebase.code.Helpers;
import dev.michalrelich.tablebase.code.three_piece.FilePopulator;
import dev.michalrelich.tablebase.code.three_piece.ThreePieceAlgorithm;

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

        FilePopulator.fullThreePiece();

        ThreePieceAlgorithm.KXKLoop(1);

        String filePath = Path.of("data", "three_piece", Helpers.getFileNameThreePiece(1),
                Helpers.getFileNameThreePiece(1) + ".bin").toString();

        for (int i = 0; i <= 50000; i++) {
            // Open file in read-only mode ("r")
            try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {

                // Jump directly to byte position 10 (0-indexed)
                file.seek(i);

                // readByte() returns a signed Java byte (-128 to 127)
                byte signedIndex = file.readByte();

                if (signedIndex == 1) {
                    System.out.println("Found checkmate at index " + i + ": " + signedIndex);
                }

//                System.out.println("Signed byte at index " + i + ": " + signedIndex);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        LocalTime currentTime = LocalTime.now();
//        System.out.println("Begin Time: " + currentTime);
//
//
//            for (int j = 0; j <= 9; j++) {
//                for (int k = 0; k <= 63; k++) {
//                    for (int l = 0; l <= 63; l++) {
//                            int[] arr = {1, 8, j, k, 1, 200 + l};
//                            PositionCheck.checkPosition(GaussHelper.longFromArr(arr));
//                    }
//                }
//            }
//
//        currentTime = LocalTime.now();
//        System.out.println("Current Time: " + currentTime);

//        Path path = Path.of("data", "three_piece", "KRK");
//        KingFoldersGenerator.generateNumberDirectories(0, 63, path);
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
