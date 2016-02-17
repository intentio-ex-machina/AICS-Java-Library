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
public class BroadcastIntentHeaderTest {
    
    /**
     * Test of setTimestamp method, of class IntentHeader.
     */
    @Test
    public void testSetTimestamp() {
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader().setTimestamp(50);
        assertEquals(50, head.getTimestamp());
    }

    /**
     * Test of setOffset method, of class IntentHeader.
     */
    @Test
    public void testSetOffset() {
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader().setOffset((short)50);
        assertEquals(50, head.getOffset());
    }
    
    /**
     * Make sure the exceptions for setOffset work.
     */
    @Test
    public void testSetOffsetExceptions() {
        BroadcastIntentHeader head = new BroadcastIntentHeader();
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
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader().setCallerUID(1);
        assertEquals(1, head.getCallerUID());
    }

    /**
     * Test of setCallerPID method, of class IntentHeader.
     */
    @Test
    public void testSetCallerPID() {
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader().setCallerPID(1);
        assertEquals(1, head.getCallerPID());
    }

    /**
     * Test of setReceiverUID method, of class IntentHeader.
     */
    @Test
    public void testSetReceiverUID() {
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader().setReceiverUID(1);
        assertEquals(1, head.getReceiverUID());
    }

    /**
     * Test of setReceiverPID method, of class IntentHeader.
     */
    @Test
    public void testSetReceiverPID() {
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader().setReceiverPID(1);
        assertEquals(1, head.getReceiverPID());
    }

    /**
     * Test of setUserID method, of class IntentHeader.
     */
    @Test
    public void testSetUserID() {
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader().setUserID(1);
        assertEquals(1, head.getUserID());
    }

    /**
     * Test of setIntentData method, of class IntentHeader.
     */
    @Test
    public void testSetIntentData() {
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader().setIntentData(new IntentData()
                        .setAction("test"));
        assertTrue(head.getIntentData().getAction().equals("test"));
    }

    /**
     * Test of getIntentType method, of class IntentHeader.
     */
    @Test
    public void testGetIntentType() {
        BroadcastIntentHeader head = new BroadcastIntentHeader();
        assertEquals(IntentHeader.TYPE_BROADCAST, head.getIntentType());
    }

    /**
     * Test of setRequestCode method, of class BroadcastIntentHeader.
     */
    @Test
    public void testSetRequestCode() {
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader().setRequestCode(5);
        assertEquals(head.getRequestCode(), 5);
    }

    /**
     * Test of setFlags method, of class BroadcastIntentHeader.
     */
    @Test
    public void testSetFlags() {
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader().setFlags(5);
        assertEquals(head.getFlags(), 5);
    }

    /**
     * Test of setReceiverComponent method, of class BroadcastIntentHeader.
     */
    @Test
    public void testSetReceiverComponent() {
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader().setReceiverComponent("test");
        assertTrue(head.getReceiverComponent().equals("test"));
    }

    /**
     * Test of setCallerComponent method, of class BroadcastIntentHeader.
     */
    @Test
    public void testSetCallerComponent() {
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader().setCallerComponent("test");
        assertTrue(head.getCallerComponent().equals("test"));
    }

    /**
     * Test of setRequiredPermission method, of class BroadcastIntentHeader.
     */
    @Test
    public void testSetRequiredPermission() {
        String permission = "android.permission.INTERNET";
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader().setRequiredPermission(permission);
        assertTrue(head.getRequiredPermission().equals(permission));
    }

