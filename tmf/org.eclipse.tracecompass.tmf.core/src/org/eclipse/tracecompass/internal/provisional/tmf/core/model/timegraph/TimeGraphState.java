/**********************************************************************
 * Copyright (c) 2017, 2018 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **********************************************************************/

package org.eclipse.tracecompass.internal.provisional.tmf.core.model.timegraph;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Implementation of {@link ITimeGraphState}
 *
 * @author Simon Delisle
 */
public class TimeGraphState implements ITimeGraphState {
    private final long fStartTime;
    private final long fDuration;
    private final long fValue;
    private final @Nullable String fLabel;
    /**
     * A map of properties to activate or deactivate
     */
    Map<String, Boolean> ACTIVE_PROPERTIES = new HashMap<>();

    /**
     * Constructor
     *
     * @param time
     *            Time
     * @param duration
     *            State duration
     * @param value
     *            Type of state (event type)
     */
    public TimeGraphState(long time, long duration, long value) {
        fStartTime = time;
        fDuration = duration;
        fValue = value;
        fLabel = null;
    }

    /**
     * Constructor
     *
     * @param time
     *            Time
     * @param duration
     *            State duration
     * @param value
     *            Type of state (event type)
     * @param label
     *            State label
     */
    public TimeGraphState(long time, long duration, long value, String label) {
        fStartTime = time;
        fDuration = duration;
        fValue = value;
        fLabel = label;
    }

    @Override
    public long getStartTime() {
        return fStartTime;
    }

    @Override
    public long getDuration() {
        return fDuration;
    }

    @Override
    public long getValue() {
        return fValue;
    }

    @Override
    public @Nullable String getLabel() {
        return fLabel;
    }

    @Override
    public Map<String, String> fetchSpecificData() {
      Map<String, String> toTest = new HashMap<>();
      String label = getLabel();
      if (label != null) {
          toTest.put("label", label); //$NON-NLS-1$
      }
      return toTest;
    }

    @Override
    public void activateProperty(String key, boolean activate) {
        ACTIVE_PROPERTIES.put(key, activate);
    }

    @Override
    public boolean isPropertyActive(String property) {
        if (property.equals(IItemProperties.fullAlpha())) {
            return ACTIVE_PROPERTIES.getOrDefault(property, true);
        }
        return ACTIVE_PROPERTIES.getOrDefault(property, false);
    }

    @Override
    public void setProperties(@NonNull Map<@NonNull String, @NonNull Boolean> properties) {
        ACTIVE_PROPERTIES.putAll(properties);
    }

    @Override
    public Map<@NonNull String, @NonNull Boolean> getProperties() {
        return new HashMap<>(ACTIVE_PROPERTIES);
    }
}
