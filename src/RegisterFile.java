public class RegisterFile {
    boolean ufile , uwb;
    int[] registers;

    public RegisterFile() {
        registers = new int[32];
    }

    //ID/EX
    // 0 -> readData 1
    // 1- > ReadData 2
    // 2 - > ReadData 3
    // 3 -> regW
    // 4 -> bitype
    // 5 -> Opcode

    int[] translate() {
        int[] ans = new int[6];
        ans[0] = registers[CPU.IF_ID[0]];
        ans[1] = (CPU.IF_ID[4] == 0 ? CPU.IF_ID[3] : registers[CPU.IF_ID[1]]);
        ans[2] = registers[CPU.IF_ID[2]];
        ans[3] = CPU.IF_ID[2];
        ans[4] = CPU.IF_ID[4];
        ans[5] = CPU.IF_ID[3];
        CPU.alu.used = true;
        ufile = false;
        return ans;
    }


    void writeBack() {
        int[] signals = CPU.M_WB;
        if (signals[0] == 1) {
            registers[signals[1]] = signals[2];
        }
        uwb = false;
    }
}
