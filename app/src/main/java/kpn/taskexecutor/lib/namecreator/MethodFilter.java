package kpn.taskexecutor.lib.namecreator;

import java.lang.reflect.Method;
import java.util.Map;

import kpn.taskexecutor.exceptions.creators.ObjectAndSeedMismatching;

public interface MethodFilter {
    Map<String, Method> filter(Map<String, Method> methods, Map<String, String> expected) throws ObjectAndSeedMismatching;
}
