
public class ALU {
    public Word32 instruction = new Word32();
    public Word32 op1 = new Word32();
    public Word32 op2 = new Word32();
    public Word32 result = new Word32();
    public Bit less = new Bit(false);
    public Bit equal = new Bit(false);

    public void doInstruction() {

        Initialize();
        int opCode = InstructionToOpCode(instruction);

        switch (opCode) {
            case 1, 12, 13, 14, 15, 16, 17:
                Adder.add(op1, op2, result);
                break;
            case 2:
                op1.and(op2, result);
                break;
            case 3:
                Multiplier.multiply(op1, op2, result);
                break;
            case 4:
                Shifter.LeftShift(op1, OpToInt(op2), result);
                break;
            case 5:
                Adder.subtract(op1, op2, result);
                break;
            case 6:
                op1.or(op2, result);
                break;
            case 7:
                Shifter.RightShift(op1, OpToInt(op2), result);
                break;
            case 11: {
                if (op1.equals(op2)) {
                    equal.assign(Bit.boolValues.TRUE);
                } else {
                    Adder.subtract(op1, op2, result);
                    if (result.word32[0].getValue().equals(Bit.boolValues.TRUE))
                        less.assign(Bit.boolValues.TRUE);
                }
                break;
            }
        }

        Flush();

    }

    private int OpToInt(Word32 value) {

        int bit, result = 0;

        for (int i = 0; i < value.word32.length; i++) {
            if (value.word32[value.word32.length - i - 1].getValue().equals(Bit.boolValues.TRUE))
                bit = 1;
            else
                bit = 0;

            result += (int) ((Math.pow(2, i)) * bit);

        }

        return result;
    }

    private int InstructionToOpCode(Word32 value) {

        int bit, result = 0, j = 0;

        for (int i = 4; i >= 0; i--) {
            if (value.word32[i + 27].getValue().equals(Bit.boolValues.TRUE))
                bit = 1;
            else
                bit = 0;

            result += (int) ((Math.pow(2, j++)) * bit);

        }

        return result;
    }

    private void Flush() { //Resets the opcode and operands for the next usage
        instruction = new Word32();
        op1 = new Word32();
        op2 = new Word32();
    }

    private void Initialize() { //Prepares the results for the calculations
        result = new Word32();
        less = new Bit(false);
        equal = new Bit(false);
    }
}
