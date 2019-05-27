import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Memory {
    String[] memory;
    int begin;
    boolean used;

    public Memory() {
        memory = new String[100];
        begin = 0;
    }

    void load(int v, int address) {
        memory[address] = Integer.toBinaryString(v);
    }

    void LoadProgram() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File("program")));
        HashMap<String, Integer> labels = new HashMap<>();
        while (br.ready()) {
            String current = br.readLine().trim();
            if (inBinaryFormat(current)) {
                memory[begin++] = current;
                continue;
            }
            if (current.charAt(current.length() - 1) == ':') {
                labels.put(current.substring(0, current.length() - 1), begin);
                continue;
            }
            memory[begin++] = convertToBinary(current);
        }
    }

    String getRegNum(String x) {
        String ret = Integer.toBinaryString(Integer.parseInt(x.substring(1)));
        while (ret.length() < 5)
            ret = "0" + ret;
        return ret;
    }

    String getOffset(String x) {
        String y = Integer.toBinaryString(Integer.parseInt(x));
        while (y.length() < 16)
            y = "0" + y;
        if (y.length() == 16) return y;
        String ans = "";
        for (int i = y.length() - 1; ans.length() < 16; i--)
            ans = y.charAt(i) + ans;
        return ans;

    }

    String convertToBinary(String current) {
        String[] x = current.split(" ");
        String ans = "";
        String dummy = "00000";
        if (x[0].equals("Addi")) {
            ans += "0";
            ans += getOffset(x[3]);
            ans += getRegNum(x[1]) + dummy + getRegNum(x[2]);
        } else {
            ans += "1";
            switch (x[0]) {
                case "Mult":
                    ans += "0000000000000001" + getRegNum(x[1]) + getRegNum(x[2]) + getRegNum(x[3]);
                    break;
                case "Add":
                    ans += "0000000000000010" + getRegNum(x[1]) + getRegNum(x[2]) + getRegNum(x[3]);
                    break;

                case "Sub":
                    ans += "0000000000000100" + getRegNum(x[1]) + getRegNum(x[2]) + getRegNum(x[3]);
                    break;
                case "Move":
                    ans += "0000000000001000" + getRegNum(x[1]) + dummy + getRegNum(x[2]);
                    break;
                case "Clear":
                    ans += "0000000000010000" + getRegNum(x[1]) + dummy + dummy;
                    break;
                case "And":
                    ans += "0000000000100000" + getRegNum(x[1]) + getRegNum(x[2]) + getRegNum(x[3]);
                    break;
                case "Or":
                    ans += "0000000001000000" + getRegNum(x[1]) + getRegNum(x[2]) + getRegNum(x[3]);
                    break;
                case "Beq":
                    ans += "0000000010000000" + getRegNum(x[1]) + getRegNum(x[2]) + getRegNum(x[3]);
                    break;
                case "Slt":
                    ans += "0000000100000000" + getRegNum(x[1]) + getRegNum(x[2]) + getRegNum(x[3]);
                    break;
                case "Lw":
                    ans += "0000001000000000" + getRegNum(x[1]) + dummy + getRegNum(x[2]);
                    break;
                case "Sw":
                    ans += "0000010000000000" + getRegNum(x[1]) + dummy + getRegNum(x[2]);
                    break;
                case "Jr":
                    ans += "0000100000000000" + getRegNum(x[1]) + dummy + dummy;
                    break;
//                default:
//                    throw new Exception("Compilaion Error : Instruction not supported");

            }
        }
        return new String(ans);
    }

    String fetch(int address) {
        return memory[address];
    }

    void write(int address, String data) {
        if (address < 0 || address >= memory.length) return;
        memory[address] = data;
    }


    boolean inBinaryFormat(String x) {
        return x.length() == 32 && x.replace("1", "").replace("0", "").length() == 0;
    }

    // 0-> regFileWrite
    // 1 -writeBackRegister
    // 2-> writeBackData
    int[] Memory_Branch() {
        int[] signals = CPU.EX_M;
        int ans[] = new int[3];
        ans[0] = signals[7];
        ans[1] = signals[2];
        if (CPU.EX_M[0] == 1) {
            //BRANCH INSTRUCTION
            CPU.PC = CPU.EX_M[1];
            CPU.flush();
        } else if (signals[3] == 1)
            //READ LW
            ans[2] = Integer.parseInt(CPU.mem.fetch(signals[1]), 2);
        else if (signals[4] == 1)
            //WRITE SW
            this.write(signals[1], Integer.toBinaryString(signals[8]));
        else {
            //NOT A MEMORY INSTRUCTION
            ans[2] = signals[1];
        }
        used = false;
        CPU.regFile.uwb = true;
        return ans;
    }


}
