/*******************************************************************************
 * Copyright (c) 2012, 2014 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Patrick Tasse - Initial API and implementation
 *   Geneviève Bastien - Added the fValue parameter to avoid subclassing
 *******************************************************************************/

package org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.timegraph.IItemProperties;

/**
 * Generic TimeEvent implementation
 *
 * @version 1.0
 * @author Patrick Tasse
 */
public class TimeEvent implements ITimeEvent {

    /** TimeGraphEntry matching this time event */
    protected ITimeGraphEntry fEntry;

    /** Beginning timestamp of this time event */
    protected long fTime;

    /** Duration of this time event */
    protected long fDuration;

    private final int fValue;

    /**
     * A map of properties to activate or deactivate
     */
    Map<@NonNull String, @NonNull Boolean> ACTIVE_PROPERTIES = new HashMap<>();

    /**
     * Default value when no other value present
     */
    private static final int NOVALUE = Integer.MIN_VALUE;

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
    public TimeEvent(ITimeGraphEntry entry, long time, long duration) {
        this(entry, time, duration, NOVALUE);
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
     * @param properties
     *            The map of properties status
     * @since 3.4
     */
    public TimeEvent(ITimeGraphEntry entry, long time, long duration, @NonNull Map<@NonNull String, @NonNull Boolean> properties) {
        this(entry, time, duration, NOVALUE, properties);

    }

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
     */
    public TimeEvent(ITimeGraphEntry entry, long time, long duration,
            int value) {
        fEntry = entry;
        fTime = time;
        fDuration = duration;
        fValue = value;
    }

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
     * @param properties
     *            The time event properties
     * @since 3.4
     */
    public TimeEvent(ITimeGraphEntry entry, long time, long duration, int value, @NonNull Map<@NonNull String, @NonNull Boolean> properties) {
        fEntry = entry;
        fTime = time;
        fDuration = duration;
        fValue = value;
        setProperties(properties);
    }

    /**
     * Get this event's status
     *
     * @return The integer matching this status
     */
    public int getValue() {
        return fValue;
    }

    /**
     * Return whether an event has a value
     *
     * @return true if the event has a value
     */
    public boolean hasValue() {
        return (fValue != NOVALUE);
    }

    @Override
    public ITimeGraphEntry getEntry() {
        return fEntry;
    }

    @Override
    public long getTime() {
        return fTime;
    }

    @Override
    public long getDuration() {
        return fDuration;
    }

    /**
     * @since 3.4
     */
    @Override
    public void activateProperty(String key, boolean activate) {
        ACTIVE_PROPERTIES.put(key, activate);
    }

    /**
     * @since 3.4
     */
    @Override
    public boolean isPropertyActive(String property) {
        if (property.equals(IItemProperties.fullAlpha())) {
            return ACTIVE_PROPERTIES.getOrDefault(property, true);
        }
        return ACTIVE_PROPERTIES.getOrDefault(property, false);
    }

    /**
     * @since 3.4
     */
    @Override
    public void setProperties(@NonNull Map<@NonNull String, @NonNull Boolean> properties) {
        ACTIVE_PROPERTIES.putAll(properties);
    }

    /**
     * @since 3.4
     */
    @Override
    public Map<@NonNull String, @NonNull Boolean> getProperties() {
        return new HashMap<>(ACTIVE_PROPERTIES);
    }

    @Override
    public ITimeEvent splitBefore(long splitTime) {
        return (splitTime > fTime ?
                new TimeEvent(fEntry, fTime, Math.min(fDuration, splitTime - fTime), fValue) :
                null);
    }

    @Override
    public ITimeEvent splitAfter(long splitTime) {
        return (splitTime < fTime + fDuration ?
                new TimeEvent(fEntry, Math.max(fTime, splitTime), fDuration - Math.max(0, splitTime - fTime),
                        fValue) :
                null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fEntry, fTime, fDuration, fValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TimeEvent other = (TimeEvent) obj;
        return Objects.equals(fEntry, other.fEntry) &&
                Objects.equals(fTime, other.fTime) &&
                Objects.equals(fDuration, other.fDuration) &&
                Objects.equals(fValue, other.fValue);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " start=" + fTime + " end=" + (fTime + fDuration) + " duration=" + fDuration + (hasValue() ? (" value=" + fValue) : ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    }
}
