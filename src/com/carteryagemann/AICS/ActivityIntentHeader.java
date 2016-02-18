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

/**
 * A header for activity intents.
 * 
 * @author Carter Yagemann
 */
public class ActivityIntentHeader extends IntentHeader {

    private int REQUEST_CODE;
    private int START_FLAGS;
    private int RECEIVER_COMPONENT_SIZE;
    private int CALLER_COMPONENT_SIZE;
    private int OPTIONS_SIZE;
    private String RECEIVER_COMPONENT;
    private String CALLER_COMPONENT;
    private byte[] OPTIONS;
    
    /**
     * The default constructor initializes all the fields as null and size 0.
     */
    public ActivityIntentHeader() {
        TIMESTAMP = 0;
        MILLI_OFFSET = 0;
        INTENT_TYPE = TYPE_ACTIVITY;
        CALLER_UID = 0;
        CALLER_PID = 0;
        RECEIVER_UID = 0;
        RECEIVER_PID = 0;
        USER_ID = 0;
        REQUEST_CODE = 0;
        START_FLAGS = 0;
        RECEIVER_COMPONENT_SIZE = 0;
        CALLER_COMPONENT_SIZE = 0;
        OPTIONS_SIZE = 0;
    }
    
    /**
     * Creates a new activity intent header by parsing a ByteBuffer. This
     * constructor also parses the intent data since every header should have
     * data attached to it.
     * 
     * @param buffer The buffer to parse.
     * @throws BufferUnderflowException If buffer is too small to be an intent
     * header.
     * @throws ParseException If buffer contains an intent header of the wrong
     * type.
     */
    public ActivityIntentHeader(ByteBuffer buffer)
            throws BufferUnderflowException, ParseException {
        try {
            TIMESTAMP = buffer.getInt();
            MILLI_OFFSET = buffer.getShort();
            INTENT_TYPE = buffer.getShort();
            if (INTENT_TYPE != TYPE_ACTIVITY)
                throw new ParseException("Not an activity intent header.",
                        buffer.position());
            CALLER_UID = buffer.getInt();
            CALLER_PID = buffer.getInt();
            RECEIVER_UID = buffer.getInt();
            RECEIVER_PID = buffer.getInt();
            USER_ID = buffer.getInt();
            REQUEST_CODE = buffer.getInt();
            START_FLAGS = buffer.getInt();
            RECEIVER_COMPONENT_SIZE = buffer.getInt();
            CALLER_COMPONENT_SIZE = buffer.getInt();
            OPTIONS_SIZE = buffer.getInt();
            INTENT_DATA_SIZE = buffer.getInt();
            if (INTENT_DATA_SIZE < 0)
                throw new ParseException("Header has no intent data!",
                buffer.position());
            if (RECEIVER_COMPONENT_SIZE > 0) {
                byte[] temp = new byte[RECEIVER_COMPONENT_SIZE];
                buffer.get(temp);
                RECEIVER_COMPONENT = new String(temp);
            }
            if (CALLER_COMPONENT_SIZE > 0) {
                byte[] temp = new byte[CALLER_COMPONENT_SIZE];
                buffer.get(temp);
                CALLER_COMPONENT = new String(temp);
            }
            if (OPTIONS_SIZE > 0) {
                OPTIONS = new byte[OPTIONS_SIZE];
                buffer.get(OPTIONS);
            }
            INTENT_DATA = new IntentData(buffer);
        } catch (BufferUnderflowException e) {
            throw e;
        }
    }
    
    public ActivityIntentHeader(byte[] array)
            throws BufferUnderflowException, ParseException {
        this(ByteBuffer.wrap(array));
    }
    
    public ActivityIntentHeader setRequestCode(int request) {
        REQUEST_CODE = request;
        return this;
    }
    
    public ActivityIntentHeader setStartFlags(int flags) {
        START_FLAGS = flags;
        return this;
    }
    
    public ActivityIntentHeader setReceiverComponent(String component) {
        RECEIVER_COMPONENT = component;
        if (component != null) RECEIVER_COMPONENT_SIZE = component.length();
        else RECEIVER_COMPONENT_SIZE = 0;
        return this;
    }
    
    public ActivityIntentHeader setCallerComponent(String component) {
        CALLER_COMPONENT = component;
        if (component != null) CALLER_COMPONENT_SIZE = component.length();
        else CALLER_COMPONENT_SIZE = 0;
        return this;
    }
    
    public ActivityIntentHeader setOptions(byte[] options) {
        OPTIONS = options;
        OPTIONS_SIZE = options.length;
        return this;
    }
    
    public int getRequestCode() { return REQUEST_CODE; }
    
    public int getStartFlags() { return START_FLAGS; }
    
    public String getReceiverComponent() { return RECEIVER_COMPONENT; }
    
    public String getCallerComponent() { return CALLER_COMPONENT; }
    
    public byte[] getOptions() { return OPTIONS; }
    
    @Override
    protected ByteBuffer toByteBuffer() {
        
        // Create fix-sized part of buffer
        ByteBuffer buffer = ByteBuffer.allocate(getSize())
                .putInt(TIMESTAMP)
                .putShort(MILLI_OFFSET)
                .putShort(INTENT_TYPE)
                .putInt(CALLER_UID)
                .putInt(CALLER_PID)
                .putInt(RECEIVER_UID)
                .putInt(RECEIVER_PID)
                .putInt(USER_ID)
                .putInt(REQUEST_CODE)
                .putInt(START_FLAGS)
                .putInt(RECEIVER_COMPONENT_SIZE)
                .putInt(CALLER_COMPONENT_SIZE)
                .putInt(OPTIONS_SIZE)
                .putInt(INTENT_DATA_SIZE);
        
        // Create variable-sized part of buffer
        if (RECEIVER_COMPONENT_SIZE > 0)
            buffer.put(RECEIVER_COMPONENT.getBytes());
        if (CALLER_COMPONENT_SIZE > 0) buffer.put(CALLER_COMPONENT.getBytes());
        if (OPTIONS_SIZE > 0) buffer.put(OPTIONS);
        
        buffer.rewind();
        return buffer;
    }
    
    @Override
    public int getSize() {
        return 4 * 13 + RECEIVER_COMPONENT_SIZE + CALLER_COMPONENT_SIZE
                + OPTIONS_SIZE;
    }
    
}
