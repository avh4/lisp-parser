package net.avh4.util.lisp;

public final class Symbol {
    private final String name;

    public static Symbol s(String name) {
        return new Symbol(name);
    }

    private Symbol(String name) {
        if (name == null) throw new NullPointerException("Symbol must have a name");
        this.name = name;
    }

    @Override public String toString() {
        return ":" + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Symbol symbol = (Symbol) o;

        if (!name.equals(symbol.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
