public class ReadAddress {


    //0 -> Reg1
    //1 -> Reg2
    //2 -> regW
    //3 -> opcode/offset
    //4 -> typebit

    int[] Decode() {
        String instruction = CPU.mem.fetch(CPU.PC++);
        int[] ans = new int[5];
//        System.err.println(instruction);
        ans[0] = Integer.parseInt(instruction.substring(27), 2);
        ans[1] = Integer.parseInt(instruction.substring(22, 27), 2);
        ans[2] = Integer.parseInt(instruction.substring(17, 22), 2);
        ans[3] = bitExtender(instruction.substring(1, 17));
        ans[4] = Integer.parseInt(instruction.substring(0, 1), 2);
        CPU.regFile.ufile = true;
        return ans;
    }

    int bitExtender(String x) {
        if (x.charAt(0) == '0') return Integer.parseInt(x, 2);
        return (int) Long.parseLong("1111111111111111" + x, 2);
    }

}
