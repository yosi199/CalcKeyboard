package keyboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yosimizrachi.calckeyboard.R;

import java.util.ArrayList;

import input.calculator.CalculationItem;

/**
 * Created by yosimizrachi on 28/06/16.
 */
public class HistoryAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<CalculationItem> mItems = new ArrayList<>();
    private HistoryView mClickListener;

    public HistoryAdapter(HistoryView listener) {
        mClickListener = listener;
    }

    public void updateData(ArrayList<CalculationItem> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public CalculationItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CalculationItem item = mItems.get(position);
        HistoryHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.history_item, parent, false);
            convertView.setOnClickListener(this);

            holder = new HistoryHolder();
            holder.layout = (LinearLayout) convertView.findViewById(R.id.item_root_layout);
            holder.memberA = (TextView) convertView.findViewById(R.id.history_member_A);
            holder.operator = (TextView) convertView.findViewById(R.id.operator);
            holder.memberB = (TextView) convertView.findViewById(R.id.history_member_B);
            holder.result = (TextView) convertView.findViewById(R.id.history_result);
            convertView.setTag(holder);
        } else {
            holder = (HistoryHolder) convertView.getTag();
        }

        holder.layout.setTag(R.id.item_id, position);
        holder.memberA.setText("" + item.getMemberA());
        holder.operator.setText(" " + item.getType().getType() + " ");
        holder.memberB.setText("" + item.getMemberB());
        holder.result.setText("" + item.getResult());

        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag(R.id.item_id);
        mClickListener.performItemClick(v, position, getItemId(position));
    }

    public static class HistoryHolder {
        LinearLayout layout;
        TextView memberA;
        TextView operator;
        TextView memberB;
        TextView result;
    }
}
