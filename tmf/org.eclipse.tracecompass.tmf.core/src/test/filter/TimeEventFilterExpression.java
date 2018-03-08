package test.filter;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

import org.eclipse.tracecompass.internal.provisional.tmf.core.model.timegraph.ITimeGraphState;

/**
 * @author ekoukad
 * @since 3.3
 *
 */
public class TimeEventFilterExpression implements BiPredicate<ITimeGraphState, Function<ITimeGraphState, Map<String, String>>> {

    private TimeEventFilterSimpleExpression fLeftOperand;
    private TimeEventFilterSimpleExpression fRightOperand;
    private String fLogicalOperator;
    public TimeEventFilterExpression(TimeEventFilterSimpleExpression leftOperand, String logicalOp, TimeEventFilterSimpleExpression rightOperand) {
        fLeftOperand = leftOperand;
        fRightOperand = rightOperand;
        fLogicalOperator = logicalOp;
    }

    @Override
    public boolean test(ITimeGraphState event, Function<ITimeGraphState, Map<String, String>> dataProvider) {
        boolean result = fLeftOperand.test(event, dataProvider);

        if (fLogicalOperator != null && !fLogicalOperator.isEmpty()) {
            if (fLogicalOperator.equals("||")) {
                result = result || fRightOperand.test(event, dataProvider);
            } else if (fLogicalOperator.equals("&&") && fRightOperand != null) {
                result = result && fRightOperand.test(event, dataProvider);
            }
        }

        return result;
    }
}
