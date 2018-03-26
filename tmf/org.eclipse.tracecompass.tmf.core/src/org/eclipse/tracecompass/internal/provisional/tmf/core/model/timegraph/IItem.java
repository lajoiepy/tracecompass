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

/**
 * Interface to get information from an item
 *
 * @author Jean-Christian Kouame
 *
 */
public interface IItem {
    /**
     * Tells whether the item is annotated or not.
     *
     * @return True if the item is annotated, false otherwise
     */
    default boolean isNotCool() {
        return false;
    }

    /**
     * Set annotated state of the item.
     *
     * @param isNotCool
     *            The annotated status of the item
     */
    default void setNotCool(boolean isNotCool) {
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
