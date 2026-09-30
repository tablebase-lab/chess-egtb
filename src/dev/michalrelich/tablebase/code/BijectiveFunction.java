package dev.michalrelich.tablebase.code;

public class BijectiveFunction {

    public static int bijectiveIndexFunctionTwoPositions(int positionOne, int positionTwo, int positionThree) {

        int function = (positionThree - 1) + 63 * (positionTwo) + (64 * 63 - 2) * positionOne;

        // figure out positionTwo = positionThree cases
        return function;
    }
}
