import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Word32Test {

    Bit t = new Bit(true);
    Bit f = new Bit(false);

    private Word32 getStripe() {
        return new Word32(new Bit[]{
                t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f,
                t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f
        });
    }

    private Word32 getStripe2() {
        return new Word32(new Bit[]{
                f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t,
                f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t
        });
    }

    private Word32 getAllTrue() {
        return new Word32(new Bit[]{
                t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t,
                t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t
        });
    }

    private Word32 getAllFalse() {
        return new Word32(new Bit[]{
                f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f,
                f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f
        });
    }

    @Test
    void getBitN() {
        var w = getStripe();
        for (int i=0;i<32;i++) {
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
        Word32 fw = getAllFalse();
        Word32 tw = getAllTrue();
        Word32 sw = getStripe();
        Word32 result = new Word32();
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
        Word32 fw = getAllFalse();
        Word32 tw = getAllTrue();
        Word32 sw = getStripe();
        Word32 result = new Word32();
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
        Word32 fw = getAllFalse();
        Word32 tw = getAllTrue();
        Word32 sw = getStripe();
        Word32 sw2 = getStripe2();
        Word32 result = new Word32();
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
        Word32 fw = getAllFalse();
        Word32 tw = getAllTrue();
        Word32 sw = getStripe();
        Word32 sw2 = getStripe2();
        Word32 result = new Word32();
        fw.not(result);
        assertTrue(tw.equals(result));
        tw.not(result);
        assertTrue(fw.equals(result));
        sw.not(result);
        assertTrue(sw2.equals(result));
        sw2.not(result);
        assertTrue(sw.equals(result));
    }

    @Test
    void getTopHalf() {
        Word32 top = new Word32(new Bit[]{
                f,f,f,f, f,f,f,f, f,f,f,f, f,f,f,f,
                t,t,t,t, t,t,t,t, t,t,t,t, t,t,t,t,
        });
        Word32 opposite = new Word32(new Bit[]{
                        t,t,t,t, t,t,t,t, t,t,t,t, t,t,t,t,
                        f,f,f,f, f,f,f,f, f,f,f,f, f,f,f,f,
                });
        Word16 result = new Word16();
        top.getTopHalf(result);
        assertTrue(result.equals(new Word16( new Bit[]{ f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, } )));
        opposite.getTopHalf(result);
        assertTrue(result.equals(new Word16( new Bit[]{ t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, } )));
    }

    @Test
    void getBottomHalf() {
        Word32 top = new Word32(new Bit[]{
                t,t,t,t, t,t,t,t, t,t,t,t, t,t,t,t,
                f,f,f,f, f,f,f,f, f,f,f,f, f,f,f,f,
        });
        Word32 opposite = new Word32(new Bit[]{
                f,f,f,f, f,f,f,f, f,f,f,f, f,f,f,f,
                t,t,t,t, t,t,t,t, t,t,t,t, t,t,t,t,
        });
        Word16 result = new Word16();
        top.getBottomHalf(result);
        assertTrue(result.equals(new Word16( new Bit[]{ f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, } )));
        opposite.getBottomHalf(result);
        assertTrue(result.equals(new Word16( new Bit[]{ t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, } )));
    }

    @Test
    void copy() {
        var stripe = getStripe();
        var stripe2 = getStripe2();
        var copyStripe = new Word32();
        var copyStripe2 = new Word32();
        stripe.copy(copyStripe);
        stripe2.copy(copyStripe2);
        assertTrue(copyStripe.equals(getStripe()));
        assertTrue(copyStripe2.equals(getStripe2()));

    }
}