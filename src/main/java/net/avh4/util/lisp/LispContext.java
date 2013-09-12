package net.avh4.util.lisp;

public interface LispContext {
    ObjectFactory get(Symbol name);
}
