package kpn.taskexecutor.lib.tasks;

import kpn.taskexecutor.lib.contexts.Context;

public interface Task {
    default void execute(Context context){}
    default boolean isContinuationPossible(){return false;}
}
