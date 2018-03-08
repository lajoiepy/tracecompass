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
public class TimeEventFilterRoot implements BiPredicate<ITimeGraphState, Function<ITimeGraphState, Map<String, String>>> {

    private Iterable<TimeEventFilterExpression> fExpressions;

    public TimeEventFilterRoot(Iterable<TimeEventFilterExpression> expressions) {
        fExpressions = expressions;
    }

    @Override
    public boolean test(ITimeGraphState event, Function<ITimeGraphState, Map<String, String>> dataProvider) {
        for (TimeEventFilterExpression expression : fExpressions) {
            if (!expression.test(event, dataProvider)) {
                return false;
            }
        }
        return true;
    }

}
