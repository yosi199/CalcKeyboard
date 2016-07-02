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
        mAdapter = new HistoryAdapter(this);
        setAdapter(mAdapter);
    }

    public void loadHistory() {
        if (mAdapter != null) {
            ArrayList<CalculationItem> items = CalcManager.getInstance().getHistoryItems();
            mAdapter.updateData(items);
        }
    }

    public void update() {
        mAdapter.notifyDataSetChanged();
        smoothScrollToPosition(mAdapter.getCount() - 1);
    }

    @Override
    protected void onAttachedToWindow() {
        if (mAdapter == null) {
            mAdapter = new HistoryAdapter(this);
        }
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        mAdapter = null;
        super.onDetachedFromWindow();
    }

}
