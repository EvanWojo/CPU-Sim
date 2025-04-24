import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PerformanceTest {

    @Test
    public void sumArray(){
        String[] program = {
                "copy 10 r0", //1
                "copy 10 r4",
                "leftshift 1 r4", //2
                "add 11 r4",
                "store r0 r0", //3
                "add 1 r0",
                "compare r4 r0", //4
                "bne -1",
                "copy 10 r0", //5
                "copy 10 r0",
                "load r0 r1", //6
                "add r1 r3",
                "add 1 r0", //7
                "compare r4 r0",
                "bne -2", //8
                "syscall 0",
                "halt"
        };
        //No caches: 71,830 clock cycles
        //With cache: 15,380 clock cycles
        //With l2 cache: 15,480 clock cycles
        //Full cache implementation: 6,030 clock cycles
        runProgram(program);
    }

    @Test
    public void sumLinkedList(){
        String[] program = {
                //Create the linked list
                "copy 10 r1", //r1 = base address | address 0
                "multiply 10 r1", //start at address 100
                "copy 10 r4", // r4 = how many times to loop | address 1
                "leftshift 1 r4", //loop 20 times
                "add 1 r2", //r2 = value to store into node | address 2
                "store r2 r1",
                "copy r1 r3", //r3 = next node address | address 3
                "add 2 r3",
                "compare r4 r2", // If this is the last node the next node pointer should be 0 | address 4
                "beq 4", //jump to last node creation
                "add 1 r1", //address 5
                "store r3 r1", //Store the pointer to the next node in memory
                "add 1 r1", //address 6
                "compare r4 r2",
                "bge -5", //address 7
                "copy 0 r0",
                "add 1 r1", //Last node, store 0 as the next pointer | address 8
                "store 0 r1",
                //Sum the linked list
                "copy 10 r1", //Reset base address | address 9
                "multiply 10 r1",
                "load r1 r5",//r5 = temp | address 10
                "add r5 r6", //r6 = sum
                "add 1 r1", // address 11
                "load r1 r1", //set r1 to the pointer to the next node
                "compare 0 r1", //check if this is the tail node | address 12
                "bne -2",
                "syscall 0",
                "halt"
        };
        //No cache: 127,760 clock cycles
        //With cache: 28,390
        //With l2 cache: 28,490 clock cycles
        //Full cache implementation: 10,590 clock cycles
        runProgram(program);
    }

    @Test
    public void sumArrayReversed(){
        String[] program = {
                "copy 10 r0", //1
                "copy 10 r4",
                "leftshift 1 r4", //2
                "add 11 r4",
                "store r0 r0", //3
                "add 1 r0",
                "compare r4 r0", //4
                "bne -1",
                "copy 15 r0", //5
                "add 15 r0",
                "add 1 r0", //6
                "copy 9 r4",
                "load r0 r1", //7
                "add r1 r3",
                "subtract 1 r0", //8
                "compare r4 r0",
                "bne -2", //9
                "syscall 0",
                "halt"
        };
        //No cache: 74,238 clock cycles
        //with cache: 30,038 clock cycles
        //with l2 cache: 17,538 clock cycles
        //Full cache implementation: 8188 clock cycles
        runProgram(program);
    }

    private static void runProgram(String[] program) {
        var assembled = Assembler.assemble(program);
        var merged = Assembler.finalOutput(assembled);
        var m = new Memory();
        m.load(merged);
        var p = new Processor(m);
        p.run();
    }

}
