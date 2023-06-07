package com.bravo.johny.utils;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;

public class CustomDisplayNameGenerator {

    public static class ReplaceCamelCase extends DisplayNameGenerator.Standard {

        @Override
        public String generateDisplayNameForClass(Class<?> testClass) {
            return this.replaceCapitals(super.generateDisplayNameForClass(testClass));
        }

        @Override
        public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
            return this.replaceCapitals(super.generateDisplayNameForNestedClass(nestedClass));
        }

        @Override
        public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
            return this.replaceCapitals(super.generateDisplayNameForMethod(testClass, testMethod));
        }

        private String replaceCapitals(String name) {

            name = name.replaceAll("([A-Z])", " $1");
            name = name.replaceAll("([0-9]+)", " $1");
            return name;
        }
    }
}
