package net.avh4.util.lisp.test;

public class Temperature {
    private final int degrees;

    public Temperature(int degrees) {
        this.degrees = degrees;
    }

    @Override public String toString() {
        return "Temperature{" +
                "degrees=" + degrees +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Temperature that = (Temperature) o;

        if (degrees != that.degrees) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return degrees;
    }
}
