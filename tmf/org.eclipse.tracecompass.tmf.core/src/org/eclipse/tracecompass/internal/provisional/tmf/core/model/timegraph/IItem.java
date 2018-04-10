/**********************************************************************
 * Copyright (c) 2018 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **********************************************************************/
package org.eclipse.tracecompass.internal.provisional.tmf.core.model.timegraph;

import java.util.Collections;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Interface to get information from an item
 *
 * @author Jean-Christian Kouame
 *
 */
public interface IItem {

    /**
     * Activate/deactivate a property. The possible properties could be found in
     * {@link IItemProperties}
     *
     * @param key
     *            the property key
     * @param activate
     *            the activation status of the property
     */
    default void activateProperty(String key, boolean activate) {
        throw new UnsupportedOperationException("Not supported"); //$NON-NLS-1$
    }

    /**
     * Get the activation status of a specific property. The possible properties
     * could be found in {@link IItemProperties}
     *
     * @param property
     *            The property key
     * @return The property activation status
     */
    default boolean isPropertyActive(String property) {
        throw new UnsupportedOperationException("Not supported"); //$NON-NLS-1$
    }

    /**
     * Set item properties
     *
     * @param properties
     *            The properties to set
     */
    default void setProperties(Map<String, Boolean> properties) {
        throw new UnsupportedOperationException("Not supported"); //$NON-NLS-1$
    }

    /**
     * Get all the item properties
     *
     * @return The properties
     */
    default Map<@NonNull String, @NonNull Boolean> getProperties() {
        throw new UnsupportedOperationException("Not supported"); //$NON-NLS-1$
    }

    /**
     * Get data from an item and return it into a key-value map
     *
     * @return The map of data
     */
    default Map<String, String> fetchSpecificData() {
        return Collections.emptyMap();
    }
}
