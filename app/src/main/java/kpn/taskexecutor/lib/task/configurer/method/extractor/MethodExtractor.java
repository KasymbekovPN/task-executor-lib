package kpn.taskexecutor.lib.task.configurer.method.extractor;

import java.lang.reflect.Method;
import java.util.Map;

public interface MethodExtractor {
    Map<String, Method> extract(Class<?> type);
}
