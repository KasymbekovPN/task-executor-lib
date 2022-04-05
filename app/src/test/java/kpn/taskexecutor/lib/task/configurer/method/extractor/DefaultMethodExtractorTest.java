package kpn.taskexecutor.lib.task.configurer.method.extractor;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class DefaultMethodExtractorTest {

    @Test
    public void shouldCheckExtraction() throws NoSuchMethodException, SecurityException{
        Class<?> type = C.class;
        Map<String, Method> expectedResult = Map.of(
            "publicA", type.getMethod("publicA"),
            "publicB", type.getMethod("publicB"),
            "publicC", type.getMethod("publicC")
        );

        DefaultMethodExtractor extractor = new DefaultMethodExtractor();
        Map<String, Method> result = extractor.extract(type);

        assertThat(expectedResult).isEqualTo(result);
    }

    private static class C extends B{
        public void publicC(){}
        protected void protectedC(){}
        private void privateC(){}
    }

    private static class B extends A{
        public void publicB(){}
        protected void protectedB(){}
        private void privateB(){}
    }

    private static class A {
        public void publicA(){}
        protected void protectedA(){}
        private void privateA(){}
    }
}
