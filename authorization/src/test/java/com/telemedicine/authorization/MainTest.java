package com.telemedicine.authorization;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void mainTestCase() {
        try {
            Main.main(new String[]{});
        } catch (Exception e) {
            fail("The main method should not throw any exceptions");
        }
    }
}