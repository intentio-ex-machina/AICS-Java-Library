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
 * A header for service intents.
 * 
 * @author Carter Yagemann <carter.yagemann@gmail.com>
 */
public class ServiceIntentHeader extends IntentHeader {
    
    @Override
    protected ByteBuffer toByteBuffer() {
        System.err.println("ServiceIntentHeader.toByteBuffer not implemented");
        return ByteBuffer.allocate(100);
    }
    
}
