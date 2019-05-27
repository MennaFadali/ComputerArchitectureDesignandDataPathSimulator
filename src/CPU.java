import java.io.IOException;
import java.util.Arrays;

public class CPU {

    static ALU alu;
    static RegisterFile regFile;
    static ReadAddress readAdd;
    static Memory mem;
    static Controller controller;
    static int PC;
    static int[] IF_ID;
    static int[] ID_EX;
    static int[] EX_M;
    static int[] M_WB;
    int cycle;

    CPU() throws IOException {
        alu = new ALU();
        regFile = new RegisterFile();
        readAdd = new ReadAddress();
        mem = new Memory();
        controller = new Controller();
        mem.LoadProgram();
        PC = 0;
        cycle = 0;
        regFile.registers[1] = 20;
        regFile.registers[2] = 10;
        mem.load(500, 99);
        start();
    }

    static void flush() {
        regFile.ufile = false;
        alu.used = false;
        mem.used = false;
    }

    public static void main(String[] args) throws IOException {
        new CPU();
    }

    void start() {
        boolean flag = false;
        while (mem.memory[PC] != null || flag) {
            cycle++;
            if (regFile.uwb) regFile.writeBack();
            if (mem.used) M_WB = mem.Memory_Branch();
            if (alu.used) EX_M = alu.execute();
            if (regFile.ufile) ID_EX = regFile.translate();
            if (mem.memory[PC] != null) IF_ID = readAdd.Decode();
            flag = (regFile.uwb || regFile.ufile || mem.used || alu.used);
            System.err.println(cycle + " : " + Arrays.toString(regFile.registers) + " " + Arrays.toString(mem.memory) + " " + mem.memory[50]);
        }
//        System.err.println(Integer.parseInt(mem.memory[99],2));
    }

}
