package kpn.taskexecutor.lib.contexts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;

import kpn.taskexecutor.exceptions.PropertyNotFoundException;

public class DefaultContextTest {
    
    private static final String NON_EXIST_PROPERTY = "non.exist.property";
    private static final String PROPERTY = "proeprty";

    @Test
    public void shouldCheckGettingAsObject_ifPropertyNotExist(){
        Throwable throwable = catchThrowable(() -> {
            new DefaultContext().get(NON_EXIST_PROPERTY);
        });
        assertThat(throwable).isInstanceOf(PropertyNotFoundException.class);
    }

    @Test
    public void shouldCheckGettingAsObject() throws PropertyNotFoundException{
        DefaultContext context = new DefaultContext();
        TestObject expctedObject = new TestObject();
        context.put(PROPERTY, expctedObject);
        
        Object object = context.get(PROPERTY);
        assertThat(expctedObject).isEqualTo(object);
    }

    @Test
    public void shouldCheckGetting_ifPropertyNotExist(){
        Throwable throwable = catchThrowable(() -> {
            new DefaultContext().get(NON_EXIST_PROPERTY, TestObject.class);
        });
        assertThat(throwable).isInstanceOf(PropertyNotFoundException.class);
    }

    @Test
    public void shouldCheckGetting_castIsFail() throws PropertyNotFoundException{
        TestObject expctedObject = new TestObject();
        DefaultContext context = new DefaultContext();
        context.put(PROPERTY, expctedObject);
        
        Throwable throwable = catchThrowable(() -> {
            context.get(PROPERTY, Float.class);
        });
        assertThat(throwable).isInstanceOf(ClassCastException.class);
    }

    @Test
    public void shouldCheckGetting() throws PropertyNotFoundException{
        TestObject expctedObject = new TestObject();
        DefaultContext context = new DefaultContext();
        context.put(PROPERTY, expctedObject);
        
        TestObject object = context.get(PROPERTY, TestObject.class);
        assertThat(expctedObject).isEqualTo(object);
    }

    private static class TestObject{}
}
