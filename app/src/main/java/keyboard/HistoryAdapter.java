package keyboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yosimizrachi.calckeyboard.R;

import java.util.ArrayList;

import input.calculator.CalculationItem;

/**
 * Created by yosimizrachi on 28/06/16.
 */
public class HistoryAdapter extends BaseAdapter {

    private ArrayList<CalculationItem> mItems = new ArrayList<>();

    public HistoryAdapter() {
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
            holder = new HistoryHolder();
            holder.memberA = (TextView) convertView.findViewById(R.id.history_member_A);
            holder.operator = (TextView) convertView.findViewById(R.id.operator);
            holder.memberB = (TextView) convertView.findViewById(R.id.history_member_B);
            holder.result = (TextView) convertView.findViewById(R.id.history_result);
            convertView.setTag(holder);
        } else {
            holder = (HistoryHolder) convertView.getTag();
        }

        holder.memberA.setText("" + item.getMemberA());
        holder.operator.setText(" " + item.getType().getType() + " ");
        holder.memberB.setText("" + item.getMemberB());
        holder.result.setText("" + item.getResult());

        return convertView;
    }

    public static class HistoryHolder {
        TextView memberA;
        TextView operator;
        TextView memberB;
        TextView result;

    }
}