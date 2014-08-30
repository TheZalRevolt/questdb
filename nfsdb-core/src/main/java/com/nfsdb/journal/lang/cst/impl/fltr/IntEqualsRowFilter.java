/*
 * Copyright (c) 2014. Vlad Ilyushchenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nfsdb.journal.lang.cst.impl.fltr;

import com.nfsdb.journal.column.AbstractColumn;
import com.nfsdb.journal.column.FixedColumn;
import com.nfsdb.journal.exceptions.JournalException;
import com.nfsdb.journal.exceptions.JournalRuntimeException;
import com.nfsdb.journal.lang.cst.Choice;
import com.nfsdb.journal.lang.cst.PartitionSlice;
import com.nfsdb.journal.lang.cst.RowAcceptor;
import com.nfsdb.journal.lang.cst.RowFilter;
import com.nfsdb.journal.lang.cst.impl.ref.IntRef;
import com.nfsdb.journal.lang.cst.impl.ref.StringRef;

public class IntEqualsRowFilter implements RowFilter, RowAcceptor {
    private final StringRef column;
    private final IntRef value;
    private FixedColumn columnRef;

    public IntEqualsRowFilter(StringRef column, IntRef value) {
        this.column = column;
        this.value = value;
    }

    @Override
    public RowAcceptor acceptor(PartitionSlice a, PartitionSlice b) {
        try {
            a.partition.open();
            AbstractColumn col = a.partition.getAbstractColumn(a.partition.getJournal().getMetadata().getColumnIndex(column.value));
            if (!(col instanceof FixedColumn)) {
                throw new JournalRuntimeException("Invalid column type");
            }
            columnRef = (FixedColumn) col;

            return this;
        } catch (JournalException e) {
            throw new JournalRuntimeException(e);
        }
    }

    @Override
    public Choice accept(long localRowIDA, long localRowIDB) {
        return columnRef.getInt(localRowIDA) == value.value ? Choice.PICK : Choice.SKIP;
    }
}
