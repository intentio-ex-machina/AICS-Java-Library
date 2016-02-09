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
 * IntentData is an object for storing the contents of the logged Android
 * intent. It should be combined with an IntentHeader to create an IntentPacket
 * which can then be stored inside an AICSFile.
 * 
 * @author Carter Yagemann <carter.yagemann@gmail.com>
 */
public class IntentData {
    
    /**
     * Puts the contents of IntentData into a ByteBuffer which is ready to be
     * written to an AICS file.
     * 
     * @return A ByteBuffer containing the contents of the IntentData.
     */
    protected ByteBuffer toByteBuffer() {
        //TODO Implement method
        System.err.println("Method IntentData.toByteBuffer not implemented!");
        return ByteBuffer.allocate(100);
    }
}
