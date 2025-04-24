public class L2Cache {
    private final Word32[] addresses;
    private int index; //Index to determine where to place an address in 'addresses'
    private final Word32[] cache;
    private final Memory memory;
    private final ALU alu;

    public L2Cache (Memory memory) {
        index = 0;
        this.memory = memory;
        this.alu = new ALU();
        addresses = new Word32[4];
        cache = new Word32[32];
        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = new Word32();
            TestConverter.fromInt(5000, addresses[i]);
        }
        for (int i = 0; i < cache.length; i++) {
            cache[i] = new Word32();
        }
    }


    public Word32[] read (Word32 maskedAddress) {
        Word32[] words = new Word32[8];
        for (int i = 0; i < words.length; i++) {
            words[i] = new Word32();
        }

        for (Word32 address : addresses) { //Look for address in cache, if it's a hit return those 8 words.
            if (maskedAddress.equals(address)) {
                for (int j = addressAsInt(address); j < addressAsInt(address) + 8; j++) {
                    cache[j].copy(words[j % 8]);
                }
                return words;
            }
        }
        index %= 4;
        maskedAddress.copy(addresses[index]);
        loadFromMemory(addresses[index]); //Otherwise get the words from memory, load them into a buffer and return the 8 words to instruction cache
        maskedAddress.copy(addresses[index++]);
        for (int i = addressAsInt(maskedAddress); i < addressAsInt(maskedAddress) + 8; i++) {
            cache[i].copy(words[i % 8]);
        }
        return words;
    }

    private void loadFromMemory(Word32 address) {
        Word32 one = new Word32();
        one.setBitN(31, new Bit(true));
        for (int i = index * 8; i < (index * 8 + 8); i++) { //Starting at index * 8 in cache, load the next 8 words from the maskedAddress passed in.
            address.copy(memory.address);
            memory.read();
            memory.value.copy(cache[i]);
            one.copy(alu.instruction);
            one.copy(alu.op1);
            address.copy(alu.op2);
            alu.doInstruction();
            alu.result.copy(address);
        }
        Processor.currentClockCycle += 350;
    }

    public Word32 load (Word32 address) { //For use in load instructions
        Word32 maskedAddress = maskAddress(address);

        for (int i = 0; i < addresses.length; i++) {
            if (maskedAddress.equals(addresses[i])) {
                return cache[i * 8 + addressAsInt(address) % 8];
            }
        }
        index %= 4;
        maskedAddress.copy(addresses[index]);
        loadFromMemory(addresses[index]);
        maskedAddress.copy(addresses[index]);
        return cache[(index * 8) + (addressAsInt(address) % 8)];
    }

    public void write (Word32 address, Word32 value) { //Write through
        address.copy(memory.address);
        value.copy(memory.value);
        memory.write();
    }

    public Word32 maskAddress (Word32 address) {
        Word32 maskedAddress = new Word32();
        address.copy(maskedAddress);
        TestConverter.fromInt(7, alu.instruction);
        TestConverter.fromInt(3, alu.op2);
        maskedAddress.copy(alu.op1);
        alu.doInstruction();
        alu.result.copy(maskedAddress);
        TestConverter.fromInt(4, alu.instruction);
        TestConverter.fromInt(3, alu.op2);
        maskedAddress.copy(alu.op1);
        alu.doInstruction();
        alu.result.copy(maskedAddress);
        return maskedAddress;
    }

    private int addressAsInt(Word32 address) {

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
