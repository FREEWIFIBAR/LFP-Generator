package Graphviz;

import java.io.FileWriter;
import java.io.IOException;

import AST.ASTNode;
import AST.BinaryNode;
import AST.NotNode;

public class GraphVizExporter {

	public static void export(ASTNode root, String filename) throws IOException {

		FileWriter writer = new FileWriter(filename);

		writer.write("digraph AST {\n");

		traverse(root, writer);

		writer.write("}\n");

		writer.close();
	}

	private static void traverse(ASTNode node, FileWriter writer) throws IOException {

		if (node == null)
			return;

		writer.write("node" + node.getId() + " [label=\"" + node.getLabel() + "\"];\n");

		if (node instanceof BinaryNode binary) {

			ASTNode left = binary.getLeft();
			ASTNode right = binary.getRight();

			writer.write("node" + node.getId() + " -> node" + left.getId() + ";\n");

			writer.write("node" + node.getId() + " -> node" + right.getId() + ";\n");

			traverse(left, writer);
			traverse(right, writer);
		}

		else if (node instanceof NotNode not) {

			ASTNode expr = not.getExpr();

			writer.write("node" + node.getId() + " -> node" + expr.getId() + ";\n");

			traverse(expr, writer);
		}
	}
}
