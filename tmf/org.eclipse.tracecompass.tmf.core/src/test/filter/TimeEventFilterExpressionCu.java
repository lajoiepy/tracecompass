package test.filter;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.tracecompass.internal.tmf.core.Activator;

/**
 * @author ekoukad
 * @since 3.3
 *
 */
public class TimeEventFilterExpressionCu {

    private TimeEventFilterSimpleExpressionCu fLeftExpr;
    private String fLogicalOp;
    private TimeEventFilterSimpleExpressionCu fRightExpr;

    public TimeEventFilterExpressionCu(TimeEventFilterSimpleExpressionCu leftExpr, String logicalOp, TimeEventFilterSimpleExpressionCu rightExpr) {
        fLeftExpr = leftExpr;
        fLogicalOp = logicalOp;
        fRightExpr = rightExpr;
    }

    public static TimeEventFilterExpressionCu compile(CommonTree treeNode) {
        int childCount = treeNode.getChildCount();
        CommonTree leftTree = childCount > 0 ? (CommonTree) treeNode.getChild(0) : null;
        if (leftTree == null) {
            Activator.logError("At least one expression is needed", new IllegalArgumentException("Invalid time event filter"));
        }
        TimeEventFilterSimpleExpressionCu left = TimeEventFilterSimpleExpressionCu.compile(leftTree);
        if (left == null) {
            return null;
        }

        CommonTree opTree = childCount > 1 ? (CommonTree) treeNode.getChild(1) : null;
        String op = opTree != null ? opTree.getText() : null;

        CommonTree rightTree = childCount > 2 ? (CommonTree) treeNode.getChild(2) : null;
        TimeEventFilterSimpleExpressionCu right = rightTree != null ? TimeEventFilterSimpleExpressionCu.compile(rightTree) : null;

        return new TimeEventFilterExpressionCu(left, op, right);
    }


//    public TimeEventFilterExpression generate(Function<ITimeGraphState, Map<String, String>> tooltipProvider) {
//        TimeEventFilterSimpleExpression leftOperand = fLeftExpr.generate(tooltipProvider);
//        TimeEventFilterSimpleExpression rightOperand = fLogicalOp != null && fRightExpr != null ? fRightExpr.generate(tooltipProvider) : null;
//        return new TimeEventFilterExpression(leftOperand, fLogicalOp, rightOperand);
//    }

    public TimeEventFilterExpression generate() {
        TimeEventFilterSimpleExpression leftOperand = fLeftExpr.generate();
        TimeEventFilterSimpleExpression rightOperand = fLogicalOp != null && fRightExpr != null ? fRightExpr.generate() : null;
        return new TimeEventFilterExpression(leftOperand, fLogicalOp, rightOperand);
    }
}
