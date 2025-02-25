import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssemblerTest {

    private final String[][] instructions = {
            {"add r1 r2","fffftffffftffftf"},
            {"syscall 100","ftfffffffttfftff"},
            {"return","ftftffffffffffff"} ,
            {"subtract 10 r4","fftfttftftffftff"} ,
            {"halt","ffffffffffffffff"} ,
            {"and r7 r13","ffftfffftttfttft"},
            {"and 7 r13","ffftftfftttfttft"},
            {"multiply r31 r0","fffttftttttfffff"},
            {"leftshift 2 r6","fftfftffftfffttf"},
            {"or -6 r9","ffttftttftfftfft"},
            {"rightshift 0 r2","ffttttfffffffftf"},
            {"call 2047","ftfftttttttttttt"},
            {"compare r18 r29","ftfttftfftftttft"},
            {"ble -100","fttffttttfftttff"},
            {"blt 100","fttftffffttfftff"},
            {"bge -100","ftttfttttfftttff"},
            {"bgt 100","fttttffffttfftff"},
            {"beq -100","tffffttttfftttff"},
            {"bne 100","tffftffffttfftff"},
            {"load r31 r0", "tfftfftttttfffff"},
            {"load 2 r6",   "tfftftffftfffttf"},
            {"store r31 r0","tffttftttttfffff"},
            {"store 2 r6",  "tfftttffftfffttf"},
            {"copy r31 r0","tftffftttttfffff"},
            {"copy 2 r6",  "tftfftffftfffttf"},
    };

    @Test
    void assemble() {
        var myFirstProgram = new String[] {
            "add r1 r2",
            "syscall 100",
            "return",
            "subtract 10 r4"
        };
        var response = Assembler.assemble(myFirstProgram);
        assertEquals("fffftffffftffftf",response[0]);
        assertEquals("ftfffffffttfftff",response[1]);
        assertEquals("ftftffffffffffff",response[2]);
        assertEquals("fftfttftftffftff",response[3]);
    }
    @Test
    void testInstructions() {
        for (var instruction : instructions) {
            var prog = new String[1];
            prog[0] = instruction[0];
            assertEquals(16,instruction[1].length(), "Instruction " + instruction[0] + " correct answer is wrong length");
            var result = Assembler.assemble(prog);
            assertEquals(instruction[1],result[0],"Instruction " + instruction[0] + " failed");
        }
    }
}