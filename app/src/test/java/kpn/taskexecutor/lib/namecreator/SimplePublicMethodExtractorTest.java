package kpn.taskexecutor.lib.namecreator;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class SimplePublicMethodExtractorTest {
    
    @Test
    public void shouldCheckExtraction_simpleClass() throws NoSuchMethodException, SecurityException{
        Class<?> type = SimpleTestClass.class;
        Map<String, Method> expectedResult = Map.of(
            "publicMethod", type.getMethod("publicMethod")
        );

        SimplePublicMethodExtractor extractor = new SimplePublicMethodExtractor();
        Map<String, Method> result = extractor.extract(type);

        assertThat(expectedResult).isEqualTo(result);
    }

    private static class SimpleTestClass{
        public void publicMethod(){}
        protected void protectedMethod(){}
        private void privateMethod(){}
    }

    @Test
    public void shouldCheckExtraction_childClass() throws NoSuchMethodException, SecurityException{
        Class<?> type = ChildTestClass.class;
        Map<String, Method> expectedResult = Map.of(
            "childPublicMethod", type.getMethod("childPublicMethod")
        );

        SimplePublicMethodExtractor extractor = new SimplePublicMethodExtractor();
        Map<String, Method> result = extractor.extract(type);

        assertThat(expectedResult).isEqualTo(result);
    }

    private static class ChildTestClass extends BaseTestClass{
        public void childPublicMethod(){}
        protected void childProtectedMethod(){}
        private void childPrivateMethod(){}
    }

    private static class BaseTestClass {
        public void basePublicMethod(){}
        protected void baseProtectedMethod(){}
        private void basePrivateMethod(){}
    }
}
