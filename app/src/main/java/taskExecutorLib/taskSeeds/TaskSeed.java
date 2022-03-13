package taskExecutorLib.taskSeeds;

import java.util.Map;

public interface TaskSeed {
    Class<?> getType();
    Map<String, Object> getFields();
}
