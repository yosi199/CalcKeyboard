package input.service;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import com.example.yosimizrachi.calckeyboard.R;

import input.calculator.CalcManager;
import input.calculator.HistoryItem;
import keyboard.CalcKeyboard;
import keyboard.CalcKeyboardView;

/**
 * Created by yosimizrachi on 27/06/16.
 */
public class CalcInputService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private static final String TAG = "CalcInputService";
    private final CalcManager mCalcManager = CalcManager.getInstance();
    private InputMethodManager mInputMethodManager;
    private CalcKeyboardView mKeyboardView;
    private CalcKeyboard mKeyboard;
    private StringBuilder mTextComposition = new StringBuilder();

    @Override
    public void onCreate() {
        super.onCreate();
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    @Override
    public View onCreateInputView() {
        mKeyboardView = (CalcKeyboardView) getLayoutInflater().inflate(R.layout.input_layout, null);
        mKeyboard = new CalcKeyboard(this, R.xml.digits);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setOnKeyboardActionListener(this);
        return mKeyboardView;
    }

    @Override
    public View onCreateCandidatesView() {
        // the layout for the history transactions
        return super.onCreateCandidatesView();
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
    }


    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                reset();
                break;
            case CalcKeyboard.SETTINGS_KEY:
                showInputChooser();
                break;
            case CalcKeyboard.CALCULATE:
                try {
                    HistoryItem historyItem = new HistoryItem();
                    int result = calculate(mTextComposition.toString(), historyItem);
                    reset();
                    mTextComposition.append(result);
                    ic.commitText(String.valueOf(result), 1);
                    mCalcManager.addToHistory(historyItem);
                } catch (NumberFormatException exception) {
                    // "complicated expressions not yet implemented. For now only simple expression will be evaluated.
                    reset();
                    ic.commitText(getString(R.string.error), 1);
                }
                break;
            case CalcKeyboard.HISTORY:
                Log.d(TAG, "ITEMS IN HISTORY " + mCalcManager.getHistoryItems().size());
                break;
            default:
                char code = (char) primaryCode;
                ic.commitText(String.valueOf(code), 1);
                mTextComposition.append(code);
        }
    }


    private void showInputChooser() {
        if (mInputMethodManager != null) {
            mInputMethodManager.showInputMethodPicker();
        }
    }

    /**
     * Will parse user input and calculate the result
     *
     * @param input the user input
     * @return the result of calculation
     */
    private int calculate(String input, HistoryItem historyItem) {
        int expressionStarts = 0;
        int expressionEnds = 0;
        String memberAString = null;
        String memberBString = null;
        int result = 0000;


        //TODO optimize!!!

        for (int index = 0; index < input.length(); index++) {
            char currentChar = input.charAt(index);

            // find the members of the expression
            if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') {
                expressionStarts = getFirstMemberIndex(input, index - 1);
                expressionEnds = getSecondMemberIndex(input, index);

                memberAString = input.substring(expressionStarts, index);
                memberBString = input.substring(index + 1, expressionEnds);
            } else {
                continue;
            }

            int numA = Integer.valueOf(memberAString);
            int numB = Integer.valueOf(memberBString);
            historyItem.setMemberA(numA);
            historyItem.setMemberB(numB);

            switch (currentChar) {
                case '+':
                    historyItem.setType(HistoryItem.OperationType.ADD);
                    result = mCalcManager.add(numA, numB);
                    break;
                case '-':
                    historyItem.setType(HistoryItem.OperationType.SUBTRACT);
                    result = mCalcManager.subtract(numA, numB);
                    break;
                case '*':
                    historyItem.setType(HistoryItem.OperationType.MULTIPLY);
                    result = mCalcManager.multiply(numA, numB);
                    break;
                case '/':
                    historyItem.setType(HistoryItem.OperationType.DIVIDE);
                    result = mCalcManager.divide(numA, numB);
                    break;
            }
        }
        historyItem.setResult(result);
        return result;
    }

    private void reset() {
        mTextComposition.delete(0, mTextComposition.length());
        getCurrentInputConnection().deleteSurroundingText(1000, 1000);
    }

    private int getFirstMemberIndex(String input, int index) {
        if (index == 0) {
            return 0;
        }
        //go back in the input and see where the first member of the operation starts

        for (int i = index; i > 0; i--) {

            char currChar = input.charAt(i);
            if (currChar == '+' || currChar == '-' || currChar == '*' || currChar == '/') {
                return i;
            }
        }

        return 0;
    }

    private int getSecondMemberIndex(String input, int index) {

        //go forward in the input and see where the first member of the operation starts

        for (int i = index + 1; i < input.length(); i++) {
            char currChar = input.charAt(i);

            if (currChar == '+' || currChar == '-' || currChar == '*' || currChar == '/' || i == input.length()) {
                return i;
            }
        }

        return input.length();
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

}
