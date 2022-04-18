package me.supeer.deobfuscator.loader;



import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Clazz {



    private final Class<?> clazz;

    public Clazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Object invoke(String methodName, String methodDesc, Object reference, Object... arguments) {
        return invoke(methodName, MethodType.fromMethodDescriptorString(methodDesc, Clazz.class.getClassLoader()), reference, arguments);
    }

    public Object invoke(String methodName, Class<?> returnType, Class<?>[] parameters, Object reference, Object... arguments) {
        return invoke(methodName, MethodType.methodType(returnType, parameters), reference, arguments);
    }

    public Object invoke(String methodName, MethodType methodType, Object reference, Object... arguments) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, methodType.parameterArray());
            if (!method.isAccessible())
                method.trySetAccessible();

            return method.invoke(reference, arguments);
        } catch (NoSuchMethodError | NoSuchMethodException ignored) {
            ignored.printStackTrace();
        } catch (Throwable e) {
            if (e.getCause() instanceof NoSuchMethodError | e.getCause() instanceof NoSuchMethodException) {
                e.printStackTrace();
            } else {
                e.printStackTrace();
            }
        }

        return null;
    }
    

    public Class<?> getClazz() {
        return clazz;
    }
}
