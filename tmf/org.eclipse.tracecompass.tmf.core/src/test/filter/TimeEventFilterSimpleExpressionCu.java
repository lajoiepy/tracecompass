package test.filter;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.tracecompass.internal.tmf.core.Activator;
import org.eclipse.tracecompass.ui.timegraph.filter.parser.FilterParserParser;

/**
 * @author Jean-Christian Kouame
 * @since 3.3
 *
 */
public class TimeEventFilterSimpleExpressionCu {

    private String fField;
    private ConditionOperator fOperator;
    private String fValue;

    /**
     * @param field
     * @param op
     * @param value
     */
    public TimeEventFilterSimpleExpressionCu(String field, ConditionOperator op, String value) {
        fField = field;
        fOperator = op;
        fValue = value;
    }

    public static TimeEventFilterSimpleExpressionCu compile(CommonTree tree) {
        if (tree.getToken() == null) {
            return null;
        }
        switch (tree.getToken().getType()) {
        case FilterParserParser.SIMPLE_EXP:
            return TimeEventFilterSimpleExpressionCu.compile((CommonTree)tree.getChild(0));
        case FilterParserParser.CONSTANT:
            return new TimeEventFilterSimpleExpressionCu("*", ConditionOperator.CONTAINS, tree.getChild(0).getText());
        case FilterParserParser.OPERATION:
            String left = tree.getChild(0).getText();
            ConditionOperator op = getConditionOperator(tree.getChild(1).getText());
            String right = tree.getChild(2).getText();
            return new TimeEventFilterSimpleExpressionCu(left, op, right);
        default:
            break;
        }
        return null;
    }

//    public TimeEventFilterSimpleExpression generate(Function<ITimeGraphState, Map<String, String>> tooltipProvider) {
//        return new TimeEventFilterSimpleExpression(fField, fOperator, fValue, tooltipProvider);
//    }

    private static ConditionOperator getConditionOperator(String equationType) {
        switch (equationType) {
        case "==":
            return ConditionOperator.EQ;
        case "!=":
            return ConditionOperator.NE;
        case "MATCHES":
            return ConditionOperator.MATCHES;
        case "CONTAINS":
            return ConditionOperator.CONTAINS;
        case ">" :
            return ConditionOperator.GT;
        case "<" :
            return ConditionOperator.LT;
        default:
            throw new IllegalArgumentException("TmfXmlCondition: invalid comparison operator."); //$NON-NLS-1$
        }
    }

    /**
     * Condition operators used to compare 2 values together
     */
    public enum ConditionOperator implements BiPredicate<String, String> {
        /** equal */
        EQ((i, j) -> i.equals(j)),
        /** not equal */
        NE((i, j) -> !i.equals(j)),
        /** Greater or equal */
        MATCHES((i, j)-> i.matches(j)),
        /** Greater than */
        CONTAINS((i, j) -> i.contains(j)),
        /** Less or equal */
        LT((i, j) -> lessThanComparison(i,j)),
        /** Less than */
        GT((i,j) -> greaterThanComparison(i,j));

        private final BiFunction<String, String, Boolean> fCmpFunction;

        ConditionOperator(BiFunction<String, String, Boolean> cmpFunction) {
            fCmpFunction = cmpFunction;
        }

        private static boolean greaterThanComparison(String i, String j) {
            try {
                long long1 = Long.parseLong(i);
                long long2 = Long.parseLong(j);
                return long1 > long2;
            } catch (NumberFormatException  e) {
                Activator.logWarning("The search criteria is not a number"); //$NON-NLS-1$
                return false;
            }
        }

        private static boolean lessThanComparison(String i, String j) {
            try {
                long long1 = Long.parseLong(i);
                long long2 = Long.parseLong(j);
                return long1 < long2;
            } catch (NumberFormatException  e) {
                Activator.logWarning("The search criteria is not a number"); //$NON-NLS-1$
                return false;
            }
        }

        @Override
        public boolean test(String arg0, String arg1) {
            return Objects.requireNonNull(fCmpFunction.apply(arg0, arg1));
        }
    }

    public TimeEventFilterSimpleExpression generate() {
        return new TimeEventFilterSimpleExpression(fField, fOperator, fValue);}
}
