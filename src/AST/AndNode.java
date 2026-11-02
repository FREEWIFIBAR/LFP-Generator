package AST;

public class AndNode extends BinaryNode {

	public AndNode(ASTNode left, ASTNode right) {
		super(left, right);
	}

	@Override
	public String toString() {
		return "(" + left + " ^ " + right + ")";
	}

	@Override
	public ASTNode simplify() {

		ASTNode l = left.simplify();
		ASTNode r = right.simplify();

		// p ^ true = p
		if (r instanceof BooleanNode b && b.getValue()) {
			return l;
		}

		if (l instanceof BooleanNode b && b.getValue()) {
			return r;
		}

		// p ^ false = false
		if (r instanceof BooleanNode b && !b.getValue()) {
			return new BooleanNode(false);
		}

		if (l instanceof BooleanNode b && !b.getValue()) {
			return new BooleanNode(false);
		}

		// p ^ ~p = false
		if (l.toString().equals(new NotNode(r).toString())) {
			return new BooleanNode(false);
		}

		return new AndNode(l, r);
	}

	@Override
	public String getLabel() {
		return "^";
	}

	@Override
	public ASTNode toCNF() {

		return new AndNode(left.toCNF(), right.toCNF());
	}
}
