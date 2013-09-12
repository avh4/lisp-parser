package net.avh4.util.lisp;

import java.util.Arrays;

public class LispParser {
    public Object parse(String lisp) {
        if (lisp.charAt(0) == '(') {
            final String[] tokens = lisp.substring(1, lisp.length() - 1).split(" +");
            final Object[] values = new Object[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                values[i] = parse(tokens[i]);
            }
            return Arrays.asList(values);
        } else if (lisp.charAt(0) == '"') {
            return lisp.substring(1, lisp.length() - 1);
        } else if (Character.isDigit(lisp.charAt(0))) {
            return Integer.parseInt(lisp);
        } else {
            return Symbol.s(lisp);
        }
    }
}
