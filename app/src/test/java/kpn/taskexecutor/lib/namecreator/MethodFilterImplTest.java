package kpn.taskexecutor.lib.namecreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.jupiter.api.Test;

import kpn.taskexecutor.exceptions.creators.ObjectAndSeedMismatching;

public class MethodFilterImplTest {
    
    @Test
    public void shouldCkeclFiltration_ifThereIsMismatching() throws NoSuchMethodException, SecurityException{
        Map<String, String> expectedNames = Map.of(
            "field0", "setField0",
            "field2", "setField2"
        );
        Class<?> type = TestClass.class;
        Map<String, Method> methods = Map.of(
            "setField0", type.getMethod("setField0", int.class),
            "setField1", type.getMethod("setField1", int.class)
        );

        MethodFilterImpl filter = new MethodFilterImpl();
        Throwable throwable = catchThrowable(() -> {
            filter.filter(methods, expectedNames);
        });
        
        assertThat(throwable).isInstanceOf(ObjectAndSeedMismatching.class);
    }

    @Test
    public void shouldCkeclFiltration() throws NoSuchMethodException, SecurityException, ObjectAndSeedMismatching{
        Map<String, String> expectedNames = Map.of(
            "field0", "setField0"
        );
        Class<?> type = TestClass.class;
        Method m0 = type.getMethod("setField0", int.class);
        Map<String, Method> methods = Map.of(
            "setField0", m0,
            "setField1", type.getMethod("setField1", int.class)
        );
        Map<String, Method> expectedMethods = Map.of(
            "field0", m0
        );

        MethodFilterImpl filter = new MethodFilterImpl();
        Map<String, Method> filterMethods = filter.filter(methods, expectedNames);

        assertThat(expectedMethods).isEqualTo(filterMethods);
    }

    private static class TestClass{
        public void setField0(int x){}
        public void setField1(int x){}
    }
}
