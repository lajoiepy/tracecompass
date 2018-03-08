/*******************************************************************************
 * Copyright (c) 2018 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package test.filter;

import java.util.function.Predicate;

import org.eclipse.tracecompass.internal.provisional.tmf.core.model.timegraph.ITimeGraphState;

/**
 * Basic implementation of a time graph event's filter
 *
 * @author Jean-Christian Kouame
 * @since 3.3
 *
 */
public class TimeEventFilter implements Predicate<ITimeGraphState> {

    private Predicate<ITimeGraphState> fPredicate;

    /**
     * Constructor
     *
     * @param regex
     *            The time event regex string
     * @param tooltipProvider
     *            The function that provides the tooltip fields value
     */
    public TimeEventFilter(Predicate<ITimeGraphState> predicate) {
        fPredicate = predicate;
    }

    /**
     * The current regex applied
     *
     * @return The current time event regex string value. The value of the regex is
     *         equal to <b>"."</b> if no filter is applied
     */
    protected Predicate<ITimeGraphState> getPredicate() {
        return fPredicate;
    }

    @Override
    public boolean test(ITimeGraphState event) {
        return fPredicate.test(event);
    }
}
