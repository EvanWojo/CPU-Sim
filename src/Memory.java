public class Memory {
    public Word32 address = new Word32();
    public Word32 value = new Word32();

    private final Word32[] dram= new Word32[1000];

    public int addressAsInt() {

        int bit, result = 0;

        for (int i = 0; i < address.word32.length ; i++) {
            if (address.word32[address.word32.length - i - 1].getValue().equals(Bit.boolValues.TRUE))
                bit = 1;
            else
                bit = 0;

            result += (int) ((Math.pow(2, i)) * bit);

        }

        if (result < 0 || result > 999)
            throw new ArrayIndexOutOfBoundsException();

        return result;
    }

    public Memory() {
        for (int i = 0; i < dram.length; i++) {
            dram[i] = new Word32();
        }
    }

    public void read() {
        dram[addressAsInt()].copy(value);
    }

    public void write() {
        value.copy(dram[addressAsInt()]);
    }

    public void load(String[] data) {

        for (int i = 0; i < data.length; i++) { //Loops through every string in data
            Word32 str = new Word32();
            str.fromString(data[i]);
            dram[i] = str;
        }
    }
}
