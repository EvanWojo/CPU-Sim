import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShifterTest {

    int[] numbers = new int[] {17,44,129,0,53,12, 83,830,725,1033};
    int[] factors = new int[] {1,2,3,4,5,6,7,8,9};
    @Test
    void leftShift() {
        for (var number : numbers) {
            for (var factor : factors) {
                Word32 word = new Word32();
                Word32 result = new Word32();
                TestConverter.fromInt(number, word);
                Shifter.LeftShift(word, factor, result);
                int expected = number * ((int) Math.pow(2, factor));
                assertEquals(expected, TestConverter.toInt(result));
            }
        }
    }

    @Test
    void rightShift() {
            for (var number : numbers) {
                for (var factor : factors) {
                    Word32 word = new Word32();
                    Word32 result = new Word32();
                    TestConverter.fromInt(number, word);
                    Shifter.RightShift(word, factor, result);
                    int expected = number / ((int) Math.pow(2, factor ));
                    assertEquals( expected,TestConverter.toInt(result));
                }
            }
    }
}