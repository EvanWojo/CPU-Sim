
public class TestConverter {
    public static void fromInt(int value, Word32 result) {

        int r;
        boolean negative = value < 0;
        value = Math.abs(value);

        for (int i = result.word32.length - 1; i >= 0; i--) {
            r = value % 2;
            value /= 2;
            if (r == 0) {
                result.word32[i].assign(Bit.boolValues.FALSE);
            } else {
                result.word32[i].assign(Bit.boolValues.TRUE);
            }
        }

        if (negative) {
            TwosComplement(result);
        }

    }

    public static int toInt(Word32 value) {

        int bit, result = 0;

        for (int i = 0; i < value.word32.length ; i++) {
            if (value.word32[value.word32.length - i - 1].getValue().equals(Bit.boolValues.TRUE))
                bit = 1;
            else
                bit = 0;

            result += (int) ((Math.pow(2, i)) * bit);

        }

        if (result < 0)
            result++;

        return result;
    }

    public static void TwosComplement(Word32 A) {
        Word32 B = new Word32();
        B.word32[31].assign(Bit.boolValues.TRUE);

        for (int i = A.word32.length - 1; i >= 0; i--) {
            if (A.word32[i].getValue().equals(Bit.boolValues.FALSE))
                A.word32[i].assign(Bit.boolValues.TRUE);
            else
                A.word32[i].assign(Bit.boolValues.FALSE);
        }

        Adder.add(A, B, A);
    }
}