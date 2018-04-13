/**********************************************************************
 * Copyright (c) 2018 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **********************************************************************/

package org.eclipse.tracecompass.tmf.core.tests.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.events.TmfEventTableColumnDataModel;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.events.TmfEventTableDataProvider;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.events.TmfEventTableFilterModel;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.filters.EventTableQueryFilter;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.filters.TimeQueryFilter;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.filters.VirtualTableQueryFilter;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.table.EventTableLine;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.table.ITmfVirtualTableDataProvider;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.table.ITmfVirtualTableModel;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.table.TmfVirtualTableModel;
import org.eclipse.tracecompass.internal.provisional.tmf.core.response.TmfModelResponse;
import org.eclipse.tracecompass.tmf.core.exceptions.TmfTraceException;
import org.eclipse.tracecompass.tmf.core.tests.shared.TmfTestTrace;
import org.eclipse.tracecompass.tmf.core.timestamp.TmfTimestamp;
import org.eclipse.tracecompass.tmf.core.timestamp.TmfTimestampFormat;
import org.eclipse.tracecompass.tmf.core.trace.ITmfTrace;
import org.eclipse.tracecompass.tmf.tests.stubs.trace.TmfTraceStub;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the {@link TmfEventTableDataProvider}
 *
 * @author Yonni Chen
 */
@NonNullByDefault
public class TmfEventTableDataProviderTest {

    private static final TmfTestTrace TEST_TRACE = TmfTestTrace.A_TEST_10K;

    private static ITmfTrace fTrace = new TmfTraceStub();
    private static ITmfVirtualTableDataProvider<TmfEventTableColumnDataModel, EventTableLine> fProvider = new TmfEventTableDataProvider(fTrace);

    private static final String TIMESTAMP_COLUMN_NAME = "Timestamp";
    private static final String EVENT_TYPE_COLUMN_NAME = "Event type";
    private static final String CONTENTS_COLUMN_NAME = "Contents";

    private static Map<String, Long> fColumns = Collections.emptyMap();

    /**
     * Set up resources
     *
     * @throws TmfTraceException
     *             Trace exception should not happen
     */
    @BeforeClass
    public static void beforeClass() throws TmfTraceException {
        fTrace.dispose();
        fTrace = new TmfTraceStub(TEST_TRACE.getFullPath(), ITmfTrace.DEFAULT_TRACE_CACHE_SIZE, false, null);
        fProvider = new TmfEventTableDataProvider(fTrace);
        // Make sure the columns are computed before the test
        fColumns = fetchColumnId();
    }

    private static @NonNull Map<String, Long> fetchColumnId() {
        List<TmfEventTableColumnDataModel> columns = fProvider.fetchTree(new TimeQueryFilter(0, 0, 1), null).getModel();
        if (columns == null) {
            return Collections.emptyMap();
        }

        // Order should be timestamp, event type and contents
        assertEquals(TIMESTAMP_COLUMN_NAME, columns.get(0).getName());
        assertEquals(EVENT_TYPE_COLUMN_NAME, columns.get(1).getName());
        assertEquals(CONTENTS_COLUMN_NAME, columns.get(2).getName());

        Map<String, Long> expectedColumns = new LinkedHashMap<>();
        for (TmfEventTableColumnDataModel column : columns) {
            expectedColumns.put(column.getName(), column.getId());
        }
        return expectedColumns;
    }

    private static String lineTimestamp(long millisecond) {
        return TmfTimestampFormat.getDefaulTimeFormat().format(TmfTimestamp.fromMillis(millisecond).toNanos());
    }

    /**
     * Dispose resources
     */
    @AfterClass
    public static void tearDown() {
        fTrace.dispose();
    }

