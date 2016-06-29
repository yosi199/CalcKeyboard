package keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import input.calculator.CalcManager;
import input.calculator.CalculationItem;


/**
 * Created by yosimizrachi on 28/06/16.
 */
public class HistoryView extends ListView {


    private HistoryAdapter mAdapter;

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
        mAdapter = new HistoryAdapter();
        setAdapter(mAdapter);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
//        params.height =
    }

    public void loadHistory() {
        ArrayList<CalculationItem> items = CalcManager.getInstance().getHistoryItems();
        mAdapter.updateData(items);
    }

    public void update() {
        mAdapter.notifyDataSetChanged();
        smoothScrollToPosition(mAdapter.getCount() - 1);
    }


}
