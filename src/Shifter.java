public class Shifter {
    public static void LeftShift(Word32 source, int amount, Word32 result) {

            for (int j = 0; j < source.word32.length; j++) {

                try {
                    result.word32[j].assign(source.word32[j + amount].getValue()); // For every bit in source assign to result the bit j + amount away
                } catch (ArrayIndexOutOfBoundsException e) {
                    result.word32[j].assign(Bit.boolValues.FALSE);
                }
            }
    }

    public static void RightShift(Word32 source, int amount, Word32 result) {

            for (int j = source.word32.length - 1; j >= 0; j--) {

                try {
                    result.word32[j].assign(source.word32[j - amount].getValue()); // For every bit in source assign to result the bit j - amount away
                } catch (ArrayIndexOutOfBoundsException e) {
                    result.word32[j].assign(Bit.boolValues.FALSE);
                }
            }
    }
}
