package kpn.taskexecutor.lib.task.configurer.creator.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import kpn.taskexecutor.exceptions.FailedTaskCreationException;
import kpn.taskexecutor.exceptions.FailedTaskSettingException;
import kpn.taskexecutor.lib.seed.Seed;
import kpn.taskexecutor.lib.task.Task;

public class DefaultTaskCreator implements TaskCreator {

    @Override
    public Task create(Seed seed, Map<String, Method> setterMethods) throws FailedTaskCreationException, FailedTaskSettingException {
        Task task = createTask(seed.getType());
        fillTask(task, setterMethods, seed.getFields());
        return task;
    }

    private Task createTask(Class<?> type) throws FailedTaskCreationException {
        try {
            return (Task) type.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new FailedTaskCreationException(String.format("Fail attempt of task construction : %s", type));
        }
    }

    private void fillTask(Task task, Map<String, Method> setterMethods, Map<String, Object> fields) throws FailedTaskSettingException {
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
            throw new FailedTaskSettingException(String.format("Setting fail : %s", failSetterNames));
        }
    }    
}
