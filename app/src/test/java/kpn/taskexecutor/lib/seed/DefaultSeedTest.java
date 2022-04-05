package kpn.taskexecutor.lib.seed;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

public class DefaultSeedTest {
    private static final String KEY = "key";
    private static final String VALUE = "value";

    @Test
    public void shouldCheckTypeGetting(){
        Class<?> expectedType = String.class;
        Seed taskSeed = DefaultSeed.builder().type(expectedType).build();
        assertThat(expectedType).isEqualTo(taskSeed.getType());
    }

    @Test
    public void shouldCheckFieldsGetting(){
        Map<String, Object> expectedFields = Map.of(KEY, VALUE);
        Seed task = DefaultSeed.builder().field(KEY, VALUE).build();
        assertThat(expectedFields).isEqualTo(task.getFields());
    }

    @Test
    public void shouldCheckAttemptOfChangingOfFields(){
        Throwable throwable = Assertions.catchThrowable(() -> {
            Seed task = DefaultSeed.builder().build();
            Map<String, Object> fields = task.getFields();
            fields.put(KEY, VALUE);
        });
        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
    }
}
