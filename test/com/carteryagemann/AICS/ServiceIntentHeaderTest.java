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
public class ServiceIntentHeaderTest {
    
    /**
     * Test of setTimestamp method, of class IntentHeader.
     */
    @Test
    public void testSetTimestamp() {
        ServiceIntentHeader head = (ServiceIntentHeader)
                new ServiceIntentHeader().setTimestamp(50);
        assertEquals(50, head.getTimestamp());
    }

    /**
     * Test of setOffset method, of class IntentHeader.
     */
    @Test
    public void testSetOffset() {
        ServiceIntentHeader head = (ServiceIntentHeader)
                new ServiceIntentHeader().setOffset((short)50);
        assertEquals(50, head.getOffset());
    }
    
    /**
     * Make sure the exceptions for setOffset work
     */
    @Test
    public void testSetOffsetExceptions() {
        ServiceIntentHeader head = new ServiceIntentHeader();
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
        ServiceIntentHeader head = (ServiceIntentHeader)
                new ServiceIntentHeader().setCallerUID(1);
        assertEquals(1, head.getCallerUID());
    }

    /**
     * Test of setCallerPID method, of class IntentHeader.
     */
    @Test
    public void testSetCallerPID() {
        ServiceIntentHeader head = (ServiceIntentHeader)
                new ServiceIntentHeader().setCallerPID(1);
        assertEquals(1, head.getCallerPID());
    }

    /**
     * Test of setReceiverUID method, of class IntentHeader.
     */
    @Test
    public void testSetReceiverUID() {
        ServiceIntentHeader head = (ServiceIntentHeader)
                new ServiceIntentHeader().setReceiverUID(1);
        assertEquals(1, head.getReceiverUID());
    }

    /**
     * Test of setReceiverPID method, of class IntentHeader.
     */
    @Test
    public void testSetReceiverPID() {
        ServiceIntentHeader head = (ServiceIntentHeader)
                new ServiceIntentHeader().setReceiverPID(1);
        assertEquals(1, head.getReceiverPID());
    }

    /**
     * Test of setUserID method, of class IntentHeader.
     */
    @Test
    public void testSetUserID() {
        ServiceIntentHeader head = (ServiceIntentHeader)
                new ServiceIntentHeader().setUserID(1);
        assertEquals(1, head.getUserID());
    }

    /**
     * Test of setIntentData method, of class IntentHeader.
     */
    @Test
    public void testSetIntentData() {
        ServiceIntentHeader head = (ServiceIntentHeader)
                new ServiceIntentHeader().setIntentData(new IntentData()
                        .setAction("test"));
        assertTrue(head.getIntentData().getAction().equals("test"));
    }

    /**
     * Test of getIntentType method, of class IntentHeader.
     */
    @Test
    public void testGetIntentType() {
        ServiceIntentHeader head = new ServiceIntentHeader();
        assertEquals(IntentHeader.TYPE_SERVICE, head.getIntentType());
    }

    /**
     * Test of setFlags method, of class ServiceIntentHeader.
     */
    @Test
    public void testSetStartFlags() {
        ServiceIntentHeader head = (ServiceIntentHeader)
                new ServiceIntentHeader().setFlags(5);
        assertEquals(head.getFlags(), 5);
    }

    /**
     * Test of setReceiverComponent method, of class ServiceIntentHeader.
     */
    @Test
    public void testSetReceiverComponent() {
        ServiceIntentHeader head = (ServiceIntentHeader)
                new ServiceIntentHeader().setReceiverComponent("test");
        assertTrue(head.getReceiverComponent().equals("test"));
    }

    /**
     * Test of setCallerComponent method, of class ServiceIntentHeader.
     */
    @Test
    public void testSetCallerComponent() {
        ServiceIntentHeader head = (ServiceIntentHeader)
                new ServiceIntentHeader().setCallerComponent("test");
        assertTrue(head.getCallerComponent().equals("test"));
    }

    /**
     * Test of setAction method, of class ServiceIntentHeader.
     */
    @Test
    public void testSetOptions() {
        String action = "bind";
        ServiceIntentHeader head = (ServiceIntentHeader)
                new ServiceIntentHeader().setAction(action);
        assertTrue(head.getAction().equals(action));
    }

