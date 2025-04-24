import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Assembler {

    private static final HashMap<String, String> opCodes = new HashMap<>();
    private static final HashMap<String, String> registers = new HashMap<>();
    private static final ArrayList<String> callReturnFormat = new ArrayList<>();

    public static String[] assemble(String[] input) {

        opCodes.put("halt", "fffff");
        opCodes.put("add", "fffft");
        opCodes.put("and", "ffftf");
        opCodes.put("multiply", "ffftt");
        opCodes.put("leftshift", "fftff");
        opCodes.put("subtract", "fftft");
        opCodes.put("or", "ffttf");
        opCodes.put("rightshift", "ffttt");
        opCodes.put("syscall", "ftfff");
        opCodes.put("call", "ftfft");
        opCodes.put("return", "ftftf");
        opCodes.put("compare", "ftftt");
        opCodes.put("ble", "fttff");
        opCodes.put("blt", "fttft");
        opCodes.put("bge", "ftttf");
        opCodes.put("bgt", "ftttt");
        opCodes.put("beq", "tffff");
        opCodes.put("bne", "tffft");
        opCodes.put("load", "tfftf");
        opCodes.put("store", "tfftt");
        opCodes.put("copy", "tftff");

        registers.put("r0", "fffff");
        registers.put("r1", "fffft");
        registers.put("r2", "ffftf");
        registers.put("r3", "ffftt");
        registers.put("r4", "fftff");
        registers.put("r5", "fftft");
        registers.put("r6", "ffttf");
        registers.put("r7", "ffttt");
        registers.put("r8", "ftfff");
        registers.put("r9", "ftfft");
        registers.put("r10", "ftftf");
        registers.put("r11", "ftftt");
        registers.put("r12", "fttff");
        registers.put("r13", "fttft");
        registers.put("r14", "ftttf");
        registers.put("r15", "ftttt");
        registers.put("r16", "tffff");
        registers.put("r17", "tffft");
        registers.put("r18", "tfftf");
        registers.put("r19", "tfftt");
        registers.put("r20", "tftff");
        registers.put("r21", "tftft");
        registers.put("r22", "tfttf");
        registers.put("r23", "tfttt");
        registers.put("r24", "ttfff");
        registers.put("r25", "ttfft");
        registers.put("r26", "ttftf");
        registers.put("r27", "ttftt");
        registers.put("r28", "tttff");
        registers.put("r29", "tttft");
        registers.put("r30", "ttttf");
        registers.put("r31", "ttttt");

        callReturnFormat.add("call");
        callReturnFormat.add("return");
        callReturnFormat.add("syscall");
        callReturnFormat.add("blt");
        callReturnFormat.add("bge");
        callReturnFormat.add("bgt");
        callReturnFormat.add("beq");
        callReturnFormat.add("bne");
        callReturnFormat.add("ble");

        String[] output = new String[input.length];
        Arrays.fill(output, "");

        for (int i = 0; i < input.length; i++) {

            String currentInstruction = input[i]; //This current line of instruction
            String[] splitInstruction = currentInstruction.split(" ");
            String opCode = opCodes.get(splitInstruction[0]); //Retrieve opcode
            if (opCode == null)
                throw new RuntimeException("Unknown op code: " + splitInstruction[0]);
            output[i] = output[i].concat(opCode); //Add opcode to this line of the output

            if (callReturnFormat.contains(splitInstruction[0])) { //Check for call/return formatted instructions

                if (splitInstruction[0].equals("return")) { //If opcode is "return" no other values are needed

                    output[i] = output[i].concat("fffffffffff");

                } else { //Otherwise parse, convert and concat the value of the call

                    int temp = Integer.parseInt(splitInstruction[1]);
                    temp = temp & 2047; //For negative numbers
                    String value = Integer.toBinaryString(temp);
                    value = String.format("%11s", value).replace(' ', '0'); //Ensure length is 11 characters
                    value = value.replace("0", "f").replace("1", "t"); //Convert binary to t and f
                    output[i] = output[i].concat(value);

                }

            } else if (splitInstruction[0].equals("halt")) { //In the case of a halt instruction the remaining bits are filled with 0s

                output[i] = output[i].concat("fffffffffff");

            } else { //For the remaining opcodes

                String srcRegister = registers.get(splitInstruction[1]);

                if (srcRegister != null) { //Determine whether instruction format is 2R or Immediate

                    output[i] = output[i].concat("f"); //Set flag to 2R
                    output[i] = output[i].concat(srcRegister);

                    String dstRegister = registers.get(splitInstruction[2]);
                    if (dstRegister == null)
                        throw new RuntimeException("Unknown register: " + splitInstruction[2]);
                    output[i] = output[i].concat(dstRegister);

                } else {

                    output[i] = output[i].concat("t");  //Set flag to Immediate

                    int temp = Integer.parseInt(splitInstruction[1]); //Convert to binary, set proper length and concat to output
                    temp = temp & 31; //For negative numbers
                    String value = Integer.toBinaryString(temp);
                    value = String.format("%5s", value).replace(' ', '0'); //Ensure length is 5 characters
                    value = value.replace("0", "f").replace("1", "t"); //Replace binary with t and f
                    output[i] = output[i].concat(value);

                    String dstRegister = registers.get(splitInstruction[2]);
                    if (dstRegister == null)
                        throw new RuntimeException("Unknown register: " + splitInstruction[2]);
                    output[i] = output[i].concat(dstRegister);

                }
            }
        }

        return output;
    }

    public static String[] finalOutput(String[] input) {

        int length = input.length/2;
        if (input.length%2 != 0) //For the case of odd sized instruction input
            length++;

        String[] finalOutput = new String[length];
        Arrays.fill(finalOutput, "");

        for (int i = 0; i < input.length; i+=2) {

            try {
                finalOutput[i / 2] = input[i].concat(input[i + 1]);
            } catch (IndexOutOfBoundsException e) {
                finalOutput[i / 2] = input[i].concat("ffffffffffffffff");
            }

        }

//        if (input.length % 2 != 0) {
//            finalOutput[input.length/2] = input[input.length - 1].concat("ffffffffffffffff");
//        }

        return finalOutput;

    }

}