package SyntaxClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FormatStringToken extends Token {
    private int formatCharNum;
    private ArrayList<String> rawStrList; // 分割成不带格式字符的串列表

    public FormatStringToken(int lineNum, String context) {
        super(Token.STRCON, lineNum, context);
        this.formatCharNum = 0;
        rawStrList = new ArrayList<>();
        formatCharNum = stringPartition();
    }

    public ArrayList<String> getRawStrList() {
        return rawStrList;
    }

    public void setFormatCharNum(int formatCharNum) {
        this.formatCharNum = formatCharNum;
    }

    public int getFormatCharNum() {
        return formatCharNum;
    }

    private int stringPartition() {
        String strContext = this.getTokenContext();
        String[] strList = strContext.split("%d");
        strList[0] = strList[0].substring(1);
        strList[strList.length - 1] = strList[strList.length - 1].substring(0, strList[strList.length - 1].length() - 1);
        rawStrList.addAll(Arrays.asList(strList));
        return strList.length - 1;
    }
}
