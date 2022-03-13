package taskExecutorLib.creators;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import taskExecutorLib.seeds.TaskSeed;
import taskExecutorLib.tasks.Task;

public class TaskCreatorImpl implements TaskCreator{

    @Override
    public Task create(TaskSeed seed) throws TaskCreationMismatchingExeption, TaskCreationContructionException, TaskCreationSettingExeption {
        Map<String, String> expectedSetterNames = createExpectedSetterNames(seed);
        Map<String, Method> setterMethods = getSetterMethods(seed, expectedSetterNames);
        Task task = createTask(seed);
        fillTask(task, setterMethods, seed);
        return task;
    }

    private Task createTask(TaskSeed seed) throws TaskCreationContructionException {
        try {
            return (Task) seed.getType().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new TaskCreationContructionException(String.format("Fail attempt of task construction : %s", seed.getType()));
        }
    }

    private Map<String, String> createExpectedSetterNames(TaskSeed seed) {
        Map<String, Object> fields = seed.getFields();

        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            String key = entry.getKey();
            result.put(key, createSetterName(key));
        }

        return result;
    }

    private String createSetterName(String fieldName) {
        String newBegin = "set" + String.valueOf(fieldName.charAt(0)).toUpperCase();
        return  newBegin + fieldName.substring(1);
    }

    private Map<String, Method> getSetterMethods(TaskSeed seed, Map<String, String> expectedSetterNames) throws TaskCreationMismatchingExeption{
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
            throw new TaskCreationMismatchingExeption(String.format("Some methods are absent : %s", absentMethodNames));
        }

        return result;
    }

    private void fillTask(Task task, Map<String, Method> setterMethods, TaskSeed seed) throws TaskCreationSettingExeption {
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
            throw new TaskCreationSettingExeption(String.format("Setting fail : %s", failSetterNames));
        }
    }
}
