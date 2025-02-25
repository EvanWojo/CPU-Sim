
public class Word16 {
    final Bit[] word16 = new Bit[16];

    public Word16() {
        for (int i = 0; i < word16.length; i++) {
            word16[i] = new Bit();
        }
    }

    public Word16(Bit[] in) {
        System.arraycopy(in, 0, word16, 0, in.length);
    }

    public void copy(Word16 result) { // sets the values in "result" to be the same as the values in this instance; use "bit.assign"
        for (int i = 0; i < 16; i++) {
            result.word16[i].assign(this.word16[i].getValue());
        }

    }

    public void setBitN(int n, Bit source) { // sets the nth bit of this word to "source"
        this.word16[n].assign(source.getValue());
    }

    public void getBitN(int n, Bit result) { // sets result to be the same value as the nth bit of this word
        result.assign(this.word16[n].getValue());
    }

    public boolean equals(Word16 other) { // is other equal to this
        return equals(this, other);
    }

    public static boolean equals(Word16 a, Word16 b) {
        for (int i = 0; i < 16; i++) {
            if(a.word16[i].getValue() != b.word16[i].getValue())
                return false;
        }
        return true;
    }

    public void and(Word16 other, Word16 result) {
        and(this, other, result);
    }

    public static void and(Word16 a, Word16 b, Word16 result) {
        for (int i = 0; i < 16; i++) {
            a.word16[i].and(b.word16[i], result.word16[i]);
        }
    }

    public void or(Word16 other, Word16 result) {
        or(this, other, result);
    }

    public static void or(Word16 a, Word16 b, Word16 result) {
        for (int i = 0; i < 16; i++) {
            a.word16[i].or(b.word16[i], result.word16[i]);
        }
    }

    public void xor(Word16 other, Word16 result) {
        xor(this, other, result);
    }

    public static void xor(Word16 a, Word16 b, Word16 result) {
        for (int i = 0; i < 16; i++) {
            a.word16[i].xor(b.word16[i], result.word16[i]);
        }
    }

    public void not( Word16 result) {
        not(this, result);
    }

    public static void not(Word16 a, Word16 result) {
        for (int i = 0; i < 16; i++) {
            a.word16[i].not(result.word16[i]);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        /*  -- match this format
        for (Bit bit : bits) {
            sb.append(bit.toString());
            sb.append(",");
        }
         */
        for (int i = 0; i < 16; i++) {
            sb.append(word16[i].toString());
            sb.append(",");
        }
        return sb.toString();
    }
}