package com.redhat.ceylon.model.test;

import org.junit.Assert;
import org.junit.Test;

import com.redhat.ceylon.model.loader.OsgiVersion;

public class OsgiVersionTests {

    @Test
    public void testVersionConversions() {
        Assert.assertEquals("0.0.0.5", OsgiVersion.fromCeylonVersion(""));
        Assert.assertEquals("1.0.0.3-n002-5", OsgiVersion.fromCeylonVersion("1-rc2"));
        Assert.assertEquals("1.0.0.5", OsgiVersion.fromCeylonVersion("1"));
        Assert.assertEquals("1.2.0.2", OsgiVersion.fromCeylonVersion("1.2.m"));
        Assert.assertEquals("1.2.0.5", OsgiVersion.fromCeylonVersion("1.2"));
        Assert.assertEquals("1.2.0.6-abc", OsgiVersion.fromCeylonVersion("1.2.abc"));
        Assert.assertEquals("1.2.0.6-abc-n003-n004-5", OsgiVersion.fromCeylonVersion("1.2.abc.3.4"));
        Assert.assertEquals("1.2.0.5-n003-n004-5", OsgiVersion.fromCeylonVersion("1.2.ga.3.4"));
        Assert.assertEquals("1.2.3.n004-5", OsgiVersion.fromCeylonVersion("1.2.3.4"));
        Assert.assertEquals("2.0.0.5", OsgiVersion.fromCeylonVersion("2"));
        Assert.assertEquals("2.0.0.5", OsgiVersion.fromCeylonVersion("2-ga"));
        Assert.assertEquals("2.0.0.5", OsgiVersion.fromCeylonVersion("2-final"));
        Assert.assertEquals("3.0.0.3-n002-5", OsgiVersion.fromCeylonVersion("3cr2"));
        Assert.assertEquals("3.0.0.3-n002-5", OsgiVersion.fromCeylonVersion("3.rc-2"));
    }

    @Test
    public void testVersionOrdering() {
        Assert.assertTrue(OsgiVersion.fromCeylonVersion("").compareTo(OsgiVersion.fromCeylonVersion("1-rc2")) < 0);
        Assert.assertTrue(OsgiVersion.fromCeylonVersion("1-rc2").compareTo(OsgiVersion.fromCeylonVersion("1")) < 0);
        Assert.assertTrue(OsgiVersion.fromCeylonVersion("1").compareTo(OsgiVersion.fromCeylonVersion("1.2.m")) < 0);
        Assert.assertTrue(OsgiVersion.fromCeylonVersion("1.2.m").compareTo(OsgiVersion.fromCeylonVersion("1.2")) < 0);
        Assert.assertTrue(OsgiVersion.fromCeylonVersion("1.2").compareTo(OsgiVersion.fromCeylonVersion("1.2.abc")) < 0);
        Assert.assertTrue(OsgiVersion.fromCeylonVersion("1.2.abc").compareTo(OsgiVersion.fromCeylonVersion("1.2.abc.3.4")) < 0);
        Assert.assertTrue(OsgiVersion.fromCeylonVersion("1.2.ga.3.4").compareTo(OsgiVersion.fromCeylonVersion("1.2.abc.3.4")) < 0);
        Assert.assertTrue(OsgiVersion.fromCeylonVersion("1.2.abc.3.4").compareTo(OsgiVersion.fromCeylonVersion("1.2.3.4")) < 0);
    }
}
