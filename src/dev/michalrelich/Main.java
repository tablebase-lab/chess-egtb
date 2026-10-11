package dev.michalrelich;

import dev.michalrelich.engine.backend.Constants;
import dev.michalrelich.engine.frontend.Board;
import dev.michalrelich.engine.frontend.Piece;
import dev.michalrelich.tablebase.code.three_piece.FileZeroPopulator;
import dev.michalrelich.tablebase.code.three_piece.ThreePieceAlgorithm;

import java.util.Random;

import static dev.michalrelich.engine.frontend.Piece.PieceColor.BLACK;
import static dev.michalrelich.engine.frontend.Piece.PieceColor.WHITE;
import static dev.michalrelich.engine.frontend.Piece.PieceType.KING;

public class Main {

    private static final Random random = new Random();

    // todo: limit conversions from int[] to long OR (ideally) use bit-packing
    // todo: pass a position that has checkmate in one to ThreePieceAlgorithm

    static void main() throws InterruptedException {

//        int[] position = {2, 8, 1, 20, 1, 211};
//
//        System.out.println(Arrays.toString(Move.removeZeroes(position)));
//
//        int[][] moves = MoveGenerator.generateMoves(position);
//        for (int[] move : moves) {
//            if (move[0] == 0) break;
//            int[][] movesOfMoves = MoveGenerator.generateMoves(move); // bug here
//            System.out.println(ThreePieceAlgorithm.isACheckmateOrStalemate(movesOfMoves));
//        }



        FileZeroPopulator.fullThreePiece();

        ThreePieceAlgorithm.KXKLoop(2);
//
//        String filePath = Path.of("data", "three_piece", Helpers.getFileNameThreePiece(2),
//                Helpers.getFileNameThreePiece(2) + ".bin").toString();
//
//        for (int i = 0; i <= 50000; i++) {
//            try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
//
//                file.seek(i);
//
//                byte signedIndex = file.readByte();
//
//                if (signedIndex == 1 || signedIndex == -1) {
//                    System.out.println("Found checkmate at index " + i + ": " + signedIndex);
//                }
//
////                System.out.println("Signed byte at index " + i + ": " + signedIndex);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
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
//        }
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
