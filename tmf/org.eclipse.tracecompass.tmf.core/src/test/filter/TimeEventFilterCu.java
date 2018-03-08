package test.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.eclipse.tracecompass.internal.provisional.tmf.core.model.timegraph.ITimeGraphState;
import org.eclipse.tracecompass.ui.timegraph.filter.parser.FilterParserLexer;
import org.eclipse.tracecompass.ui.timegraph.filter.parser.FilterParserParser;
import org.eclipse.tracecompass.ui.timegraph.filter.parser.FilterParserParser.parse_return;

import com.google.common.collect.Iterables;

/**
 * @author ekoukad
 * @since 3.3
 *
 */
public class TimeEventFilterCu {

    List<TimeEventFilterExpressionCu> fExpressions;

    static {

    }
    public TimeEventFilterCu(List<TimeEventFilterExpressionCu> expressions) {
        fExpressions = expressions;
    }

    public static TimeEventFilterCu compile(String regex) {
        if (regex == null || regex.isEmpty()) {
            return null;
        }
        try {
            ANTLRStringStream stream = new ANTLRStringStream(regex);
            FilterParserLexer lexer = new FilterParserLexer(stream);
            boolean[] invalid = new boolean[1];
            lexer.setErrorListener(e -> invalid[0] = e instanceof RecognitionException);

            CommonTokenStream tokens = new CommonTokenStream();
            tokens.setTokenSource(lexer);

            FilterParserParser parser = new FilterParserParser(tokens);
            parser.setErrorListener(e -> invalid[0] |= e instanceof RecognitionException);
            parse_return parse = parser.parse();

            if (invalid[0]) {
                System.out.println("invalid filter");
                return null;
            }

            CommonTree tree = parse.getTree();
            List<CommonTree> children = tree.getChildren();
            List<TimeEventFilterExpressionCu> expressions = new ArrayList<>();
            for (CommonTree child : children) {
                TimeEventFilterExpressionCu compile = TimeEventFilterExpressionCu.compile(child);
                if (compile == null) {
                    return null;
                }
                expressions.add(compile);
            }
            return new TimeEventFilterCu(expressions);
        } catch (RecognitionException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public Predicate<ITimeGraphState> generate(Function<ITimeGraphState, Map<String, String>> tooltipProvider) {
//        Iterable<TimeEventFilterExpression> expressions = Iterables.transform(fExpressions, exp -> exp.generate(tooltipProvider));
//        return new TimeEventFilterRoot(expressions);
//    }

    public BiPredicate<ITimeGraphState, Function<ITimeGraphState, Map<String, String>>> generate() {
        Iterable<TimeEventFilterExpression> expressions = Iterables.transform(fExpressions, exp -> exp.generate());
        return new TimeEventFilterRoot(expressions);
    }
}
