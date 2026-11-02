package AST;

public class BooleanNode extends ASTNode {

	private boolean value;

	public BooleanNode(boolean value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}

	@Override
	public ASTNode simplify() {
		return this;
	}

	@Override
	public String getLabel() {
		return Boolean.toString(value);
	}

	public boolean getValue() {
		return value;
	}

	@Override
	public ASTNode toCNF() {
		return this;
	}
}
