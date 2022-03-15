package kpn.taskexecutor.lib.seeds;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.seeds.SeedImpl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

public class SeedImplTest {
    private static final String KEY = "key";
    private static final String VALUE = "value";

    @Test
    public void shouldCheckTypeGetting(){
        Class<?> expectedType = String.class;
        Seed taskSeed = SeedImpl.builder().type(expectedType).build();
        assertThat(expectedType).isEqualTo(taskSeed.getType());
    }

    @Test
    public void shouldCheckFieldsGetting(){
        Map<String, Object> expectedFields = Map.of(KEY, VALUE);
        Seed task = SeedImpl.builder().field(KEY, VALUE).build();
        assertThat(expectedFields).isEqualTo(task.getFields());
    }

    @Test
    public void shouldCheckAttemptOfChangingOfFields(){
        Throwable throwable = Assertions.catchThrowable(() -> {
            Seed task = SeedImpl.builder().build();
            Map<String, Object> fields = task.getFields();
            fields.put(KEY, VALUE);
        });
        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
    }
}
