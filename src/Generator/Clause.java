package Generator;

import java.util.*;

public class Clause {

	private List<Literal> literals = new ArrayList<>();

	public void addLiteral(Literal l) {
		literals.add(l);
	}

	@Override
	public String toString() {

		return literals.toString();
	}
}
