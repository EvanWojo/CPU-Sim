import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdderTest {

    Bit t= new Bit(true);
    Bit f= new Bit(false);

    private final int[] numbers = new int[]{
            10,300,0,-44,
            -1423594213, 1568954312, -845293742, 993467552, -1002356876, 650487392, -1679321994, 1225674195, -1483948319, 1778226502,
            -1215732624, 1684326107, -2000000000, 985376580, -368293261, 1435982683, -1349867242, 1659783211, -987434539, 1984926593,
            -1249689381, 567834206, -915738246, 1253118047, -1726490681, 287156821, -234987501, 1004167338, -1022349024, 1432598096,
            -541222031, 1529501409, -1354939675, 1848512084, -659723872, 1212637159, -1865380945, 1319174467, -531264957, 1423198693,
            -1856904220, 1762390080, -885593118, 1953276494, -1534292968, 312845100, -1459848126, 1160834292, -1728982749, 672494314,
            -804569507, 2000000000, -913123004, 793281411, -1349225479, 1126352639, -1204846983, 1706265927, -1689192557, 729095836,
            -428625593, 1023678212, -1271236756, 1611774240, -1162436285, 517496914, -2000000000, 1239761297, -1271821383, 951339517,
            -1115029406, 639715380, -963912050, 1019045685, -1454034953, 1694520610, -1582931746, 854220893, -1270798946, 980612153,
            -1716182635, 679432467, -1129071994, 1817369502, -1276455863, 1120518271, -1465328086, 1341798471, -1052921199, 813790007,
            -898602115, 1723867113, -1659012482, 929672746, -1064738039, 1413785072, -635308568, 1657128944, -1786543917, 741276693
    };
    @Test
    void testTestConverter() {
        var value = new Word32();
        assertEquals(0,TestConverter.toInt(value));
        value = new Word32(new Bit[]{
               f,f,f,f, f,f,f,f,      f,f,f,f, f,f,f,f,
                f,f,f,f, f,f,f,f,      f,f,t,f, t,f,t,f
        });
        assertEquals(42,TestConverter.toInt(value));

        value = new Word32(new Bit[]{
                t,t,t,t, t,t,t,t,      t,t,t,t, t,t,t,t,
                t,t,t,t, t,t,t,t,     t,t,t,f, t,f,f,t
        });
        assertEquals(-23,TestConverter.toInt(value));
    }

    @Test
    void testTestConverterRoundTrip() {
        for (var i : numbers) {
            var value = new Word32();
            TestConverter.fromInt(i,value);
            assertEquals(i, TestConverter.toInt(value));
        }
    }

    @Test
    void add() {
        var x = new Word32();
        var y = new Word32();
        var z = new Word32();
        for (var i : numbers) {
            for (var j : numbers) {
                TestConverter.fromInt(i, x);
                TestConverter.fromInt(j, y);
                Adder.add(x, y, z);
                assertEquals(i + j, TestConverter.toInt(z));
            }
        }
    }

    @Test
    void subtract() {
        var x = new Word32();
        var y = new Word32();
        var z = new Word32();
        for (var i : numbers) {
            for (var j : numbers) {
                TestConverter.fromInt(i, x);
                TestConverter.fromInt(j, y);
                Adder.subtract(x, y, z);
                assertEquals(i - j, TestConverter.toInt(z));
            }
        }
    }
}