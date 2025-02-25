public class Word32 {

    final Bit[] word32 = new Bit[32];

    public Word32() {
        for (int i = 0; i < word32.length; i++) {
            word32[i] = new Bit();
        }
    }

    public Word32(Bit[] in) {
        System.arraycopy(in, 0, word32, 0, word32.length);
    }

    public void getTopHalf(Word16 result) { // sets result = bits 0-15 of this word. use bit.assign
        for (int i = 0; i < 16; i++) {
            result.word16[i].assign(this.word32[i].getValue());
        }
    }

    public void getBottomHalf(Word16 result) { // sets result = bits 16-31 of this word. use bit.assign
        for (int i = 0; i < 16; i++) {
            result.word16[i].assign(this.word32[i + 16].getValue());
        }
    }

    public void copy(Word32 result) { // sets result's bit to be the same as this. use bit.assign
        for (int i = 0; i < word32.length; i++) {
            result.word32[i].assign(this.word32[i].getValue());
        }
    }

    public boolean equals(Word32 other) {
        return equals(this, other);
    }

    public static boolean equals(Word32 a, Word32 b) {
        for (int i = 0; i < 32; i++) {
            if (!a.word32[i].getValue().equals(b.word32[i].getValue())) {
                return false;
            }
        }
        return true;
    }

    public void getBitN(int n, Bit result) { // use bit.assign
        result.assign(this.word32[n].getValue());
    }

    public void setBitN(int n, Bit source) { //  use bit.assign
        this.word32[n].assign(source.getValue());
    }

    public void and(Word32 other, Word32 result) {
        and(this, other, result);
    }

    public static void and(Word32 a, Word32 b, Word32 result) {
        for (int i = 0; i < a.word32.length; i++) {
            a.word32[i].and(b.word32[i], result.word32[i]);
        }
    }

    public void or(Word32 other, Word32 result) {
        or(this, other, result);
    }

    public static void or(Word32 a, Word32 b, Word32 result) {
        for (int i = 0; i < a.word32.length; i++) {
            a.word32[i].or(b.word32[i], result.word32[i]);
        }
    }

    public void xor(Word32 other, Word32 result) {
        xor(this, other, result);
    }

    public static void xor(Word32 a, Word32 b, Word32 result) {
        for (int i = 0; i < a.word32.length; i++) {
            a.word32[i].xor(b.word32[i], result.word32[i]);
        }
    }

    public void not( Word32 result) {
        not(this, result);
    }

    public static void not(Word32 a, Word32 result) {
        for (int i = 0; i < a.word32.length; i++) {
            a.word32[i].not(result.word32[i]);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        /* -- match this format
        for (Bit bit : bits) {
            sb.append(bit.toString());
            sb.append(",");
        }
         */
        for (Bit bit : word32) {
            sb.append(bit.toString());
            sb.append(",");
        }
        return sb.toString();
    }
}
