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

import java.util.Random;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Carter Yagemann <carter.yagemann@gmail.com>
 */
public class IntentDataTest {
    
    private Random RANDOM = new Random(System.currentTimeMillis());
    
    public IntentDataTest() {
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
     * Test of toByteBuffer method, of class IntentData.
     */
    @Test
    public void testToByteBuffer() {
        System.out.println("toByteBuffer");
        IntentData instance = new IntentData();
        byte[] expResult = new byte[224];
        Arrays.fill(expResult, (byte) 0);
        byte[] result = instance.toByteBuffer().array();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of setFlags method, of class IntentData.
     */
    @Test
    public void testSetFlags() {
        System.out.println("setFlags");
        int flags = RANDOM.nextInt();
        IntentData instance = new IntentData().setFlags(flags);
        assertEquals(flags, instance.getFlags());
    }

    /**
     * Test of setAction method, of class IntentData.
     */
    @Test
    public void testSetAction() {
        System.out.println("setAction");
        String action = "android.intent.action.AIRPLANE_MODE";
        IntentData instance = new IntentData().setAction(action);
        assertEquals(action, instance.getAction());
    }

    /**
     * Test of setData method, of class IntentData.
     */
    @Test
    public void testSetData() {
        System.out.println("setData");
        String data = "file:///tmp/android.txt";
        IntentData instance = new IntentData().setData(data);
        assertEquals(data, instance.getData());
    }

    /**
     * Test of setCategory method, of class IntentData.
     */
    @Test
    public void testSetCategory() {
        System.out.println("setCategory");
        String category = "android.intent.category.APP_BROWSER";
        IntentData instance = new IntentData().setCategory(category);
        assertEquals(category, instance.getCategory());
    }

    /**
     * Test of setType method, of class IntentData.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        String type = "text/plain";
        IntentData instance = new IntentData().setType(type);
        assertEquals(type, instance.getType());
    }

    /**
     * Test of setClipData method, of class IntentData.
     */
    @Test
    public void testSetClipData() {
        System.out.println("setClipData");
        byte[] data = new byte[100];
        RANDOM.nextBytes(data);
        IntentData instance = new IntentData().setClipData(data);
        assertEquals(data, instance.getClipData());
    }

    /**
     * Test of setExtras method, of class IntentData.
     */
    @Test
    public void testSetExtras() {
        System.out.println("setExtras");
        byte[] extras = new byte[100];
        RANDOM.nextBytes(extras);
        IntentData instance = new IntentData().setExtras(extras);
        assertEquals(extras, instance.getExtras());
    }

    /**
     * Test of getSize method, of class IntentData.
     */
    @Test
    public void testGetSize() {
        System.out.println("getSize");
        String action = "android.intent.action.AIRPLANE_MODE";
        String data = "file:///tmp/android.txt";
        String category = "android.intent.category.APP_BROWSER";
        String type = "text/plain";
        byte[] clipdata = new byte[100];
        byte[] extras = new byte[100];
        RANDOM.nextBytes(clipdata);
        RANDOM.nextBytes(extras);
        IntentData instance = new IntentData()
                .setFlags(0)
                .setAction(action)
                .setData(data)
                .setCategory(category)
                .setType(type)
                .setClipData(clipdata)
                .setExtras(extras);
        int expResult = 32 * 7 + action.length() + data.length()
                + category.length() + type.length() + clipdata.length
                + extras.length;
        int result = instance.getSize();
        assertEquals(expResult, result);
    }
    
}
