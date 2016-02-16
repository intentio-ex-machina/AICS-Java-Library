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
 * An IntentHeader contains important information about an Android intent which
 * isn't contained in the intent itself. This includes information such as the
 * sender and receiver of the intent.
 * 
 * @author Carter Yagemann <carter.yagemann@gmail.com>
 */
abstract public class IntentHeader {
    
    public static final short TYPE_ACTIVITY = 0;
    public static final short TYPE_BROADCAST = 1;
    public static final short TYPE_SERVICE = 2;
    
    protected int TIMESTAMP;
    protected short MILLI_OFFSET;
    protected short INTENT_TYPE;
    protected int CALLER_UID;
    protected int CALLER_PID;
    protected int RECEIVER_UID;
    protected int RECEIVER_PID;
    protected int USER_ID;
    
    protected IntentData INTENT_DATA;
    protected int INTENT_DATA_SIZE;
    
    /**
     * Returns the type of the first intent header in the buffer. The returned
     * value can then be used to determine which constructor should be called on
     * the buffer.
     * 
     * @param buffer The buffer to parse. The header must start at the current
     * position of this buffer.
     * @return The type of the intent header at the start of the buffer.
     * @throws ParseException If type can't be determined
     * @throws BufferUnderflowException If buffer is too tiny to be an intent
     * header.
     */
    public static short parseIntentType(ByteBuffer buffer)
            throws ParseException, BufferUnderflowException {
        try {
            short type = buffer.getShort(buffer.position() + 6);
            // Type can only be 0, 1, or 2
            if (type < 0 || type > 2)
                throw new ParseException("Cannot parse intent header.",
                        buffer.position());
            return type;
        } catch (BufferUnderflowException e) {
            throw e;
        }
    }
    
    /**
     * Returns the type of the first intent header in the buffer. The returned
     * value can then be used to determine which constructor should be called on
     * the buffer.
     * 
     * @param array The byte array to parse. The header must start at the
     * beginning of this array.
     * @return The type of the intent header at the start of the buffer.
     * @throws ParseException If type can't be determined.
     * @throws BufferUnderflowException If buffer is too tiny to be an intent
     * header.
     */
    public static short parseIntentType(byte[] array)
            throws ParseException, BufferUnderflowException {
        try {
            return parseIntentType(ByteBuffer.wrap(array));
        } catch (ParseException | BufferUnderflowException e) {
            throw e;
        }
    }
    
    /**
     * Sets the time for the intent this header describes. Should be in UNIX
     * time.
     * 
     * @param time The time in UNIX time.
     * @return Itself for chaining.
     */
    public IntentHeader setTimestamp(int time) {
        TIMESTAMP = time;
        return this;
    }
    
    /**
     * The millisecond offset for the intent this header describes.
     * 
     * @param milli The offset from the time in milliseconds.
     * @return 
     * @throws IllegalArgumentException Offset cannot be negative and must be
     * less than a second.
     */
    public IntentHeader setOffset(short milli) throws IllegalArgumentException {
        if (milli < 0 || milli > 999) throw new IllegalArgumentException();
        MILLI_OFFSET = milli;
        return this;
    }
    
    public IntentHeader setCallerUID(int uid) {
        CALLER_UID = uid;
        return this;
    }
    
    public IntentHeader setCallerPID(int pid) {
        CALLER_PID = pid;
        return this;
    }
    
    public IntentHeader setReceiverUID(int uid) {
        RECEIVER_UID = uid;
        return this;
    }
    
    public IntentHeader setReceiverPID(int pid) {
        RECEIVER_PID = pid;
        return this;
    }
    
    public IntentHeader setUserID(int user) {
        USER_ID = user;
        return this;
    }
    
    public IntentHeader setIntentData(IntentData data) {
        INTENT_DATA = data;
        if (data != null) INTENT_DATA_SIZE = data.getSize();
        else INTENT_DATA_SIZE = 0;
        return this;
    }
    
    public int getTimestamp() { return TIMESTAMP; }
    
    public short getOffset() { return MILLI_OFFSET; }
    
    public short getIntentType() { return INTENT_TYPE; }
    
    public int getCallerUID() { return CALLER_UID; }
    
    public int getCallerPID() { return CALLER_PID; }
    
    public int getReceiverUID() { return RECEIVER_UID; }
    
    public int getReceiverPID() { return RECEIVER_PID; }
    
    public int getUserID() { return USER_ID; }
    
    public IntentData getIntentData() { return INTENT_DATA; }
    
    /**
     * Convert the contents of the intent header into a ByteBuffer which is
     * ready to be written to a file. This buffer only contains the header.
     * To flatten the data, use getIntentData().toByteBuffer().
     * 
     * @return A ByteBuffer containing the contents of the intent header.
     */
    abstract protected ByteBuffer toByteBuffer();
    
    /**
     * Calculates the total number of bytes needed to write this header.
     * 
     * @return The number of bytes this header will use when flattened into
     * a sequence of bytes.
     */
    abstract public int getSize();
    
}
