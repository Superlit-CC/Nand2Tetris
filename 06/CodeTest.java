import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * @author superlit
 * @create 2024/1/11 16:56
 */
public class CodeTest {

    @Test
    public void test1() {
        System.out.println(new File("./add/Add.asm").getName().split("\\.")[0] + ".hack");
    }

    @Test
    public void testAdd() {
        Parser parser = new Parser("./add/Add.asm");
        parser.advance();
        parser.advance();
        Assert.assertEquals(Code.dest(parser.dest()), "010");
        Assert.assertEquals(Code.comp(parser.comp()), "0110000");
        Assert.assertEquals(Code.jump(parser.jump()), "000");

        parser.advance();
        parser.advance();
        Assert.assertEquals(Code.dest(parser.dest()), "010");
        Assert.assertEquals(Code.comp(parser.comp()), "0000010");
        Assert.assertEquals(Code.jump(parser.jump()), "000");

        parser.advance();
        parser.advance();
        Assert.assertEquals(Code.dest(parser.dest()), "001");
        Assert.assertEquals(Code.comp(parser.comp()), "0001100");
        Assert.assertEquals(Code.jump(parser.jump()), "000");
    }

    @Test
    public void testMax() {
        Parser parser = new Parser("./max/Max.asm");
        parser.advance();
        parser.advance();
        // D=M
        Assert.assertEquals(Code.dest(parser.dest()), "010");
        Assert.assertEquals(Code.comp(parser.comp()), "1110000");
        Assert.assertEquals(Code.jump(parser.jump()), "000");

        parser.advance();
        parser.advance();
        // D=D-M
        Assert.assertEquals(Code.dest(parser.dest()), "010");
        Assert.assertEquals(Code.comp(parser.comp()), "1010011");
        Assert.assertEquals(Code.jump(parser.jump()), "000");

        parser.advance();
        parser.advance();
        // D;JGT
        Assert.assertEquals(Code.dest(parser.dest()), "000");
        Assert.assertEquals(Code.comp(parser.comp()), "0001100");
        Assert.assertEquals(Code.jump(parser.jump()), "001");
    }
}
