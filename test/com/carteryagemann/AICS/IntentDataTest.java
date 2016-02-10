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

import java.util.Arrays;
import java.util.Random;
import javax.xml.bind.DatatypeConverter;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * A series of tests for IntentData.
 * 
 * @author Carter Yagemann <carter.yagemann@gmail.com>
 */
public class IntentDataTest {
    
    private final Random RANDOM = new Random(System.currentTimeMillis());

    /**
     * Test IntentData's ability to parse an array of bytes
     */
    @Test
    public void testInitialization() {
        // Create a flattened IntentData
        String action = "android.intent.action.AIRPLANE_MODE";
        String data = "file:///tmp/android.txt";
        String category = "android.intent.category.APP_BROWSER";
        String type = "text/plain";
        byte[] clipdata = new byte[300];
        byte[] extras = new byte[300];
        RANDOM.nextBytes(clipdata);
        RANDOM.nextBytes(extras);
        byte[] array = new IntentData()
                .setFlags(1)
                .setAction(action)
                .setData(data)
                .setCategory(category)
                .setType(type)
                .setClipData(clipdata)
                .setExtras(extras)
                .toByteBuffer()
                .array();
        
        // Initialize a new IntentData from the flattened array
        IntentData result = new IntentData(array);
        
        // Make sure everything is the same
        assertEquals(array.length, result.getSize());
        assertEquals(1, result.getFlags());
        assertEquals(action, result.getAction());
        assertEquals(data, result.getData());
        assertEquals(category, result.getCategory());
        assertEquals(type, result.getType());
        assertArrayEquals(clipdata, result.getClipData());
        assertArrayEquals(extras, result.getExtras());
    }
    
    /**
     * Test of toByteBuffer method, of class IntentData.
     */
    @Test
    public void testToByteBuffer() {
        // Blank IntentData test
        IntentData instance = new IntentData();
        byte[] expResult = new byte[224];
        Arrays.fill(expResult, (byte) 0);
        byte[] result = instance.toByteBuffer().array();
        assertArrayEquals(expResult, result);
        
        // Full IntentData test
        String action = "android.intent.action.AIRPLANE_MODE";
        String data = "file:///tmp/android.txt";
        String category = "android.intent.category.APP_BROWSER";
        String type = "text/plain";
        byte[] clipdata = new byte[300];
        byte[] extras = new byte[300];
        RANDOM.nextBytes(clipdata);
        RANDOM.nextBytes(extras);
        byte[] array = new IntentData()
                .setFlags(1)
                .setAction(action)
                .setData(data)
                .setCategory(category)
                .setType(type)
                .setClipData(clipdata)
                .setExtras(extras)
                .toByteBuffer()
                .array();
        
        String flags = DatatypeConverter
                .printHexBinary(Arrays.copyOfRange(array, 0, 4));
        String actionSize = DatatypeConverter
                .printHexBinary(Arrays.copyOfRange(array, 4, 8));
        String dataSize = DatatypeConverter
                .printHexBinary(Arrays.copyOfRange(array, 8, 12));
        String categorySize = DatatypeConverter
                .printHexBinary(Arrays.copyOfRange(array, 12, 16));
        String typeSize = DatatypeConverter
                .printHexBinary(Arrays.copyOfRange(array, 16, 20));
        String clipdataSize = DatatypeConverter
                .printHexBinary(Arrays.copyOfRange(array, 20, 24));
        String extrasSize = DatatypeConverter
                .printHexBinary(Arrays.copyOfRange(array, 24, 28));
        
        System.out.println("TEST INTENT_DATA FIXED PORTION");
        System.out.println("*-----------------FLAGS------------------*");
        System.out.println("|                " + flags + "                |");
        System.out.println("*---------------ACTION_SIZE--------------*");
        System.out.println("|                " + actionSize
                + "                |");
        System.out.println("*---------------DATA_SIZE----------------*");
        System.out.println("|                " + dataSize
                + "                |");
        System.out.println("*-------------CATEGORY_SIZE--------------*");
        System.out.println("|                " + categorySize
                + "                |");
        System.out.println("*---------------TYPE_SIZE----------------*");
        System.out.println("|                " + typeSize
                + "                |");
        System.out.println("*-------------CLIPDATA_SIZE--------------*");
        System.out.println("|                " + clipdataSize
                + "                |");
        System.out.println("*--------------EXTRAS_SIZE---------------*");
        System.out.println("|                " + extrasSize
                + "                |");
        System.out.println("*----------------------------------------*\n");
        
        // Test fixed portion of instance
        assertTrue(flags.equals("00000001"));
        assertTrue(actionSize.equals("00000023"));
        assertTrue(dataSize.equals("00000017"));
        assertTrue(categorySize.equals("00000023"));
        assertTrue(typeSize.equals("0000000A"));
        assertTrue(clipdataSize.equals("0000012C"));
        assertTrue(extrasSize.equals("0000012C"));
        
        // Test variable portion of instance
        int index = 28;
        String rAction = new String(Arrays.copyOfRange(array, index,
                index += action.length()));
        System.out.println(action + " vs " + rAction);
        assertTrue(rAction.equals(action));
        
        String rData = new String(Arrays.copyOfRange(array, index,
                index += data.length()));
        System.out.println(data + " vs " + rData);
        assertTrue(rData.equals(data));
        
        String rCategory = new String(Arrays.copyOfRange(array, index,
                index += category.length()));
        System.out.println(category + " vs " + rCategory);
        assertTrue(rCategory.equals(category));
        
        String rType = new String(Arrays.copyOfRange(array, index,
                index += type.length()));
        System.out.println(type + " vs " + rType);
        assertTrue(rType.equals(type));
        
        assertTrue(Arrays.equals(Arrays.copyOfRange(array, index,
                index += clipdata.length), clipdata));
        assertTrue(Arrays.equals(Arrays.copyOfRange(array, index,
                index += extras.length), extras));
    }

