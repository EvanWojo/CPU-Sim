import java.util.*;

public class Processor {
    private final Memory mem;
    private final L2Cache l2cache;
    private final InstructionCache cache;
    public List<String> output;
    private final Word16 instruction;
    private final Word32 pc, op1, op2;
    private final Word32[] registers;
    private int opCode = 0;
    private final Stack<Word32> stack;
    private boolean halt = false; //Flag to stop
    private boolean less, greater, equal; //Compare flags
    private boolean format, callReturn, bottom; //Flags for the format of the instructions, and how to read the instructions/increment PC
    public static int currentClockCycle = 0;



    public Processor(Memory m) {
        mem = m;
        l2cache = new L2Cache(mem);
        cache = new InstructionCache(l2cache);
        op1 = new Word32();
        op2 = new Word32();
        pc = new Word32();
        output = new LinkedList<>();
        instruction = new Word16();
        registers = new Word32[32];
        stack = new Stack<>();

        for (int i = 0; i < registers.length; i++) {
            registers[i] = new Word32();
        }
    }

    public void run() {
        while (!halt) {
            fetch();
            decode();
            execute();
            store();
        }
        System.out.println("Clock cycles: " + currentClockCycle);
    }

    private void fetch() {
        Word32 temp = cache.read(pc);
        if (!bottom) {
            temp.getTopHalf(instruction);
        } else
            temp.getBottomHalf(instruction);
    }

    private void decode() {

        opCode = TestConverter.toInt(getOpCode(instruction));
        if (opCode <= 7 || opCode == 11 || opCode >= 18) {

            Bit temp = new Bit();
            instruction.getBitN(5, temp);
            if (temp.getValue().equals(Bit.boolValues.FALSE)) { //Format 0
                registers[TestConverter.toInt(getFirstValue(instruction))].copy(op1); //Retrieve register addresses from instructions and assign the values to op1 and 2
                registers[TestConverter.toInt(getSecondValue(instruction))].copy(op2);
            } else {
                getFirstValue(instruction).copy(op1);
                registers[TestConverter.toInt(getSecondValue(instruction))].copy(op2);
                format = true;
            }
        } else {
            getImmediateValue(instruction).copy(op1); //For call/return format
        }
    }

    private void execute() {

        ALU alu = new ALU();
        Word32 one = new Word32();
        TestConverter.fromInt(1, one);

        switch (opCode) {
            case 0:
                halt = true;
                break;
            case 1, 2, 3, 4, 5, 6, 7: {
                op1.copy(alu.op2);
                op2.copy(alu.op1);
                getOpCode(instruction).copy(alu.instruction);
                alu.doInstruction();
                alu.result.copy(op2);
                if (opCode == 3)
                    currentClockCycle += 10;
                else
                    currentClockCycle += 2;
                break;
            }
            case 8:
                syscall(TestConverter.toInt(op1));
                break; //Syscall
            case 9: { //Call
                Word32 address = new Word32();
                TestConverter.fromInt(1, one);
                mem.address.copy(address);
                address.copy(alu.op1);
                one.copy(alu.op2);
                one.copy(alu.instruction);
                alu.doInstruction();
                stack.push(alu.result);

                CallReturn();
                break;
            }
            case 10: { //Return
                stack.pop().copy(pc);
                callReturn = true;
                break;
            }
            case 11: { //Compare
                less = false;
                equal = false;
                greater = false;
                op1.copy(alu.op1);
                op2.copy(alu.op2);
                getOpCode(instruction).copy(alu.instruction);
                alu.doInstruction();
                if (alu.less.getValue().equals(Bit.boolValues.TRUE))
                    less = true;
                else if (alu.equal.getValue().equals(Bit.boolValues.TRUE))
                    equal = true;
                else
                    greater = true;
                break;
            }
            case 12:
                if (less || equal) CallReturn();
                break; //BLE
            case 13:
                if (less) CallReturn();
                break; //BLT
            case 14:
                if (greater || equal) CallReturn();
                break; //BGE
            case 15:
                if (greater) CallReturn();
                break; //BGT
            case 16:
                if (equal) CallReturn();
                break; //BEQ
            case 17:
                if (!equal) CallReturn();
                break; //BNE
            case 18: { //Load
                if (format) { //Intermediate
                    op1.copy(alu.op1);
                    op2.copy(alu.op2);
                    one.copy(alu.instruction);
                    alu.doInstruction();
                    l2cache.load(alu.result).copy(op2);
                    break;
                } //2R
                l2cache.load(op1).copy(op2);
                currentClockCycle += 50;
                break;
            }
            case 19: { //Store
                l2cache.write(op2, op1);
                currentClockCycle += 50;
                break;
            }
            case 20: { //Copy
                op1.copy(op2);
            }


        }

    }

