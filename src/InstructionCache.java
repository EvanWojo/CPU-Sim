public class InstructionCache {

    private final Word32 address = new Word32();
    private final Word32[] cache = new Word32[8];
    private final L2Cache l2cache;

    public InstructionCache(L2Cache l2cache) {
        for (int i = 0; i < cache.length; i++) {
            cache[i] = new Word32();
        }
        this.l2cache = l2cache;
        TestConverter.fromInt(5000, address);
    }

    public Word32 read(Word32 address) {
        Word32 maskedAddress = l2cache.maskAddress(address);

        if (maskedAddress.equals(this.address)) { //If the instruction is in this cache
            Processor.currentClockCycle += 10;
        } else { //Otherwise consult L2
            load(address);
            maskedAddress.copy(this.address);
            Processor.currentClockCycle += 50;
        }
        return cache[addressAsInt(address) % 8];

    }

    public void load(Word32 maskedAddress) {
        Word32[] words = new Word32[8];
        for (int i = 0; i < words.length; i++) {
            words[i] = new Word32();
        }
        words = l2cache.read(maskedAddress);
        for (int i = 0; i < words.length; i++) {
            words[i].copy(cache[i]);
        }
    }

    public int addressAsInt(Word32 address) {

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
}
