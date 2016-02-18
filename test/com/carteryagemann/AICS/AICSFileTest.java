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

import static org.junit.Assert.*;
import org.junit.Test;
import java.nio.ByteBuffer;
import java.nio.BufferUnderflowException;
import java.text.ParseException;

/**
 *
 * @author Carter Yagemann <carter.yagemann@gmail.com>
 */
public class AICSFileTest {

    /**
     * Test that a file can be flattened into a ByteBuffer and restored.
     */
    @Test
    public void testReadAndWrite() {
        AICSFile file = new AICSFile((short) 5, (byte) 1, (byte) 1);
        
        ActivityIntentHeader h1 = (ActivityIntentHeader) new ActivityIntentHeader()
                .setCallerComponent("component.caller")
                .setReceiverComponent("component.receiver")
                .setIntentData(new IntentData().setAction("action.one"));
        
        ServiceIntentHeader h2 = (ServiceIntentHeader) new ServiceIntentHeader()
                .setCallerComponent("component.caller")
                .setReceiverComponent("component.receiver")
                .setIntentData(new IntentData().setAction("action.two"));
        
        ServiceIntentHeader h3 = (ServiceIntentHeader) new ServiceIntentHeader()
                .setCallerComponent("component.caller")
                .setReceiverComponent("component.receiver")
                .setIntentData(new IntentData().setAction("action.three"));
        
        BroadcastIntentHeader h4 = (BroadcastIntentHeader) new BroadcastIntentHeader()
                .setCallerComponent("component.caller")
                .setReceiverComponent("component.receiver")
                .setIntentData(new IntentData().setAction("action.four"));
        
        ActivityIntentHeader h5 = (ActivityIntentHeader) new ActivityIntentHeader()
                .setCallerComponent("component.caller")
                .setReceiverComponent("component.receiver")
                .setIntentData(new IntentData().setAction("action.five"));
        
        ActivityIntentHeader h6 = (ActivityIntentHeader) new ActivityIntentHeader()
                .setCallerComponent("component.caller")
                .setReceiverComponent("component.receiver")
                .setIntentData(new IntentData().setAction("action.six"));
        
        file.appendIntent(h1)
                .appendIntent(h2)
                .appendIntent(h3)
                .appendIntent(h4)
                .appendIntent(h5)
                .appendIntent(h6);
        
        ByteBuffer buffer = file.toByteBuffer();
        assertEquals(buffer.position(), 0);
        
        try {
            AICSFile file2 = AICSFile.readFromBuffer(buffer);
            for (int i = 0; i < file.size(); i++) {
                // Check intent data action
                assertTrue(file.getIntent(i).getIntentData().getAction()
                        .equals(file2.getIntent(i).getIntentData().getAction()));
                // Check caller and receiver components
                switch(file.getIntent(i).getIntentType()) {
                    case IntentHeader.TYPE_ACTIVITY:
                        ActivityIntentHeader heada1 = (ActivityIntentHeader) file.getIntent(i);
                        ActivityIntentHeader heada2 = (ActivityIntentHeader) file2.getIntent(i);
                        assertTrue(heada1.getCallerComponent().equals(heada2.getCallerComponent()));
                        assertTrue(heada1.getReceiverComponent().equals(heada2.getReceiverComponent()));
                        break;
                    case IntentHeader.TYPE_BROADCAST:
                        BroadcastIntentHeader headb1 = (BroadcastIntentHeader) file.getIntent(i);
                        BroadcastIntentHeader headb2 = (BroadcastIntentHeader) file2.getIntent(i);
                        assertTrue(headb1.getCallerComponent().equals(headb2.getCallerComponent()));
                        assertTrue(headb1.getReceiverComponent().equals(headb2.getReceiverComponent()));
                        break;
                    case IntentHeader.TYPE_SERVICE:
                        ServiceIntentHeader heads1 = (ServiceIntentHeader) file.getIntent(i);
                        ServiceIntentHeader heads2 = (ServiceIntentHeader) file2.getIntent(i);
                        assertTrue(heads1.getCallerComponent().equals(heads2.getCallerComponent()));
                        assertTrue(heads1.getReceiverComponent().equals(heads2.getReceiverComponent()));
                        break;
                }
            }
            
            ByteBuffer buffer2 = file2.toByteBuffer();
            assertArrayEquals(buffer.array(), buffer2.array());
        } catch (ParseException | BufferUnderflowException e) {
            System.err.println(e.toString());
            fail("Failed to parse buffer into file.");
        }
        
        try {
            AICSFile file3 = AICSFile.readFromArray(buffer.array());
            for (int i = 0; i < file.size(); i++) {
                // Check intent data action
                assertTrue(file.getIntent(i).getIntentData().getAction()
                        .equals(file3.getIntent(i).getIntentData().getAction()));
                // Check caller and receiver components
                switch(file.getIntent(i).getIntentType()) {
                    case IntentHeader.TYPE_ACTIVITY:
                        ActivityIntentHeader heada1 = (ActivityIntentHeader) file.getIntent(i);
                        ActivityIntentHeader heada2 = (ActivityIntentHeader) file3.getIntent(i);
                        assertTrue(heada1.getCallerComponent().equals(heada2.getCallerComponent()));
                        assertTrue(heada1.getReceiverComponent().equals(heada2.getReceiverComponent()));
                        break;
                    case IntentHeader.TYPE_BROADCAST:
                        BroadcastIntentHeader headb1 = (BroadcastIntentHeader) file.getIntent(i);
                        BroadcastIntentHeader headb2 = (BroadcastIntentHeader) file3.getIntent(i);
                        assertTrue(headb1.getCallerComponent().equals(headb2.getCallerComponent()));
                        assertTrue(headb1.getReceiverComponent().equals(headb2.getReceiverComponent()));
                        break;
                    case IntentHeader.TYPE_SERVICE:
                        ServiceIntentHeader heads1 = (ServiceIntentHeader) file.getIntent(i);
                        ServiceIntentHeader heads2 = (ServiceIntentHeader) file3.getIntent(i);
                        assertTrue(heads1.getCallerComponent().equals(heads2.getCallerComponent()));
                        assertTrue(heads1.getReceiverComponent().equals(heads2.getReceiverComponent()));
                        break;
                }
            }
            
            ByteBuffer buffer3 = file3.toByteBuffer();
            assertArrayEquals(buffer.array(), buffer3.array());
        } catch (ParseException | BufferUnderflowException e) {
            System.err.println(e.toString());
            fail("Failed to parse buffer into file.");
        }
    }

    /**
     * Test of size method, of class AICSFile.
     */
    @Test
    public void testSize() {
        AICSFile file = new AICSFile((short) 5, (byte) 1, (byte) 1);
        IntentData id1 = new IntentData();
        ActivityIntentHeader h1 = (ActivityIntentHeader)
                new ActivityIntentHeader().setIntentData(id1);
        IntentData id2 = new IntentData();
        ServiceIntentHeader h2 = (ServiceIntentHeader)
                new ServiceIntentHeader().setIntentData(id2);
        file.appendIntent(h1);
        file.appendIntent(h2);
        
        assertEquals(file.size(), 2);
    }
    
}
