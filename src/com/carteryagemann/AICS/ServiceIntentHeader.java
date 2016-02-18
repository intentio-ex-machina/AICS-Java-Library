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
 * A header for service intents.
 * 
 * @author Carter Yagemann
 */
public class ServiceIntentHeader extends IntentHeader {
    
    private int FLAGS;
    private int RECEIVER_COMPONENT_SIZE;
    private int CALLER_COMPONENT_SIZE;
    private int ACTION_SIZE;
    private String RECEIVER_COMPONENT;
    private String CALLER_COMPONENT;
    private String ACTION;
    
    /**
     * The default constructor initializes all the fields as null and size 0.
     */
    public ServiceIntentHeader() {
        TIMESTAMP = 0;
        MILLI_OFFSET = 0;
        INTENT_TYPE = TYPE_SERVICE;
        CALLER_UID = 0;
        CALLER_PID = 0;
        RECEIVER_UID = 0;
        RECEIVER_PID = 0;
        USER_ID = 0;
        FLAGS = 0;
        RECEIVER_COMPONENT_SIZE = 0;
        CALLER_COMPONENT_SIZE = 0;
        ACTION_SIZE = 0;
    }
    
    /**
     * Creates a new service intent header by parsing a ByteBuffer. This
     * constructor also parses the intent data since every header should have
     * data attached to it.
     * 
     * @param buffer The buffer to parse.
     * @throws BufferUnderflowException If buffer is too small to be an intent
     * header.
     * @throws ParseException If buffer contains an intent header of the wrong
     * type.
     */
    public ServiceIntentHeader(ByteBuffer buffer)
            throws BufferUnderflowException, ParseException {
        try {
            TIMESTAMP = buffer.getInt();
            MILLI_OFFSET = buffer.getShort();
            INTENT_TYPE = buffer.getShort();
            if (INTENT_TYPE != TYPE_SERVICE)
                throw new ParseException("Not a service intent header.",
                        buffer.position());
            CALLER_UID = buffer.getInt();
            CALLER_PID = buffer.getInt();
            RECEIVER_UID = buffer.getInt();
            RECEIVER_PID = buffer.getInt();
            USER_ID = buffer.getInt();
            FLAGS = buffer.getInt();
            RECEIVER_COMPONENT_SIZE = buffer.getInt();
            CALLER_COMPONENT_SIZE = buffer.getInt();
            ACTION_SIZE = buffer.getInt();
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
            if (ACTION_SIZE > 0) {
                byte[] temp = new byte[ACTION_SIZE];
                buffer.get(temp);
                ACTION = new String(temp);
            }
            INTENT_DATA = new IntentData(buffer);
        } catch (BufferUnderflowException e) {
            throw e;
        }
    }
    
    public ServiceIntentHeader(byte[] array)
            throws BufferUnderflowException, ParseException {
        this(ByteBuffer.wrap(array));
    }
    
    public ServiceIntentHeader setFlags(int flags) {
        FLAGS = flags;
        return this;
    }
    
    public ServiceIntentHeader setReceiverComponent(String component) {
        RECEIVER_COMPONENT = component;
        if (component != null) RECEIVER_COMPONENT_SIZE = component.length();
        else RECEIVER_COMPONENT_SIZE = 0;
        return this;
    }
    
    public ServiceIntentHeader setCallerComponent(String component) {
        CALLER_COMPONENT = component;
        if (component != null) CALLER_COMPONENT_SIZE = component.length();
        else CALLER_COMPONENT_SIZE = 0;
        return this;
    }
    
    public ServiceIntentHeader setAction(String action) {
        ACTION = action;
        if (action != null) ACTION_SIZE = action.length();
        else ACTION_SIZE = 0;
        return this;
    }
    
    public int getFlags() { return FLAGS; }
    
    public String getReceiverComponent() { return RECEIVER_COMPONENT; }
    
    public String getCallerComponent() { return CALLER_COMPONENT; }
    
    public String getAction() { return ACTION; }
    
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
                .putInt(FLAGS)
                .putInt(RECEIVER_COMPONENT_SIZE)
                .putInt(CALLER_COMPONENT_SIZE)
                .putInt(ACTION_SIZE)
                .putInt(INTENT_DATA_SIZE);
        
        // Create variable-sized part of buffer
        if (RECEIVER_COMPONENT_SIZE > 0)
            buffer.put(RECEIVER_COMPONENT.getBytes());
        if (CALLER_COMPONENT_SIZE > 0) buffer.put(CALLER_COMPONENT.getBytes());
        if (ACTION_SIZE > 0) buffer.put(ACTION.getBytes());
        
        buffer.rewind();
        return buffer;
    }
    
    @Override
    public int getSize() {
        return 4 * 12 + RECEIVER_COMPONENT_SIZE + CALLER_COMPONENT_SIZE
                + ACTION_SIZE;
    }
    
}
