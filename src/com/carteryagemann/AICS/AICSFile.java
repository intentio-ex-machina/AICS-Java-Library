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
import java.util.ArrayList;

/**
 * An Android Intent Capture Session file. The file is organized into a fixed
 * sized header followed by a chain of intent entries. Each intent logged in the
 * file is represented as a header, containing meta information about the
 * intent, and the intent itself.
 *
 * @author Carter Yagemann <carter.yagemann@gmail.com>
 */
public class AICSFile {
    
    private final AICSFileHeader FILE_HEADER;
    private ArrayList<IntentPacket> INTENTS;
    
    /**
     * Creates an AICSFile object by reading the contents of an AICS file.
     * 
     * @param filepath The path to the AICS file.
     * @return An AICSFile object containing the contents of the file.
     */
    public static AICSFile readFromFile(String filepath) {
        //TODO Implement method
        System.err.println("Method AICSFile.readFromFile not implemented!");
        return null;
    }
    
    /**
     * The default constructor for creating a new AICSFile.
     * 
     * @param major The major Android version upon which the intents logged to
     * this file were captured on.
     * @param minor The minor Android version upon which the intents logged to
     * this file were captured on.
     * @param patch The patch Android version upon which the intents logged to
     * this file were captured on.
     */
    AICSFile(short major, byte minor, byte patch) {
        FILE_HEADER = new AICSFileHeader(major, minor, patch);
        INTENTS = new ArrayList<>();
    }
    
    /**
     * Appends an IntentPacket to the list of packets belonging to this capture
     * session.
     * 
     * @param packet The packet to append.
     */
    public void appendIntent(IntentPacket packet) {
        INTENTS.add(packet);
    }
    
    /**
     * Get the intent at the provided index.
     * 
     * @param index Which intent to get.
     * @return The intent at that index.
     */
    public IntentPacket getIntent(int index) {
        return INTENTS.get(index);
    }
    
    /**
     * Deletes all the intents in this file.
     */
    public void clearIntents() {
        INTENTS.clear();
    }
    
    /**
     * Returns how many intents are in this file.
     * 
     * @return The number of intents in this file.
     */
    public int size() {
        return INTENTS.size();
    }
    
    /**
     * Writes the AICSFile to a file at the designated file path.
     * 
     * @param filepath The location where the AICS file should be written.
     */
    public void writeToFile(String filepath) {
        //TODO Implement method
        System.err.println("Method AICSFile.writeToFile not implemented!");
    }
    
    /**
     * Every AICS file starts with a file header. The first part is a 32 bit
     * magic number 0xA1B2C3D4. This magic number is included so the program
     * reading the file can determine the endian order of the file. If the
     * reader reads 0xD4C3B2A1, then the ordering of the bytes for all the other
     * fields will need to be swapped.
     * 
     * AICS major version and minor version are both short integers and
     * represent the file formatting version used in the file. This is to
     * anticipate future versions which might have different structures.
     * 
     * Finally, the Android version fields are used so the reader knows what
     * version of Android the intents were captured on. This is included to
     * anticipate future changes made to the structure of Android intents.
     */
    private class AICSFileHeader {
        
        private final int MAGIC_NUMBER = 0xA1B2C3D4;
        private final short FORMAT_MAJOR_VERSION = 0;
        private final short FORMAT_MINOR_VERSION = 1;
        
        private final short ANDROID_MAJOR_VERSION;
        private final byte ANDROID_MINOR_VERSION;
        private final byte ANDROID_PATCH_VERSION;
        
        /**
         * The default constructor for a file header requires the major, minor,
         * and patch version of the Android device the intents were captured on.
         * 
         * @param major Major Android version.
         * @param minor Minor Android version.
         * @param patch Patch Android version.
         */
        AICSFileHeader(short major, byte minor, byte patch) {
            ANDROID_MAJOR_VERSION = major;
            ANDROID_MINOR_VERSION = minor;
            ANDROID_PATCH_VERSION = patch;
        }
        
        /**
         * Convert the file header into a ByteBuffer which is ready to be
         * written to a file.
         * 
         * @return A ByteBuffer containing the file header.
         */
        protected ByteBuffer toByteBuffer() {
            return ByteBuffer.allocate(96)
                    .putInt(MAGIC_NUMBER)
                    .putShort(FORMAT_MAJOR_VERSION)
                    .putShort(FORMAT_MINOR_VERSION)
                    .putShort(ANDROID_MAJOR_VERSION)
                    .put(ANDROID_MINOR_VERSION)
                    .put(ANDROID_PATCH_VERSION);
        }
    }
}
