package taskExecutorLib.contexts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;

public class SimpleContextTest {
    
    private static final String NON_EXIST_PROPERTY = "non.exist.property";
    private static final String PROPERTY = "proeprty";

    @Test
    public void shouldCheckGettingAsObject_ifPropertyNotExist(){
        Throwable throwable = catchThrowable(() -> {
            new SimpleContext().get(NON_EXIST_PROPERTY);
        });
        assertThat(throwable).isInstanceOf(PropertyNonExist.class);
    }

    @Test
    public void shouldCheckGettingAsObject() throws PropertyNonExist{
        SimpleContext context = new SimpleContext();
        TestObject expctedObject = new TestObject();
        context.put(PROPERTY, expctedObject);
        
        Object object = context.get(PROPERTY);
        assertThat(expctedObject).isEqualTo(object);
    }

    @Test
    public void shouldCheckGetting_ifPropertyNotExist(){
        Throwable throwable = catchThrowable(() -> {
            new SimpleContext().get(NON_EXIST_PROPERTY, TestObject.class);
        });
        assertThat(throwable).isInstanceOf(PropertyNonExist.class);
    }

    @Test
    public void shouldCheckGetting_castIsFail() throws PropertyNonExist{
        TestObject expctedObject = new TestObject();
        SimpleContext context = new SimpleContext();
        context.put(PROPERTY, expctedObject);
        
        Throwable throwable = catchThrowable(() -> {
            context.get(PROPERTY, Float.class);
        });
        assertThat(throwable).isInstanceOf(ClassCastException.class);
    }

    @Test
    public void shouldCheckGetting() throws PropertyNonExist{
        TestObject expctedObject = new TestObject();
        SimpleContext context = new SimpleContext();
        context.put(PROPERTY, expctedObject);
        
        TestObject object = context.get(PROPERTY, TestObject.class);
        assertThat(expctedObject).isEqualTo(object);
    }

    private static class TestObject{}
}
