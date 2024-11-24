package system.programing.syntax.analyzer;

import java.util.ArrayList;
import java.util.List;

public class ParseTreeNode {
    private TreeNodeType type;
    private String value;
    private List<ParseTreeNode> children;

    public ParseTreeNode(TreeNodeType type) {
        this.type = type;
        this.children = new ArrayList<>();
    }

    public ParseTreeNode(TreeNodeType type, String value) {
        this.type = type;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public void addChild(ParseTreeNode child) {
        children.add(child);
    }

    public TreeNodeType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public List<ParseTreeNode> getChildren() {
        return children;
    }

    public void printTree(String indent, boolean isLast) {
        System.out.print(indent);
        if (isLast) {
            System.out.print("└── ");
            indent += "    ";
        } else {
            System.out.print("├── ");
            indent += "│   ";
        }

        if (value != null) {
            System.out.println(type + ": " + value);
        } else {
            System.out.println(type);
        }

        for (int i = 0; i < children.size(); i++) {
            children.get(i).printTree(indent, i == children.size() - 1);
        }
    }
}