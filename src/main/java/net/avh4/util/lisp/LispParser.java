package net.avh4.util.lisp;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;

public class LispParser {
    private final LispContext context;

    public LispParser(LispContext context) {
        this.context = context;
    }

    public Object parse(String lisp) {
        StringReader sr = new StringReader(lisp);
        PushbackReader pr = new PushbackReader(sr);
        return parse(pr);
    }

    private Object parse(PushbackReader pr) {
        try {
            int c = pr.read();
            pr.unread(c);
            if (c == '(') {
                return readListOrObject(pr);
            } else if (c == '"') {
                return readString(pr);
            } else if (Character.isDigit(c)) {
                return readNumber(pr);
            } else {
                return readSymbol(pr);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Object readSymbol(PushbackReader pr) throws IOException {
        final String name = getWord(pr);
        return Symbol.s(name);
    }

    private String getWord(PushbackReader pr) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c;
        while (true) {
            c = pr.read();
            if (c == -1) break;
            if (c == ' ' || c == ')') {
                pr.unread(c);
                break;
            }
            sb.append((char) c);
        }
        return sb.toString();
    }

    private Object readNumber(PushbackReader pr) throws IOException {
        return Integer.parseInt(getWord(pr));
    }

    private Object readString(PushbackReader pr) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c;
        c = pr.read();
        if (c != '"') throw new RuntimeException("Expected '\"'");
        while (true) {
            c = pr.read();
            if (c == -1) throw new RuntimeException("expected closing '\"'");
            if (c == '"') break;
            sb.append((char) c);
        }
        return sb.toString();
    }

    private Object readListOrObject(PushbackReader pr) throws IOException {
        ArrayList<Object> values = new ArrayList<>();
        int c = pr.read();
        if (c != '(') throw new RuntimeException("Expected '('");
        while (true) {
            c = pr.read();
            if (c == -1) break;
            if (c == ')') break;
            if (c == ' ') continue;
            pr.unread(c);
            final Object o = parse(pr);
            values.add(o);
        }
        if (values.size() > 0 && values.get(0) instanceof Symbol) {
            final ObjectFactory factory = context.get((Symbol) values.get(0));
            if (factory != null) {
                return factory.create(values.toArray());
            }
        }
        return values;
    }
}
