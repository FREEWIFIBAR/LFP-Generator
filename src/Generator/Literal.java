package Generator;

public class Literal {

	private String name;

	private boolean negated;

	public Literal(String name, boolean negated) {
		this.name = name;
		this.negated = negated;
	}

	@Override
	public String toString() {

		return negated ? "~" + name : name;
	}
}
