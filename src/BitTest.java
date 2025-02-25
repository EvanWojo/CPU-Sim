import org.junit.jupiter.api.Assertions;

class BitTest {

    Bit t1, t2, f1, f2;
    BitTest() {
        t1 = new Bit(true);
        t2 = new Bit(true);
        f1 = new Bit(false);
        f2 = new Bit(false);
    }

    private void stillOK() {
        Assertions.assertEquals(Bit.boolValues.TRUE,t1.getValue());
        Assertions.assertEquals(Bit.boolValues.TRUE,t2.getValue());
        Assertions.assertEquals(Bit.boolValues.FALSE,f1.getValue());
        Assertions.assertEquals(Bit.boolValues.FALSE,f2.getValue());
    }

    @org.junit.jupiter.api.Test
    void testAnd() {
        Bit result = new Bit(true);
        t1.and(t2,result);
        Assertions.assertEquals(Bit.boolValues.TRUE,result.getValue());
        f1.and(t2,result);
        Assertions.assertEquals(Bit.boolValues.FALSE,result.getValue());
        t1.and(f2,result);
        Assertions.assertEquals(Bit.boolValues.FALSE,result.getValue());
        f1.and(f2,result);
        Assertions.assertEquals(Bit.boolValues.FALSE,result.getValue());
        stillOK();
    }

    @org.junit.jupiter.api.Test
    void testOr() {
        Bit result = new Bit(true);
        t1.or(t2,result);
        Assertions.assertEquals(Bit.boolValues.TRUE,result.getValue());
        f1.or(t2,result);
        Assertions.assertEquals(Bit.boolValues.TRUE,result.getValue());
        t1.or(f2,result);
        Assertions.assertEquals(Bit.boolValues.TRUE,result.getValue());
        f1.or(f2,result);
        Assertions.assertEquals(Bit.boolValues.FALSE,result.getValue());
        stillOK();
    }

    @org.junit.jupiter.api.Test
    void testXor() {
        Bit result = new Bit(true);
        t1.xor(t2,result);
        Assertions.assertEquals(Bit.boolValues.FALSE,result.getValue());
        f1.xor(t2,result);
        Assertions.assertEquals(Bit.boolValues.TRUE,result.getValue());
        t1.xor(f2,result);
        Assertions.assertEquals(Bit.boolValues.TRUE,result.getValue());
        f1.xor(f2,result);
        Assertions.assertEquals(Bit.boolValues.FALSE,result.getValue());
        stillOK();
    }

    @org.junit.jupiter.api.Test
    void testNot() {
        Bit result = new Bit(true);
        t1.not(result);
        Assertions.assertEquals(Bit.boolValues.FALSE,result.getValue());
        f1.not(result);
        Assertions.assertEquals(Bit.boolValues.TRUE,result.getValue());
        stillOK();
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        Bit result = new Bit(true);
        Assertions.assertEquals("t",result.toString());
        Bit f = new Bit(false);
        Assertions.assertEquals("f",f.toString());
    }
}