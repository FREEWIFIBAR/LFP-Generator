package Compiler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import AST.ASTNode;
import Generator.CNFConverter;
import Generator.CNFTransformer;
import Generator.Clause;
import Generator.ClauseWriter;
import Graphviz.GraphVizExporter;
import Lexer.Lexer;
import Parser.Parser;
import Simplifier.Simplifier;

public class Compiler {

	public static void main(String[] args) {

		try {

			if (args.length != 1) {

				System.out.println("Uso: java Compiler <fichero_entrada>");

				return;
			}

			String inputFile = args[0];

			String contenido = Files.readString(Path.of(inputFile));

			// Analisis Lexico + Sintactico
			Lexer lexer = new Lexer(contenido);

			Parser parser = new Parser(lexer);

			parser.parseTeoria();

			// Obtener formulas
			List<ASTNode> formulas = parser.getFormulas();

			System.out.println("Numero de formulas: " + formulas.size());

			for (int i = 0; i < formulas.size(); i++) {

				System.out.println("\nFORMULA " + (i + 1));

				ASTNode original = formulas.get(i);

				// Mostrar AST original
				System.out.println("Formula original:");
				System.out.println(original);

				// Exportar AST original
				String originalDot = "ast_original_" + i + ".dot";

				GraphVizExporter.export(original, originalDot);

				System.out.println("AST original exportado: " + originalDot);

				// Simplificar
				ASTNode simplified = Simplifier.fullySimplify(original);

				System.out.println("\nFormula simplificada:");
				System.out.println(simplified);

				// Exportar AST simplificado
				String simplifiedDot = "ast_simplified_" + i + ".dot";

				GraphVizExporter.export(simplified, simplifiedDot);

				System.out.println("AST simplificado exportado: " + simplifiedDot);

				// Convertir a FNC
				ASTNode cnf = CNFTransformer.cnf(simplified);

				System.out.println("\nForma Normal Clausal:");
				System.out.println(cnf);

				// Exportar AST FNC
				String cnfDot = "ast_cnf_" + i + ".dot";

				GraphVizExporter.export(cnf, cnfDot);

				System.out.println("AST FNC exportado: " + cnfDot);

				// Extraer clausulas
				List<Clause> clauses = CNFConverter.extractClauses(cnf);

				System.out.println("\nClausulas generadas:");

				for (Clause c : clauses) {
					System.out.println(c);
				}

				// Guardar
				String claFile = "formula_" + i + ".cla";

				ClauseWriter.write(clauses, claFile);

				System.out.println("\nFichero CLA generado: " + claFile);
			}

		}

		catch (Exception e) {

			System.out.println("ERROR");

			System.out.println(e.getMessage());

			e.printStackTrace();
		}
	}
}
