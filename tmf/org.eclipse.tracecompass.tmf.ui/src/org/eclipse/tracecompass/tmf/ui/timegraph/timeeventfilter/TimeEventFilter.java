/*******************************************************************************
 * Copyright (c) 2018 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.tracecompass.tmf.ui.timegraph.timeeventfilter;

import java.util.function.Predicate;

import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.ITimeEvent;

/**
 * Basic implementation of a time graph event's filter
 *
 * @author Jean-Christian Kouame
 * @since 3.3
 *
 */
public class TimeEventFilter implements Predicate<ITimeEvent> {

    private Predicate<ITimeEvent> fPredicate;

    /**
     * Constructor
     *
     * @param regex
     *            The time event regex string
     * @param tooltipProvider
     *            The function that provides the tooltip fields value
     */
    public TimeEventFilter(Predicate<ITimeEvent> predicate) {
        fPredicate = predicate;
    }

    /**
     * The current regex applied
     *
     * @return The current time event regex string value. The value of the regex is
     *         equal to <b>"."</b> if no filter is applied
     */
    protected Predicate<ITimeEvent> getPredicate() {
        return fPredicate;
    }

    @Override
    public boolean test(ITimeEvent event) {
        return fPredicate.test(event);
    }
}
