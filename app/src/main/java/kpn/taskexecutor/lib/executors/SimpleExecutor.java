package kpn.taskexecutor.lib.executors;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

import kpn.taskexecutor.exceptions.creators.FailureOnTaskCreation;
import kpn.taskexecutor.exceptions.creators.ObjectAndSeedMismatching;
import kpn.taskexecutor.exceptions.creators.ObjectSettingFailure;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.creators.TaskConfigurer;
import kpn.taskexecutor.lib.generators.Generator;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.tasks.Task;

public class SimpleExecutor implements Executor{

    private final TaskConfigurer creator;
    private final Context context;
    private final Deque<Generator> generators = new ArrayDeque<>();

    public SimpleExecutor(TaskConfigurer creator, Context context) {
        this.creator = creator;
        this.context = context;
    }

    @Override
    public void addGenerator(Generator generator) {
        generators.addLast(generator);
    }

    @Override
    public Boolean execute() throws ObjectAndSeedMismatching, FailureOnTaskCreation, ObjectSettingFailure {
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
