package lib.tasks;

import lib.contexts.Context;

public interface Task {
    default void execute(Context context){}
    default boolean isContinuationPossible(){return false;}
}
