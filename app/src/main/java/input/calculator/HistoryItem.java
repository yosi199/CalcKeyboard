package input.calculator;

/**
 * Created by yosimizrachi on 28/06/2016.
 */
public class HistoryItem {

    private int mMemberA;
    private int mMemberB;
    private int mResult;
    private OperationType mType;


    public enum OperationType {

        MULTIPLY("*"),
        DIVIDE("/"),
        SUBTRACT("-"),
        ADD("+");

        OperationType(String type) {
            mType = type;
        }

        private final String mType;

        public String getType() {
            return mType;
        }
    }

    public HistoryItem() {
    }

    public void setMemberA(int memberA) {
        mMemberA = memberA;
    }

    public void setMemberB(int memberB) {
        mMemberB = memberB;
    }

    public void setResult(int result) {
        mResult = result;
    }

    public void setType(OperationType type) {
        mType = type;
    }

    public OperationType getType() {
        return mType;
    }

    public int getMemberA() {
        return mMemberA;
    }

    public int getMemberB() {
        return mMemberB;
    }

    public int getResult() {
        return mResult;
    }
}
