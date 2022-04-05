package kpn.taskexecutor.lib.task;

import kpn.taskexecutor.lib.contexts.Context;

public interface Task {
    default void execute(Context context){}
    default boolean isContinuationPossible(){return false;}
}
