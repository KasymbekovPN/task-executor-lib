package kpn.taskexecutor.lib.task.configurer;

import kpn.taskexecutor.exceptions.AbsentMethodNamesException;
import kpn.taskexecutor.exceptions.FailedTaskCreationException;
import kpn.taskexecutor.exceptions.FailedTaskSettingException;
import kpn.taskexecutor.lib.seed.Seed;
import kpn.taskexecutor.lib.task.Task;

public interface TaskConfigurer{
    Task configure(Seed seed) throws AbsentMethodNamesException, FailedTaskCreationException, FailedTaskSettingException;
}