    /**
     * Test of toByteBuffer method, of class ServiceIntentHeader.
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
        int flags = 3;
        String receiverComponent = "test.receiver";
        String callerComponent = "test.caller";
        String action = "start";
        IntentData data = new IntentData();
        
        ServiceIntentHeader head = (ServiceIntentHeader)
                new ServiceIntentHeader()
                .setFlags(flags)
                .setReceiverComponent(receiverComponent)
                .setCallerComponent(callerComponent)
                .setAction(action)
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
        assertEquals(IntentHeader.TYPE_SERVICE, output.getShort());
        assertEquals(callerUID, output.getInt());
        assertEquals(callerPID, output.getInt());
        assertEquals(receiverUID, output.getInt());
        assertEquals(receiverPID, output.getInt());
        assertEquals(userID, output.getInt());
        assertEquals(flags, output.getInt());
        assertEquals(receiverComponent.length(), output.getInt());
        assertEquals(callerComponent.length(), output.getInt());
        assertEquals(action.length(), output.getInt());
        assertEquals(data.getSize(), output.getInt());
        
        // Tests for variable portion
        byte[] temp = new byte[receiverComponent.length()];
        output.get(temp);
        assertTrue(receiverComponent.equals(new String(temp)));
        
        temp = new byte[callerComponent.length()];
        output.get(temp);
        assertTrue(callerComponent.equals(new String(temp)));
        
        temp = new byte[action.length()];
        output.get(temp);
        assertTrue(action.equals(new String(temp)));
    }

    /**
     * Test of getSize method, of class ServiceIntentHeader.
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
        int flags = 3;
        String receiverComponent = "test.receiver";
        String callerComponent = "test.caller";
        String action = "stop";
        IntentData data = new IntentData();
        
        ServiceIntentHeader head = (ServiceIntentHeader)
                new ServiceIntentHeader()
                .setFlags(flags)
                .setReceiverComponent(receiverComponent)
                .setCallerComponent(callerComponent)
                .setAction(action)
                .setTimestamp(timestamp)
                .setOffset(offset)
                .setCallerUID(callerUID)
                .setCallerPID(callerPID)
                .setReceiverUID(receiverUID)
                .setReceiverPID(receiverPID)
                .setUserID(userID)
                .setIntentData(data);
        
        assertEquals(76, head.getSize());
    }
    
    /**
     * Test the headers parsing constructor.
     */
    @Test
    public void testServiceIntentHeaderParser() {
        int timestamp = 22;
        short offset = 5;
        int callerUID = 100;
        int callerPID = 101;
        int receiverUID = 102;
        int receiverPID = 103;
        int userID = 1;
        int flags = 3;
        String receiverComponent = "test.receiver";
        String callerComponent = "test.caller";
        String action = "bind";
        IntentData data = new IntentData();
        
        ServiceIntentHeader head = (ServiceIntentHeader)
                new ServiceIntentHeader()
                .setFlags(flags)
                .setReceiverComponent(receiverComponent)
                .setCallerComponent(callerComponent)
                .setAction(action)
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
        
        ServiceIntentHeader newHead = new ServiceIntentHeader();
        try {
            newHead = new ServiceIntentHeader(output);
        } catch (BufferUnderflowException | ParseException e) {
            fail(e.toString());
        }
        
        // Tests for fixed portion
        assertEquals(head.getTimestamp(), newHead.getTimestamp());
        assertEquals(head.getOffset(), newHead.getOffset());
        assertEquals(IntentHeader.TYPE_SERVICE, newHead.getIntentType());
        assertEquals(head.getCallerUID(), newHead.getCallerUID());
        assertEquals(head.getCallerPID(), newHead.getCallerPID());
        assertEquals(head.getReceiverUID(), newHead.getReceiverUID());
        assertEquals(head.getReceiverPID(), newHead.getReceiverPID());
        assertEquals(head.getUserID(), newHead.getUserID());
        assertEquals(head.getFlags(), newHead.getFlags());
        assertTrue(head.getReceiverComponent()
                .equals(newHead.getReceiverComponent()));
        assertTrue(head.getCallerComponent()
                .equals(newHead.getCallerComponent()));
        assertTrue(head.getAction().equals(newHead.getAction()));
        assertArrayEquals(head.getIntentData().toByteBuffer().array(),
                newHead.getIntentData().toByteBuffer().array());
    }
    
}