    /**
     * Test columns returned by the provider.
     */
    @Test
    public void testDataProviderFetchColumn() {
        Long timestampColumnId = fColumns.get(TIMESTAMP_COLUMN_NAME);
        Long eventTypeColumnId = fColumns.get(EVENT_TYPE_COLUMN_NAME);
        Long contentsColumnId = fColumns.get(CONTENTS_COLUMN_NAME);
        assertNotNull(timestampColumnId);
        assertNotNull(eventTypeColumnId);
        assertNotNull(contentsColumnId);
        List<TmfEventTableColumnDataModel> expectedColumnModel = Arrays.asList(
                new TmfEventTableColumnDataModel(timestampColumnId, -1, TIMESTAMP_COLUMN_NAME, "", false),
                new TmfEventTableColumnDataModel(eventTypeColumnId, -1, EVENT_TYPE_COLUMN_NAME, "The type of this event. This normally determines the field layout.", false),
                new TmfEventTableColumnDataModel(contentsColumnId, -1, CONTENTS_COLUMN_NAME, "The fields (or payload) of this event", false));

        TmfModelResponse<List<TmfEventTableColumnDataModel>> response = fProvider.fetchTree(new TimeQueryFilter(0, 0, 1), null);
        List<TmfEventTableColumnDataModel> currentColumnModel = response.getModel();
        assertEquals(expectedColumnModel, currentColumnModel);
    }

    /**
     * Given a start index and count, we check model returned by the data provider.
     * This test don't provide desired columns, so it queries data for all of them
     */
    @Test
    public void testDataProvider() {
        VirtualTableQueryFilter queryFilter = new EventTableQueryFilter(Collections.emptyList(), 0, 5, null);

        List<EventTableLine> expectedData = Arrays.asList(
                new EventTableLine(Arrays.asList(lineTimestamp(1), "Type-0", ""), 0, TmfTimestamp.fromMillis(1), 0, 0),
                new EventTableLine(Arrays.asList(lineTimestamp(2), "Type-1", ""), 1, TmfTimestamp.fromMillis(2), 1, 0),
                new EventTableLine(Arrays.asList(lineTimestamp(3), "Type-2", ""), 2, TmfTimestamp.fromMillis(3), 2, 0),
                new EventTableLine(Arrays.asList(lineTimestamp(4), "Type-3", ""), 3, TmfTimestamp.fromMillis(4), 3, 0),
                new EventTableLine(Arrays.asList(lineTimestamp(5), "Type-4", ""), 4, TmfTimestamp.fromMillis(5), 4, 0));

        TmfModelResponse<ITmfVirtualTableModel<EventTableLine>> response = fProvider.fetchLines(queryFilter, null);
        ITmfVirtualTableModel<EventTableLine> currentModel = response.getModel();

        ITmfVirtualTableModel<EventTableLine> expectedModel = new TmfVirtualTableModel<>(new ArrayList<>(fColumns.values()), expectedData, 0, fTrace.getNbEvents());
        assertEquals(expectedModel, currentModel);
    }

    /**
     * Given a start index that is out of bound and count, we check data returned
     * by the data provider.
     */
    @Test
    public void testDataProviderWithOutOfBoundIndex() {
        VirtualTableQueryFilter queryFilter = new EventTableQueryFilter(Collections.emptyList(), 2000000, 5, null);
        TmfModelResponse<ITmfVirtualTableModel<EventTableLine>> response = fProvider.fetchLines(queryFilter, null);
        ITmfVirtualTableModel<EventTableLine> currentModel = response.getModel();

        assertNotNull(currentModel);
        assertEquals(new ArrayList<>(fColumns.values()), currentModel.getColumnIds());
        assertTrue(currentModel.getData().isEmpty());
    }

    /**
     * Given a start index, count and a list of desired columns, we check model
     * returned by the data provider.
     */
    @Test
    public void testDataProviderWithDesiredColumns() {
        Long eventTypeColumnId = fColumns.get(EVENT_TYPE_COLUMN_NAME);
        assertNotNull(eventTypeColumnId);
        VirtualTableQueryFilter queryFilter = new EventTableQueryFilter(Collections.singletonList(eventTypeColumnId), 5, 5, null);

        List<Long> expectedColumnsId = Arrays.asList(eventTypeColumnId);
        List<EventTableLine> expectedData = Arrays.asList(
                new EventTableLine(Arrays.asList("Type-5"), 5, TmfTimestamp.fromMillis(6), 5, 0),
                new EventTableLine(Arrays.asList("Type-6"), 6, TmfTimestamp.fromMillis(7), 6, 0),
                new EventTableLine(Arrays.asList("Type-0"), 7, TmfTimestamp.fromMillis(8), 7, 0),
                new EventTableLine(Arrays.asList("Type-1"), 8, TmfTimestamp.fromMillis(9), 8, 0),
                new EventTableLine(Arrays.asList("Type-2"), 9, TmfTimestamp.fromMillis(10), 9, 0));

        TmfModelResponse<ITmfVirtualTableModel<EventTableLine>> response = fProvider.fetchLines(queryFilter, null);
        ITmfVirtualTableModel<EventTableLine> currentModel = response.getModel();

        ITmfVirtualTableModel<EventTableLine> expectedModel = new TmfVirtualTableModel<>(expectedColumnsId, expectedData, 5, fTrace.getNbEvents());
        assertEquals(expectedModel, currentModel);
    }

