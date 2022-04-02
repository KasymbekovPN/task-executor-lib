package kpn.taskexecutor.lib.executors;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import kpn.taskexecutor.exceptions.contexts.ContextPropertyNonExist;
import kpn.taskexecutor.exceptions.creators.FailureOnTaskCreation;
import kpn.taskexecutor.exceptions.creators.ObjectAndSeedMismatching;
import kpn.taskexecutor.exceptions.creators.ObjectSettingFailure;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.SimpleContext;
import kpn.taskexecutor.lib.creators.SimpleTaskBuilder;
import kpn.taskexecutor.lib.generators.Generator;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.seeds.SeedImpl;
import kpn.taskexecutor.lib.tasks.Task;

public class SimpleExecutorTest {
    
    @Test
    public void shouldCheckFailExecution() throws ObjectAndSeedMismatching, FailureOnTaskCreation, ObjectSettingFailure{
        Executor executor = new SimpleExecutor(new SimpleTaskBuilder(), new SimpleContext());
        executor.addGenerator(new TestFailTaskGenerator());
        Boolean executionResult = executor.execute();
        assertThat(executionResult).isFalse();
    }

    public static class TestFailTaskGenerator implements Generator{
        private boolean first = true;
        
        @Override
        public Optional<Seed> getNextIfExist(Context context) {
            if (first){
                Seed seed = SeedImpl.builder().type(TestFailTask.class).build();
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
    public void shouldCheckExecution() throws ObjectAndSeedMismatching, FailureOnTaskCreation, ObjectSettingFailure, ContextPropertyNonExist{
        String key0 = "key0";
        String key1 = "key1";
        Map<String, Integer> data = Map.of(key0, 1, key1, 2);

        SimpleContext context = new SimpleContext();
        Executor executor = new SimpleExecutor(new SimpleTaskBuilder(), context);
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
                Seed seed = SeedImpl.builder().type(TestTask.class).field("key", key).field("value", value).build();
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
