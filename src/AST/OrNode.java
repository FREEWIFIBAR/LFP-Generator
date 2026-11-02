package AST;

public class OrNode extends BinaryNode {

	public OrNode(ASTNode left, ASTNode right) {
		super(left, right);
	}

	@Override
	public String toString() {
		return "(" + left + " v " + right + ")";
	}

	@Override
	public ASTNode simplify() {

		ASTNode l = left.simplify();
		ASTNode r = right.simplify();

		// p v false = p
		if (r instanceof BooleanNode b && !b.getValue()) {
			return l;
		}

		if (l instanceof BooleanNode b && !b.getValue()) {
			return r;
		}

		// p v true = true
		if (r instanceof BooleanNode b && b.getValue()) {
			return new BooleanNode(true);
		}

		if (l instanceof BooleanNode b && b.getValue()) {
			return new BooleanNode(true);
		}

		// p v ~p = true
		if (l.toString().equals(new NotNode(r).toString())) {
			return new BooleanNode(true);
		}

		return new OrNode(l, r);
	}

	@Override
	public String getLabel() {
		return "v";
	}

	@Override
	public ASTNode toCNF() {

		ASTNode l = left.toCNF();

		ASTNode r = right.toCNF();

		// p v (q ^ r)
		if (r instanceof AndNode and) {

			return new AndNode(

					new OrNode(l, and.getLeft()).toCNF(),

					new OrNode(l, and.getRight()).toCNF());
		}

		// (p ^ q) v r
		if (l instanceof AndNode and) {

			return new AndNode(

					new OrNode(and.getLeft(), r).toCNF(),

					new OrNode(and.getRight(), r).toCNF());
		}

		return new OrNode(l, r);
	}
}
