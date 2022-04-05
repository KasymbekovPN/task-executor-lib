package kpn.taskexecutor.lib.executors;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

import kpn.taskexecutor.exceptions.AbsentMethodNamesException;
import kpn.taskexecutor.exceptions.FailedTaskCreationException;
import kpn.taskexecutor.exceptions.FailedTaskSettingException;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.seed.Seed;
import kpn.taskexecutor.lib.seed.generator.Generator;
import kpn.taskexecutor.lib.task.Task;
import kpn.taskexecutor.lib.task.configurer.TaskConfigurer;

public class DefaultExecutor implements Executor{

    private final TaskConfigurer creator;
    private final Context context;
    private final Deque<Generator> generators = new ArrayDeque<>();

    public DefaultExecutor(TaskConfigurer creator, Context context) {
        this.creator = creator;
        this.context = context;
    }

    @Override
    public Executor addGenerator(Generator generator) {
        generators.addLast(generator);
        return this;
    }

    @Override
    public Boolean execute() throws AbsentMethodNamesException, FailedTaskCreationException, FailedTaskSettingException {
        boolean success = true;
        do {
            Generator generator = generators.pollFirst();
            Optional<Seed> maybeSeed;
            do {
                maybeSeed = generator.getNextIfExist(context);
                if (maybeSeed.isPresent()){
                    Task task = creator.configure(maybeSeed.get());
                    task.execute(context);
                    success = task.isContinuationPossible();
                }
            } while (maybeSeed.isPresent() && success);
        } while (!generators.isEmpty() && success);

        return success;
    }
}
