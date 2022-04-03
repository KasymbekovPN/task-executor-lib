package kpn.taskexecutor.lib.namecreator;

import java.lang.reflect.Method;
import java.util.Map;

import kpn.taskexecutor.exceptions.creators.FailureOnTaskCreation;
import kpn.taskexecutor.exceptions.creators.ObjectSettingFailure;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.tasks.Task;

public interface TaskCreator {
    Task create(Seed seed, Map<String, Method> setterMethods) throws FailureOnTaskCreation, ObjectSettingFailure;
}
