package kpn.taskexecutor.lib.executors;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import kpn.taskexecutor.exceptions.AbsentMethodNamesException;
import kpn.taskexecutor.exceptions.FailedTaskCreationException;
import kpn.taskexecutor.exceptions.FailedTaskSettingException;
import kpn.taskexecutor.exceptions.PropertyNotFoundException;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.DefaultContext;
import kpn.taskexecutor.lib.seed.DefaultSeed;
import kpn.taskexecutor.lib.seed.Seed;
import kpn.taskexecutor.lib.seed.generator.Generator;
import kpn.taskexecutor.lib.task.Task;
import kpn.taskexecutor.lib.task.configurer.DefaultTaskConfigurer;

public class DefaultExecutorTest {
    
    @Test
    public void shouldCheckFailExecution() throws AbsentMethodNamesException, FailedTaskCreationException, FailedTaskSettingException{
        Executor executor = new DefaultExecutor(DefaultTaskConfigurer.builder().build(), new DefaultContext());
        executor.addGenerator(new TestFailTaskGenerator());
        Boolean executionResult = executor.execute();
        assertThat(executionResult).isFalse();
    }

    public static class TestFailTaskGenerator implements Generator{
        private boolean first = true;
        
        @Override
        public Optional<Seed> getNextIfExist(Context context) {
            if (first){
                Seed seed = DefaultSeed.builder().type(TestFailTask.class).build();
                return Optional.of(seed); 
            }
            return Optional.empty();
        }
    }

    public static class TestFailTask implements Task{
        @Override
        public boolean isContinuationPossible() {
            return false;
        }
    }

    @Test
    public void shouldCheckExecution() throws AbsentMethodNamesException, FailedTaskCreationException, FailedTaskSettingException, PropertyNotFoundException{
        String key0 = "key0";
        String key1 = "key1";
        Map<String, Integer> data = Map.of(key0, 1, key1, 2);

        DefaultContext context = new DefaultContext();
        Executor executor = new DefaultExecutor(DefaultTaskConfigurer.builder().build(), context);
        executor.addGenerator(new TestTaskGenerator(key0, data.get(key0)));
        executor.addGenerator(new TestTaskGenerator(key1, data.get(key1)));

        Boolean executionResult = executor.execute();
        assertThat(executionResult).isTrue();
        assertThat(data.get(key0)).isEqualTo(context.get(key0));
        assertThat(data.get(key1)).isEqualTo(context.get(key1));
    }

    public static class TestTaskGenerator implements Generator{

        private final String key;
        private final Integer value;

        private boolean first = true;

        public TestTaskGenerator(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public Optional<Seed> getNextIfExist(Context context) {
            if (first){
                first = false;
                Seed seed = DefaultSeed.builder().type(TestTask.class).field("key", key).field("value", value).build();
                return Optional.of(seed);
            }
            return Optional.empty();
        }
    }

    public static class TestTask implements Task{

        private Integer value;
        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        @Override
        public void execute(Context context) {
            context.put(key, value);
        }

        @Override
        public boolean isContinuationPossible() {
            return true;
        }
    }
}
