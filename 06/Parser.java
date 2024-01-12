import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取汇编文件并解析，提供方便访问汇编命令成分的方案，去掉所有的空格和注释
 * @author superlit
 * @create 2024/1/10 15:40
 */
public class Parser {
    public static final int A_COMMAND = 0;
    public static final int C_COMMAND = 1;
    public static final int L_COMMAND = 2;
    private static final String COMMENT = "//";

    private String asmFilePath;
    private Reader reader;
    private BufferedReader bufferedReader; // 输入流
    private List<String> symbolCommandList; // 符号命令集合
    private int currentCommandIndex; // 当前命令下标

    public Parser(String asmFilePath) {
        this.asmFilePath = asmFilePath;
        this.symbolCommandList = new ArrayList<>();
        this.currentCommandIndex = -1;
        loadAllSymbolCommands();
    }

    /**
     * 获取输出流
     */
    private void getReader() {
        try {
            reader = new FileReader(asmFilePath);
            bufferedReader = new BufferedReader(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭流
     */
    private void close() {
        try {
            bufferedReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入所有符号命令至命令集合中
     */
    private void loadAllSymbolCommands() {
        getReader();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!isBlankLine(line)) {
                    symbolCommandList.add(getCommand(line));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    /**
     * 提取当前行的字符命令，去除空格
     * 实现：提取 COMMENT 前的字符串
     */
    private String getCommand(String line) {
        String s = removeWhitespace(line);
        int commentIndex = s.indexOf(COMMENT);
        if (commentIndex != -1) {
            s = s.substring(0, s.indexOf(COMMENT));
        }
        return s;
    }

    private String removeWhitespace(String line) {
        return line.replace(" ", "");
    }

    /**
     * 判断是否是空行，空行包括注释行或只有回车行
     * @return 是-true
     */
    private boolean isBlankLine(String line) {
        String s = removeWhitespace(line);
        return s.indexOf(COMMENT) == 0 || s.equals("");
    }

    /**
     * 是否有更多命令
     * @return true-有 false-没有
     */
    public boolean hasMoreCommands() {
        return currentCommandIndex + 1 < symbolCommandList.size();
    }

    /**
     * 从输入流中读取下一条命令，并将其作为当前命令
     */
    public void advance() {
        currentCommandIndex += 1;
    }

    /**
     * 返回当前命令的类型
     * @return 0-A 1-C 2-L伪命令
     */
    public int commandType() {
        String currentCommand = symbolCommandList.get(currentCommandIndex);
        if (currentCommand.contains("@")) {
            return A_COMMAND;
        }
        if (currentCommand.contains("(") && currentCommand.contains(")")) {
            return L_COMMAND;
        }
        return C_COMMAND;
    }

    /**
     * 返回@Xxx或(Xxx)的当前命令的符号
     * @return Xxx
     */
    public String symbol() {
        String currentCommand = symbolCommandList.get(currentCommandIndex);
        if (commandType() == A_COMMAND) {
            return currentCommand.substring(1);
        } else if (commandType() == L_COMMAND) {
            return currentCommand.substring(1, currentCommand.length() - 1);
        } else {
            System.out.println("Command Type Error!");
        }
        return null;
    }

    /**
     * 返回当前C-指令的dest助记符
     * C-指令: dest=comp;jump
     * dest或jump都可以为空
     * 如果dest为空，"=" 省略
     * 如果jump为空，";" 省略
     */
    public String dest() {
        if (commandType() != C_COMMAND) {
            System.out.println("Command Type Error!");
            return null;
        }
        String currentCommand = symbolCommandList.get(currentCommandIndex);
        int eqIdx = currentCommand.indexOf("=");
        if (eqIdx == -1) {
            return null;
        }
        return currentCommand.substring(0, eqIdx);
    }

    /**
     * 返回当前C-指令的comp助记符
     * C-指令: dest=comp;jump
     */
    public String comp() {
        if (commandType() != C_COMMAND) {
            System.out.println("Command Type Error!");
            return null;
        }
        String currentCommand = symbolCommandList.get(currentCommandIndex);
        int eqIdx = currentCommand.indexOf("=");
        int semIdx = currentCommand.indexOf(";");
        // 分三种情况讨论，分别是dest或jump被省略
        if (eqIdx != -1 && semIdx == -1) {
            return currentCommand.substring(eqIdx + 1);
        } else if (eqIdx == -1 && semIdx != -1) {
            return currentCommand.substring(0, semIdx);
        } else {
            return currentCommand.substring(eqIdx + 1, semIdx);
        }
    }

    /**
     * 返回当前C-指令的jump助记符
     */
    public String jump() {
        if (commandType() != C_COMMAND) {
            System.out.println("Command Type Error!");
            return null;
        }
        String currentCommand = symbolCommandList.get(currentCommandIndex);
        int semIdx = currentCommand.indexOf(";");
        if (semIdx == -1) {
            return null;
        }
        return currentCommand.substring(semIdx + 1);
    }
}
