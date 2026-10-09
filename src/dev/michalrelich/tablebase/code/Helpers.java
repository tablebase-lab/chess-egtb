package dev.michalrelich.tablebase.code;

import dev.michalrelich.engine.backend.Constants;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Helpers {

    // intended for a loop from 0 to 27
    public static boolean isEighthKingPosition(int pos) {
        return pos <= 3 || (pos >= 9 && pos <= 11) || pos == 18 || pos == 19 || pos == 27;
    }

    public static boolean isLeftHalfKingPosition(int pos) {
        int length = Constants.BOARD_LENGTH;

        if (length % 2 != 0) return false;

        return pos % length < length / 2 + 1;
    }

    public static void rewriteByteForIntIndex(Path path, int index, byte value) {
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5 * 1024 * 1024); // 5MBs at a time
            mappedByteBuffer.put(index, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFileNameThreePiece(int pieceInt) {
        return switch (pieceInt) {
            case 1 -> "KQK";
            case 2 -> "KRK";
            case 5 -> "KPK";
            default -> null;
        };
    }
}
