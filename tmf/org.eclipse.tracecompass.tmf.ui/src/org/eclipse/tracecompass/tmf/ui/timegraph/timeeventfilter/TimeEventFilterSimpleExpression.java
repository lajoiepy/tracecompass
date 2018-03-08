package org.eclipse.tracecompass.tmf.ui.timegraph.timeeventfilter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

import org.eclipse.tracecompass.tmf.ui.timegraph.timeeventfilter.TimeEventFilterSimpleExpressionCu.ConditionOperator;
import org.eclipse.tracecompass.tmf.ui.widgets.timegraph.model.ITimeEvent;

import com.google.common.collect.Iterables;

/**
 * @author ekoukad
 * @since 3.3
 *
 */
public class TimeEventFilterSimpleExpression implements BiPredicate<ITimeEvent, Function<ITimeEvent, Map<String, String>>> {

    private String fField;
    private ConditionOperator fOperator;
    private String fValue;

    public TimeEventFilterSimpleExpression(String field, ConditionOperator operator, String value) {
        fField = field;
        fOperator = operator;
        fValue = value;
    }

    @Override
    public boolean test(ITimeEvent event, Function<ITimeEvent, Map<String, String>> dataProvider) {

//        if (predicate.test(EMPTY_STRING)) {
//            return true;
//        }

        Map<String, String> toTest = new HashMap<>();
        String entryName = event.getEntry().getName();
        if (entryName != null) {
            toTest.put("entry", entryName); //$NON-NLS-1$
        }

        Map<String, String> tooltips = dataProvider.apply(event);
        if (tooltips != null && !tooltips.isEmpty()) {
            toTest.putAll(tooltips);
        }
        return Iterables.any(toTest.entrySet(), entry -> (fField.equals("*") || entry.getKey().equals(fField) || entry.getKey().equals("> " + fField)) && fOperator.test(entry.getValue(), fValue)); //$NON-NLS-1$ //$NON-NLS-2$
    }

}
