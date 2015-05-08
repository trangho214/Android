package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Sample.ChildSample;
import Sample.SampleObject1;
import softs.hnt.com.batda.R;

/**
 * Created by TrangHo on 08-05-2015.
 */
public class OrdersDetailsExpandableAdapter extends BaseExpandableListAdapter {
    Context context;
    List<SampleObject1> parentList;

    public OrdersDetailsExpandableAdapter(Context context, List<SampleObject1> parentList){
        this.context = context;
        this.parentList = parentList;
    }
    @Override
    public int getGroupCount() {
        return parentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return getGroup(groupPosition).childSamples.size() +2;
    }

    @Override
    public SampleObject1 getGroup(int groupPosition) {
        return parentList.get(groupPosition);
    }

    @Override
    public ChildSample getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition).childSamples.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        SampleObject1 currentParent = getGroup(groupPosition);
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rowitem_parent_ordersdetail,null);
        }
        TextView txtCustomerName = (TextView)convertView.findViewById(R.id.txtName_RowItemParent);
        TextView txtCity = (TextView)convertView.findViewById(R.id.txtCity_RowItemParent);
        TextView txtTime = (TextView)convertView.findViewById(R.id.txtTime_RowItemParent);
        TextView txtPrice = (TextView)convertView.findViewById(R.id.txtPrice_RowItemParent);
        ImageView ivIndicator = (ImageView)convertView.findViewById(R.id.ivExpandIcon_RowItemParent);
        if(isExpanded)
        {
            ivIndicator.setImageResource(R.drawable.ic_action_expand);
        }
        else
        {
            ivIndicator.setImageResource(R.drawable.ic_action_next_item);
        }
        txtCustomerName.setText(currentParent.title);
        txtCity.setText(currentParent.city);
        txtTime.setText(currentParent.date);
        txtPrice.setText(currentParent.price);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SampleObject1 currentParent = getGroup(groupPosition);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(childPosition ==0)
        {
            convertView = inflater.inflate(R.layout.header_chilview,null);
            TextView txtPhoneNumber = (TextView)convertView.findViewById(R.id.txtPhoneNumber_HeaderChildView);
            TextView txtEmail = (TextView)convertView.findViewById(R.id.txtEmail_HeaderChildView);
            TextView txtAddress = (TextView)convertView.findViewById(R.id.txtAddress_HeaderChildView);

            txtPhoneNumber.setText(currentParent.phoneNumber);
            txtEmail.setText(currentParent.email);
            txtAddress.setText(currentParent.address);
        }
        if(childPosition>0 && childPosition<getChildrenCount(groupPosition)-1)
        {
            ChildSample currentChild = getChild(groupPosition, childPosition-1);
            convertView = inflater.inflate(R.layout.rowitem_child_ordersdetails,null);
            TextView txtDescription = (TextView)convertView.findViewById(R.id.txtDescription_RowItemChildView);
            TextView txtProductCode = (TextView)convertView.findViewById(R.id.txtProductCode_RowItemChildView);
            TextView txtQuantity = (TextView)convertView.findViewById(R.id.txtQuantity_RowItemChildView);
            TextView txtUnitPrice = (TextView)convertView.findViewById(R.id.txtUnitPrice_RowItemChildView);
            TextView txtResult = (TextView)convertView.findViewById(R.id.txtResult_RowItemChildView);

            txtDescription.setText(currentChild.description);
            txtProductCode.setText(currentChild.productCode);
            txtQuantity.setText(String.valueOf(currentChild.quantity));
            txtUnitPrice.setText(String.valueOf(currentChild.unitPrice));
            txtResult.setText(String.valueOf(Result(currentChild)));
        }
        if (childPosition == getChildrenCount(groupPosition)-1)
        {
            convertView = inflater.inflate(R.layout.footer_chilview,null);

            TextView txtTemporarySum = (TextView)convertView.findViewById(R.id.txtTemporarySum_FooterChildView);
            TextView txtDiscount = (TextView)convertView.findViewById(R.id.txtDiscount_FooterChildView);
            TextView txtShippingCost = (TextView)convertView.findViewById(R.id.txtShippingCost_FooterChildView);
            TextView txtSum = (TextView)convertView.findViewById(R.id.txtSum_FooterChildView);
            double tempSum = TempSum(currentParent.childSamples);
            txtTemporarySum.setText(String.valueOf(tempSum));
            txtDiscount.setText(String.valueOf(currentParent.discount));
            txtShippingCost.setText(String.valueOf(currentParent.shippingCost));
            txtSum.setText(String.valueOf(Sum(tempSum, currentParent.discount, currentParent.shippingCost)));
        }
        return convertView;
    }

    private double Result(ChildSample childSample)
    {
        return (double)childSample.unitPrice * childSample.quantity;
    }
    private double TempSum(List<ChildSample> childSamples)
    {
        double temp = 0;
        for (int i =0; i<childSamples.size(); i++)
        {
            temp += Result(childSamples.get(i));
        }
        return temp;
    }


    private double Sum(double tempSum, double discount, int shippingCost)
    {
      return tempSum - (tempSum*discount) + shippingCost;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
