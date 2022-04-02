package kpn.taskexecutor.lib.namecreator;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class SimplePublicMethodExtractor implements MethodExtractor {

    @Override
    public Map<String, Method> extract(Class<?> type) {
        return Arrays
            .stream(type.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toMap(Method::getName, k -> k));
    }
}
