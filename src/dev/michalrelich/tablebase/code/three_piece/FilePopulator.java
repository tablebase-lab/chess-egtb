package dev.michalrelich.tablebase.code.three_piece;

import dev.michalrelich.tablebase.code.EGTBConstants;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FilePopulator {

    public static void threePiece(int pieceInt) {

        String fileName = switch (pieceInt) {
            case 1 -> "KQK";
            case 2 -> "KRK";
            case 5 -> "KPK";
            default -> null;
        };

        int totalBytes = EGTBConstants.KXKPositions(pieceInt);
        int bufferSize = 8*1024*1024;
        int iterations = totalBytes / bufferSize;

        int remainder = totalBytes % bufferSize;
        byte[] byteArr = new byte[bufferSize];

        try (FileOutputStream fileOut = new FileOutputStream(String.format("%1$s/%2$s/%2$s.bin",
                EGTBConstants.THREE_PIECE_PATH, fileName))) {

            BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOut);

            for (int i = 0; i < iterations; i++) {
                bufferedOut.write(byteArr);
            }

            byte[] remainderArr = new byte[remainder];
            bufferedOut.write(remainderArr);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fullThreePiece() {
        int[] pieceInts = {1, 2, 5};

        for (int pieceInt : pieceInts) {
            threePiece(pieceInt);
        }
    }
}
