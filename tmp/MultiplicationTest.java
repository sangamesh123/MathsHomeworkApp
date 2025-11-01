import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MultiplicationTest {
    private static final char NBSP = '\u00A0';
    private static String padRightZeros(String s, int n) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < n; i++) sb.append('0');
        return sb.toString();
    }
    private static String digitsOnly(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') sb.append(c);
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        String a = "88"; // multiplicand
        String b = "0.10"; // multiplier
        int ad = a.indexOf('.');
        int bd = b.indexOf('.');
        String ai = (ad>=0? a.substring(0, ad) : a);
        String af = (ad>=0? a.substring(ad+1) : "");
        String bi = (bd>=0? b.substring(0, bd) : b);
        String bf = (bd>=0? b.substring(bd+1) : "");
        String aScaled = ai + af;
        String bScaled = bi + bf;
        if (aScaled.isEmpty()) aScaled = "0";
        if (bScaled.isEmpty()) bScaled = "0";
        BigInteger A = new BigInteger(aScaled);
        BigInteger B = new BigInteger(bScaled);
        int resultFrac = af.length() + bf.length();
        System.out.println("aScaled="+aScaled+" bScaled="+bScaled+" resultFrac="+resultFrac);

        // mulCarryRow from lsd of B (single digit)
        int[] aDigits = new int[aScaled.length()];
        for (int i=0;i<aScaled.length();i++) aDigits[i]=aScaled.charAt(i)-'0';
        int bLen = bScaled.length();
        int lsdDigit = (bLen>0)? (bScaled.charAt(bLen-1) - '0') : 0;
        char[] mulCarry = computeCarryRowForSingleDigit(aDigits, lsdDigit);
        System.out.println("lsdDigit="+lsdDigit+" mulCarryRow='"+new String(mulCarry)+"'");

        // partials
        List<String> partials = new ArrayList<>();
        List<String> displayPartials = new ArrayList<>();
        if (bLen > 0) {
            for (int idx = bLen - 1, shift = 0; idx >= 0; idx--, shift++) {
                int d = bScaled.charAt(idx) - '0';
                BigInteger p = A.multiply(BigInteger.valueOf(d));
                String ps = p.toString();
                String shifted = padRightZeros(ps, shift);
                partials.add(shifted);
                StringBuilder vv = new StringBuilder(ps);
                for (int s = 0; s < shift; s++) vv.append('+');
                displayPartials.add(vv.toString());
            }
        }
        System.out.println("partials: "+partials);
        System.out.println("displayPartials: "+displayPartials);

        BigInteger prod = A.multiply(B);
        String rs = prod.toString();
        if (resultFrac > 0) {
            while (rs.length() <= resultFrac) rs = "0" + rs;
            int split = rs.length() - resultFrac;
            rs = rs.substring(0, split) + "." + rs.substring(split);
        }
        System.out.println("final result string='"+rs+"'");
    }

    private static char[] computeCarryRowForSingleDigit(int[] multiplicand, int digit) {
        int L = multiplicand.length;
        char[] mark = new char[L];
        for (int i = 0; i < L; i++) mark[i] = NBSP;
        if (digit == 0) return mark;
        int carry = 0;
        for (int k = L - 1; k >= 0; k--) {
            int prod = multiplicand[k] * digit + carry;
            carry = prod / 10;
            if (carry > 0 && k - 1 >= 0) {
                int v = carry % 10; // fit a single digit carry in the cell
                mark[k - 1] = (char) ('0' + v);
            }
        }
        return mark;
    }
}

