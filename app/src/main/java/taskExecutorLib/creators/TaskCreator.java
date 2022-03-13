package taskExecutorLib.creators;

import taskExecutorLib.seeds.TaskSeed;
import taskExecutorLib.tasks.Task;

public interface TaskCreator{
    Task create(TaskSeed seed) throws TaskCreationMismatchingExeption, TaskCreationContructionException, TaskCreationSettingExeption;
}
