package kpn.taskexecutor.lib.namecreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.jupiter.api.Test;

import kpn.taskexecutor.exceptions.creators.FailureOnTaskCreation;
import kpn.taskexecutor.exceptions.creators.ObjectSettingFailure;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.seeds.SeedImpl;
import kpn.taskexecutor.lib.tasks.Task;

public class TaskCreatorImplTest {
    
    @Test
    public void shouldCheckCreation_ifTaskCreationFail(){
        Seed seed = SeedImpl.builder()
            .type(TaskWithArgsConstructor.class)
            .build();
        TaskCreatorImpl creator = new TaskCreatorImpl();

        Throwable throwable = catchThrowable(() -> {
            creator.create(seed, Map.of());
        });
        assertThat(throwable).isInstanceOf(FailureOnTaskCreation.class);
    }

    private static class TaskWithArgsConstructor implements Task{
        private final int x;
        public TaskWithArgsConstructor(int x) {
            this.x = x;
        }
    }

    @Test
    public void shouldCheckCreation_ifSettingFail() throws NoSuchMethodException, SecurityException{
        Seed seed = SeedImpl.builder()
            .type(TestTask.class)
            .field("field", "value")
            .build();
        Map<String, Method> methods = Map.of(
            "field", TestTask.class.getMethod("setField", int.class)
        );

        TaskCreatorImpl creator = new TaskCreatorImpl();

        Throwable throwable = catchThrowable(() -> {
            creator.create(seed, methods);
        });
        assertThat(throwable).isInstanceOf(ObjectSettingFailure.class);
    }

    @Test
    public void shouldCheckCreation() throws NoSuchMethodException, SecurityException, FailureOnTaskCreation, ObjectSettingFailure{
        int expectedField = 123;
        Seed seed = SeedImpl.builder()
            .type(TestTask.class)
            .field("field", expectedField)
            .build();
        Map<String, Method> methods = Map.of(
            "field", TestTask.class.getMethod("setField", int.class)
        );

        TaskCreatorImpl creator = new TaskCreatorImpl();
        Task task = creator.create(seed, methods);

        assertThat(new TestTask(expectedField)).isEqualTo(task);
    }

    private static class TestTask implements Task{
        private int field;

        public TestTask(int field) {
            this.field = field;
        }

        public TestTask() {
        }

        public void setField(int field){
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