    private void printReg() {
        for (int i = 0; i < 32; i++) {
            var line = "r" + i + ":" + registers[i].toString();
            output.add(line);
            System.out.println(line);
        }
    }

    private void printMem() {
        for (int i = 0; i < 1000; i++) {
            Word32 addr = new Word32();
            Word32 value = new Word32();
            // Convert i to Word32 here...
            TestConverter.fromInt(i, addr);
            addr.copy(mem.address);
            mem.read();
            mem.value.copy(value);
            var line = i + ":" + value;
            output.add(line);
            System.out.println(line);
        }
    }

    private void store() {

        if (opCode >= 1 && opCode <= 7) {
            if (format) { //Immediate only store 2nd value to appropriate register
                op2.copy(registers[TestConverter.toInt(getSecondValue(instruction))]);
            } else { //2R store both values into appropriate registers
                op1.copy(registers[TestConverter.toInt(getFirstValue(instruction))]);
                op2.copy(registers[TestConverter.toInt(getSecondValue(instruction))]);
            }
        } else if (opCode == 18 || opCode == 20) { //Load and Copy only stores 2nd value into registers
            op2.copy(registers[TestConverter.toInt(getSecondValue(instruction))]);
        }

        if (bottom && !callReturn) { //If we just read the bottom of the instruction, and the PC wasn't changed in any way then increment the PC by one
            incrementPC();
        } else if (!callReturn) //If it's time to read the bottom of the word and a Call/Return didn't just happen then set bottom to true
            bottom = true;

        format = false;
        callReturn = false;
    }

    private Word32 getOpCode(Word16 instruction) {

        Word32 op = new Word32();

        for (int i = 0; i < 5; i++) {
            Bit temp = new Bit();
            instruction.getBitN(i, temp);
            op.setBitN(i + 27, temp);
        }
        return op;
    }

    private Word32 getFirstValue(Word16 instruction) {

        Word32 val = new Word32();
        Bit temp = new Bit();
        instruction.getBitN(6, temp);
        if (temp.getValue().equals(Bit.boolValues.TRUE)) {
            for (int i = 0; i < 31; i++) {
                val.setBitN(i, temp);
            }
        }

        for (int i = 6; i < 11; i++) {
            temp = new Bit();
            instruction.getBitN(i, temp);
            val.setBitN(i + 21, temp);
        }
        return val;
    }

    private Word32 getSecondValue(Word16 instruction) {

        Word32 val = new Word32();
        Bit temp = new Bit();
        instruction.getBitN(11, temp);
        if (temp.getValue().equals(Bit.boolValues.TRUE)) {
            for (int i = 0; i < 31; i++) {
                val.setBitN(i, temp);
            }
        }

        for (int i = 11; i < 16; i++) {
            temp = new Bit();
            instruction.getBitN(i, temp);
            val.setBitN(i + 16, temp);
        }
        return val;
    }

    private Word32 getImmediateValue(Word16 instruction) {

        Word32 val = new Word32();
        Bit temp = new Bit();
        instruction.getBitN(5, temp);
        if (temp.getValue().equals(Bit.boolValues.TRUE)) {
            for (int i = 0; i < 31; i++) {
                val.setBitN(i, temp);
            }
        }

        for (int i = 5; i < 16; i++) {
            temp = new Bit();
            instruction.getBitN(i, temp);
            val.setBitN(i + 16, temp);
        }
        return val;
    }

    private void syscall(int n) {

        switch (n) {
            case 0:
                printReg();
                break;
            case 1:
                printMem();
        }

    }

    private void CallReturn() {
        ALU alu = new ALU();
        Word32 one = new Word32();
        TestConverter.fromInt(1, one);
        pc.copy(alu.op1);
        op1.copy(alu.op2);
        one.copy(alu.instruction);
        alu.doInstruction();
        alu.result.copy(pc);
        callReturn = true;
        bottom = false;
    }

    private void incrementPC() {
        ALU alu = new ALU();
        Word32 one = new Word32();
        TestConverter.fromInt(1, one);
        pc.copy(alu.op1);
        one.copy(alu.op2);
        one.copy(alu.instruction);
        alu.doInstruction();
        alu.result.copy(pc);
        bottom = false;
    }

}