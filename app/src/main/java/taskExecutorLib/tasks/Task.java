package taskExecutorLib.tasks;

import taskExecutorLib.contexts.Context;

public interface Task {
    Object var = null;
    default void execute(Context context){}
    default boolean isContinuationPossible(){return false;}
}
