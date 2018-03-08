/*******************************************************************************
 * Copyright (c) 2018 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.tracecompass.tmf.ui.timegraph.timeeventfilter;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.ITimeEvent;

import com.google.common.collect.Iterables;

/**
 * Basic implementation of a time graph event's filter
 *
 * @author Jean-Christian Kouame
 * @since 3.3
 *
 */
public class TimeEventFilterOld implements Predicate<ITimeEvent> {

    private static final String EMPTY_STRING = ""; //$NON-NLS-1$
    private static final String WILDCARD_REGEX = ".*"; //$NON-NLS-1$
    private static final Pattern WILDCARD_PATTERN = Pattern.compile("\\*"); //$NON-NLS-1$
    private Predicate<String> fPredicate;
    private Function<ITimeEvent, Map<String, String>> fTooltipProvider;

    /**
     * Constructor
     *
     * @param regex
     *            The time event regex string
     * @param tooltipProvider
     *            The function that provides the tooltip fields value
     */
    public TimeEventFilterOld(String regex, Function<ITimeEvent, Map<String, String>> tooltipProvider) {
        String regexValue = regex != null && !regex.isEmpty() ? WILDCARD_PATTERN.matcher(regex).replaceAll(WILDCARD_REGEX) : WILDCARD_REGEX;
        fPredicate = Pattern.compile(regexValue).asPredicate();
        fTooltipProvider = tooltipProvider;
    }

    /**
     * The current regex applied
     *
     * @return The current time event regex string value. The value of the regex is
     *         equal to <b>"."</b> if no filter is applied
     */
    protected Predicate<String> getPredicate() {
        return fPredicate;
    }

    @Override
    public boolean test(ITimeEvent event) {
        boolean matches = false;
        Predicate<String> predicate = getPredicate();

        if (predicate.test(EMPTY_STRING)) {
            return true;
        }

        String entryName = event.getEntry().getName();
        if (entryName != null && predicate.test(entryName)) {
            matches = true;
        } else {
            Map<String, String> tooltips = fTooltipProvider.apply(event);
            if (tooltips == null) {
                return true;
            }
            Collection<String> values = tooltips.values();
            matches = Iterables.any(values, predicate::test);
        }
        return matches;
    }
}
