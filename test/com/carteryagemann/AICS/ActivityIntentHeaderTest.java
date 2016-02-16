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

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.text.ParseException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Carter Yagemann <carter.yagemann@gmail.com>
 */
public class ActivityIntentHeaderTest {
    
    /**
     * Test of setTimestamp method, of class IntentHeader.
     */
    @Test
    public void testSetTimestamp() {
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader().setTimestamp(50);
        assertEquals(50, head.getTimestamp());
    }

    /**
     * Test of setOffset method, of class IntentHeader.
     */
    @Test
    public void testSetOffset() {
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader().setOffset((short)50);
        assertEquals(50, head.getOffset());
    }
    
    /**
     * Make sure the exceptions for setOffset work
     */
    @Test
    public void testSetOffsetExceptions() {
        ActivityIntentHeader head = new ActivityIntentHeader();
        try {
            head.setOffset((short)-1);
            fail("setOffset(-1) didn't trigger an exception");
        } catch (IllegalArgumentException e1) {
            try {
                head.setOffset((short)1001);
                fail("setOffset(1001) didn't trigger an exception");
            } catch (IllegalArgumentException e2) {
                // Succesfully triggered both exceptions
            }
        }
    }

    /**
     * Test of setCallerUID method, of class IntentHeader.
     */
    @Test
    public void testSetCallerUID() {
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader().setCallerUID(1);
        assertEquals(1, head.getCallerUID());
    }

    /**
     * Test of setCallerPID method, of class IntentHeader.
     */
    @Test
    public void testSetCallerPID() {
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader().setCallerPID(1);
        assertEquals(1, head.getCallerPID());
    }

    /**
     * Test of setReceiverUID method, of class IntentHeader.
     */
    @Test
    public void testSetReceiverUID() {
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader().setReceiverUID(1);
        assertEquals(1, head.getReceiverUID());
    }

    /**
     * Test of setReceiverPID method, of class IntentHeader.
     */
    @Test
    public void testSetReceiverPID() {
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader().setReceiverPID(1);
        assertEquals(1, head.getReceiverPID());
    }

    /**
     * Test of setUserID method, of class IntentHeader.
     */
    @Test
    public void testSetUserID() {
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader().setUserID(1);
        assertEquals(1, head.getUserID());
    }

    /**
     * Test of setIntentData method, of class IntentHeader.
     */
    @Test
    public void testSetIntentData() {
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader().setIntentData(new IntentData()
                        .setAction("test"));
        assertTrue(head.getIntentData().getAction().equals("test"));
    }

    /**
     * Test of getIntentType method, of class IntentHeader.
     */
    @Test
    public void testGetIntentType() {
        ActivityIntentHeader head = new ActivityIntentHeader();
        assertEquals(IntentHeader.TYPE_ACTIVITY, head.getIntentType());
    }

    /**
     * Test of setRequestCode method, of class ActivityIntentHeader.
     */
    @Test
    public void testSetRequestCode() {
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader().setRequestCode(5);
        assertEquals(head.getRequestCode(), 5);
    }

    /**
     * Test of setStartFlags method, of class ActivityIntentHeader.
     */
    @Test
    public void testSetStartFlags() {
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader().setStartFlags(5);
        assertEquals(head.getStartFlags(), 5);
    }

    /**
     * Test of setReceiverComponent method, of class ActivityIntentHeader.
     */
    @Test
    public void testSetReceiverComponent() {
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader().setReceiverComponent("test");
        assertTrue(head.getReceiverComponent().equals("test"));
    }

    /**
     * Test of setCallerComponent method, of class ActivityIntentHeader.
     */
    @Test
    public void testSetCallerComponent() {
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader().setCallerComponent("test");
        assertTrue(head.getCallerComponent().equals("test"));
    }

    /**
     * Test of setOptions method, of class ActivityIntentHeader.
     */
    @Test
    public void testSetOptions() {
        byte[] options = {0x01, 0x02, 0x03, 0x04};
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader().setOptions(options);
        assertArrayEquals(head.getOptions(), options);
    }

