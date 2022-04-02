package kpn.taskexecutor.lib.creators;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import kpn.taskexecutor.exceptions.creators.FailureOnTaskCreation;
import kpn.taskexecutor.exceptions.creators.ObjectAndSeedMismatching;
import kpn.taskexecutor.exceptions.creators.ObjectSettingFailure;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.tasks.Task;

public class SimpleTaskBuilder implements TaskBuilder{

    @Override
    public Task build(Seed seed) throws ObjectAndSeedMismatching, FailureOnTaskCreation, ObjectSettingFailure {
        Map<String, String> expectedSetterNames = createExpectedSetterNames(seed);
        Map<String, Method> setterMethods = getSetterMethods(seed, expectedSetterNames);
        Task task = createTask(seed);
        fillTask(task, setterMethods, seed);
        return task;
    }

    protected Task createTask(Seed seed) throws FailureOnTaskCreation {
        try {
            return (Task) seed.getType().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new FailureOnTaskCreation(String.format("Fail attempt of task construction : %s", seed.getType()));
        }
    }

    protected Map<String, String> createExpectedSetterNames(Seed seed) {
        Map<String, Object> fields = seed.getFields();

        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            String key = entry.getKey();
            result.put(key, createSetterName(key));
        }

        return result;
    }

    protected String createSetterName(String fieldName) {
        String newBegin = "set" + String.valueOf(fieldName.charAt(0)).toUpperCase();
        return  newBegin + fieldName.substring(1);
    }

    protected Map<String, Method> getSetterMethods(Seed seed, Map<String, String> expectedSetterNames) throws ObjectAndSeedMismatching{
        Map<String, Method> methods = Arrays
            .stream(seed.getType().getDeclaredMethods())
            .collect(Collectors.toMap(Method::getName, k -> k));
        HashMap<String, Method> result = new HashMap<>();

        HashSet<String> absentMethodNames = new HashSet<>();
        for (Map.Entry<String, String> entry : expectedSetterNames.entrySet()) {
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

    protected void fillTask(Task task, Map<String, Method> setterMethods, Seed seed) throws ObjectSettingFailure {
        Map<String, Object> fields = seed.getFields();
        Set<String> failSetterNames = new HashSet<>();
        for (Map.Entry<String, Method> entry : setterMethods.entrySet()) {
            Method method = entry.getValue();
            try {
                method.invoke(task, fields.get(entry.getKey()));
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
                failSetterNames.add(method.getName());
            }
        }

        if (!failSetterNames.isEmpty()){
            throw new ObjectSettingFailure(String.format("Setting fail : %s", failSetterNames));
        }
    }
}
