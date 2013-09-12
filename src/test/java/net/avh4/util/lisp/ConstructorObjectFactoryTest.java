package net.avh4.util.lisp;

import net.avh4.util.lisp.test.Animal;
import net.avh4.util.lisp.test.Temperature;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ConstructorObjectFactoryTest {
    private ConstructorObjectFactory subject;
    private ConstructorObjectFactory subjectWithInt;

    @Before
    public void setUp() throws Exception {
        subject = new ConstructorObjectFactory(Animal.class);
        subjectWithInt = new ConstructorObjectFactory(Temperature.class);
    }

    @Test
    public void shouldInstantiateObjects() throws Exception {
        final Object o = subject.create(new Object[]{Symbol.s("animal"), Symbol.s("sheep"), "Ba"});
        assertThat(o).isEqualTo(new Animal(Symbol.s("sheep"), "Ba"));
    }

    @Test
    public void shouldInstantiateObjectsWithIntParams() throws Exception {
        final Object o = subjectWithInt.create(new Object[]{Symbol.s("temperature"), 42});
        assertThat(o).isEqualTo(new Temperature(42));
    }

    @Test
    public void shouldProvideMessageForWrongArguments() throws Exception {
        try {
            final Object o = subject.create(new Object[]{Symbol.s("animal"), Symbol.s("sheep"), 42});
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).contains("Symbol, String");
        }
    }
}
