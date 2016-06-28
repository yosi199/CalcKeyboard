package input.calculator;

/**
 * Created by yosimizrachi on 28/06/2016.
 */
public class CalculationItem {

    // Native code
    static {
        System.loadLibrary("ndkModule");
    }

    private int mMemberA;
    private int mMemberB;
    private int mResult;
    private OperationType mType;

    public CalculationItem() {
    }

    public OperationType getType() {
        return mType;
    }

    public void setType(OperationType type) {
        mType = type;
    }

    public int getMemberA() {
        return mMemberA;
    }

    public void setMemberA(int memberA) {
        mMemberA = memberA;
    }

    public int getMemberB() {
        return mMemberB;
    }

    public void setMemberB(int memberB) {
        mMemberB = memberB;
    }

    public int getResult() {
        return mResult;
    }

    public void setResult(int result) {
        mResult = result;
    }

    public int calculateValue() {
        if (mType != null) {
            switch (mType) {
                case MULTIPLY:
                    return nativeMultiply(mMemberA, mMemberB);
                case DIVIDE:
                    return nativeDivide(mMemberA, mMemberB);
                case SUBTRACT:
                    return nativeSubtract(mMemberA, mMemberB);
                case ADD:
                    return nativeAdd(mMemberA, mMemberB);
                default:
                    return -1;
            }
        } else {
            return -1;
        }
    }

    private final native int nativeMultiply(int a, int b);

    private final native int nativeDivide(int a, int b);

    private final native int nativeAdd(int a, int b);

    private final native int nativeSubtract(int a, int b);

    public enum OperationType {

        MULTIPLY("*"),
        DIVIDE("/"),
        SUBTRACT("-"),
        ADD("+");

        private final String mType;
        private int mOperatorIndex;

        OperationType(String type) {
            mType = type;
        }

        public String getType() {
            return mType;
        }

        public int getOperatorIndex() {
            return mOperatorIndex;
        }

        public void setOperatorIndex(int index) {
            mOperatorIndex = index;
        }
    }


}
