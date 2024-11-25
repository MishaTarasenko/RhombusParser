package system.programing;

import system.programing.data.analyzer.DataAnalyzer;
import system.programing.input.analyzer.FirstAnalyzer;
import system.programing.input.parser.InputParser;
import system.programing.lexical.analyzer.Analyzer;
import system.programing.lexical.analyzer.Token;
import system.programing.lexical.analyzer.TokenType;
import system.programing.rhombus.draw.RhombusPanel;
import system.programing.rhombus.solution.RhombusSolver;
import system.programing.semantic.analyzer.Rhombus;
import system.programing.semantic.analyzer.SemanticAnalyzer;
import system.programing.syntax.analyzer.ParseTreeNode;
import system.programing.syntax.analyzer.SyntaxAnalyzer;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    private static final List<String> inputs = List.of(
            "Знайдіть всі кути ромба ABCD, якщо його периметр дорівнює 24 см, а висота - 3 см.",
            "Знайдіть периметр ромба ABCD, якщо ∠B = 60°, сторона BD = 9 см.",
            "Одна з діагоналей ромба дорівнює 30 см. Знайти іншу діагональ ромба, якщо його периметр дорівнює 68 см.",
            "Сторона ромба дорівнює 6 см, а його площа – 18 см2. Знайти найбільший кут ромба.",
            "Периметр ромба ABCD дорівнює 52 см, AN дорівнює 5 см, а DN – 12 см, знайдіть AD.",
            "В ромбі ABCD AN дорівнює 12 см, а DN – 5 см, знайдіть AD.",
            "У ромбі ABCD всі сторони = 8 см. Знайдіть його периметр.",
            "Діагональ ромба дорівнює 10 см, а інша - 24 см. Обчисліть площу цього ромба.",
            "У ромба один з гострих кутів дорівнює 60°. Знайдіть всі інші кути ромба.",
            "Якщо одна діагональ дорівнює 16 см, а інша - 12 см, знайдіть довжину сторони ромба."
            );

    public static void main(String[] args) throws IOException, InterruptedException, SyntaxAnalyzer.SyntaxException, SemanticAnalyzer.SemanticException {
        for (String text : inputs) {
            RhombusSolver solver = new RhombusSolver();
            List<ParseTreeNode> parseTrees = new ArrayList<>();
            Set<String> identifiersMap = new HashSet<>();
            InputParser parser = new InputParser(text);
            FirstAnalyzer analyzer = new FirstAnalyzer(parser.parse());
            System.out.println(text);
            DataAnalyzer dataAnalyzer = new DataAnalyzer(analyzer.analyze());
            String commands = dataAnalyzer.getCommands();

            for (String command : commands.split("#")) {
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
                parseTrees.add(syntaxAnalyzer.parseSentence());
            }
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
            Rhombus rhombus = semanticAnalyzer.analyze(parseTrees);
            System.out.println(rhombus);

            JFrame frame = new JFrame("Малювання Ромба");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new RhombusPanel(rhombus));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            solver.solve(rhombus);
            System.out.println(rhombus);

            System.out.println("\n\n");
            Thread.sleep(5000);
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