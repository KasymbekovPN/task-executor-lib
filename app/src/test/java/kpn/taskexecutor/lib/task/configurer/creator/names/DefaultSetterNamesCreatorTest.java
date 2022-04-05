package kpn.taskexecutor.lib.task.configurer.creator.names;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.Test;

public class DefaultSetterNamesCreatorTest {
    

    @Test
    public void shouldCheckCreation(){
        Set<String> set = Set.of("name0", "name1", "name2");
        Map<String, String> expectedResult = Map.of(
            "name0", "name0",
            "name1", "name1",
            "name2", "name2"
        );

        DefaultSetterNamesCreator creator = new DefaultSetterNamesCreator(new TestNameCreator());
        Map<String, String> result = creator.apply(set);

        assertThat(expectedResult).isEqualTo(result);
    }

    private static class TestNameCreator implements UnaryOperator<String>{
        @Override
        public String apply(String t) {
            return t;
        }
    }
}
