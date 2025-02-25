public class Multiplier {
    public static void multiply(Word32 a, Word32 b, Word32 result) {

        int shiftAmount = 0;
        Word32 temp = new Word32(); //Holds the previous word to be added after a shift

        for (int i = b.word32.length - 1; i >= 0; i--) {

            if (b.word32[i].getValue().equals(Bit.boolValues.TRUE)) { //Only shift and add if b[i] is 1
                Shifter.LeftShift(a, shiftAmount, result);
                Adder.add(result, temp, result);
            }
            result.copy(temp);
            shiftAmount++;
        }
    }
}
