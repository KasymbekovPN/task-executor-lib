package kpn.taskexecutor.lib.task.configurer.creator.name;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DefaultSetterNameCreatorTest {

    @Test
    public void shouldCheckCreation(){
        String field = "someField";
        String expectedSetterName = "setSomeField";
        
        DefaultSetterNameCreator creator = new DefaultSetterNameCreator();
        String setterName = creator.apply(field);

        assertThat(expectedSetterName).isEqualTo(setterName);
    }
}