    /**
     * Given a start index, count and a list of desired columns that contains a
     * non-existent column, we check model returned by the data provider.
     */
    @Test
    public void testDataProviderWithOneNonExistentColumns() {
        Long eventTypeColumnId = fColumns.get(EVENT_TYPE_COLUMN_NAME);
        Long timestampColumnId = fColumns.get(TIMESTAMP_COLUMN_NAME);
        assertNotNull(timestampColumnId);
        assertNotNull(eventTypeColumnId);
        VirtualTableQueryFilter queryFilter = new EventTableQueryFilter(Arrays.asList(eventTypeColumnId, 10L, timestampColumnId), 150, 5, null);

        List<Long> expectedColumnsId = Arrays.asList(eventTypeColumnId, timestampColumnId);
        List<EventTableLine> expectedData = Arrays.asList(
                new EventTableLine(Arrays.asList("Type-3", lineTimestamp(151)), 150, TmfTimestamp.fromMillis(151), 150, 0),
                new EventTableLine(Arrays.asList("Type-4", lineTimestamp(152)), 151, TmfTimestamp.fromMillis(152), 151, 0),
                new EventTableLine(Arrays.asList("Type-5", lineTimestamp(153)), 152, TmfTimestamp.fromMillis(153), 152, 0),
                new EventTableLine(Arrays.asList("Type-6", lineTimestamp(154)), 153, TmfTimestamp.fromMillis(154), 153, 0),
                new EventTableLine(Arrays.asList("Type-0", lineTimestamp(155)), 154, TmfTimestamp.fromMillis(155), 154, 0));

        TmfModelResponse<ITmfVirtualTableModel<EventTableLine>> response = fProvider.fetchLines(queryFilter, null);
        ITmfVirtualTableModel<EventTableLine> currentModel = response.getModel();

        ITmfVirtualTableModel<EventTableLine> expectedModel = new TmfVirtualTableModel<>(expectedColumnsId, expectedData, 150, fTrace.getNbEvents());
        assertEquals(expectedModel, currentModel);
    }

    /**
     * Given a start index, count and a list of desired columns that contains only
     * non-existent columns, we check data returned by the data provider.
     */
    @Test
    public void testDataProviderWithNonExistentColumns() {
        VirtualTableQueryFilter queryFilter = new EventTableQueryFilter(Arrays.asList(10L, 11L), 0, 10, null);
        TmfModelResponse<ITmfVirtualTableModel<EventTableLine>> response = fProvider.fetchLines(queryFilter, null);
        ITmfVirtualTableModel<EventTableLine> currentModel = response.getModel();

        assertNotNull(currentModel);
        assertTrue(currentModel.getColumnIds().isEmpty());
        assertTrue(currentModel.getData().isEmpty());
    }

