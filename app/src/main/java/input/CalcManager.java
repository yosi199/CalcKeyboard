package input;

import java.util.ArrayList;

import input.calculator.HistoryItem;

/**
 * Created by yosimizrachi on 28/06/2016.
 */
public class CalcManager {

    // Native code
    static {
        System.loadLibrary("ndkModule");
    }

    private ArrayList<HistoryItem> mHistoryItems;

    private final native int nativeMultiply(int a, int b);

    private final native int nativeDivide(int a, int b);

    private final native int nativeAdd(int a, int b);

    private final native int nativeSubtract(int a, int b);

    private static final CalcManager INSTANCE = new CalcManager();

    private CalcManager() {
    }

    public static CalcManager getInstance() {
        return INSTANCE;
    }

    public int multiply(int a, int b) throws IllegalArgumentException {
        if (a == 0 || b == 0) {
            throwError();
        }
        return nativeMultiply(a, b);
    }

    public int divide(int a, int b) throws IllegalArgumentException {
        if (a == 0 || b == 0) {
            throwError();
        }
        return nativeDivide(a, b);
    }

    public int add(int a, int b) throws IllegalArgumentException {
        if (a == 0 || b == 0) {
            throwError();
        }
        return nativeAdd(a, b);
    }

    public int subtract(int a, int b) throws IllegalArgumentException {
        if (a == 0 || b == 0) {
            throwError();
        }
        return nativeSubtract(a, b);
    }

    private void throwError() {
        throw new IllegalArgumentException("Please enter valid numbers");

    }

    public void addToHistory(HistoryItem historyItem) {
        if (mHistoryItems == null) {
            mHistoryItems = new ArrayList<>();
        }
        mHistoryItems.add(historyItem);
    }

    public ArrayList<HistoryItem> getHistoryItems() {
        return mHistoryItems;
    }
}
