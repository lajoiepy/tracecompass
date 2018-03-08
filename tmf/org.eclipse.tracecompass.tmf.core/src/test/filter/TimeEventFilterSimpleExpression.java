package test.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

import org.eclipse.tracecompass.internal.provisional.tmf.core.model.timegraph.ITimeGraphState;

import com.google.common.collect.Iterables;

import test.filter.TimeEventFilterSimpleExpressionCu.ConditionOperator;

/**
 * @author ekoukad
 * @since 3.3
 *
 */
public class TimeEventFilterSimpleExpression implements BiPredicate<ITimeGraphState, Function<ITimeGraphState, Map<String, String>>> {

    private String fField;
    private ConditionOperator fOperator;
    private String fValue;

    public TimeEventFilterSimpleExpression(String field, ConditionOperator operator, String value) {
        fField = field;
        fOperator = operator;
        fValue = value;
    }

    @Override
    public boolean test(ITimeGraphState event, Function<ITimeGraphState, Map<String, String>> dataProvider) {

//        if (predicate.test(EMPTY_STRING)) {
//            return true;
//        }

        Map<String, String> toTest = new HashMap<>();
//        String entryName = event.getEntry().getName();
//        if (entryName != null) {
//            toTest.put("entryName", entryName); //$NON-NLS-1$
//        }

        String label = event.getLabel();
        if (label != null) {
            toTest.put("label", label);
        }

        Map<String, String> tooltips = dataProvider.apply(event);
        if (tooltips != null) {
            toTest.putAll(tooltips);
        }
        return Iterables.any(toTest.entrySet(), entry -> (fField.equals("*") || entry.getKey().equals(fField) || entry.getKey().equals("> " + fField)) && fOperator.test(entry.getValue(), fValue)); //$NON-NLS-1$ //$NON-NLS-2$
    }

}
