package kpn.taskexecutor.lib.namecreator;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import kpn.taskexecutor.exceptions.creators.ObjectAndSeedMismatching;

public class MethodFilterImpl implements MethodFilter {

    @Override
    public Map<String, Method> filter(Map<String, Method> methods, Map<String, String> expected) throws ObjectAndSeedMismatching {
        HashMap<String, Method> result = new HashMap<>();

        HashSet<String> absentMethodNames = new HashSet<>();
        for (Map.Entry<String, String> entry : expected.entrySet()){
            String expectedMethodName = entry.getValue();
            if (methods.containsKey(expectedMethodName)){
                result.put(entry.getKey(), methods.get(expectedMethodName));
            } else {
                absentMethodNames.add(expectedMethodName);
            }
        }

        if (!absentMethodNames.isEmpty()){
            throw new ObjectAndSeedMismatching(String.format("Some methods are absent : %s", absentMethodNames));
        }

        return result;
    }
}
