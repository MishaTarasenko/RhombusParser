package system.programing.input.analyzer;

import system.programing.input.parser.Lemma;
import system.programing.input.parser.LemmaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FirstAnalyzer {

    private final List<List<Lemma>> lemmas;

    public FirstAnalyzer(List<List<Lemma>> lemmas) {
        this.lemmas = lemmas;
    }

    public List<DataInfo> analyze() {
        List<DataInfo> dataInfos = new ArrayList<>();
        for (List<Lemma> lemmaList : this.lemmas) {
            List<Lemma> nouns = new ArrayList<>();
            List<Lemma> verbs = new ArrayList<>();

            for (Lemma lemma : lemmaList) {
                if (lemma.getType() == LemmaType.NOUN) {
                    nouns.add(lemma);
                } else if (lemma.getType() == LemmaType.VERB) {
                    verbs.add(lemma);
                }
            }

            dataInfos.addAll(analyzeWords(lemmaList, nouns, verbs));
        }
        return clearResult(dataInfos);
    }

    private List<DataInfo> analyzeWords(List<Lemma> lemmaList, List<Lemma> nouns, List<Lemma> verbs) {
        List<DataInfo> curInfo = new ArrayList<>();
        for (Lemma lemma : nouns) {
            curInfo.add(new DataInfo(null, getProbablyName(lemmaList, lemma), DataType.fromString(lemma.getLemma())));
        }

        for (Lemma lemma : verbs) {
            findContextForVerb(lemmaList, lemma, curInfo);
        }

        return curInfo;
    }

    private void findContextForVerb(List<Lemma> lemmaList, Lemma lemma, List<DataInfo> curInfo) {
        int index = lemmaList.indexOf(lemma);

        if (lemma.getLemma().toUpperCase().equals("ДОРІВНЮВАТ")) {
            curInfo.add(getAppropriationInfo(lemmaList, index));
        } else if (lemma.getLemma().toUpperCase().equals("ЗНАЙТ") || lemma.getLemma().toUpperCase().equals("ОБЧИСЛИТ")) {
            curInfo.add(new DataInfo(getSubjectForSearch(lemmaList, index), getProbablySearchName(lemmaList, index), DataType.SEARCH));
        }
    }

    private DataInfo getAppropriationInfo(List<Lemma> lemmaList, int index) {
        String noun = "";
        String value = null;
        Lemma lemma = null;
        for (int i = index; i >=0 ; i--) {
            if (lemmaList.get(i).getType() == LemmaType.NOUN
                    && (!lemmaList.get(i).getLemma().equals("РОМБ") && !lemmaList.get(i).getLemma().equals("ДОВЖИН"))
                    && !couldBeName(lemmaList.get(i).getLemma())) {
                if (lemmaList.get(i).getLemma().equals("КУТ")) {
                    if (lemmaList.get(i - 1).getType() == LemmaType.ADV || lemmaList.get(i - 1).getType() == LemmaType.DET || lemmaList.get(i - 1).getType() == LemmaType.ADJ) {
                        noun = lemmaList.get(i - 1).getLemma() + " " + lemmaList.get(i).getLemma();
                    } else {
                        noun = lemmaList.get(i).getLemma();
                    }
                    lemma = lemmaList.get(i);
                } else {
                    noun = lemmaList.get(i).getLemma();
                    lemma = lemmaList.get(i);
                }
                break;
            }
        }
        for (int i = index + 1; i < lemmaList.size() ; i++) {
            if (lemmaList.get(i).getType() == LemmaType.NUM && lemmaList.get(i + 1).getType() == LemmaType.NOUN) {
                value = lemmaList.get(i).getLemma() + " " + lemmaList.get(i + 1).getLemma();
                break;
            }
        }

        if (lemma == null) {
            return new DataInfo(value, getProbablyName(lemmaList, lemmaList.get(index)), DataType.fromString(noun));
        }else {
            return new DataInfo(value, getProbablyName(lemmaList, lemma), DataType.fromString(noun));
        }
    }

    private String getSubjectForSearch(List<Lemma> lemmaList, int index) {
        for (int i = index + 1; i < lemmaList.size() ; i++) {
            if ((lemmaList.get(i).getType() == LemmaType.NOUN)
                    && (!lemmaList.get(i).getLemma().equals("РОМБ") && !lemmaList.get(i).getLemma().equals("ДОВЖИН"))
                    && !couldBeName(lemmaList.get(i).getLemma())) {
                if (lemmaList.get(i - 1).getType() == LemmaType.ADV || lemmaList.get(i - 1).getType() == LemmaType.DET || lemmaList.get(i - 1).getType() == LemmaType.ADJ) {
                    return lemmaList.get(i - 1).getLemma() + " " + lemmaList.get(i).getLemma();
                }
                return lemmaList.get(i).getLemma();
            }
        }
        return null;
    }

    private String getProbablyName(List<Lemma> lemmaList, Lemma lemma) {
        if (lemma == null) return null;

        int index = lemmaList.indexOf(lemma);
        if (index == -1) {
            throw new IllegalArgumentException("No such word: " + lemma.getLemma());
        }

        if (index - 1 >= 0) {
            if ((lemmaList.get(index - 1).getType() == LemmaType.X
                    || lemmaList.get(index - 1).getType() == LemmaType.PROPN
                    || lemmaList.get(index - 1).getType() == LemmaType.SYM)
                    && couldBeName(lemmaList.get(index - 1).getLemma())) {
                return lemmaList.get(index - 1).getLemma();
            }
        }

        if (index + 1 < lemmaList.size()) {
            if ((lemmaList.get(index + 1).getType() == LemmaType.X
                    || lemmaList.get(index + 1).getType() == LemmaType.PROPN
                    || lemmaList.get(index + 1).getType() == LemmaType.SYM)
                    && couldBeName(lemmaList.get(index + 1).getLemma())) {
                return lemmaList.get(index + 1).getLemma();
            }
        }

        return null;
    }

    private String getProbablySearchName(List<Lemma> lemmaList, int index) {
        for (int i = index + 1; i < lemmaList.size() ; i++) {
            if ((lemmaList.get(i).getType() == LemmaType.X || lemmaList.get(i).getType() == LemmaType.PROPN || lemmaList.get(i).getType() == LemmaType.SYM) && couldBeName(lemmaList.get(i).getLemma())) {
                return lemmaList.get(i).getLemma();
            }
        }
        return null;
    }

    private boolean couldBeName(String probablyName) {
        Set<Character> allowedLetters = Set.of('A', 'B', 'C', 'D', 'E', 'F', 'K', 'M', 'N', 'T');
        boolean isIdentifier = probablyName.toUpperCase().chars()
                .mapToObj(c -> (char) c)
                .allMatch(c -> allowedLetters.contains(c) || Character.isDigit(c));
        return (isIdentifier && probablyName.length() <= 4 && probablyName.length() > 0);
    }

    private List<DataInfo> clearResult(List<DataInfo> dataInfoList) {
        return dataInfoList.stream().filter(e -> (e.getName() != null || e.getValue()!= null)).toList();
    }
}