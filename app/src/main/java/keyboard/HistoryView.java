package keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import java.util.ArrayList;

import input.calculator.CalcManager;
import input.calculator.CalculationItem;

/**
 * Created by yosimizrachi on 28/06/16.
 */
public class HistoryView extends ListView {

    public HistoryView(Context context) {
        super(context);
        init();
    }

    public HistoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HistoryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    public void loadHistory() {
        ArrayList<CalculationItem> items = CalcManager.getInstance().getHistoryItems();
        HistoryAdapter adapter = new HistoryAdapter(items);
        setAdapter(adapter);
    }
}
