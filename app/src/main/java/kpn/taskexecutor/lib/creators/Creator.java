package kpn.taskexecutor.lib.creators;

import kpn.taskexecutor.exceptions.creators.FailureOnTaskCreation;
import kpn.taskexecutor.exceptions.creators.ObjectAndSeedMismatching;
import kpn.taskexecutor.exceptions.creators.ObjectSettingFailure;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.tasks.Task;

public interface Creator{
    Task create(Seed seed) throws ObjectAndSeedMismatching, FailureOnTaskCreation, ObjectSettingFailure;
}