    /**
     * Test of setFlags method, of class IntentData.
     */
    @Test
    public void testSetFlags() {
        int flags = RANDOM.nextInt();
        IntentData instance = new IntentData().setFlags(flags);
        assertEquals(flags, instance.getFlags());
    }

    /**
     * Test of setAction method, of class IntentData.
     */
    @Test
    public void testSetAction() {
        String action = "android.intent.action.AIRPLANE_MODE";
        IntentData instance = new IntentData().setAction(action);
        assertEquals(action, instance.getAction());
    }

    /**
     * Test of setData method, of class IntentData.
     */
    @Test
    public void testSetData() {
        String data = "file:///tmp/android.txt";
        IntentData instance = new IntentData().setData(data);
        assertEquals(data, instance.getData());
    }

    /**
     * Test of setCategory method, of class IntentData.
     */
    @Test
    public void testSetCategory() {
        String category = "android.intent.category.APP_BROWSER";
        IntentData instance = new IntentData().setCategory(category);
        assertEquals(category, instance.getCategory());
    }

    /**
     * Test of setType method, of class IntentData.
     */
    @Test
    public void testSetType() {
        String type = "text/plain";
        IntentData instance = new IntentData().setType(type);
        assertEquals(type, instance.getType());
    }

    /**
     * Test of setClipData method, of class IntentData.
     */
    @Test
    public void testSetClipData() {
        byte[] data = new byte[100];
        RANDOM.nextBytes(data);
        IntentData instance = new IntentData().setClipData(data);
        assertArrayEquals(data, instance.getClipData());
    }

    /**
     * Test of setExtras method, of class IntentData.
     */
    @Test
    public void testSetExtras() {
        byte[] extras = new byte[100];
        RANDOM.nextBytes(extras);
        IntentData instance = new IntentData().setExtras(extras);
        assertArrayEquals(extras, instance.getExtras());
    }

    /**
     * Test of getSize method, of class IntentData.
     */
    @Test
    public void testGetSize() {
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
