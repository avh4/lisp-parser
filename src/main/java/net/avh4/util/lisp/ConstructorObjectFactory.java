package net.avh4.util.lisp;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ConstructorObjectFactory implements ObjectFactory {
    private final Class<?> clazz;

    public ConstructorObjectFactory(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override public Object create(Object[] args) {
        final Constructor<?>[] constructors = clazz.getConstructors();
        final Constructor<?> constructor = constructors[0];
        if (args.length - 1 != constructor.getParameterTypes().length) {
            throw new IllegalArgumentException(getUsage(constructor) + "\n got: " + Arrays.toString(args));
        }
        for (int i = 0; i < constructor.getParameterTypes().length; i++) {
            Class<?> aClass = constructor.getParameterTypes()[i];
            if (aClass.isInstance(args[i + 1])) continue;
            if (aClass == Integer.TYPE && Integer.class.isInstance(args[i + 1])) continue;
            throw new IllegalArgumentException(getUsage(constructor) + "\n got: " + Arrays.toString(args));
        }
        try {
            return constructor.newInstance(Arrays.copyOfRange(args, 1, args.length));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private String getUsage(Constructor<?> constructor) {
        final StringBuilder sb = new StringBuilder();
        sb.append(clazz.getSimpleName()).append(" requires ");
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> aClass = parameterTypes[i];
            sb.append(aClass.getSimpleName());
            if (i < parameterTypes.length - 1) sb.append(", ");
        }
        return sb.toString();
    }
}
