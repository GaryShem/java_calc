import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class fraction implements java.io.Serializable{
    private int _numerator;
    private int _denominator;

    public int getDenominator() {
        return _denominator;
    }

    public static fraction tryParse(String str)
    {
        Pattern p = Pattern.compile("\\s*(\\d+)/(\\d+)\\s*");
        Matcher m = p.matcher(str);
        if (m.matches() == false)
            return null;
        int numerator = Integer.parseInt(m.group(1));
        int denominator = Integer.parseInt(m.group(2));
        return new fraction(numerator, denominator);
    }

    public void setDenominator(int value) throws IllegalArgumentException {
        if (value == 0)
            throw new IllegalArgumentException("Denominator cannot be 0");
        _denominator = value;
        CutFraction();
    }

    public int getNumerator() {
        return _numerator;
    }

    public void setNumerator(int value) {
        _numerator = value;
        CutFraction();
    }

    public fraction() {
        _numerator = 0;
        setDenominator(1);
    }

    public fraction(int numerator, int denominator) {
        _numerator = numerator;
        setDenominator(denominator);
        CutFraction();
    }

    public fraction add(fraction n2) {
        int numerator, denominator;

        if (getDenominator() != n2.getDenominator()) {
            denominator = getDenominator() * n2.getDenominator();
            numerator = getNumerator() * n2.getDenominator() + n2.getNumerator() * getDenominator();
        } else {
            denominator = getDenominator();
            numerator = getNumerator() + n2.getNumerator();
        }
        fraction result = new fraction(numerator, denominator);
        result.CutFraction();
        return result;
    }

    public fraction subtract(fraction n2) {
        int numerator, denominator;

        if (getDenominator() != n2.getDenominator()) {
            denominator = getDenominator() * n2.getDenominator();
            numerator = getNumerator() * n2.getDenominator() - n2.getNumerator() * getDenominator();
        } else {
            denominator = getDenominator();
            numerator = getNumerator() - n2.getNumerator();
        }
        fraction result = new fraction(numerator, denominator);
        result.CutFraction();
        return result;
    }

    public fraction divide(fraction n2) {
        int numerator, denominator;
        numerator = getNumerator() * n2.getDenominator();
        denominator = n2.getNumerator() * getDenominator();
        fraction result = new fraction(numerator, denominator);
        result.CutFraction();
        return result;
    }

    public fraction multiply(fraction n2) {
        if (getNumerator() == 0 || n2.getNumerator() == 0) {
            return new fraction(0, 1);
        }
        int numerator, denominator;
        numerator = getNumerator() * n2.getNumerator();
        denominator = getDenominator() * n2.getDenominator();
        fraction result = new fraction(numerator, denominator);
        result.CutFraction();
        return result;
    }

    public void CutFraction() {
        int greatestCommonDivisor = getGCF(getNumerator(), getDenominator());
        _numerator /= greatestCommonDivisor;
        _denominator /= greatestCommonDivisor;
        if (_denominator < 0) {
            _numerator *= -1;
            _denominator *= -1;
        }
    }

    private int getGCF(int n1, int n2) {
        n1 = Math.abs(n1);
        n2 = Math.abs(n2);

        while (n2 != 0) {
            int temp = n2;
            n2 = n1 % n2;
            n1 = temp;
        }
        return n1;
    }

    @Override
    public String toString() {
        return String.format("%1$d/%2$d", getNumerator(), getDenominator());
    }
}
