package wci.backend.interpreter.executors;
import java.util.ArrayList;

import wci.intermediate.*;
import wci.backend.interpreter.*;

import static wci.intermediate.ICodeNodeType.*;
import static wci.backend.interpreter.RuntimeErrorCode.*;

/**
 * <h1>WhenExecutor</h1>
 *
 * <p>Execute an When statement.</p>
 *
 */
public class WhenExecutor extends StatementExecutor{
	/**
     * Constructor.
     * @param the parent executor.
     */
    public WhenExecutor(Executor parent)
    {
        super(parent);
    }

    /**
     * Execute an WHEN statement.
     * @param node the root node of the statement.
     * @return null.
     */
    public Object execute(ICodeNode node)
    {
        // Get the WHEN node's children.
        ArrayList<ICodeNode> children = node.getChildren();
        ICodeNode exprNode = children.get(0);
        ICodeNode alsoStmtNode = children.get(1);
        ICodeNode otherwiseStmtNode = children.size() > 2 ? children.get(2) : null;

        ExpressionExecutor expressionExecutor = new ExpressionExecutor(this);
        StatementExecutor statementExecutor = new StatementExecutor(this);

        // Evaluate the expression to determine which statement to execute.
        boolean b = (Boolean) expressionExecutor.execute(exprNode);
        if (b) {
            statementExecutor.execute(alsoStmtNode);
        }
        else if (otherwiseStmtNode != null) {
            statementExecutor.execute(otherwiseStmtNode);
        }

        ++executionCount;  // count the WHEN statement itself
        return null;
    }

}

