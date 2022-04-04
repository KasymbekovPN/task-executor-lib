package kpn.taskexecutor.lib.creators;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import kpn.taskexecutor.exceptions.creators.FailureOnTaskCreation;
import kpn.taskexecutor.exceptions.creators.ObjectAndSeedMismatching;
import kpn.taskexecutor.exceptions.creators.ObjectSettingFailure;
import kpn.taskexecutor.lib.namecreator.MethodExtractor;
import kpn.taskexecutor.lib.namecreator.MethodFilter;
import kpn.taskexecutor.lib.namecreator.TaskCreator;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.seeds.SeedImpl;
import kpn.taskexecutor.lib.tasks.Task;

public class DefaultTaskConfigurerTest {
    
    private static final int FIELD_VALUE = 123;
    private static final Class<?> TYPE = TestTask.class;
    private static final String SETTER_NAME = "setField";
    private static final String FIELD_NAME = "field";

    private Map<String, String> setterNamesCreatorResult;
    private Map<String, Method> methodExecutorResult;
    private Map<String, Method> methodFilterResult;

    private Seed seed;
    private Method setter;
    private TestTask testTask;

    @BeforeEach
    public void setUp() throws NoSuchMethodException, SecurityException{
        seed = SeedImpl.builder()
            .type(TYPE)
            .field(FIELD_NAME, FIELD_VALUE)
            .build();

        setter = TYPE.getMethod(SETTER_NAME, int.class);

        setterNamesCreatorResult = Map.of(FIELD_NAME, SETTER_NAME);
        methodExecutorResult = Map.of(SETTER_NAME, setter);
        methodFilterResult = Map.of(FIELD_NAME, setter);

        testTask = new TestTask(FIELD_VALUE);
    }

    @Test
    public void shouldCheckConfiguration() throws ObjectAndSeedMismatching, FailureOnTaskCreation, ObjectSettingFailure{
        DefaultTaskConfigurer configurer = DefaultTaskConfigurer.build()
            .setterNamesCreator(createSetterNamesCreator())
            .methodExtractor(createMethodExtractor())
            .methodFilder(createMethodFilter())
            .taskCreator(createTaskCreator())
            .build();

        Task task = configurer.configure(seed);
        assertThat(testTask).isEqualTo(task);
    }

    private Function<Set<String>, Map<String, String>> createSetterNamesCreator() {
        TestSetterNamesCreator setterNamesCreator = Mockito.mock(TestSetterNamesCreator.class);
        Mockito
            .when(setterNamesCreator.apply(seed.getFields().keySet()))
            .thenReturn(setterNamesCreatorResult);
        return setterNamesCreator;
    }

    private MethodExtractor createMethodExtractor() {
        MethodExtractor methodExtractor = Mockito.mock(MethodExtractor.class);
        Mockito
            .when(methodExtractor.extract(TYPE))
            .thenReturn(methodExecutorResult);
        return methodExtractor;
    }

    private MethodFilter createMethodFilter() throws ObjectAndSeedMismatching {
        MethodFilter methodFilter = Mockito.mock(MethodFilter.class);
        Mockito
            .when(methodFilter.filter(methodExecutorResult, setterNamesCreatorResult))
            .thenReturn(methodFilterResult);
        return methodFilter;
    }

    private TaskCreator createTaskCreator() throws FailureOnTaskCreation, ObjectSettingFailure {
        TaskCreator taskCreator = Mockito.mock(TaskCreator.class);
        Mockito
            .when(taskCreator.create(seed, methodFilterResult))
            .thenReturn(testTask);
        return taskCreator;
    }

    public abstract class TestSetterNamesCreator implements Function<Set<String>, Map<String, String>>{}

    public static class TestTask implements Task {
        private int field;

        public TestTask() {
        }

        public TestTask(int field) {
            this.field = field;
        }

        public void setField(int field) {
            this.field = field;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + field;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            TestTask other = (TestTask) obj;
            if (field != other.field)
                return false;
            return true;
        }        
    }
}