    /**
     * Test of toByteBuffer method, of class BroadcastIntentHeader.
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
        int flags = 3;
        String receiverComponent = "test.receiver";
        String callerComponent = "test.caller";
        String permission = "android.permission.INTERNET";
        IntentData data = new IntentData();
        
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader()
                .setRequestCode(requestCode)
                .setFlags(flags)
                .setReceiverComponent(receiverComponent)
                .setCallerComponent(callerComponent)
                .setRequiredPermission(permission)
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
        assertEquals(IntentHeader.TYPE_BROADCAST, output.getShort());
        assertEquals(callerUID, output.getInt());
        assertEquals(callerPID, output.getInt());
        assertEquals(receiverUID, output.getInt());
        assertEquals(receiverPID, output.getInt());
        assertEquals(userID, output.getInt());
        assertEquals(requestCode, output.getInt());
        assertEquals(flags, output.getInt());
        assertEquals(receiverComponent.length(), output.getInt());
        assertEquals(callerComponent.length(), output.getInt());
        assertEquals(permission.length(), output.getInt());
        assertEquals(data.getSize(), output.getInt());
        
        // Tests for variable portion
        byte[] temp = new byte[receiverComponent.length()];
        output.get(temp);
        assertTrue(receiverComponent.equals(new String(temp)));
        
        temp = new byte[callerComponent.length()];
        output.get(temp);
        assertTrue(callerComponent.equals(new String(temp)));
        
        temp = new byte[permission.length()];
        output.get(temp);
        assertTrue(permission.equals(new String(temp)));
    }

    /**
     * Test of getSize method, of class BroadcastIntentHeader.
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
        int flags = 3;
        String receiverComponent = "test.receiver";
        String callerComponent = "test.caller";
        String permission = "android.permission.INTERNET";
        IntentData data = new IntentData();
        
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader()
                .setRequestCode(requestCode)
                .setFlags(flags)
                .setReceiverComponent(receiverComponent)
                .setCallerComponent(callerComponent)
                .setRequiredPermission(permission)
                .setTimestamp(timestamp)
                .setOffset(offset)
                .setCallerUID(callerUID)
                .setCallerPID(callerPID)
                .setReceiverUID(receiverUID)
                .setReceiverPID(receiverPID)
                .setUserID(userID)
                .setIntentData(data);
        
        assertEquals(103, head.getSize());
    }
    
    /**
     * Test the headers parsing constructor.
     */
    @Test
    public void testBroadcastIntentHeaderParser() {
        int timestamp = 22;
        short offset = 5;
        int callerUID = 100;
        int callerPID = 101;
        int receiverUID = 102;
        int receiverPID = 103;
        int userID = 1;
        int requestCode = 5000;
        int flags = 3;
        String receiverComponent = "test.receiver";
        String callerComponent = "test.caller";
        String permission = "android.permission.INTERNET";
        IntentData data = new IntentData();
        
        BroadcastIntentHeader head = (BroadcastIntentHeader)
                new BroadcastIntentHeader()
                .setRequestCode(requestCode)
                .setFlags(flags)
                .setReceiverComponent(receiverComponent)
                .setCallerComponent(callerComponent)
                .setRequiredPermission(permission)
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
        
        BroadcastIntentHeader newHead = new BroadcastIntentHeader();
        try {
            newHead = new BroadcastIntentHeader(output);
        } catch (BufferUnderflowException | ParseException e) {
            fail(e.toString());
        }
        
        // Tests for fixed portion
        assertEquals(head.getTimestamp(), newHead.getTimestamp());
        assertEquals(head.getOffset(), newHead.getOffset());
        assertEquals(IntentHeader.TYPE_BROADCAST, newHead.getIntentType());
        assertEquals(head.getCallerUID(), newHead.getCallerUID());
        assertEquals(head.getCallerPID(), newHead.getCallerPID());
        assertEquals(head.getReceiverUID(), newHead.getReceiverUID());
        assertEquals(head.getReceiverPID(), newHead.getReceiverPID());
        assertEquals(head.getUserID(), newHead.getUserID());
        assertEquals(head.getRequestCode(), newHead.getRequestCode());
        assertEquals(head.getFlags(), newHead.getFlags());
        assertTrue(head.getReceiverComponent()
                .equals(newHead.getReceiverComponent()));
        assertTrue(head.getCallerComponent()
                .equals(newHead.getCallerComponent()));
        assertTrue(head.getRequiredPermission()
                .equals(newHead.getRequiredPermission()));
        assertArrayEquals(head.getIntentData().toByteBuffer().array(),
                newHead.getIntentData().toByteBuffer().array());
    }
    
}
