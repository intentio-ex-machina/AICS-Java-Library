AICS Java Library
=================

This is a Java library for reading and writing files in the
[Android Intent Capture Session (AICS) format](https://bitbucket.org/intentio-ex-machina/aics-file-format).
AICS is designed for logging Android intents for later inspection.

Getting Started
---------------

The downloads section of this repository contains a precompiled version of this
library as well as a Javadoc. You can also compile the library yourself from the
provided source if you desire.

The repository for the [AICS format](https://bitbucket.org/intentio-ex-machina/aics-file-format)
has all the details of how an AICS file is structured. This section summarizes
how those structures correspond to the java classes in this library.

First, every file is represented by the `AICSFile` class. Its constructor
requires the version number for the Android device the included intents were
captured on.

Once an `AICSFile` object has been created, intents can be added and fetched.
Every intent is represented by an intent header and an intent data. The data
contains information present in the captured intent itself while the header
stores valuable meta information which the intent itself does not contain.

There is a unique header class for each type of intent. All these classes
derive from the `IntentHeader` class. Since `AICSFile.getIntent()` returns this
generic header object, the returned object needs to be casted into the correct
type. Use the header's `getIntentType()` method to determine which class to
cast the object to.

While each intent type has a unique header, they all use the same `IntentData`
class for the intent's data. Every intent header needs a data section, so make
sure to attach your `IntentData` objects to their corresponding intent headers
using the `setIntentData()` method.

Finally, the file can be flattened into a `ByteBuffer` using the
`toByteBuffer()` method or to an array of bytes using `toByteBuffer().array()`.
This sequence of bytes can then be written to a file.

Likewise, an array of bytes or a `ByteBuffer` can be parsed into a new
`AICSFile` object using the static methods `readFromBuffer()` and
`readFromArray()`.

License
-------

Copyright (C) 2016 Carter Yagemann

Licensed under the Apache License, Version 2.0 (the "License"); you may not use
this file except in compliance with the License. You may obtain a copy of the
License at:

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.