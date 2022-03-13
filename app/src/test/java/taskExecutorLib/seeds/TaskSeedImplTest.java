package taskExecutorLib.seeds;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

public class TaskSeedImplTest {
    
    private static final String KEY = "key";
    private static final String VALUE = "value";

    @Test
    public void shouldCheckTypeGetting(){
        Class<?> expectedType = String.class;
        TaskSeed taskSeed = TaskSeedImpl.builder().type(expectedType).build();
        assertThat(expectedType).isEqualTo(taskSeed.getType());
    }

    @Test
    public void shouldCheckFieldsGetting(){
        Map<String, Object> expectedFields = Map.of(KEY, VALUE);
        TaskSeed task = TaskSeedImpl.builder().field(KEY, VALUE).build();
        assertThat(expectedFields).isEqualTo(task.getFields());
    }

    @Test
    public void shouldCheckAttemptOfChangingOfFields(){
        Throwable throwable = Assertions.catchThrowable(() -> {
            TaskSeed task = TaskSeedImpl.builder().build();
            Map<String, Object> fields = task.getFields();
            fields.put(KEY, VALUE);
        });
        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
    }
}
