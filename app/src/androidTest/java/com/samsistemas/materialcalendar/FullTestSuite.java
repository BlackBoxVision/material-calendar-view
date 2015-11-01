package com.samsistemas.materialcalendar;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author jonatan.salas
 */
public class FullTestSuite extends TestSuite {

    public static Test suite() {
        return new TestSuiteBuilder(FullTestSuite.class)
                .includeAllPackagesUnderHere()
                .build();
    }

    public FullTestSuite() { super(); }
}