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
 * An IntentPacket consists of an IntentHeader and an IntentData and
 * encapsulates all the information pertaining to a single logged Android
 * intent.
 * 
 * @author Carter Yagemann <carter.yagemann@gmail.com>
 */
public class IntentPacket {
    
    private IntentHeader INTENT_HEADER;
    private IntentData INTENT_DATA;
    
    /**
     * The default constructor for an IntentPacket. It requires an IntentHeader
     * and an IntentData.
     * 
     * @param header The header for the Android intent.
     * @param data  The data from the Android intent.
     */
    IntentPacket(IntentHeader header, IntentData data) {
        INTENT_HEADER = header;
        INTENT_DATA = data;
    }
    
    /**
     * Sets the header of the intent packet to a new header.
     * 
     * @param header The new header.
     * @return Itself for chaining.
     * @throws NullPointerException If argument is null.
     */
    public IntentPacket setHeader(IntentHeader header)
            throws NullPointerException {
        if (header == null) throw new NullPointerException();
        INTENT_HEADER = header;
        return this;
    }
    
    /**
     * Sets the data of the intent packet to a new one.
     * 
     * @param data The new data.
     * @return Itself for chaining.
     * @throws NullPointerException If the argument is null.
     */
    public IntentPacket setData(IntentData data) throws NullPointerException {
        if (data == null) throw new NullPointerException();
        INTENT_DATA = data;
        return this;
    }
    
    public IntentHeader getHeader() { return INTENT_HEADER; }
    
    public IntentData getData() { return INTENT_DATA; }
    
    /**
     * Converts the IntentPacket into a ByteBuffer which is ready to be written
     * to an AICS file.
     * 
     * @return A ByteBuffer containing the contents of IntentPacket.
     */
    protected ByteBuffer toByteBuffer() {
        byte[] headArray = INTENT_HEADER.toByteBuffer().array();
        byte[] dataArray = INTENT_DATA.toByteBuffer().array();
        
        return ByteBuffer.allocate(headArray.length + dataArray.length)
                .put(headArray, 0, headArray.length)
                .put(dataArray, headArray.length, dataArray.length);
    }
}