    /**
     * Given a start index, count and a list of desired columns, we check model
     * returned by the data provider. We also apply a filter on a column
     */
    @Test
    public void testDataProviderWithSimpleFilter() {
        Long eventTypeColumnId = fColumns.get(EVENT_TYPE_COLUMN_NAME);
        Long timestampColumnId = fColumns.get(TIMESTAMP_COLUMN_NAME);
        assertNotNull(timestampColumnId);
        assertNotNull(eventTypeColumnId);

        Map<Long, String> tableFilter = new HashMap<>();
        tableFilter.put(eventTypeColumnId, "1");
        TmfEventTableFilterModel filterModel = new TmfEventTableFilterModel(tableFilter, null, false);

        VirtualTableQueryFilter queryFilter = new EventTableQueryFilter(Arrays.asList(eventTypeColumnId, timestampColumnId), 0, 5, filterModel);

        List<Long> expectedColumnsId = Arrays.asList(eventTypeColumnId, timestampColumnId);
        TmfTimestampFormat.getDefaulTimeFormat().format(TmfTimestamp.fromMillis(2).toNanos());
        List<EventTableLine> expectedData = Arrays.asList(
                new EventTableLine(Arrays.asList("Type-1", lineTimestamp(2)), 0, TmfTimestamp.fromMillis(2), 1, 0),
                new EventTableLine(Arrays.asList("Type-1", lineTimestamp(9)), 1, TmfTimestamp.fromMillis(9), 8, 0),
                new EventTableLine(Arrays.asList("Type-1", lineTimestamp(16)), 2, TmfTimestamp.fromMillis(16), 15, 0),
                new EventTableLine(Arrays.asList("Type-1", lineTimestamp(23)), 3, TmfTimestamp.fromMillis(23), 22, 0),
                new EventTableLine(Arrays.asList("Type-1", lineTimestamp(30)), 4, TmfTimestamp.fromMillis(30), 29, 0));

        TmfModelResponse<ITmfVirtualTableModel<EventTableLine>> response = fProvider.fetchLines(queryFilter, null);
        ITmfVirtualTableModel<EventTableLine> currentModel = response.getModel();
        
        TmfVirtualTableModel<@NonNull EventTableLine> expectedModel = new TmfVirtualTableModel<>(expectedColumnsId, expectedData, 0, 1429);
        assertEquals(expectedModel, currentModel);
    }

    /**
     * Given a start index, count and a list of desired columns, we check model
     * returned by the data provider. We also apply two filters on two columns
     */
    @Test
    public void testDataProviderWithMultipleFilter() {
        Long eventTypeColumnId = fColumns.get(EVENT_TYPE_COLUMN_NAME);
        Long timestampColumnId = fColumns.get(TIMESTAMP_COLUMN_NAME);
        assertNotNull(timestampColumnId);
        assertNotNull(eventTypeColumnId);

        Map<Long, String> tableFilter = new HashMap<>();
        tableFilter.put(eventTypeColumnId, "0");
        tableFilter.put(timestampColumnId, "8");
        TmfEventTableFilterModel filterModel = new TmfEventTableFilterModel(tableFilter, null, false);

        VirtualTableQueryFilter queryFilter = new EventTableQueryFilter(Arrays.asList(1L, 0L), 0, 5, filterModel);

        List<Long> expectedColumnsId = Arrays.asList(eventTypeColumnId, timestampColumnId);
        List<EventTableLine> expectedData = Arrays.asList(
                new EventTableLine(Arrays.asList("Type-0", lineTimestamp(8)), 0, TmfTimestamp.fromMillis(8), 7, 0),
                new EventTableLine(Arrays.asList("Type-0", lineTimestamp(78)), 1, TmfTimestamp.fromMillis(78), 77, 0),
                new EventTableLine(Arrays.asList("Type-0", lineTimestamp(85)), 2, TmfTimestamp.fromMillis(85), 84, 0),
                new EventTableLine(Arrays.asList("Type-0", lineTimestamp(148)), 3, TmfTimestamp.fromMillis(148), 147, 0),
                new EventTableLine(Arrays.asList("Type-0", lineTimestamp(183)), 4, TmfTimestamp.fromMillis(183), 182, 0));

        TmfModelResponse<ITmfVirtualTableModel<EventTableLine>> response = fProvider.fetchLines(queryFilter, null);
        ITmfVirtualTableModel<EventTableLine> currentModel = response.getModel();

        ITmfVirtualTableModel<EventTableLine> expectedModel = new TmfVirtualTableModel<>(expectedColumnsId, expectedData, 0, 492);
        assertEquals(expectedModel, currentModel);
    }

    /**
     * Sets a negative index to EventTableQueryFilter. Expected an
     * IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testQueryFilterIndexParameter() {
        new EventTableQueryFilter(Collections.emptyList(), -1, 5, null);
    }
}