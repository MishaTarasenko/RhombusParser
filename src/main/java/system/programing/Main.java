package system.programing;

import system.programing.data.analyzer.DataAnalyzer;
import system.programing.input.analyzer.FirstAnalyzer;
import system.programing.input.parser.InputParser;
import system.programing.lexical.analyzer.Analyzer;
import system.programing.lexical.analyzer.Token;
import system.programing.lexical.analyzer.TokenType;
import system.programing.syntax.analyzer.ParseTreeNode;
import system.programing.syntax.analyzer.SyntaxAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    private static final List<String> inputs = List.of(
            "Знайдіть всі кути ромба, якщо його периметр дорівнює 24 см, а висота - 3 см.",
            "Знайдіть периметр ромба ABCD, якщо ∠A = 60°, сторона BD = 9 см.",
            "Одна з діагоналей ромба дорівнює 30 см. Знайти іншу діагональ ромба, якщо його периметр дорівнює 68 см.",
            "Сторона ромба дорівнює 6 см, а його площа – 18 см2. Знайти найбільший кут ромба.",
            "Периметр ромба ABCD дорівнює 52 см, AN дорівнює 12 см, а DN – 5 см, знайдіть AD.",
            "Периметр ромба ABCD дорівнює 52 см, AN дорівнює 12 см, а DN – 5 см, знайдіть AC.",
            "У ромбі ABCD всі сторони = 8 см. Знайдіть його периметр.",
            "Діагональ ромба дорівнює 10 см, а інша - 24 см. Обчисліть площу цього ромба.",
            "У ромба один з гострих кутів дорівнює 60°. Знайдіть всі інші кути ромба.",
            "Якщо одна діагональ дорівнює 16 см, а інша - 12 см, знайдіть довжину сторони ромба."
            );

    public static void main(String[] args) throws IOException, InterruptedException, SyntaxAnalyzer.SyntaxException {
        for (String text : inputs) {
            Set<String> identifiersMap = new HashSet<>();
            InputParser parser = new InputParser(text);
            FirstAnalyzer analyzer = new FirstAnalyzer(parser.parse());
            System.out.println(text);
            DataAnalyzer dataAnalyzer = new DataAnalyzer(analyzer.analyze());
            String commands = dataAnalyzer.getCommands();
            System.out.println("Commands --> " + commands);

            for (String command: commands.split("#")) {
                Analyzer lexer = new Analyzer(command);
                List<String> tokens = new ArrayList<>();
                Token token;

                while ((token = lexer.getNextToken()) != null) {
                    if (token.getType() == TokenType.IDENTIFIER) {
                        identifiersMap.add(token.getValue());
                    }
                    tokens.add(token.getValue());
                }

                SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(tokens);
                ParseTreeNode parseTree = syntaxAnalyzer.parseSentence();
                System.out.println("Parse Tree:");
                parseTree.printTree("", true);
            }

            System.out.println("\n\n");
        }
    }
}


/*
<речення> ::= <простий оператор> | <визначення> | <запит>

<простий оператор> ::= Побудувати ромб <ромб>

<визначення> ::= Визначити сторона <пара імен> = <довжина> |
                 Визначити діагональ <пара імен> = <довжина> |
                 Визначити кут <ім’я> = <кут> |
                 Визначити гострий кут = <кут> |
                 Визначити тупий кут = <кут> |
                 Визначити висота <ім’я> = <довжина> |
                 Визначити периметр = <довжина> |
                 Визначити площа = <довжина> |
                 Визначити <пара імен> = <довжина> |

<запит> ::= Знайти <тип даних>

<тип даних> ::= периметр |
                площа |
                довжина сторін |
                довжина сторони <пара імен> |
                міру кута <ім’я> |
                міру гострий кут |
                міру тупий кут |
                довжину діагональ <пара імен> |
                довжину діагональ |
                довжину висота <пара імен> |
                всі кути |
                <пара імен>

<ромб> ::= <ім’я> <ім’я> <ім’я> <ім’я>

<пара імен> ::= <ім’я> <ім’я>

<ім’я> ::= A | B | C | D | E | F | K | M | N

<довжина> ::= <число> [см | м | мм | дм]

<кут> ::= <число>
*/