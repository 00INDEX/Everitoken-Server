package everitoken.Utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Func {
    public static String getExceptionMessage(Exception e){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        e.printStackTrace(pout);
        String ret = new String(out.toByteArray());
        pout.close();
        try {
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
