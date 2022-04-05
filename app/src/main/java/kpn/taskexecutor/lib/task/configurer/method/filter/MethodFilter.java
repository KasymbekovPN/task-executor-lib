package kpn.taskexecutor.lib.task.configurer.method.filter;

import java.lang.reflect.Method;
import java.util.Map;

import kpn.taskexecutor.exceptions.AbsentMethodNamesException;

public interface MethodFilter {
    Map<String, Method> filter(Map<String, Method> methods, Map<String, String> expected) throws AbsentMethodNamesException;
}
