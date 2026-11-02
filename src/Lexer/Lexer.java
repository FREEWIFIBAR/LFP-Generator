package Lexer;

public class Lexer {

	private String input;
	private int pos = 0;

	public Lexer(String input) {
		this.input = input;
	}

	private char peek() {
		if (pos >= input.length())
			return '\0';
		return input.charAt(pos);
	}

	private char advance() {
		return input.charAt(pos++);
	}

	private void skipWhitespace() {
		while (Character.isWhitespace(peek())) {
			advance();
		}
	}

	public Token nextToken() {
		skipWhitespace();

		char c = peek();

		// EOF
		if (c == '\0') {
			return new Token(TokenType.EOF, "");
		}

		// Simbolos
		switch (c) {
		case '(':
			advance();
			return new Token(TokenType.LPAREN, "(");
		case ')':
			advance();
			return new Token(TokenType.RPAREN, ")");
		case ';':
			advance();
			return new Token(TokenType.SEMICOLON, ";");
		case ':':
			advance();
			return new Token(TokenType.COLON, ":");
		case ',':
			advance();
			return new Token(TokenType.COMMA, ",");
		case '~':
			advance();
			return new Token(TokenType.NOT, "~");
		}

		// Operadores compuestos
		if (c == '<') {
			if (input.startsWith("<->", pos)) {
				pos += 3;
				return new Token(TokenType.EQUIV, "<->");
			}
		}

		if (c == '-') {
			if (input.startsWith("->", pos)) {
				pos += 2;
				return new Token(TokenType.IMPL, "->");
			}
		}

		if (c == '^') {
			advance();
			return new Token(TokenType.AND, "^");
		}

		if (c == 'v') {
			advance();
			return new Token(TokenType.OR, "v");
		}

		// Identificadores y palabras clave
		if (Character.isLetter(c)) {
			StringBuilder sb = new StringBuilder();

			while (Character.isLetterOrDigit(peek())) {
				sb.append(advance());
			}

			String word = sb.toString();

			switch (word) {
			case "name":
				return new Token(TokenType.NAME, word);
			case "import":
				return new Token(TokenType.IMPORT, word);
			case "formulas":
				return new Token(TokenType.FORMULAS, word);
			case "true":
				return new Token(TokenType.TRUE, word);
			case "false":
				return new Token(TokenType.FALSE, word);

			case "and":
				return new Token(TokenType.AND, word);
			case "or":
				return new Token(TokenType.OR, word);
			case "impl":
				return new Token(TokenType.IMPL, word);
			case "equiv":
				return new Token(TokenType.EQUIV, word);

			default:
				return new Token(TokenType.IDENTIFIER, word);
			}
		}

		throw new RuntimeException("Caracter inesperado: " + c);
	}
}
