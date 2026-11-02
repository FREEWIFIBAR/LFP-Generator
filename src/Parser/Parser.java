package Parser;

import java.util.ArrayList;
import java.util.List;

import AST.ASTNode;
import AST.AndNode;
import AST.BooleanNode;
import AST.EquivNode;
import AST.IdentifierNode;
import AST.ImplNode;
import AST.NotNode;
import AST.OrNode;
import Lexer.Lexer;
import Lexer.Token;
import Lexer.TokenType;

public class Parser {

	private Lexer lexer;
	private Token current;

	private List<ASTNode> formulas = new ArrayList<>();

	public Parser(Lexer lexer) {
		this.lexer = lexer;
		this.current = lexer.nextToken();
	}

	private void eat(TokenType type) {
		if (current.type == type) {
			current = lexer.nextToken();
		} else {
			throw new RuntimeException("Error sintactico. Esperado: " + type + " pero encontrado: " + current.type);
		}
	}

	public void parseTeoria() {
		parseNombre();
		parseImportaciones();
		parseFormulas();

		eat(TokenType.EOF);

		System.out.println("Sintaxis correcta");
	}

	private void parseNombre() {
		eat(TokenType.NAME);
		eat(TokenType.IDENTIFIER);
		eat(TokenType.SEMICOLON);
	}

	private void parseImportaciones() {

		while (current.type == TokenType.IMPORT) {
			parseImportacion();
		}
	}

	private void parseImportacion() {

		eat(TokenType.IMPORT);

		eat(TokenType.IDENTIFIER);

		while (current.type == TokenType.COMMA) {
			eat(TokenType.COMMA);
			eat(TokenType.IDENTIFIER);
		}

		eat(TokenType.SEMICOLON);
	}

	private void parseFormulas() {

		eat(TokenType.FORMULAS);
		eat(TokenType.COLON);

		while (current.type == TokenType.NOT || current.type == TokenType.LPAREN || current.type == TokenType.TRUE
				|| current.type == TokenType.FALSE || current.type == TokenType.IDENTIFIER) {

			ASTNode formula = parseFBF();

			formulas.add(formula);

			eat(TokenType.SEMICOLON);
		}
	}

	private ASTNode parseFBF() {

		ASTNode left = parseImpl();

		if (current.type == TokenType.EQUIV) {

			eat(TokenType.EQUIV);

			ASTNode right = parseFBF();

			return new EquivNode(left, right);
		}

		return left;
	}

	private ASTNode parseImpl() {

		ASTNode left = parseOr();

		if (current.type == TokenType.IMPL) {

			eat(TokenType.IMPL);

			ASTNode right = parseImpl();

			return new ImplNode(left, right);
		}

		return left;
	}

	private ASTNode parseOr() {

		ASTNode left = parseAnd();

		if (current.type == TokenType.OR) {

			eat(TokenType.OR);

			ASTNode right = parseOr();

			return new OrNode(left, right);
		}

		return left;
	}

	private ASTNode parseAnd() {

		ASTNode left = parseNot();

		if (current.type == TokenType.AND) {

			eat(TokenType.AND);

			ASTNode right = parseAnd();

			return new AndNode(left, right);
		}

		return left;
	}

	private ASTNode parseNot() {

		if (current.type == TokenType.NOT) {

			eat(TokenType.NOT);

			return new NotNode(parseNot());
		}

		return parsePrimario();
	}

	private ASTNode parsePrimario() {

		switch (current.type) {

		case LPAREN:

			eat(TokenType.LPAREN);

			ASTNode node = parseFBF();

			eat(TokenType.RPAREN);

			return node;

		case TRUE:

			eat(TokenType.TRUE);

			return new BooleanNode(true);

		case FALSE:

			eat(TokenType.FALSE);

			return new BooleanNode(false);

		case IDENTIFIER:

			String name = current.lexeme;

			eat(TokenType.IDENTIFIER);

			return new IdentifierNode(name);

		default:

			throw new RuntimeException("Token inesperado: " + current.type);
		}
	}

	public List<ASTNode> getFormulas() {
		return formulas;
	}

}
