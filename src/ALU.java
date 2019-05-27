public class ALU {

    boolean used;

    // 0 -> zero
    // 1 -> result
    // 2 -> WriteBack
    // 3 -> memRead
    // 4 -> memWrite
    // 5 -> PCSource
    // 6 -> memorALUResult
    // 7 -> regFileWrite
    // 8 -> readData3

    int[] execute() {
        int[] ans = new int[9];
        ans[8] = CPU.ID_EX[2];
        ans[2] = CPU.ID_EX[3];
        int op1 = CPU.ID_EX[0];
        int op2 = CPU.ID_EX[1];
        int type = CPU.ID_EX[4];
        int opcode = CPU.ID_EX[5];
        int res = op1;
        if (type == 0) {
            //ADDD IMMETDIATE
            res = op1 + op2;
            ans[3] = 0;
            ans[4] = 0;
            ans[5] = 0;
            ans[6] = 1;
            ans[7] = 1;
        } else {
            switch (opcode) {
                case 1:
                    res = op1 * op2;
                    ans[3] = 0;
                    ans[4] = 0;
                    ans[5] = 0;
                    ans[6] = 1;
                    ans[7] = 1;
                    break;
                case 2:
                    res = op1 + op2;
                    ans[3] = 0;
                    ans[4] = 0;
                    ans[5] = 0;
                    ans[6] = 1;
                    ans[7] = 1;
                    break;
                case 4:
                    res = op1 - op2;
                    ans[3] = 0;
                    ans[4] = 0;
                    ans[5] = 0;
                    ans[6] = 1;
                    ans[7] = 1;
                    break;
                case 8:
                    res = op1;
                    ans[3] = 0;
                    ans[4] = 0;
                    ans[5] = 0;
                    ans[6] = 1;
                    ans[7] = 1;
                    break;
                case 16:
                    res = 0;
                    ans[3] = 0;
                    ans[4] = 0;
                    ans[5] = 0;
                    ans[6] = 1;
                    ans[7] = 1;
                    break;
                case 32:
                    res = op1 & op2;
                    ans[3] = 0;
                    ans[4] = 0;
                    ans[5] = 0;
                    ans[6] = 1;
                    ans[7] = 1;
                    break;
                case 64:
                    res = op1 | op2;
                    ans[3] = 0;
                    ans[4] = 0;
                    ans[5] = 0;
                    ans[6] = 1;
                    ans[7] = 1;
                    break;
                //BEQ
                case 128:
                    res = CPU.ID_EX[2];
                    ans[0] = (op1 - op2 == 0) ? 1 : 0;
                    ans[3] = 0;
                    ans[4] = 0;
                    ans[5] = 1;
                    ans[6] = 0;
                    ans[7] = 0;
                    break;
                case 256:
                    res = (op1 < op2) ? 1 : 0;
                    ans[3] = 0;
                    ans[4] = 0;
                    ans[5] = 0;
                    ans[6] = 1;
                    ans[7] = 1;
                    break;
                case 512:
                    //LW
                    ans[3] = 1;
                    ans[4] = 0;
                    ans[5] = 0;
                    ans[6] = 1;
                    ans[7] = 1;
                    res = op1;
                    break;
                case 1024:
                    //SW
                    ans[3] = 0;
                    ans[4] = 1;
                    ans[5] = 0;
                    ans[6] = 0;
                    ans[7] = 0;
                    break;
                //JR
                case 2048:
                    res = CPU.ID_EX[2];
                    ans[0] = 1;
                    ans[3] = 0;
                    ans[4] = 0;
                    ans[5] = 1;
                    ans[6] = 0;
                    ans[7] = 0;
                    break;
            }
        }
        ans[1] = res;
        used = false;
        CPU.mem.used = true;

        return ans;
    }

}
