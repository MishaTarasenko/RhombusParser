package system.programing.input.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Lemma {

    private final String name;
    private final LemmaType type;

    public Lemma(String lemma, LemmaType type) {
        this.name = lemma;
        this.type = type;
    }

    public String getLemma() {
        return name;
    }

    public LemmaType getType() {
        return type;
    }

    public static List<Lemma> getLemmasFromJSON(String jsonResponse) {
        List<Lemma> lemmaList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode resultNode = rootNode.get("result");

            if (resultNode != null && !resultNode.isNull()) {
                String conlluData = resultNode.asText();

                conlluData = conlluData.replace("\\n", "\n").replace("\\r", "\r");

                lemmaList = Lemma.getLemmasFromStr(conlluData);
            } else {
                System.err.println("Поле 'result' не знайдено в JSON-відповіді.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lemmaList;
    }

    private static List<Lemma> getLemmasFromStr(String input) {
        List<Lemma> lemmas = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new StringReader(input));
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] fields = line.split("\t");

                if (fields.length < 8) {
                    continue;
                }

                String lemma = fields[2];
                String upos = fields[3];

                lemmas.add(new Lemma(getStem(lemma), LemmaType.valueOf(upos)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lemmas;
    }

    private static String getStem(String word) {
        String normalizedWord = word.toUpperCase();
        normalizedWord = normalizedWord.replaceAll("(У|Ю|А|Я|І|И|ОМУ|ОВІ|ЕВІ|АМ|ЯМ|ІВ|ЇВ|АХ|ЯХ|ІЮ|ІЯ|КА|ЦІ|ТІ|АТ|У|Ю|ЮТ|ІЇ|Ї|ЄЇ)$", "");
        return normalizedWord;
    }

    @Override
    public String toString() {
        return "Lemma{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
