package AST;

public abstract class BinaryNode extends ASTNode {

	protected ASTNode left;
	protected ASTNode right;

	public BinaryNode(ASTNode left, ASTNode right) {
		this.left = left;
		this.right = right;
	}

	public ASTNode getLeft() {
		return left;
	}

	public ASTNode getRight() {
		return right;
	}
}
