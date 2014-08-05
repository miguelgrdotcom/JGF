package hu.test.nulloutput;

import java.io.PrintStream;

public class NullOutput
{
    public static void run(NullOutputRunner runner) {
        PrintStream tmp = new PrintStream(new NullOutputStream());
        PrintStream err = System.err;
        PrintStream out = System.out;

        System.setErr(tmp);
        System.setOut(tmp);

        runner.run();

        System.setErr(err);
        System.setOut(out);
    }
}
