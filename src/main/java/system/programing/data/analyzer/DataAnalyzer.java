package system.programing.data.analyzer;

import system.programing.input.analyzer.DataInfo;
import system.programing.input.analyzer.DataType;

import java.util.ArrayList;
import java.util.List;

public class DataAnalyzer {

    private final List<DataInfo> infos;

    public DataAnalyzer(List<DataInfo> info) {
        this.infos = info;
    }

    public String getCommands() {
        List<String> commands = new ArrayList<>();
        String searchCommand = null;
        String diamondCommand = null;
        DataInfo previousInfo = null;

        for (int i = 0; i < infos.size(); i++) {
            DataInfo info = infos.get(i);
            if (info.getType() == DataType.SEARCH) {
                searchCommand = getCommand(info, null);
                continue;
            } else if (info.getType() == DataType.DIAMOND) {
                diamondCommand = getCommand(info, null);
                continue;
            }
            if (info.getType() == DataType.OTHER) {
                commands.add(getCommand(info, previousInfo));
            } else {
                commands.add(getCommand(info, null));
            }
            previousInfo = info;
        }

        if (diamondCommand != null) {
            commands.addFirst(diamondCommand);
        } else {
            commands.addFirst("Побудувати ромб null#");
        }
        commands.add(searchCommand);
        return removeDuplicates(commands);
    }

    private String getCommand(DataInfo info, DataInfo prevInfo) {
        return switch (info.getType()) {
            case DIAMOND -> buildDiamondCommand(info);
            case SIDE -> buildSideCommand(info);
            case HEIGHT -> buildHeightCommand(info);
            case ANGLE -> buildAngleCommand(info);
            case ACUTE_ANGLE -> buildAcuteAngleCommand(info);
            case OBTUSE_ANGLE -> buildObtuseAngleCommand(info);
            case DIAGONAL -> buildDiagonalCommand(info);
            case PERIMETER -> buildPerimeterCommand(info);
            case SQUARE -> buildSquareCommand(info);
            case SEARCH -> buildSearchCommand(info);
            case OTHER -> buildOtherCommand(info, prevInfo);
        };
    }

    private String buildDiamondCommand(DataInfo info) {
        return "Побудувати ромб " + info.getName() + "#";
    }

    private String buildSideCommand(DataInfo info) {
        return "Визначити сторона " + info.getName() + " = " + info.getValue() + "#";
    }

    private String buildHeightCommand(DataInfo info) {
        return "Визначити висота " + info.getName() + " = " + info.getValue() + "#";
    }

    private String buildAngleCommand(DataInfo info) {
        return "Визначити кут " + info.getName() + " = " + info.getValue() + "#";
    }

    private String buildAcuteAngleCommand(DataInfo info) {
        return "Визначити гострий кут " + info.getName() + " = " + info.getValue() + "#";
    }

    private String buildObtuseAngleCommand(DataInfo info) {
        return "Визначити тупий кут " + info.getName() + " = " + info.getValue() + "#";
    }

    private String buildDiagonalCommand(DataInfo info) {
        return "Визначити діагональ " + info.getName() + " = " + info.getValue() + "#";
    }

    private String buildPerimeterCommand(DataInfo info) {
        return "Визначити периметр = " + info.getValue() + "#";
    }

    private String buildSquareCommand(DataInfo info) {
        return "Визначити площа = " + info.getValue() + "#";
    }

    private String buildSearchCommand(DataInfo info) {
        if (info.getValue() != null) {
            return switch (info.getValue()) {
                case "СТОРОН" -> "Знайти довжина сторона#";
                case "ВЕСЬ КУТ", "ІНШИЙ КУТ" -> "Знайти всі кути#";
                case "ПЕРИМЕТР", "ЙОГО ПЕРИМЕТР" -> "Знайти периметр#";
                case "ІНШИЙ ДІАГОНАЛЬ" -> "Знайти довжина діагональ " + info.getName() + "#";
                case "НАЙБІЛЬШИЙ КУТ" -> "Знайти міру тупий кут#";
                case "ПЛОЩ" -> "Знайти площа#";
                default -> "";
            };
        } else {
            return "Знайти " + info.getName() + "#";
        }
    }

    private String buildOtherCommand(DataInfo info, DataInfo prevInfo) {
            if (prevInfo.getType() == DataType.DIAGONAL) {
                return buildDiagonalCommand(info);
            }
        return "Визначити " + info.getName() + " = " + info.getValue() + "#";
    }

    private String removeDuplicates(List<String> commands) {
        List<String> duplicates = new ArrayList<>();

        for (String command : commands) {
            for (String curCommand : commands) {
                if (curCommand.equals(command)) {continue;}
                if (isSimilar(command, curCommand)) {
                    if (command.contains("null")) {
                        duplicates.add(command);
                    } else if (curCommand.contains("null")) {
                        duplicates.add(curCommand);
                    }
                }
            }
        }

        commands.removeAll(duplicates);
        StringBuilder strB = new StringBuilder();

        for (String command : commands) {
            strB.append(command);
        }

        return strB.toString();
    }

    private boolean isSimilar(String first, String second) {
        int countSimilarity = 0;

        for (int i = 0; i < Math.min(first.length(), second.length()); i++) {
            if (first.charAt(i) == second.charAt(i)) {
                countSimilarity++;
            }
        }

        if (countSimilarity == first.length() - 4 || countSimilarity == second.length() - 4) {
            return true;
        }
        return false;
    }
}