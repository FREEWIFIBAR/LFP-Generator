package AST;

public class IdentifierNode extends ASTNode {

	private String name;

	public IdentifierNode(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public ASTNode simplify() {
		return this;
	}

	@Override
	public String getLabel() {
		return name;
	}

	@Override
	public ASTNode toCNF() {
		return this;
	}

	public String getName() {
		return name;
	}
}
