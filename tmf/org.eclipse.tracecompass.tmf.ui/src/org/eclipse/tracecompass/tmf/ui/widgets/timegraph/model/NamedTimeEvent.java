/*******************************************************************************
 * Copyright (c) 2017 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model;

import java.util.Map;
import java.util.Objects;

import org.eclipse.jdt.annotation.NonNull;

/**
 * {@link TimeEvent} with a label.
 *
 * @since 3.3
 * @author Loic Prieur-Drevon
 */
public class NamedTimeEvent extends TimeEvent {
    private final @NonNull String fLabel;

    /**
     * Constructor
     *
     * @param entry
     *            The entry to which this time event is assigned
     * @param time
     *            The timestamp of this event
     * @param duration
     *            The duration of this event
     * @param value
     *            The status assigned to the event
     * @param label
     *            This event's label
     */
    public NamedTimeEvent(ITimeGraphEntry entry, long time, long duration,
            int value, @NonNull String label) {
        super(entry, time, duration, value);
        fLabel = label.intern();
    }

    /**
     * Constructor
     *
     * @param entry
     *                       The entry to which this time event is assigned
     * @param time
     *                       The timestamp of this event
     * @param duration
     *                       The duration of this event
     * @param value
     *                       The status assigned to the event
     * @param label
     *                       This event's label
     * @param properties
     *                       The map of item properties status
     * @since 3.4
     */
    public NamedTimeEvent(TimeGraphEntry entry, long time, long duration, int value, String label, @NonNull Map<@NonNull String, @NonNull Boolean> properties) {
        super(entry, time, duration, value, properties);
        fLabel = label.intern();
    }

    /**
     * Getter for the label
     *
     * @return the event's label
     */
    public @NonNull String getLabel() {
        return fLabel;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NamedTimeEvent) {
            return super.equals(obj) && Objects.equals(fLabel, ((NamedTimeEvent) obj).fLabel);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fLabel);
    }
}
