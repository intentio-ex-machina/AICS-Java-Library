/*
 * Copyright 2016 Carter Yagemann <carter.yagemann@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.carteryagemann.AICS;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Carter Yagemann <carter.yagemann@gmail.com>
 */
public class AICSFileTest {
    
    public AICSFileTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of readFromFile method, of class AICSFile.
     */
    @Test
    public void testReadFromFile() {
        System.out.println("readFromFile");
        String filepath = "";
        AICSFile expResult = null;
        AICSFile result = AICSFile.readFromFile(filepath);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of appendIntent method, of class AICSFile.
     */
    @Test
    public void testAppendIntent() {
        System.out.println("appendIntent");
        IntentPacket packet = null;
        AICSFile instance = null;
        instance.appendIntent(packet);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntent method, of class AICSFile.
     */
    @Test
    public void testGetIntent() {
        System.out.println("getIntent");
        int index = 0;
        AICSFile instance = null;
        IntentPacket expResult = null;
        IntentPacket result = instance.getIntent(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clearIntents method, of class AICSFile.
     */
    @Test
    public void testClearIntents() {
        System.out.println("clearIntents");
        AICSFile instance = null;
        instance.clearIntents();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class AICSFile.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        AICSFile instance = null;
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeToFile method, of class AICSFile.
     */
    @Test
    public void testWriteToFile() {
        System.out.println("writeToFile");
        String filepath = "";
        AICSFile instance = null;
        instance.writeToFile(filepath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
