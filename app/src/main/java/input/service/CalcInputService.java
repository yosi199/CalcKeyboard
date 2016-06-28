package input.service;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import com.example.yosimizrachi.calckeyboard.R;

import views.CalcKeyboard;
import views.CalcKeyboardView;

/**
 * Created by yosimizrachi on 27/06/16.
 */
public class CalcInputService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private static final String TAG = "CalcInputService";
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
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

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
                    int result = calculate(mTextComposition.toString());
                    reset();
                    mTextComposition.append(result);
                    ic.commitText(String.valueOf(result), 1);
                } catch (NumberFormatException exception) {
                    // "complicated expressions not yet implemented. For now only simple expression will be evaluated.
                    reset();
                    ic.commitText(getString(R.string.error), 1);
                }
                break;
            default:
                char code = (char) primaryCode;
                ic.commitText(String.valueOf(code), 1);
                mTextComposition.append(code);
        }
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

    private void showInputChooser() {
        if (mInputMethodManager != null) {
            mInputMethodManager.showInputMethodPicker();
        }
    }

    private int calculate(String input) {
        int expressionStarts = 0;
        int expressionEnds = 0;
        String memberAString = null;
        String memberBString = null;
        int result = 0000;

        for (int index = 0; index < input.length(); index++) {
            char currentChar = input.charAt(index);

            // find the members of the expression
            if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') {
                expressionStarts = getFirstMemberIndex(input, index - 1);
                expressionEnds = getSecondMemberIndex(input, index);

                memberAString = input.substring(expressionStarts, index);
                memberBString = input.substring(index + 1, expressionEnds);
            }


            switch (currentChar) {
                case '+':
                    result = add(Integer.valueOf(memberAString), Integer.valueOf(memberBString));
                    break;
                case '-':
                    result = subtract(Integer.valueOf(memberAString), Integer.valueOf(memberBString));
                    break;
                case '*':
                    result = multiply(Integer.valueOf(memberAString), Integer.valueOf(memberBString));
                    break;
                case '/':
                    result = divide(Integer.valueOf(memberAString), Integer.valueOf(memberBString));
                    break;
            }
        }
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
            if (i == 0) {
                return 0;
            }

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


    // TODO implement in JNI
    private int multiply(int a, int b) {
        return a * b;
    }

    private int divide(int a, int b) {
        return a / b;
    }

    private int add(int a, int b) {
        return a + b;
    }

    private int subtract(int a, int b) {
        return a - b;
    }
}
