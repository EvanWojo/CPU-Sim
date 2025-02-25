import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ALUTest {

    private void setOpcode(ALU a, int opcode) {
        Word32 w = new Word32();
        TestConverter.fromInt(opcode,w);
        Bit temp = new Bit(false);
        for (int i=0;i<5;i++) {
            w.getBitN(27+i,temp);
            a.instruction.setBitN(i,temp);
        }
    }

    private void setupTest(ALU alu, int opcode, int value1, int value2) {
        setOpcode(alu,opcode); // add
        TestConverter.fromInt(value1,alu.op1);
        TestConverter.fromInt(value2,alu.op2);
    }

    @Test
    void alu() {
        var alu = new ALU();
        setupTest(alu,1,100,200); // ADD
        alu.doInstruction();
        assertEquals(300,TestConverter.toInt(alu.result));

        setupTest(alu,2,64,96); // AND
        alu.doInstruction();
        assertEquals(64,TestConverter.toInt(alu.result));

        setupTest(alu,3,6,6); // MULTIPLY
        alu.doInstruction();
        assertEquals(36,TestConverter.toInt(alu.result));

        setupTest(alu,4,10,2); // Left Shift
        alu.doInstruction();
        assertEquals(40,TestConverter.toInt(alu.result));

        setupTest(alu,5,100,200); // Subtract
        alu.doInstruction();
        assertEquals(-100,TestConverter.toInt(alu.result));

        setupTest(alu,6,64,96); // Or
        alu.doInstruction();
        assertEquals(96,TestConverter.toInt(alu.result));

        setupTest(alu,7,1000,2); // Right Shift
        alu.doInstruction();
        assertEquals(250,TestConverter.toInt(alu.result));

        setupTest(alu,11,1000,2); // Compare - less
        alu.doInstruction();
        assertEquals(Bit.boolValues.FALSE,alu.less.getValue());
        assertEquals(Bit.boolValues.FALSE,alu.equal.getValue());

        setupTest(alu,11,1000,2000); // Compare - greater
        alu.doInstruction();
        assertEquals(Bit.boolValues.TRUE,alu.less.getValue());
        assertEquals(Bit.boolValues.FALSE,alu.equal.getValue());

        setupTest(alu,11,2000,2000); // Compare - equal
        alu.doInstruction();
        assertEquals(Bit.boolValues.FALSE,alu.less.getValue());
        assertEquals(Bit.boolValues.TRUE,alu.equal.getValue());
    }
}