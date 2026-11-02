package AST;

public abstract class ASTNode {

	private static int counter = 0;

	protected int id;

	public ASTNode() {
		this.id = counter++;
	}

	public int getId() {
		return id;
	}

	public abstract ASTNode simplify();

	public abstract String getLabel();

	public abstract ASTNode toCNF();
}
