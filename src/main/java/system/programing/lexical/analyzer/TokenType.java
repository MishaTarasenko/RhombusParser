package system.programing.lexical.analyzer;

public enum TokenType {
    IDENTIFIER,       // Identifiers
    FUNCTION,         // Built-in functions (ПОБУДУВАТИ, ПРОВЕСТИ, ЗНАЙТИ, )
    KEYWORDS,         // Keywords (РОМБ, ДІАГОНАЛЬ, ВИСОТА, ПЛОЩА...)
    DELIMITERS,
    ASSIGNMENT,
    NUMBER,
    UNIT,
    RESERVED,
    INVALID           // Invalid lexemes
}
