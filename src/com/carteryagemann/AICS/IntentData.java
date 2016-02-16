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

/**
 * IntentData is an object for storing the contents of the logged Android
 * intent. It should be combined with an IntentHeader to create an IntentPacket
 * which can then be stored inside an AICSFile.
 * 
 * @author Carter Yagemann <carter.yagemann@gmail.com>
 */
public class IntentData {
    
    private int FLAGS;
    private int ACTION_SIZE;
    private int DATA_SIZE;
    private int CATEGORY_SIZE;
    private int TYPE_SIZE;
    private int CLIPDATA_SIZE;
    private int EXTRAS_SIZE;
    private String ACTION;
    private String DATA;
    private String CATEGORY;
    private String TYPE;
    private byte[] CLIPDATA;
    private byte[] EXTRAS;
    
    /**
     * The default constructor initializes all the fields as null and size 0.
     */
    IntentData() {
        FLAGS = 0;
        ACTION_SIZE = 0;
        DATA_SIZE = 0;
        CATEGORY_SIZE = 0;
        TYPE_SIZE = 0;
        CLIPDATA_SIZE = 0;
        EXTRAS_SIZE = 0;
    }
    
    /**
     * Creates a new IntentData object from a ByteBuffer.
     * 
     * @param buffer The ByteBuffer containing a flattened IntentData object.
     * @throws BufferUnderflowException If the buffer is malformed.
     */
    IntentData(ByteBuffer buffer) throws BufferUnderflowException {
        try {
            FLAGS = buffer.getInt();
            ACTION_SIZE = buffer.getInt();
            DATA_SIZE = buffer.getInt();
            CATEGORY_SIZE = buffer.getInt();
            TYPE_SIZE = buffer.getInt();
            CLIPDATA_SIZE = buffer.getInt();
            EXTRAS_SIZE = buffer.getInt();
            
            if (ACTION_SIZE > 0) {
                byte[] temp = new byte[ACTION_SIZE];
                buffer.get(temp);
                ACTION = new String(temp);
            }
            
            if (DATA_SIZE > 0) {
                byte[] temp = new byte[DATA_SIZE];
                buffer.get(temp);
                DATA = new String(temp);
            }
            
            if (CATEGORY_SIZE > 0) {
                byte[] temp = new byte[CATEGORY_SIZE];
                buffer.get(temp);
                CATEGORY = new String(temp);
            }
            
            if (TYPE_SIZE > 0) {
                byte[] temp = new byte[TYPE_SIZE];
                buffer.get(temp);
                TYPE = new String(temp);
            }
            
            if (CLIPDATA_SIZE > 0) {
                CLIPDATA = new byte[CLIPDATA_SIZE];
                buffer.get(CLIPDATA);
            }
            
            if (EXTRAS_SIZE > 0) {
                EXTRAS = new byte[EXTRAS_SIZE];
                buffer.get(EXTRAS);
            }
        } catch (BufferUnderflowException e) {
            throw e;
        }
    }
    
    /**
     * Creates a new IntentData object from an array of bytes.
     * 
     * @param array The array to parse into an IntentData object.
     * @throws BufferUnderflowException If the array is malformed.
     */
    IntentData(byte[] array) throws BufferUnderflowException {
        this(ByteBuffer.wrap(array));
    }
    
    public IntentData setFlags(int flags) {
        FLAGS = flags;
        return this;
    }
    
    public IntentData setAction(String action) {
        ACTION = action;
        if (action != null) ACTION_SIZE = action.length();
        else ACTION_SIZE = 0;
        return this;
    }
    
    public IntentData setData(String data) {
        DATA = data;
        if (data != null) DATA_SIZE = data.length();
        else DATA_SIZE = 0;
        return this;
    }
    
    public IntentData setCategory(String category) {
        CATEGORY = category;
        if (category != null) CATEGORY_SIZE = category.length();
        else CATEGORY_SIZE = 0;
        return this;
    }
    
    public IntentData setType(String type) {
        TYPE = type;
        if (type != null) TYPE_SIZE = type.length();
        else TYPE_SIZE = 0;
        return this;
    }
    
    public IntentData setClipData(byte[] data) {
        CLIPDATA = data;
        CLIPDATA_SIZE = data.length;
        return this;
    }
    
    public IntentData setExtras(byte[] extras) {
        EXTRAS = extras;
        EXTRAS_SIZE = extras.length;
        return this;
    }
    
    public int getFlags() { return FLAGS; }
    
    public String getAction() { return ACTION; }
    
    public String getData() { return DATA; }
    
    public String getCategory() { return CATEGORY; }
    
    public String getType() { return TYPE; }
    
    public byte[] getClipData() { return CLIPDATA; }
    
    public byte[] getExtras() { return EXTRAS; }
    
    /**
     * Calculates the total number of bytes needed to write this IntentData.
     * 
     * @return The number of bytes this IntentData will use when flattened into
     * a sequence of bytes.
     */
    public int getSize() {
        return 32 * 7 + ACTION_SIZE + DATA_SIZE + CATEGORY_SIZE + TYPE_SIZE
                + CLIPDATA_SIZE + EXTRAS_SIZE;
    }
    
    /**
     * Puts the contents of IntentData into a ByteBuffer which is ready to be
     * written to an AICS file.
     * 
     * @return A ByteBuffer containing the contents of the IntentData.
     */
    protected ByteBuffer toByteBuffer() {
        // Created fix-sized part of buffer
        ByteBuffer buffer = ByteBuffer.allocate(getSize())
                .putInt(FLAGS)
                .putInt(ACTION_SIZE)
                .putInt(DATA_SIZE)
                .putInt(CATEGORY_SIZE)
                .putInt(TYPE_SIZE)
                .putInt(CLIPDATA_SIZE)
                .putInt(EXTRAS_SIZE);
        
        // Create variable-sized part of buffer
        if (ACTION_SIZE > 0) buffer.put(ACTION.getBytes());
        if (DATA_SIZE > 0) buffer.put(DATA.getBytes());
        if (CATEGORY_SIZE > 0) buffer.put(CATEGORY.getBytes());
        if (TYPE_SIZE > 0) buffer.put(TYPE.getBytes());
        if (CLIPDATA_SIZE > 0) buffer.put(CLIPDATA);
        if (EXTRAS_SIZE > 0) buffer.put(EXTRAS);
        
        buffer.rewind();
        return buffer;
    }
}
