import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于Hack计算机的符号语言编译器，
 * 将符号语言转化为机器可执行的二进制语言
 *
 * @author superlit
 * @create 2024/1/10 14:36
 */
public class Assembler {
    private String asmFilePath;
    private Map<String, Integer> symbolTable; // 用于储存符号和对应的数字对

    public Assembler(String asmFilePath) {
        this.asmFilePath = asmFilePath;
        symbolTable = new HashMap<>();
    }

    /**
     * 加载预定义符号到 symbolTable
     */
    private void loadPredefinedSymbols() {
        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);
        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KBD", 24576);
        for (int i = 0; i <= 15; i++) {
            symbolTable.put("R" + i, i);
        }
    }

    /**
     * 加载标签符号到 symbolTable 中
     */
    private void loadLabels() {
        int current = 0;
        Parser parser = new Parser(asmFilePath);
        while (parser.hasMoreCommands()) {
            parser.advance();
            if (parser.commandType() == Parser.L_COMMAND) {
                symbolTable.put(parser.symbol(), current);
            } else {
                current += 1;
            }
        }
    }

    private boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {

        }
        return false;
    }

    /**
     * 加载变量符号并解析指令
     *
     * @return 指令数组
     */
    private List<String> encode() {
        Parser parser = new Parser(asmFilePath);
        int valAddress = 16;
        List<String> commandList = new ArrayList<>();
        while (parser.hasMoreCommands()) {
            String command = "";
            parser.advance();

            if (parser.commandType() == Parser.A_COMMAND) { // A-指令
                String symbol = parser.symbol();
                int val;
                if (isNumeric(symbol)) {
                    val = Integer.parseInt(symbol);
                } else {
                    if (!symbolTable.containsKey(symbol)) {
                        symbolTable.put(symbol, valAddress);
                        valAddress += 1;
                    }
                    val = symbolTable.get(symbol);
                }
                int value = 1 << 15 | val; // 保留15bit
                command = command + "0" + Integer.toBinaryString(value).substring(1);
            } else if (parser.commandType() == Parser.C_COMMAND) { // C-指令
                command += "111";
                command += Code.comp(parser.comp());
                command += Code.dest(parser.dest());
                command += Code.jump(parser.jump());
            } else { // L-指令 跳过
                continue;
            }
            commandList.add(command);
        }
        return commandList;
    }

    /**
     * 将commandList内容输出到.hack文件
     */
    private void load2Hack(List<String> commandList) {
        String fileName = new File(asmFilePath).getName().split("\\.")[0] + ".hack";
        try {
            File file = new File(fileName);
            FileOutputStream fos = null;
            file.createNewFile(); // 创建该文件
            fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");

            for (String command : commandList) {
                osw.write(command);
                osw.write("\n");
            }
            osw.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void asm2Hack() {
        // 1. 加载预定义符号
        loadPredefinedSymbols();
        // 2. 加载标签符号
        loadLabels();
        // 3. 加载变量符号并解析指令
        List<String> commandList = encode();
        // 4. 输出到.hack文件
        load2Hack(commandList);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage error!");
            return;
        }
        String asmFilePath = args[0];
        Assembler assembler = new Assembler(asmFilePath);
        assembler.asm2Hack();
    }
}
