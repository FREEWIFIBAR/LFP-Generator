package Generator;

import java.util.*;

import AST.ASTNode;
import AST.AndNode;
import AST.IdentifierNode;
import AST.NotNode;
import AST.OrNode;

public class CNFConverter {

	public static List<Clause> extractClauses(ASTNode node) {

		List<Clause> clauses = new ArrayList<>();

		collectClauses(node, clauses);

		return clauses;
	}

	private static void collectClauses(ASTNode node, List<Clause> clauses) {

		if (node instanceof AndNode and) {

			collectClauses(and.getLeft(), clauses);

			collectClauses(and.getRight(), clauses);
		}

		else {

			Clause clause = new Clause();

			collectLiterals(node, clause);

			clauses.add(clause);
		}
	}

	private static void collectLiterals(ASTNode node, Clause clause) {

		if (node instanceof OrNode or) {

			collectLiterals(or.getLeft(), clause);

			collectLiterals(or.getRight(), clause);
		}

		else if (node instanceof NotNode not) {

			ASTNode expr = not.getExpr();

			if (expr instanceof IdentifierNode id) {

				clause.addLiteral(new Literal(id.getName(), true));
			}
		}

		else if (node instanceof IdentifierNode id) {

			clause.addLiteral(new Literal(id.getName(), false));
		}
	}
}
