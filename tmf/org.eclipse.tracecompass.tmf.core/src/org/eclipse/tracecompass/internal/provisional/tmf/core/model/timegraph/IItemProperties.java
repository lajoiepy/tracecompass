/**********************************************************************
 * Copyright (c) 2018 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **********************************************************************/
package org.eclipse.tracecompass.internal.provisional.tmf.core.model.timegraph;

/**
 * Interface that contains the list of possible properties for a timegraph item
 *
 * @author Jean-Christian Kouame
 *
 */
@SuppressWarnings("nls")
public interface IItemProperties {

    /**
     * get the alpha property key string
     *
     * @return The alpha property key string
     */
    public static String fullAlpha() {
        return "full.alpha";
    }

    /**
     * get the alpha property key string
     *
     * @return The alpha property key string
     */
    public static String drawBound() {
        return "bound";
    }
}
