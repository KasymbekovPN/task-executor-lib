package kpn.taskexecutor.lib.task.configurer;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import kpn.taskexecutor.exceptions.AbsentMethodNamesException;
import kpn.taskexecutor.exceptions.FailedTaskCreationException;
import kpn.taskexecutor.exceptions.FailedTaskSettingException;
import kpn.taskexecutor.lib.seed.Seed;
import kpn.taskexecutor.lib.task.Task;
import kpn.taskexecutor.lib.task.configurer.creator.name.DefaultSetterNameCreator;
import kpn.taskexecutor.lib.task.configurer.creator.names.DefaultSetterNamesCreator;
import kpn.taskexecutor.lib.task.configurer.creator.task.DefaultTaskCreator;
import kpn.taskexecutor.lib.task.configurer.creator.task.TaskCreator;
import kpn.taskexecutor.lib.task.configurer.method.extractor.DefaultMethodExtractor;
import kpn.taskexecutor.lib.task.configurer.method.extractor.MethodExtractor;
import kpn.taskexecutor.lib.task.configurer.method.filter.DefaultMethodFilter;
import kpn.taskexecutor.lib.task.configurer.method.filter.MethodFilter;

public class DefaultTaskConfigurer implements TaskConfigurer {

    private final Function<Set<String>, Map<String, String>> setterNamesCreator;
    private final MethodExtractor methodExtractor;
    private final MethodFilter methodFilter;
    private final TaskCreator taskCreator;

    public static Builder builder(){
        return new Builder();
    }

    private DefaultTaskConfigurer(Function<Set<String>, Map<String, String>> setterNamesCreator,
                           MethodExtractor methodExtractor,
                           MethodFilter methodFilter,
                           TaskCreator taskCreator) {
        this.setterNamesCreator = setterNamesCreator;
        this.methodExtractor = methodExtractor;
        this.methodFilter = methodFilter;
        this.taskCreator = taskCreator;
    }

    @Override
    public Task configure(Seed seed) throws AbsentMethodNamesException, FailedTaskCreationException, FailedTaskSettingException {
        Map<String, String> expectedSetterNames = setterNamesCreator.apply(seed.getFields().keySet());
        Map<String, Method> methods = methodExtractor.extract(seed.getType());
        Map<String, Method> setterMethods = methodFilter.filter(methods, expectedSetterNames);
        return taskCreator.create(seed, setterMethods);
    }

    public static class Builder{
        private Function<Set<String>, Map<String, String>> setterNamesCreator;
        private MethodExtractor methodExtractor;
        private MethodFilter methodFilter;
        private TaskCreator taskCreator;

        public Builder setterNamesCreator(Function<Set<String>, Map<String, String>> setterNamesCreator){
            this.setterNamesCreator = setterNamesCreator;
            return this;
        }

        public Builder methodExtractor(MethodExtractor methodExtractor){
            this.methodExtractor = methodExtractor;
            return this;
        }

        public Builder methodFilder(MethodFilter methodFilter){
            this.methodFilter = methodFilter;
            return this;
        }

        public Builder taskCreator(TaskCreator taskCreator){
            this.taskCreator = taskCreator;
            return this;
        }

        public DefaultTaskConfigurer build(){
            checkOrSet();
            return new DefaultTaskConfigurer(setterNamesCreator, methodExtractor, methodFilter, taskCreator);
        }

        private void checkOrSet() {
            checkOrSetSetterNamesCreator();
            checkOrSetMethodExtractor();
            checkOrSetMethodFilter();
            checkOrSetTaskCreator();
        }

        private void checkOrSetSetterNamesCreator() {
            if (setterNamesCreator == null){
                setterNamesCreator = new DefaultSetterNamesCreator(new DefaultSetterNameCreator());
            }
        }

        private void checkOrSetMethodExtractor() {
            if (methodExtractor == null){
                methodExtractor = new DefaultMethodExtractor();
            }
        }

        private void checkOrSetMethodFilter() {
            if (methodFilter == null){
                methodFilter = new DefaultMethodFilter();
            }
        }

        private void checkOrSetTaskCreator() {
            if (taskCreator == null){
                taskCreator = new DefaultTaskCreator();
            }
        }
    }
}
