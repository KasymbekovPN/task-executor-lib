package kpn.taskexecutor.lib.task.configurer.creator.task;

import java.lang.reflect.Method;
import java.util.Map;

import kpn.taskexecutor.exceptions.FailedTaskCreationException;
import kpn.taskexecutor.exceptions.FailedTaskSettingException;
import kpn.taskexecutor.lib.seed.Seed;
import kpn.taskexecutor.lib.task.Task;

public interface TaskCreator {
    Task create(Seed seed, Map<String, Method> setterMethods) throws FailedTaskCreationException, FailedTaskSettingException;
}
