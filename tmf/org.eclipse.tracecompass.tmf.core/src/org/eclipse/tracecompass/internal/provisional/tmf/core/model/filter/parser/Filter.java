/*******************************************************************************
 * Copyright (c) 2018 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.tracecompass.internal.provisional.tmf.core.model.filter.parser;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

import org.eclipse.tracecompass.internal.provisional.tmf.core.model.timegraph.IItem;

/**
 * Item filter runtime object
 *
 * @author Jean-Christian Kouame
 * @since 3.3
 *
 */
public class Filter implements BiPredicate<IItem, Function<IItem, Map<String, String>>> {

    private Iterable<FilterExpression> fExpressions;

    /**
     * Constructor
     *
     * @param expressions
     *            The list of filter expression to test
     */
    public Filter(Iterable<FilterExpression> expressions) {
        fExpressions = expressions;
    }

    @Override
    public boolean test(IItem item, Function<IItem, Map<String, String>> dataProvider) {
        for (FilterExpression expression : fExpressions) {
            if (!expression.test(item, dataProvider)) {
                return false;
            }
        }
        return true;
    }

}
