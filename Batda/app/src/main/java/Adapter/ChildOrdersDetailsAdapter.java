package Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import Sample.ChildSample;
import softs.hnt.com.batda.R;

/**
 * Created by TrangHo on 06-05-2015.
 */
public class ChildOrdersDetailsAdapter extends BaseAdapter {
    List<ChildSample> childSampleList;
    Activity context;
    public ChildOrdersDetailsAdapter(Activity context, List<ChildSample> childSampleList)
    {
        this.context = context;
        this.childSampleList = childSampleList;
    }
    @Override
    public int getCount() {
        return childSampleList.size();
    }

    @Override
    public ChildSample getItem(int position) {
        return childSampleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        ChildSample currentItem = getItem(position);

        convertView = inflater.inflate(R.layout.rowitem_child_ordersdetails, parent, false);

        TextView txtDescription = (TextView)convertView.findViewById(R.id.txtDescription_RowItemChildView);
        TextView txtProductCode = (TextView)convertView.findViewById(R.id.txtProductCode_RowItemChildView);
        TextView txtQuantity = (TextView)convertView.findViewById(R.id.txtQuantity_RowItemChildView);
        TextView txtUnitPrice = (TextView)convertView.findViewById(R.id.txtUnitPrice_RowItemChildView);
        TextView txtResult = (TextView)convertView.findViewById(R.id.txtResult_RowItemChildView);

        txtDescription.setText( currentItem.description);
        txtProductCode.setText(currentItem.productCode);
        txtQuantity.setText(String.valueOf(currentItem.quantity));
        txtUnitPrice.setText(String.valueOf(currentItem.unitPrice));
        double result = currentItem.quantity * currentItem.unitPrice;
        txtResult.setText(String.valueOf(result));

        return convertView;
    }


    public double temporarySum()
    {
        double sum = 0;
        for (int i =0; i <getCount(); i++)
        {

            sum += getItem(i).quantity * getItem(i).unitPrice;
        }
        return sum;
    }
}
