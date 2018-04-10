/*******************************************************************************
 * Copyright (c) 2013, 2015 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Patrick Tasse - Initial API and implementation
 *******************************************************************************/

package org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model;

import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

/**
 * A null time event. Used to represent an event that should not be drawn,
 * for example as a zoomed event that overshadows an underlying event.
 */
public class NullTimeEvent extends TimeEvent {

    /**
     * Standard constructor
     *
     * @param entry
     *            The entry matching this event
     * @param time
     *            The timestamp of this event
     * @param duration
     *            The duration of the event
     */
    public NullTimeEvent(ITimeGraphEntry entry, long time, long duration) {
        super(entry, time, duration);
    }

    /**
     * Standard constructor
     *
     * @param entry
     *            The entry matching this event
     * @param time
     *            The timestamp of this event
     * @param duration
     *            The duration of the event
     * @param notCool
     *            The annotated status of the event
     * @since 3.4
     */
    public NullTimeEvent(TimeGraphEntry entry, long time, long duration, @NonNull Map<@NonNull String, @NonNull Boolean> properties) {
        super(entry, time, duration, properties);
    }

    @Override
    public ITimeEvent splitBefore(long splitTime) {
        return (splitTime > fTime ?
                new NullTimeEvent(fEntry, fTime, Math.min(fDuration, splitTime - fTime)) :
                null);
    }

    @Override
    public ITimeEvent splitAfter(long splitTime) {
        return (splitTime < fTime + fDuration ?
                new NullTimeEvent(fEntry, Math.max(fTime, splitTime), fDuration - Math.max(0, splitTime - fTime)) :
                null);
    }

}
