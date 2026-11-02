package AST;

public class NotNode extends ASTNode {

	private ASTNode expr;

	public NotNode(ASTNode expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "~" + expr;
	}

	@Override
	public String getLabel() {
		return "~";
	}

	@Override
	public ASTNode simplify() {

		ASTNode simplified = expr.simplify();

		// Doble negacion
		if (simplified instanceof NotNode not) {

			return not.getExpr().simplify();
		}

		// Negacion de constante
		if (simplified instanceof BooleanNode bool) {

			return new BooleanNode(!bool.getValue());
		}

		// De Morgan AND
		if (simplified instanceof AndNode and) {

			return new OrNode(new NotNode(and.getLeft()).simplify(), new NotNode(and.getRight()).simplify());
		}

		// De Morgan OR
		if (simplified instanceof OrNode or) {

			return new AndNode(new NotNode(or.getLeft()).simplify(), new NotNode(or.getRight()).simplify());
		}

		return new NotNode(simplified);
	}

	public ASTNode getExpr() {
		return expr;
	}

	@Override
	public ASTNode toCNF() {
		return this;
	}
}
