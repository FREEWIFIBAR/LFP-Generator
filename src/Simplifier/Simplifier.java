package Simplifier;

import AST.ASTNode;

public class Simplifier {

	public static ASTNode fullySimplify(ASTNode node) {

	    ASTNode prev;

	    do {

	        prev = node;

	        node = node.simplify();

	    } while (!prev.toString().equals(node.toString()));

	    return node;
	}
}
