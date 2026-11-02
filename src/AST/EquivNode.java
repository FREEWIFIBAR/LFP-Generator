package AST;

public class EquivNode extends BinaryNode {

	public EquivNode(ASTNode left, ASTNode right) {
		super(left, right);
	}

	@Override
	public String toString() {
		return "(" + left + " <-> " + right + ")";
	}

	@Override
	public ASTNode simplify() {

		ASTNode p1 = new ImplNode(left, right);

		ASTNode p2 = new ImplNode(right, left);

		return new AndNode(p1, p2).simplify();
	}

	@Override
	public String getLabel() {
		return "<->";
	}

	@Override
	public ASTNode toCNF() {
		return this;
	}
}
