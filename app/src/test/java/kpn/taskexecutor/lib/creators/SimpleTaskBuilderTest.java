package kpn.taskexecutor.lib.creators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.List;

import org.junit.jupiter.api.Test;

import kpn.taskexecutor.exceptions.creators.FailureOnTaskCreation;
import kpn.taskexecutor.exceptions.creators.ObjectAndSeedMismatching;
import kpn.taskexecutor.exceptions.creators.ObjectSettingFailure;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.seeds.SeedImpl;
import kpn.taskexecutor.lib.tasks.Task;

public class SimpleTaskBuilderTest {
    private static final int INT_VALUE = 123;
    private static final String STR_VALUE = "some.string";
    private static final List<String> FLOAT_LIST_VALUE = List.of("1", "2", "3");
    private static final int X_VALUE = 456;
    private static final int Y_VALUE = 789;
    private static final String NO_EXIST_FIELD = "noExistField";
    private static final String NO_EXIST_FIELD_VALUE = "no.exist.field.value";

    @Test
    public void shouldCheckTaskCreation_whenThereIsMismatching(){
        Seed seed = SeedImpl.builder()
            .type(TestTask.class)
            .field(NO_EXIST_FIELD, NO_EXIST_FIELD_VALUE)
            .build();

        Throwable throwable = catchThrowable(() -> {
            TaskBuilder creator = new SimpleTaskBuilder();
            creator.build(seed);
        });
        assertThat(throwable).isInstanceOf(ObjectAndSeedMismatching.class);
    }

    @Test
    public void shouldCheckTaskCreation_whenTaskConstructionFail(){
        Seed seed = SeedImpl.builder()
            .type(TestTaskWithoutNoArgsCont.class)
            .build();

        Throwable throwable = catchThrowable(() -> {
            TaskBuilder creator = new SimpleTaskBuilder();
            creator.build(seed);
        });
        assertThat(throwable).isInstanceOf(FailureOnTaskCreation.class);
    }

    @Test
    public void shouldCheckTaskCreation_whenSettingFail(){
        Seed seed = SeedImpl.builder()
            .type(TestTask.class)
            .field("floatListValue", "str")
            .build();
        
        Throwable throwable = catchThrowable(() -> {
            new SimpleTaskBuilder().build(seed);
        });
        assertThat(throwable).isInstanceOfAny(ObjectSettingFailure.class);
    }

    @Test
    public void shouldCheckTaskCreation() throws ObjectAndSeedMismatching, FailureOnTaskCreation, ObjectSettingFailure{
        Seed seed = SeedImpl.builder()
            .type(TestTask.class)
            .field("intValue", INT_VALUE)
            .field("strValue", STR_VALUE)
            .field("floatListValue", FLOAT_LIST_VALUE)
            .field("objectValue", new ValueClass(X_VALUE, Y_VALUE))
            .build();

        TaskBuilder creator = new SimpleTaskBuilder();
        Task task = creator.build(seed);

        assertThat(task.getClass()).isEqualTo(TestTask.class);
        TestTask testTask = (TestTask) task;
        assertThat(testTask.getIntValue()).isEqualTo(INT_VALUE);
        assertThat(testTask.getStrValue()).isEqualTo(STR_VALUE);
        assertThat(testTask.getFloatListValue()).isEqualTo(FLOAT_LIST_VALUE);
        assertThat(testTask.getObjectValue()).isEqualTo(new ValueClass(X_VALUE, Y_VALUE));
    }

    public static class TestTaskWithoutNoArgsCont implements Task{
        private final int x;

        public TestTaskWithoutNoArgsCont(int x) {
            this.x = x;
        }
    }

    public static class TestTask implements Task{
        private Integer intValue;
        private String strValue;
        private List<Float> floatListValue;
        private ValueClass objectValue;

        public void setIntValue(Integer intValue) {
            this.intValue = intValue;
        }

        public Integer getIntValue() {
            return intValue;
        }

        public void setStrValue(String strValue) {
            this.strValue = strValue;
        }

        public String getStrValue() {
            return strValue;
        }

        public void setFloatListValue(List<Float> floatListValue) {
            this.floatListValue = floatListValue;
        }

        public List<Float> getFloatListValue() {
            return floatListValue;
        }

        public void setObjectValue(ValueClass objectValue) {
            this.objectValue = objectValue;
        }

        public ValueClass getObjectValue() {
            return objectValue;
        }
    }

    private static class BaseClass{
        protected final int x;

        public BaseClass(int x) {
            this.x = x;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (!(obj instanceof BaseClass)) return false;
            final BaseClass other = (BaseClass) obj;
            if (!other.canEqual((Object) this)) return false;
            if (this.x != other.x) return false;
            return true;
        }

        @Override
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            result = result * PRIME + this.x;
			return result;
        }

        private boolean canEqual(final Object other){
            return other instanceof BaseClass;
        }
    }

    private static class ValueClass extends BaseClass{
        private final int y;

        public ValueClass(int x, int y) {
            super(x);
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
			if (obj == this) return true;
			if (!(obj instanceof ValueClass)) return false;
			final ValueClass other = (ValueClass) obj;
			if (!other.canEqual((Object) this)) return false;
			if (!super.equals(obj)) return false;
			if (this.y != other.y) return false;
			return true;
        }

        @Override
        public int hashCode() {
			final int PRIME = 59;
			int result = super.hashCode();
			result = result * PRIME + this.y;
			return result;
        }

        private boolean canEqual(final Object other){
            return other instanceof ValueClass;
        }
    }
}