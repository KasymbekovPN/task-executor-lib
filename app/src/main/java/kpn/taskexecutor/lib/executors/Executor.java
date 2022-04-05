package kpn.taskexecutor.lib.executors;

import kpn.taskexecutor.exceptions.AbsentMethodNamesException;
import kpn.taskexecutor.exceptions.FailedTaskCreationException;
import kpn.taskexecutor.exceptions.FailedTaskSettingException;
import kpn.taskexecutor.lib.seed.generator.Generator;

public interface Executor {
    Executor addGenerator(Generator generator);
    Boolean execute() throws AbsentMethodNamesException, FailedTaskCreationException, FailedTaskSettingException; 
}
