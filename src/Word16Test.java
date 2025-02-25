import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Word16Test {

    Bit t = new Bit(true);
    Bit f = new Bit(false);

    private Word16 getStripe() {
        return new Word16(new Bit[]{ t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f });
    }

    private Word16 getStripe2() {
        return new Word16(new Bit[]{ f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t });
    }

    private Word16 getAllTrue() {
        return new Word16(new Bit[]{ t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t });
    }

    private Word16 getAllFalse() {
        return new Word16(new Bit[]{ f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f });
    }

    @Test
    void getBitN() {
        var w = getStripe();
        for (int i=0;i<16;i++) {
            Bit result = new Bit(true);
            w.getBitN(i,result);
            if (i%2==1)
                assertEquals(result.getValue(),f.getValue());
            else
                assertEquals(result.getValue(),t.getValue());
        }
    }

    @Test
    void and() {
        Word16 fw = getAllFalse();
        Word16 tw = getAllTrue();
        Word16 sw = getStripe();
        Word16 result = new Word16();
        fw.and(fw,result);
        assertTrue(fw.equals(result));
        tw.and(tw,result);
        assertTrue(tw.equals(result));
        sw.and(tw,result);
        assertTrue(sw.equals(result));
        sw.and(sw,result);
        assertTrue(sw.equals(result));
    }

    @Test
    void or() {
        Word16 fw = getAllFalse();
        Word16 tw = getAllTrue();
        Word16 sw = getStripe();
        Word16 result = new Word16();
        fw.or(fw,result);
        assertTrue(fw.equals(result));
        tw.or(tw,result);
        assertTrue(tw.equals(result));
        sw.or(tw,result);
        assertTrue(tw.equals(result));
        sw.or(sw,result);
        assertTrue(sw.equals(result));
        sw.or(fw,result);
        assertTrue(sw.equals(result));
    }

    @Test
    void xor() {
        Word16 fw = getAllFalse();
        Word16 tw = getAllTrue();
        Word16 sw = getStripe();
        Word16 sw2 = getStripe2();
        Word16 result = new Word16();
        fw.xor(fw,result);
        assertTrue(fw.equals(result));

        tw.xor(tw,result);
        assertTrue(fw.equals(result));

        sw.xor(tw,result);
        assertTrue(sw2.equals(result));

        sw.xor(sw,result);
        assertTrue(fw.equals(result));

        sw.xor(fw,result);
        assertTrue(sw.equals(result));
    }

    @Test
    void not() {
        Word16 fw = getAllFalse();
        Word16 tw = getAllTrue();
        Word16 sw = getStripe();
        Word16 sw2 = getStripe2();
        Word16 result = new Word16();
        fw.not(result);
        assertTrue(tw.equals(result));
        tw.not(result);
        assertTrue(fw.equals(result));
        sw.not(result);
        assertTrue(sw2.equals(result));
        sw2.not(result);
        assertTrue(sw.equals(result));
    }
}