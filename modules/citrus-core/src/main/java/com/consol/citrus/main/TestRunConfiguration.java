/*
 * Copyright 2006-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.main;

import com.consol.citrus.TestClass;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christoph Deppisch
 * @since 2.7.4
 */
public class TestRunConfiguration {

    /** Test to execute at runtime */
    private List<TestClass> testClasses = new ArrayList<>();

    /** Package to execute at runtime */
    private List<String> packages = new ArrayList<>();

    /** Include tests based on these test name pattern */
    private String[] includes = new String[] { "^.*IT$", "^.*ITCase$", "^IT.*$" };

    /**
     * Gets the testClasses.
     *
     * @return
     */
    public List<TestClass> getTestClasses() {
        return testClasses;
    }

    /**
     * Sets the testClasses.
     *
     * @param testClasses
     */
    public void setTestClasses(List<TestClass> testClasses) {
        this.testClasses = testClasses;
    }

    /**
     * Gets the packages.
     *
     * @return
     */
    public List<String> getPackages() {
        return packages;
    }

    /**
     * Sets the packages.
     *
     * @param packages
     */
    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    /**
     * Gets the includes.
     *
     * @return
     */
    public String[] getIncludes() {
        return includes;
    }

    /**
     * Sets the includes.
     *
     * @param includes
     */
    public void setIncludes(String[] includes) {
        this.includes = includes;
    }
}
