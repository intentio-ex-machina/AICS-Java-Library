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
import java.text.ParseException;
import java.nio.BufferUnderflowException;

/**
 * An Android Intent Capture Session file. The file is organized into a fixed
 * sized header followed by a chain of intent entries. Each intent logged in the
 * file is represented as a header, containing meta information about the
 * intent, and the intent itself.
 *
 * @author Carter Yagemann
 */
public class AICSFile {
    
    private final static String MAGIC_PARSE_ERROR = "Failed to read magic "
            + "number. File is either corrupt or in little endian order "
            + "(currently not implemented).";
    
    private final AICSFileHeader FILE_HEADER;
    private final ArrayList<IntentHeader> INTENTS;
    
    /**
     * Creates an AICSFile object from a ByteBuffer.
     * 
     * @param buffer The buffer to parse.
     * @return An AICSFile.
     * @throws java.text.ParseException If the buffer can't be parsed.
     */
    public static AICSFile readFromBuffer(ByteBuffer buffer)
            throws ParseException {
        
        int originalPos = buffer.position();
        
        /* Read File Header */
        
        // Magic Number
        int magic = buffer.getInt();
        if (magic != AICSFileHeader.MAGIC_NUMBER)
            throw new ParseException(MAGIC_PARSE_ERROR, buffer.position());
        
        // File Format Version
        short formatMajor = buffer.getShort();
        short formatMinor = buffer.getShort();
        if (formatMajor != AICSFileHeader.FORMAT_MAJOR_VERSION ||
                formatMinor != AICSFileHeader.FORMAT_MINOR_VERSION)
            throw new ParseException("File's format version " + formatMajor +
                    "." + formatMinor + " does not match library's supported" +
                    "version " + AICSFileHeader.FORMAT_MAJOR_VERSION + "." +
                    AICSFileHeader.FORMAT_MINOR_VERSION, buffer.position());
        
        // Android Version
        AICSFile file = new AICSFile(buffer.getShort(), buffer.get(),
                buffer.get());
        
        /* Read Intents */
        
        // Every header has to have at least 8 bytes for the timestamp, offset,
        // and intent type.
        while (buffer.remaining() > 8) {
            try { // Try to parse the intent
                switch (IntentHeader.parseIntentType(buffer)) {
                    case IntentHeader.TYPE_ACTIVITY:
                        file.appendIntent(new ActivityIntentHeader(buffer));
                        break;
                    case IntentHeader.TYPE_BROADCAST:
                        file.appendIntent(new BroadcastIntentHeader(buffer));
                        break;
                    case IntentHeader.TYPE_SERVICE:
                        file.appendIntent(new ServiceIntentHeader(buffer));
                        break;
                }
            } catch (BufferUnderflowException | ParseException e) {
                System.err.println(e.toString());
                buffer.position(originalPos); // Restore original position
                return file; // Return as much as we could parse.
            }
        }
        
        buffer.position(originalPos); // Restore original position
        return file; // Success!
    }
    
    /**
     * Creates an AICSFile from an array of bytes.
     * 
     * @param array The array to parse.
     * @return An AICSFile.
     * @throws java.text.ParseException If buffer can't be parsed.
     */
    public static AICSFile readFromArray(byte[] array) throws ParseException {
        try {
            return readFromBuffer(ByteBuffer.wrap(array));
        } catch (BufferUnderflowException | ParseException e) {
            throw e;
        }
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
     * @return Itself.
     */
    public AICSFile appendIntent(IntentHeader packet) {
        INTENTS.add(packet);
        return this;
    }
    
    /**
     * Get the intent at the provided index.
     * 
     * @param index Which intent to get.
     * @return The intent at that index.
     */
    public IntentHeader getIntent(int index) { return INTENTS.get(index); }
    
    /**
     * Deletes all the intents in this file.
     * 
     * @return Itself.
     */
    public AICSFile clearIntents() {
        INTENTS.clear();
        return this;
    }
    
    /**
     * Returns how many intents are in this file.
     * 
     * @return The number of intents in this file.
     */
    public int size() { return INTENTS.size(); }
    
    /**
     * Flattens the AICSFile into a ByteBuffer for writing.
     * 
     * @return A ByteBuffer containing the flattened version of the file.
     */
    public ByteBuffer toByteBuffer() {
        
        int size = 0;
        size += FILE_HEADER.getSize(); // File header first...
        for (IntentHeader intent : INTENTS) // ... then the intents
            size += intent.getSize() + intent.getIntentData().getSize();
        
        // Merge list of buffers into one buffer
        ByteBuffer output = ByteBuffer.allocate(size);
        output.put(FILE_HEADER.toByteBuffer());
        for (IntentHeader intent : INTENTS) {
            output.put(intent.toByteBuffer());
            output.put(intent.getIntentData().toByteBuffer());
        }
        
        output.rewind();
        return output;
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
        
        public final static int MAGIC_NUMBER = 0xA1B2C3D4;
        public final static short FORMAT_MAJOR_VERSION = 0;
        public final static short FORMAT_MINOR_VERSION = 1;
        
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
            ByteBuffer buffer = ByteBuffer.allocate(12)
                    .putInt(MAGIC_NUMBER)
                    .putShort(FORMAT_MAJOR_VERSION)
                    .putShort(FORMAT_MINOR_VERSION)
                    .putShort(ANDROID_MAJOR_VERSION)
                    .put(ANDROID_MINOR_VERSION)
                    .put(ANDROID_PATCH_VERSION);
            buffer.rewind();
            return buffer;
        }
        
        protected int getSize() {
            return 12; // File header is fixed size
        }
    }
}
