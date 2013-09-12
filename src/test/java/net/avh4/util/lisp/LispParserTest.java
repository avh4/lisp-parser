package net.avh4.util.lisp;

import net.avh4.util.lisp.test.Animal;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.stub;

public class LispParserTest {

    private LispParser subject;
    @Mock private LispContext context;
    private ObjectFactory animalFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        animalFactory = new ObjectFactory() {
            @Override public Object create(Object[] args) {
                return new Animal((Symbol) args[1], (String) args[2]);
            }
        };

        stub(context.get(Symbol.s("animal"))).toReturn(animalFactory);
        subject = new LispParser(context);
    }

    @Test
    public void shouldParseSymbol() throws Exception {
        Object o = subject.parse("sym");
        assertThat(o).isEqualTo(Symbol.s("sym"));
    }

    @Test
    public void shouldParseString() throws Exception {
        Object o = subject.parse("\"string\"");
        assertThat(o).isEqualTo("string");
    }

    @Test
    public void shouldParseInt() throws Exception {
        Object o = subject.parse("76");
        assertThat(o).isEqualTo(76);
    }

    @Test
    public void shouldParseList() throws Exception {
        Object o = subject.parse("(1 2 3 4)");
        assertThat(o).isInstanceOf(Iterable.class);
        assertThat((Iterable) o).containsSequence(1, 2, 3, 4);
    }

    @Test
    public void shouldParseObject() throws Exception {
        Object o = subject.parse("(animal cow \"Moo\")");
        assertThat(o).isEqualTo(new Animal(Symbol.s("cow"), "Moo"));
    }

    @Test
    public void shouldReturnListIfNoObjectFactoryIsProvided() throws Exception {
        Object o = subject.parse("(alien cow \"Zaaap!\")");
        assertThat(o).isInstanceOf(Iterable.class);
        assertThat((Iterable) o).containsSequence(Symbol.s("alien"), Symbol.s("cow"), "Zaaap!");
    }

    @Test
    public void shouldParseNestedList() throws Exception {
        Object o = subject.parse("(1 2 (3 4))");
        assertThat(o).isInstanceOf(Iterable.class);
    }
}
