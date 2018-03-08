package org.eclipse.tracecompass.tmf.ui.timegraph.timeeventfilter;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.ITimeEvent;

/**
 * @author ekoukad
 * @since 3.3
 *
 */
public class TimeEventFilterExpression implements BiPredicate<ITimeEvent, Function<ITimeEvent, Map<String, String>>> {

    private TimeEventFilterSimpleExpression fLeftOperand;
    private TimeEventFilterSimpleExpression fRightOperand;
    private String fLogicalOperator;
    public TimeEventFilterExpression(TimeEventFilterSimpleExpression leftOperand, String logicalOp, TimeEventFilterSimpleExpression rightOperand) {
        fLeftOperand = leftOperand;
        fRightOperand = rightOperand;
        fLogicalOperator = logicalOp;
    }

    @Override
    public boolean test(ITimeEvent event, Function<ITimeEvent, Map<String, String>> dataProvider) {
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
