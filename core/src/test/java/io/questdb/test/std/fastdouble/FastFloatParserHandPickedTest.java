/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2024 QuestDB
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

package io.questdb.test.std.fastdouble;

import io.questdb.std.NumericException;
import io.questdb.std.fastdouble.FastFloatParser;

public class FastFloatParserHandPickedTest extends AbstractFloatHandPickedTest {

    @Override
    float parse(CharSequence str, boolean rejectOverflow) throws NumericException {
        return FastFloatParser.parseFloat(str, rejectOverflow);
    }

    @Override
    protected float parse(String str, int offset, int length, boolean rejectOverflow) throws NumericException {
        return FastFloatParser.parseFloat(str, offset, length, rejectOverflow);
    }
}
