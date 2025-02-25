import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryTest {

    @Test
    void addressAsInt() {
        var m = new Memory();
        for (int i = 0; i < 1000; i++) {
            TestConverter.fromInt(i, m.address);
            assertEquals(i, m.addressAsInt());
        }
    }

    @Test
    void readwrite() {
        var m = new Memory();
        for (int i = 0; i < 1000; i++) {
            TestConverter.fromInt(i, m.address);
            TestConverter.fromInt(i+4000, m.value);
            m.write();
        }
        for (int i = 0; i < 1000; i++) {
            TestConverter.fromInt(i, m.address);
            m.read();
            assertEquals(i+4000, TestConverter.toInt(m.value));
        }
    }

    @Test
    void load() {
        var data = new String[1000];
        var result = new Word32();
        for (int i = 0; i < 1000; i++) {
            TestConverter.fromInt(8000+i,result);
            data[i]=result.toString().replace(",","");
        }
        var m = new Memory();
        m.load(data);
        for (int i = 0; i < 1000; i++) {
            TestConverter.fromInt(i, m.address);
            m.read();
            assertEquals(i+8000, TestConverter.toInt(m.value));
        }
    }
}