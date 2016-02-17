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
public class IntentHeaderTest {

    /**
     * Test of parseIntentType method, of class IntentHeader.
     */
    @Test
    public void testParseIntentType_ByteBuffer() {
        ActivityIntentHeader head = new ActivityIntentHeader();
        short type = -1;
        try {
            type = IntentHeader.parseIntentType(head.toByteBuffer());
        } catch (ParseException | BufferUnderflowException e) {
            fail(e.toString());
        }
        assertEquals(type, IntentHeader.TYPE_ACTIVITY);
        
        ServiceIntentHeader serviceHead = new ServiceIntentHeader();
        type = -1;
        try {
            type = IntentHeader.parseIntentType(serviceHead.toByteBuffer());
        } catch (ParseException | BufferUnderflowException e) {
            fail(e.toString());
        }
        assertEquals(type, IntentHeader.TYPE_SERVICE);
    }
    
    /**
     * Test of parseIntentType method to make sure it isn't messing up the
     * ByteBuffer's pointer position.
     */
    @Test
    public void testParsePointer() {
        ByteBuffer buffer = new ActivityIntentHeader().toByteBuffer();
        short type = -1;
        try {
            type = IntentHeader.parseIntentType(buffer);
        } catch (ParseException | BufferUnderflowException e) {
            fail(e.toString());
        }
        assertEquals(type, IntentHeader.TYPE_ACTIVITY);
        try {
            type = IntentHeader.parseIntentType(buffer);
        } catch (ParseException | BufferUnderflowException e) {
            fail(e.toString());
        }
        assertEquals(type, IntentHeader.TYPE_ACTIVITY);
    }

    /**
     * Test of parseIntentType method, of class IntentHeader.
     */
    @Test
    public void testParseIntentType_byteArr() {
        ActivityIntentHeader head = new ActivityIntentHeader();
        short type = -1;
        try {
            type = IntentHeader.parseIntentType(head.toByteBuffer().array());
        } catch (ParseException | BufferUnderflowException e) {
            fail(e.toString());
        }
        assertEquals(type, IntentHeader.TYPE_ACTIVITY);
        
        ServiceIntentHeader serviceHead = new ServiceIntentHeader();
        type = -1;
        try {
            type = IntentHeader.parseIntentType(serviceHead
                    .toByteBuffer().array());
        } catch (ParseException | BufferUnderflowException e) {
            fail(e.toString());
        }
        assertEquals(type, IntentHeader.TYPE_SERVICE);
    }
}
