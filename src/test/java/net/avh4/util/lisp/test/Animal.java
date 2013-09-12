package net.avh4.util.lisp.test;

import net.avh4.util.lisp.Symbol;

public class Animal {
    private final Symbol name;
    private final String sound;

    public Animal(Symbol name, String sound) {
        this.name = name;
        this.sound = sound;
    }

    @Override public String toString() {
        return "Animal{" +
                "name=" + name +
                ", sound='" + sound + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Animal animal = (Animal) o;

        if (name != null ? !name.equals(animal.name) : animal.name != null) return false;
        if (sound != null ? !sound.equals(animal.sound) : animal.sound != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (sound != null ? sound.hashCode() : 0);
        return result;
    }
}
