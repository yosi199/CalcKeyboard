package input.calculator;

import java.util.ArrayList;

/**
 * Created by yosimizrachi on 28/06/2016.
 */
public class CalcManager {

    private static final CalcManager INSTANCE = new CalcManager();

    // Native code
    static {
        System.loadLibrary("ndkModule");
    }

    private ArrayList<HistoryItem> mHistoryItems;

    private CalcManager() {
    }

    public static CalcManager getInstance() {
        return INSTANCE;
    }

    private final native int nativeMultiply(int a, int b);

    private final native int nativeDivide(int a, int b);

    private final native int nativeAdd(int a, int b);

    private final native int nativeSubtract(int a, int b);

    public int multiply(int a, int b) throws IllegalArgumentException {
        return nativeMultiply(a, b);
    }

    public int divide(int a, int b) throws IllegalArgumentException {
        return nativeDivide(a, b);
    }

    public int add(int a, int b) throws IllegalArgumentException {
        return nativeAdd(a, b);
    }

    public int subtract(int a, int b) throws IllegalArgumentException {
        return nativeSubtract(a, b);
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
