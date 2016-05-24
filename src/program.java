import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class program {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                while (true) {
                    System.out.println("Enter expression (empty string to exit)");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String s = reader.readLine();
                    if (s.length() < 1)
                        return;
                    parse_string(s);
                }
            } else if (args.length == 1) {
                FileInputStream fis = new FileInputStream(args[0]);

                //Construct BufferedReader from InputStreamReader
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                String line = null;
                while ((line = br.readLine()) != null) {
                    parse_string(line);
                }

                br.close();
            } else {
                throw new Exception("Invalid arguments");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void parse_string(String s) throws Exception {
        Pattern p = Pattern.compile("(\\d+/\\d+)\\s*([+-/*])\\s*(\\d+/\\d+)");
        Matcher m = p.matcher(s);
        if (m.matches() == false) {
            throw new Exception("Invalid input format");
        }
        fraction leftOperand = fraction.tryParse(m.group(1));
        fraction rightOperand = fraction.tryParse(m.group(3));
        fraction result = null;
        switch (m.group(2)) {
            case "+":
                result = leftOperand.add(rightOperand);
                break;
            case "-":
                result = leftOperand.subtract(rightOperand);
                break;
            case "/":
                result = leftOperand.divide(rightOperand);
                break;
            case "*":
                result = leftOperand.multiply(rightOperand);
                break;
        }
        System.out.println(String.format("%1$s %2$s %3$s = %4$s", leftOperand.toString(), m.group(2),
                rightOperand.toString(), result));
    }
}
