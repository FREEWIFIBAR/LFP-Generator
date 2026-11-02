package Generator;

import AST.ASTNode;
import Simplifier.Simplifier;

public class CNFTransformer {

	public static ASTNode cnf(ASTNode node) {

		node = Simplifier.fullySimplify(node);

		node = node.toCNF();

		node = Simplifier.fullySimplify(node);

		return node;
	}
}
