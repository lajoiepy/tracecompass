/**********************************************************************
 * Copyright (c) 2018 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **********************************************************************/
package org.eclipse.tracecompass.internal.provisional.tmf.core.model.filters;

import java.util.Collection;
import java.util.List;

/**
 * Standardized query filter to query XY data providers for a Collection of
 * entries and filter the data using the given regex.
 *
 * @author Jean-Christian Kouame
 *
 */
public class TimegraphStateQueryFilter extends SelectionTimeQueryFilter {

    private String fRegex;
    private boolean fRemoveUnmatched;

    /**
     * Constructor
     *
     * @param times
     *            sorted list of times to query.
     * @param items
     *            The unique keys of the selected entries.
     * @param regex
     *            The regex use to filter the queried data
     * @param removeUnmatched
     *            Tells whether unmatched items should be rerurned or not
     */
    public TimegraphStateQueryFilter(List<Long> times, Collection<Long> items, String regex, boolean removeUnmatched) {
        super(times, items);
        fRegex = regex;
        fRemoveUnmatched = removeUnmatched;
    }

    /**
     * Constructor
     *
     * @param start
     *            The starting value
     * @param end
     *            The ending value
     * @param n
     *            The number of entries
     * @param items
     *            The unique keys of the selected entries
     * @param regex
     *            The regex use to filter the queried data
     * @param removeUnmatched
     *            Tells whether unmatched items should be returned or not
     */
    public TimegraphStateQueryFilter(long start, long end, int n, Collection<Long> items, String regex, boolean removeUnmatched) {
        super(start, end, n, items);
        fRegex = regex;
        fRemoveUnmatched = removeUnmatched;
    }

    /**
     * Get the regex use to filter the queried data
     *
     * @return The regex
     */
    public String getRegex() {
        return fRegex;
    }

    /**
     * Tells whether the unmatched items need to be removed
     *
     * @return True if the unmatched element need to be remove, false otherwise
     */
    public boolean removeUnmatched() {
        return fRemoveUnmatched;
    }

}
