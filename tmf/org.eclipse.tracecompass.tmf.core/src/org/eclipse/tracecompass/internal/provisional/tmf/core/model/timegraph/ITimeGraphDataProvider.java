/**********************************************************************
 * Copyright (c) 2017, 2018 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **********************************************************************/

package org.eclipse.tracecompass.internal.provisional.tmf.core.model.timegraph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.filter.parser.FilterCu;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.filters.SelectionTimeQueryFilter;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.filters.TimeQueryFilter;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.tree.ITmfTreeDataProvider;
import org.eclipse.tracecompass.internal.provisional.tmf.core.response.TmfModelResponse;

import com.google.common.base.Joiner;
import com.google.common.collect.Multimap;

/**
 * Interface that provides data for states to time. Such a model has a
 * collection of {@link ITimeGraphEntryModel}s, which can be organized into a
 * tree. Each entry can have consecutive {@link ITimeGraphState}s associated to
 * it by its unique ID. The entries can also be connected by
 * {@link ITimeGraphArrow}s which go from a source entry at a start time to a
 * destination entry after a certain duration. These items can also display
 * additional information in tool tips.
 *
 * Typical usage is to build a tree of {@link ITimeGraphEntryModel}s, and
 * associate the relevant line of {@link ITimeGraphState}s to each.
 * {@link ITimeGraphArrow}s then describe additional relations from one state to
 * another. {@link ITimeGraphState#getValue()} can be used to assign a color to
 * a state.
 *
 * @param <M>
 *            the type of {@link ITimeGraphEntryModel} that the
 *            {@link ITimeGraphDataProvider} implementations will return
 * @author Simon Delisle
 */
public interface ITimeGraphDataProvider<M extends ITimeGraphEntryModel> extends ITmfTreeDataProvider<M> {

    /**
     * Computes a list of time graph row models, which associate an entry's ID to
     * sampled states.
     *
     * @param filter
     *            Time graph query filter, specifies which IDs to return and the
     *            sampling rate.
     * @param monitor
     *            Progress monitor
     *
     * @return A {@link TmfModelResponse} that encapsulate a
     *         {@link ITimeGraphRowModel}
     */
    TmfModelResponse<List<ITimeGraphRowModel>> fetchRowModel(SelectionTimeQueryFilter filter, @Nullable IProgressMonitor monitor);

    /**
     * Computes a list of time graph arrows.
     *
     * @param filter
     *            Time query filter, specifies the sampling rate.
     * @param monitor
     *            Progress monitor
     *
     * @return A {@link TmfModelResponse} that encapsulate a {@link ITimeGraphArrow}
     */
    TmfModelResponse<List<ITimeGraphArrow>> fetchArrows(TimeQueryFilter filter, @Nullable IProgressMonitor monitor);

    /**
     * Computes a tool tip for a time stamp and entry.
     *
     * @param filter
     *            Time query filter, specifies the time stamp, and item on which to
     *            give more information
     * @param monitor
     *            Progress monitor
     *
     * @return A {@link TmfModelResponse} that encapsulate a map of Tooltips
     */
    TmfModelResponse<Map<String, String>> fetchTooltip(SelectionTimeQueryFilter filter, @Nullable IProgressMonitor monitor);

    /**
     * Build a map of predicates by properties
     *
     * @param regexes
     *                    The multimap of regexes to match
     * @return The map of predicates by properties
     */
    default Map<String, BiPredicate<IItem, Function<IItem, Map<String, String>>>> buildPredicates(Multimap<@NonNull String, @NonNull String> regexes) {
        Map<String, BiPredicate<IItem, Function<IItem, Map<String, String>>>> predicates = new HashMap<>();
        for (Map.Entry<String, Collection<String>> entry : regexes.asMap().entrySet()) {
            String property = entry.getKey();
            String regex = Joiner.on("||").skipNulls().join(entry.getValue()); //$NON-NLS-1$
            FilterCu cu = FilterCu.compile(regex);
            BiPredicate<IItem, Function<IItem, Map<String, String>>> predicate = cu != null ? cu.generate() : null;
            if (predicate != null) {
                predicates.put(property, predicate);
            }
        }
        return predicates;
    }

    /**
     * Do filter the timegraph states with the provided predicates and
     * activate/deactivate the associated properties
     *
     * @param predicates
     *                           The predicates by property
     * @param timegraphState
     *                           The timegraph state
     * @param startTime
     *                           The start time of the state
     * @param id
     *                           The id of the current timegraph entry
     */
    default void doFilter(Map<String, BiPredicate<IItem, Function<IItem, Map<String, String>>>> predicates, ITimeGraphState timegraphState, long startTime, Long id) {
        if (!predicates.isEmpty()) {
            SelectionTimeQueryFilter filter = new SelectionTimeQueryFilter(startTime, startTime, 1, Collections.singletonList(id));
            TmfModelResponse<Map<String, String>> response = fetchTooltip(filter, new NullProgressMonitor());
            Map<String, String> model = response.getModel();

            for (Map.Entry<String, BiPredicate<IItem, Function<IItem, Map<String, String>>>> entry : predicates.entrySet()) {
                boolean test = entry.getValue().test(timegraphState, state -> model);
                timegraphState.activateProperty(entry.getKey(), test);
            }
        }
    }

    /**
     * Do not add states that don't have the full alpha property activated to the
     * events list
     *
     * @param eventList
     *                           The events list
     * @param remove
     *                           The remove status of the timegraph state
     * @param timegraphState
     *                           The timegraph state
     */
    default void removeUnmatched(List<ITimeGraphState> eventList, boolean remove, ITimeGraphState timegraphState) {
        if (timegraphState.isPropertyActive(IItemProperties.fullAlpha()) || !remove) {
            eventList.add(timegraphState);
        }
    }
}
