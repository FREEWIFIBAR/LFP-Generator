package AST;

public class ImplNode extends BinaryNode {

	public ImplNode(ASTNode left, ASTNode right) {
		super(left, right);
	}

	@Override
	public String toString() {
		return "(" + left + " -> " + right + ")";
	}

	@Override
	public ASTNode simplify() {

		return new OrNode(new NotNode(left), right).simplify();
	}

	@Override
	public String getLabel() {
		return "->";
	}

	@Override
	public ASTNode toCNF() {
		return this;
	}
}
