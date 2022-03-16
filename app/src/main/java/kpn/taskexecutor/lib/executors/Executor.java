package kpn.taskexecutor.lib.executors;

import kpn.taskexecutor.exceptions.creators.FailureOnTaskCreation;
import kpn.taskexecutor.exceptions.creators.ObjectAndSeedMismatching;
import kpn.taskexecutor.exceptions.creators.ObjectSettingFailure;
import kpn.taskexecutor.lib.generators.Generator;

public interface Executor {
    void addGenerator(Generator generator);
    Boolean execute() throws ObjectAndSeedMismatching, FailureOnTaskCreation, ObjectSettingFailure; 
}
