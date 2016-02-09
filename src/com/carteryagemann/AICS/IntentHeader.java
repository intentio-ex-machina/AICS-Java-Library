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
    
    /**
     * Convert the contents of the intent header into a ByteBuffer which is
     * ready to be written to a file.
     * 
     * @return A ByteBuffer containing the contents of the intent header.
     */
    abstract protected ByteBuffer toByteBuffer();
    
}
