/*******************************************************************************
 * Copyright (c) 2018 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.tracecompass.internal.provisional.tmf.core.model.filter.parser;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.tracecompass.internal.tmf.core.Activator;
import org.eclipse.tracecompass.tmf.ui.view.filter.parser.FilterParserParser;

/**
 * Compilation unit for a simple filter expression
 *
 * @author Jean-Christian Kouame
 * @since 3.3
 *
 */
public class FilterSimpleExpressionCu {

    private String fField;
    private BiPredicate<String, String> fOperator;
    private String fValue;

    /**
     * Constructor
     *
     * @param field
     *            The field to look for
     * @param op
     *            The operator to use for the test
     * @param value
     *            The value to to test
     */
    public FilterSimpleExpressionCu(String field, BiPredicate<String, String> op, String value) {
        fField = field;
        fOperator = op;
        fValue = value;
    }

    /**
     * Compile a simple filter expression compilation unit from a tree
     *
     * @param tree
     *            The input tree
     * @return The simple filter expression compilation unit
     */
    public static FilterSimpleExpressionCu compile(CommonTree tree) {
        if (tree.getToken() == null) {
            return null;
        }
        switch (tree.getToken().getType()) {
        case FilterParserParser.SIMPLE_EXP:
            return FilterSimpleExpressionCu.compile((CommonTree) tree.getChild(0));
        case FilterParserParser.CONSTANT:
            return new FilterSimpleExpressionCu(IFilterStrings.WILDCARD, ConditionOperator.CONTAINS, tree.getChild(0).getText());
        case FilterParserParser.OPERATION:
            String left = tree.getChild(0).getText();
            BiPredicate<String, String> op = getConditionOperator(tree.getChild(1).getText());
            String right = tree.getChild(2).getText();
            return new FilterSimpleExpressionCu(left, op, right);
        case FilterParserParser.OPERATION1:
            String left1 = tree.getChild(0).getText();
            BiPredicate<String, String> op1 = getConditionOperator(tree.getChild(1).getText());
            String right1 = null;
            return new FilterSimpleExpressionCu(left1, op1, right1);
        case FilterParserParser.OPERATION2:
            BiPredicate<String, String> op2 = getConditionOperator(tree.getChild(0).getText());
            String left2 = IFilterStrings.WILDCARD;
            String right2 = tree.getChild(1).getText();
            return new FilterSimpleExpressionCu(left2, op2, right2);
        default:
            break;
        }
        return null;
    }

    /**
     * Generates a filter simple expression runtime object
     *
     * @return The filter simple expression
     */
    public FilterSimpleExpression generate() {
        return new FilterSimpleExpression(fField, fOperator, fValue);
    }

    private static BiPredicate<String, String> getConditionOperator(String equationType) {
        switch (equationType) {
        case IFilterStrings.EQUAL:
            return ConditionOperator.EQ;
        case IFilterStrings.NOT_EQUAL:
            return ConditionOperator.NE;
        case IFilterStrings.MATCHES:
            return ConditionOperator.MATCHES;
        case IFilterStrings.CONTAINS:
            return ConditionOperator.CONTAINS;
        case IFilterStrings.PRESENT:
            return ConditionOperator.PRESENT;
        case IFilterStrings.NOT:
            return ConditionOperator.NOT;
        default:
            throw new IllegalArgumentException("TmfXmlCondition: invalid comparison operator."); //$NON-NLS-1$
        }
    }

    /**
     * Condition operators used to compare 2 values together
     */
    protected enum ConditionOperator implements BiPredicate<String, String> {
        /** equal */
        EQ((i, j) -> i.equals(j)),
        /** not equal */
        NE((i, j) -> !i.equals(j)),
        /** Matches */
        MATCHES((i, j) -> i.matches(j)),
        /** Contains*/
        CONTAINS((i, j) -> i.contains(j)),
        /** Less than */
        LT((i, j) -> lessThanComparison(i, j)),
        /** Greater than */
        GT((i, j) -> greaterThanComparison(i, j)),
        /** Field is present*/
        PRESENT((i, j) -> true),
        /** not */
        NOT((i, j) -> CONTAINS.test(i, j));

        private final BiFunction<String, String, Boolean> fCmpFunction;

        ConditionOperator(BiFunction<String, String, Boolean> cmpFunction) {
            fCmpFunction = cmpFunction;
        }

        private static boolean greaterThanComparison(String i, String j) {
            try {
                long long1 = Long.parseLong(i);
                long long2 = Long.parseLong(j);
                return long1 > long2;
            } catch (NumberFormatException e) {
                Activator.logWarning("The search criteria is not a number"); //$NON-NLS-1$
                return false;
            }
        }

        private static boolean lessThanComparison(String i, String j) {
            try {
                long long1 = Long.parseLong(i);
                long long2 = Long.parseLong(j);
                return long1 < long2;
            } catch (NumberFormatException e) {
                Activator.logWarning("The search criteria is not a number"); //$NON-NLS-1$
                return false;
            }
        }

        @Override
        public boolean test(String arg0, String arg1) {
            return Objects.requireNonNull(fCmpFunction.apply(arg0, arg1));
        }
    }
}