    /**
     * Test of toByteBuffer method, of class ActivityIntentHeader.
     */
    @Test
    public void testToByteBuffer() {
        int timestamp = 22;
        short offset = 5;
        int callerUID = 100;
        int callerPID = 101;
        int receiverUID = 102;
        int receiverPID = 103;
        int userID = 1;
        int requestCode = 5000;
        int startFlags = 3;
        String receiverComponent = "test.receiver";
        String callerComponent = "test.caller";
        byte[] options = {0x01, 0x02, 0x03};
        IntentData data = new IntentData();
        
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader()
                .setRequestCode(requestCode)
                .setStartFlags(startFlags)
                .setReceiverComponent(receiverComponent)
                .setCallerComponent(callerComponent)
                .setOptions(options)
                .setTimestamp(timestamp)
                .setOffset(offset)
                .setCallerUID(callerUID)
                .setCallerPID(callerPID)
                .setReceiverUID(receiverUID)
                .setReceiverPID(receiverPID)
                .setUserID(userID)
                .setIntentData(data);
        
        ByteBuffer output = head.toByteBuffer();
        
        // Tests for fixed portion
        assertEquals(timestamp, output.getInt());
        assertEquals(offset, output.getShort());
        assertEquals(IntentHeader.TYPE_ACTIVITY, output.getShort());
        assertEquals(callerUID, output.getInt());
        assertEquals(callerPID, output.getInt());
        assertEquals(receiverUID, output.getInt());
        assertEquals(receiverPID, output.getInt());
        assertEquals(userID, output.getInt());
        assertEquals(requestCode, output.getInt());
        assertEquals(startFlags, output.getInt());
        assertEquals(receiverComponent.length(), output.getInt());
        assertEquals(callerComponent.length(), output.getInt());
        assertEquals(options.length, output.getInt());
        assertEquals(data.getSize(), output.getInt());
        
        // Tests for variable portion
        byte[] temp = new byte[receiverComponent.length()];
        output.get(temp);
        assertTrue(receiverComponent.equals(new String(temp)));
        
        temp = new byte[callerComponent.length()];
        output.get(temp);
        assertTrue(callerComponent.equals(new String(temp)));
        
        temp = new byte[options.length];
        output.get(temp);
        assertArrayEquals(options, temp);
    }

    /**
     * Test of getSize method, of class ActivityIntentHeader.
     */
    @Test
    public void testGetSize() {
        int timestamp = 22;
        short offset = 5;
        int callerUID = 100;
        int callerPID = 101;
        int receiverUID = 102;
        int receiverPID = 103;
        int userID = 1;
        int requestCode = 5000;
        int startFlags = 3;
        String receiverComponent = "test.receiver";
        String callerComponent = "test.caller";
        byte[] options = {0x01, 0x02, 0x03};
        IntentData data = new IntentData();
        
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader()
                .setRequestCode(requestCode)
                .setStartFlags(startFlags)
                .setReceiverComponent(receiverComponent)
                .setCallerComponent(callerComponent)
                .setOptions(options)
                .setTimestamp(timestamp)
                .setOffset(offset)
                .setCallerUID(callerUID)
                .setCallerPID(callerPID)
                .setReceiverUID(receiverUID)
                .setReceiverPID(receiverPID)
                .setUserID(userID)
                .setIntentData(data);
        
        assertEquals(79, head.getSize());
    }
    
    /**
     * Test the headers parsing constructor.
     */
    @Test
    public void testActivityIntentHeaderParser() {
        int timestamp = 22;
        short offset = 5;
        int callerUID = 100;
        int callerPID = 101;
        int receiverUID = 102;
        int receiverPID = 103;
        int userID = 1;
        int requestCode = 5000;
        int startFlags = 3;
        String receiverComponent = "test.receiver";
        String callerComponent = "test.caller";
        byte[] options = {0x01, 0x02, 0x03};
        IntentData data = new IntentData();
        
        ActivityIntentHeader head = (ActivityIntentHeader)
                new ActivityIntentHeader()
                .setRequestCode(requestCode)
                .setStartFlags(startFlags)
                .setReceiverComponent(receiverComponent)
                .setCallerComponent(callerComponent)
                .setOptions(options)
                .setTimestamp(timestamp)
                .setOffset(offset)
                .setCallerUID(callerUID)
                .setCallerPID(callerPID)
                .setReceiverUID(receiverUID)
                .setReceiverPID(receiverPID)
                .setUserID(userID)
                .setIntentData(data);
        
        byte[] outputHead = head.toByteBuffer().array();
        byte[] outputData = head.getIntentData().toByteBuffer().array();
        byte[] output = new byte[outputHead.length + outputData.length];
        System.arraycopy(outputHead, 0, output, 0, outputHead.length);
        System.arraycopy(outputData, 0, output, outputHead.length, outputData.length);
        
        ActivityIntentHeader newHead = new ActivityIntentHeader();
        try {
            newHead = new ActivityIntentHeader(output);
        } catch (BufferUnderflowException | ParseException e) {
            fail(e.toString());
        }
        
        // Tests for fixed portion
        assertEquals(head.getTimestamp(), newHead.getTimestamp());
        assertEquals(head.getOffset(), newHead.getOffset());
        assertEquals(IntentHeader.TYPE_ACTIVITY, newHead.getIntentType());
        assertEquals(head.getCallerUID(), newHead.getCallerUID());
        assertEquals(head.getCallerPID(), newHead.getCallerPID());
        assertEquals(head.getReceiverUID(), newHead.getReceiverUID());
        assertEquals(head.getReceiverPID(), newHead.getReceiverPID());
        assertEquals(head.getUserID(), newHead.getUserID());
        assertEquals(head.getRequestCode(), newHead.getRequestCode());
        assertEquals(head.getStartFlags(), newHead.getStartFlags());
        assertTrue(head.getReceiverComponent()
                .equals(newHead.getReceiverComponent()));
        assertTrue(head.getCallerComponent()
                .equals(newHead.getCallerComponent()));
        assertArrayEquals(head.getOptions(), newHead.getOptions());
        assertArrayEquals(head.getIntentData().toByteBuffer().array(),
                newHead.getIntentData().toByteBuffer().array());
    }
}
