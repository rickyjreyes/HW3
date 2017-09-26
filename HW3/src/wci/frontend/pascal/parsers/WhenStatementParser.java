package wci.frontend.pascal.parsers;
import java.util.EnumSet;

import wci.frontend.*;
import wci.frontend.pascal.*;
import wci.intermediate.*;
import wci.intermediate.icodeimpl.*;

import static wci.frontend.pascal.PascalTokenType.*;
import static wci.frontend.pascal.PascalErrorCode.*;
import static wci.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static wci.intermediate.icodeimpl.ICodeKeyImpl.*;

/**
 * <h1>WhenStatementParser</h1>
 *
 * <p>Parse a Pascal WHEN statement.</p>
 *
 */
public class WhenStatementParser extends StatementParser{
	/**
     * Constructor.
     * @param parent the parent parser.
     */
    public WhenStatementParser(PascalParserTD parent)
    {
        super(parent);
    }
    
    // Synchronization set for THEN.
    private static final EnumSet<PascalTokenType> ALSO_SET =
        StatementParser.STMT_START_SET.clone();
    static {
        ALSO_SET.add(ALSO);
        ALSO_SET.addAll(StatementParser.STMT_FOLLOW_SET);
    }
    /**
     * Parse an WHEN statement.
     * @param token the initial token.
     * @return the root node of the generated parse tree.
     * @throws Exception if an error occurred.
     */
    public ICodeNode parse(Token token)
        throws Exception
    {
        token = nextToken();  // consume the WHEN

        // Create an WHEN node.
        ICodeNode whenNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.WHEN);

        // Parse the expression.
        // The WHEN node adopts the expression subtree as its first child.
        ExpressionParser expressionParser = new ExpressionParser(this);
        whenNode.addChild(expressionParser.parse(token));

        // Synchronize at the ALSO.
        token = synchronize(ALSO_SET);
        if (token.getType() == ALSO) {
            token = nextToken();  // consume the ALSO "=>"
        }
        else {
            errorHandler.flag(token, MISSING_ALSO, this);
        }

        // Parse the ALSO statement.
        // The WHEN node adopts the statement subtree as its second child.
        StatementParser statementParser = new StatementParser(this);
        whenNode.addChild(statementParser.parse(token));
        token = currentToken();

        // Look for an OTHERWISE.
        if (token.getType() == OTHERWISE) {
            token = nextToken();  // consume the ALSO

            // Parse the OTHERWISE statement.
            // The WHEN node adopts the statement subtree as its third child.
            whenNode.addChild(statementParser.parse(token));
        }

        return whenNode;
    }

}