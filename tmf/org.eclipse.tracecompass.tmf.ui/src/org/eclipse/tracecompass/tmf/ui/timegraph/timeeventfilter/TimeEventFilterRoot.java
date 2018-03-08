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
public class TimeEventFilterRoot implements BiPredicate<ITimeEvent, Function<ITimeEvent, Map<String, String>>> {

    private Iterable<TimeEventFilterExpression> fExpressions;

    public TimeEventFilterRoot(Iterable<TimeEventFilterExpression> expressions) {
        fExpressions = expressions;
    }

    @Override
    public boolean test(ITimeEvent event, Function<ITimeEvent, Map<String, String>> dataProvider) {
        for (TimeEventFilterExpression expression : fExpressions) {
            if (!expression.test(event, dataProvider)) {
                return false;
            }
        }
        return true;
    }

}
