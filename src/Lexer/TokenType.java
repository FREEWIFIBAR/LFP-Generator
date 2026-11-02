package Lexer;

public enum TokenType {

	// Palabras clave
	NAME, IMPORT, FORMULAS, TRUE, FALSE,

	// Identificadores
	IDENTIFIER,

	// Operadores
	NOT, // ~
	AND, // ^ o and
	OR, // v o or
	IMPL, // -> o impl
	EQUIV, // <-> o equiv

	// Símbolos
	LPAREN, RPAREN, SEMICOLON, COLON, COMMA,

	EOF
}
