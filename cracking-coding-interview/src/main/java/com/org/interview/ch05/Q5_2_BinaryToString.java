package com.org.interview.ch05;

// Q5.2 Given a decimal number passed in as a string, print the binary representation.
// If the number cannot be represented accurately in binary, print "ERROR".
public class Q5_2_BinaryToString {

    public static String printBinary(String val) {
        int dotPos = val.indexOf('.');
        int intPart = 0;
        double decPart = 0.0;

        // Parse integer part manually
        for (int i = 0; i < dotPos; i++) {
            intPart = intPart * 10 + (val.charAt(i) - '0');
        }
        // Parse decimal part manually
        double factor = 0.1;
        for (int i = dotPos + 1; i < val.length(); i++) {
            decPart += (val.charAt(i) - '0') * factor;
            factor *= 0.1;
        }

        // Build integer binary
        StringBuilder intStr = new StringBuilder();
        if (intPart == 0) {
            intStr.append('0');
        } else {
            while (intPart > 0) {
                intStr.insert(0, (intPart & 1) == 1 ? '1' : '0');
                intPart >>= 1;
            }
        }

        // Build decimal binary
        StringBuilder decStr = new StringBuilder();
        while (decPart > 0) {
            if (decStr.length() > 32) return "ERROR";
            decPart *= 2;
            if (decPart >= 1.0) {
                decStr.append('1');
                decPart -= 1.0;
            } else {
                decStr.append('0');
            }
        }

        return intStr.toString() + "." + decStr.toString();
    }

    public static void main(String[] args) {
        System.out.println(printBinary("19.25")); // 10011.01
        System.out.println(printBinary("3.72"));  // ERROR
    }
}
