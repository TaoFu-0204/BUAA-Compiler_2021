public class Error implements Comparable{
    public static char[] errTypeName = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'};
    private int errType;
    private int lineNo;

    public Error(int errType, int lineNo) {
        this.errType = errType;
        this.lineNo = lineNo;
    }

    public int getErrType() {
        return errType;
    }

    public int getLineNo() {
        return lineNo;
    }

    @Override
    public String toString() {
        return String.format("%d %s", lineNo, errTypeName[errType]);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Error) {
            if (this.lineNo != ((Error) o).getLineNo()) {
                return this.lineNo - ((Error) o).getLineNo();
            } else {
                return (this.errType - ((Error) o).getErrType());
            }
        }
        return -1;
    }
}
