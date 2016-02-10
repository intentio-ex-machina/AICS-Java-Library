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

import java.nio.ByteBuffer;

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
    
    private int TIMESTAMP;
    private short MILLI_OFFSET;
    private short INTENT_TYPE;
    private int CALLER_UID;
    private int CALLER_PID;
    private int RECEIVER_UID;
    private int RECEIVER_PID;
    private int USER_ID;
    
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
    
    public int getTimestamp() { return TIMESTAMP; }
    
    public short getOffset() { return MILLI_OFFSET; }
    
    public short getIntentType() { return INTENT_TYPE; }
    
    public int getCallerUID() { return CALLER_UID; }
    
    public int getCallerPID() { return CALLER_PID; }
    
    public int getReceiverUID() { return RECEIVER_UID; }
    
    public int getReceiverPID() { return RECEIVER_PID; }
    
    public int getUserID() { return USER_ID; }
    
    /**
     * Convert the contents of the intent header into a ByteBuffer which is
     * ready to be written to a file.
     * 
     * @return A ByteBuffer containing the contents of the intent header.
     */
    abstract protected ByteBuffer toByteBuffer();
    
}
