/**
 * 将Hack汇编语言助记符转化成二进制码
 * @author superlit
 * @create 2024/1/10 16:05
 */
public class Code {
    /**
     * 返回 dest 助记符二进制码
     * @return 3bits
     */
    public static String dest(String s) {
        String bi = null;
        if (s == null) {
            bi = "000";
        } else if (s.equals("M")) {
            bi = "001";
        } else if (s.equals("D")) {
            bi = "010";
        } else if (s.equals("MD")) {
            bi = "011";
        } else if (s.equals("A")) {
            bi = "100";
        } else if (s.equals("AM")) {
            bi = "101";
        } else if (s.equals("AD")) {
            bi = "110";
        } else if (s.equals("AMD")) {
            bi = "111";
        } else {
            System.out.println("Syntax Error!");
        }
        return bi;
    }

    /**
     * 返回 comp 助记符二进制码
     * @return 7bits
     */
    public static String comp(String s) {
        String bi = null;
        if (s.equals("0")) {
            bi = "0101010";
        } else if (s.equals("1")) {
            bi = "0111111";
        } else if (s.equals("-1")) {
            bi = "0111010";
        } else if (s.equals("D")) {
            bi = "0001100";
        } else if (s.equals("A")) {
            bi = "0110000";
        } else if (s.equals("!D")) {
            bi = "0001101";
        } else if (s.equals("!A")) {
            bi = "0110001";
        } else if (s.equals("-D")) {
            bi = "0001111";
        } else if (s.equals("-A")) {
            bi = "0110011";
        } else if (s.equals("D+1")) {
            bi = "0011111";
        } else if (s.equals("A+1")) {
            bi = "0110111";
        } else if (s.equals("D-1")) {
            bi = "0001110";
        } else if (s.equals("A-1")) {
            bi = "0110010";
        } else if (s.equals("D+A")) {
            bi = "0000010";
        } else if (s.equals("D-A")) {
            bi = "0010011";
        } else if (s.equals("A-D")) {
            bi = "0000111";
        } else if (s.equals("D&A")) {
            bi = "0000000";
        } else if (s.equals("D|A")) {
            bi = "0010101";
        } else if (s.equals("M")) {
            bi = "1110000";
        } else if (s.equals("!M")) {
            bi = "1110001";
        } else if (s.equals("-M")) {
            bi = "1110011";
        } else if (s.equals("M+1")) {
            bi = "1110111";
        } else if (s.equals("M-1")) {
            bi = "1110010";
        } else if (s.equals("D+M")) {
            bi = "1000010";
        } else if (s.equals("D-M")) {
            bi = "1010011";
        } else if (s.equals("M-D")) {
            bi = "1000111";
        } else if (s.equals("D&M")) {
            bi = "1000000";
        } else if (s.equals("D|M")) {
            bi = "1010101";
        } else {
            System.out.println("Syntax Error!");
        }
        return bi;
    }

    /**
     * 返回 jump 助记符二进制码
     * @return 3bits
     */
    public static String jump(String s) {
        String bi = null;
        if (s == null) {
            bi = "000";
        } else if (s.equals("JGT")) {
            bi = "001";
        } else if (s.equals("JEQ")) {
            bi = "010";
        } else if (s.equals("JGE")) {
            bi = "011";
        } else if (s.equals("JLT")) {
            bi = "100";
        } else if (s.equals("JNE")) {
            bi = "101";
        } else if (s.equals("JLE")) {
            bi = "110";
        } else if (s.equals("JMP")) {
            bi = "111";
        } else {
            System.out.println("Syntax Error!");
        }
        return bi;
    }
}
