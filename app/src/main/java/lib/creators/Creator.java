package lib.creators;

import exceptions.creators.FailureOnTaskCreation;
import exceptions.creators.ObjectAndSeedMismatching;
import exceptions.creators.ObjectSettingFailure;
import lib.seeds.Seed;
import lib.tasks.Task;

public interface Creator{
    Task create(Seed seed) throws ObjectAndSeedMismatching, FailureOnTaskCreation, ObjectSettingFailure;
}
