package taskExecutorLib.seeds;

import java.util.Map;

public interface TaskSeed {
    Class<?> getType();
    Map<String, Object> getFields();
}
