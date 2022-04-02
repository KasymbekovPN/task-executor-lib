package kpn.taskexecutor.lib.namecreator;

import java.util.function.UnaryOperator;

public class CamelSetterNameCreator implements UnaryOperator<String> {

    @Override
    public String apply(String t) {
        String newBegin = "set" + String.valueOf(t.charAt(0)).toUpperCase();
        return  newBegin + t.substring(1);
    }
}
