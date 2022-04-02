package kpn.taskexecutor.lib.namecreator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CamelSetterNameCreatorTest {

    @Test
    public void shouldCheckCreation(){
        String field = "someField";
        String expectedSetterName = "setSomeField";
        
        CamelSetterNameCreator creator = new CamelSetterNameCreator();
        String setterName = creator.apply(field);

        assertThat(expectedSetterName).isEqualTo(setterName);
    }
}
