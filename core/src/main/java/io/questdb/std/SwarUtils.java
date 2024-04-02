/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2023 QuestDB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package io.questdb.std;

public final class SwarUtils {

    public static final long ZERO_CHECK_BYTE = 0x80;

    private SwarUtils() {
    }

    /**
     * Broadcasts the given byte to a long.
     */
    public static long broadcast(byte b) {
        return 0x101010101010101L * (b & 0xffL);
    }

    /**
     * Returns non-zero result in case if the input contains a zero byte.
     * <p>
     * Each zero byte of the input is replaced with {@link #ZERO_CHECK_BYTE} in the output.
     * Each non-zero byte is replaced with zero byte.
     */
    public static long checkZeroByte(long w) {
        return ((w - 0x0101010101010101L) & ~(w) & 0x8080808080808080L);
    }
}
