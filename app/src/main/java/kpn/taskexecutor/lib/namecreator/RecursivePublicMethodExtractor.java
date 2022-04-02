package kpn.taskexecutor.lib.namecreator;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RecursivePublicMethodExtractor implements MethodExtractor {

    @Override
    public Map<String, Method> extract(Class<?> type) {
        Set<Method> methods = getPublicMethods(type);
        return methods.stream().collect(Collectors.toMap(Method::getName, k -> k));
    }

    private Set<Method> getPublicMethods(Class<?> type) {
        Set<Method> methods = Arrays
            .stream(type.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toSet());

        Class<?> superClass = type.getSuperclass();
        if (superClass != null && !superClass.equals(Object.class)){
            methods.addAll(getPublicMethods(superClass));
        }
        return methods;
    }
}
