
public class Adder {
    public static void subtract(Word32 a, Word32 b, Word32 result) {

        TestConverter.TwosComplement(b); //Negate B + 1

        add(a, b, result); // a + (-b)

    }

    public static void add(Word32 a, Word32 b, Word32 result) {

        boolean carry = false;

        for (int i = a.word32.length - 1; i >= 0; i--) {

            if (a.word32[i].getValue().equals(Bit.boolValues.FALSE) && b.word32[i].getValue().equals(Bit.boolValues.FALSE)) { // 0 + 0
                if (!carry) {
                    result.word32[i].assign(Bit.boolValues.FALSE);
                } else {
                    result.word32[i].assign(Bit.boolValues.TRUE);
                    carry = false;
                }
            } else if (a.word32[i].getValue().equals(Bit.boolValues.TRUE) && b.word32[i].getValue().equals(Bit.boolValues.FALSE)) { // 1 + 0
                if (!carry) {
                    result.word32[i].assign(Bit.boolValues.TRUE);
                } else {
                    result.word32[i].assign(Bit.boolValues.FALSE);
                }
            } else if (a.word32[i].getValue().equals(Bit.boolValues.FALSE) && b.word32[i].getValue().equals(Bit.boolValues.TRUE)) { // 0 + 1
                if (!carry) {
                    result.word32[i].assign(Bit.boolValues.TRUE);
                } else {
                    result.word32[i].assign(Bit.boolValues.FALSE);
                }
            } else if (a.word32[i].getValue().equals(Bit.boolValues.TRUE) && b.word32[i].getValue().equals(Bit.boolValues.TRUE)) { // 1 + 1
                if (!carry) {
                    result.word32[i].assign(Bit.boolValues.FALSE);
                } else {
                    result.word32[i].assign(Bit.boolValues.TRUE);
                }
                carry = true;
            }
        }
    }
}
