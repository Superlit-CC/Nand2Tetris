import org.junit.Assert;
import org.junit.Test;

/**
 * @author superlit
 * @create 2024/1/11 14:15
 */
public class ParserTest {

    @Test
    public void testHasMoreCommands() {
        Parser parser = new Parser("./add/Add.asm");
        for (int i = 0; i < 6; i ++ ) {
            Assert.assertTrue(parser.hasMoreCommands());
            parser.advance();
        }
        Assert.assertFalse(parser.hasMoreCommands());
        Assert.assertFalse(parser.hasMoreCommands());

        parser = new Parser("./max/Max.asm");
        for (int i = 0; i < 20; i ++ ) {
            Assert.assertTrue(parser.hasMoreCommands());
            parser.advance();
        }
        Assert.assertFalse(parser.hasMoreCommands());
        Assert.assertFalse(parser.hasMoreCommands());
    }

    @Test
    public void testCommandType() {
        Parser parser = new Parser("./add/Add.asm");
        parser.advance();
        Assert.assertEquals(parser.commandType(), Parser.A_COMMAND);
        parser.advance();
        Assert.assertEquals(parser.commandType(), Parser.C_COMMAND);
        parser.advance();
        Assert.assertEquals(parser.commandType(), Parser.A_COMMAND);
        parser.advance();
        Assert.assertEquals(parser.commandType(), Parser.C_COMMAND);
        parser.advance();
        Assert.assertEquals(parser.commandType(), Parser.A_COMMAND);
        parser.advance();
        Assert.assertEquals(parser.commandType(), Parser.C_COMMAND);
    }

    @Test
    public void testSymbol() {
        Parser parser = new Parser("./add/Add.asm");
        parser.advance();
        Assert.assertEquals(parser.symbol(), "2");
        parser.advance();
        Assert.assertNull(parser.symbol());
        parser.advance();
        Assert.assertEquals(parser.symbol(), "3");
        parser.advance();
        Assert.assertNull(parser.symbol());
        parser.advance();
        Assert.assertEquals(parser.symbol(), "0");
        parser.advance();
        Assert.assertNull(parser.symbol());
    }

    @Test
    public void testCCommand() {
        Parser parser = new Parser("./add/Add.asm");
        parser.advance();
        Assert.assertEquals(parser.symbol(), "2");

        parser.advance();
        Assert.assertEquals(parser.dest(), "D");
        Assert.assertEquals(parser.comp(), "A");
        Assert.assertNull(parser.jump());

        parser.advance();
        Assert.assertEquals(parser.symbol(), "3");

        parser.advance();
        Assert.assertEquals(parser.dest(), "D");
        Assert.assertEquals(parser.comp(), "D+A");
        Assert.assertNull(parser.jump());

        parser.advance();
        Assert.assertEquals(parser.symbol(), "0");

        parser.advance();
        Assert.assertEquals(parser.dest(), "M");
        Assert.assertEquals(parser.comp(), "D");

        parser = new Parser("./max/Max.asm");
        parser.advance();
        Assert.assertEquals(parser.symbol(), "R0");

        parser.advance();
        Assert.assertEquals(parser.dest(), "D");
        Assert.assertEquals(parser.comp(), "M");

        parser.advance();
        Assert.assertEquals(parser.symbol(), "R1");

        parser.advance();
        Assert.assertEquals(parser.dest(), "D");
        Assert.assertEquals(parser.comp(), "D-M");

        parser.advance();
        Assert.assertEquals(parser.symbol(), "ITSR0");

        parser.advance();
        Assert.assertNull(parser.dest());
        Assert.assertEquals(parser.comp(), "D");
        Assert.assertEquals(parser.jump(), "JGT");
    }

}
