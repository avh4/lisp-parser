package net.avh4.util.lisp;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class LispParserTest {

    private LispParser subject;

    @Before
    public void setUp() throws Exception {
        subject = new LispParser();
    }

    @Test
    public void shouldParseSymbol() throws Exception {
        Object o = subject.parse("sym");
        Assertions.assertThat(o).isEqualTo(Symbol.s("sym"));
    }

    @Test
    public void shouldParseString() throws Exception {
        Object o = subject.parse("\"string\"");
        Assertions.assertThat(o).isEqualTo("string");
    }

    @Test
    public void shouldParseInt() throws Exception {
        Object o = subject.parse("76");
        Assertions.assertThat(o).isEqualTo(76);
    }

    @Test
    public void shouldParseList() throws Exception {
        Object o = subject.parse("(1 2 3 4)");
        Assertions.assertThat(o).isInstanceOf(Iterable.class);
        Assertions.assertThat((Iterable) o).containsSequence(1, 2, 3, 4);
    }
}
