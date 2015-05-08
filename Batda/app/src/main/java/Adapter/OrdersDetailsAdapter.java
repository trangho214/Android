package Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import Sample.SampleObject1;
import softs.hnt.com.batda.CommonMethods;
import softs.hnt.com.batda.R;

/**
 * Created by TrangHo on 06-05-2015.
 */
public class OrdersDetailsAdapter extends BaseAdapter {

    List<SampleObject1> sampleObject1List;
    Activity context;
    public OrdersDetailsAdapter(Activity context, List<SampleObject1> sampleObject1List)
    {
        this.context = context;
        this.sampleObject1List = sampleObject1List;
    }

    @Override
    public int getCount() {
        return sampleObject1List.size();
    }

    @Override
    public SampleObject1 getItem(int position) {
        return sampleObject1List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        SampleObject1 currentObject = getItem(position);
        ChildOrdersDetailsAdapter childOrdersDetailsAdapter = new ChildOrdersDetailsAdapter(context, currentObject.childSamples);


        double temporarySum = childOrdersDetailsAdapter.temporarySum();
        double discount = temporarySum *currentObject.discount;
        double sum = temporarySum - (temporarySum*currentObject.discount) + currentObject.shippingCost;
        int i = 0;
        if(rowView == null)
        {

            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.rowitem_ordersdetails,parent, false);
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.txtCustomerName = (TextView)rowView.findViewById(R.id.txtName_RowItemOrdersDetails);
            viewHolder.txtCity = (TextView)rowView.findViewById(R.id.txtCity_RowItemOrdersDetails);
            viewHolder.txtTime = (TextView)rowView.findViewById(R.id.txtTime_RowItemOrdersDetails);
            viewHolder.txtPrice = (TextView)rowView.findViewById(R.id.txtPrice_RowItemOrdersDetails);
            viewHolder.ivExpand = (ImageView)rowView.findViewById(R.id.ivExpandIcon_RowItemOrdersDetails);
            viewHolder.rlo = (RelativeLayout)rowView.findViewById(R.id.rlo_ChildView);
//            loInclude = (RelativeLayout)rowView.findViewById(R.id.rloChild_RowItemOrdersDetails);
            viewHolder.txtPhoneNumber = (TextView)rowView.findViewById(R.id.txtPhoneNumber_ChildView);
            viewHolder.txtEmail = (TextView)rowView.findViewById(R.id.txtEmail_ChildView);
            viewHolder.txtAddress = (TextView)rowView.findViewById(R.id.txtAddress_ChildView);
            viewHolder.lvChild = (ListView)rowView.findViewById(R.id.lvDetail_ChildView);

            View vFooter = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listviewchild_footer,null,false);
            viewHolder.txtTemporarySum = (TextView)vFooter.findViewById(R.id.txtTemporarySum_ChildView);
            viewHolder.txtDiscount = (TextView)vFooter.findViewById(R.id.txtDiscount_ChildView);
            viewHolder.txtShipCost = (TextView)vFooter.findViewById(R.id.txtShippingCost_ChildView);
            viewHolder.txtSum = (TextView)vFooter.findViewById(R.id.txtSum_ChildView);
            viewHolder.lvChild.addFooterView(vFooter);

//            viewHolder.txtTemporarySum = (TextView)rowView.findViewById(R.id.txtTemporarySum_ChildView);
//            viewHolder.txtDiscount = (TextView)rowView.findViewById(R.id.txtDiscount_ChildView);
//            viewHolder.txtShipCost = (TextView)rowView.findViewById(R.id.txtShippingCost_ChildView);
//            viewHolder.txtSum = (TextView)rowView.findViewById(R.id.txtSum_ChildView);

            viewHolder.edDescription = (TextView)rowView.findViewById(R.id.txtDescription_ChildView);
            viewHolder.btnDeliveryConfirm = (Button)rowView.findViewById(R.id.btnDeliveryConfirm_ChildView);
            viewHolder.btnPayConfirm = (Button)rowView.findViewById(R.id.btnPayConfirm_ChildView);
            viewHolder.btnStatus = (Button)rowView.findViewById(R.id.btnStatus);

            rowView.setTag(viewHolder);
        }

        final ViewHolder vh = (ViewHolder)rowView.getTag();
        vh.txtCustomerName.setText(currentObject.customerName);
        vh.txtCity.setText(currentObject.city);
        vh.txtTime.setText(currentObject.date);
        vh.txtPrice.setText(currentObject.price);

        vh.ivExpand.setImageResource(R.drawable.ic_action_next_item);
        vh.ivExpand.setTag(R.drawable.ic_action_next_item);
        vh.rlo.setVisibility(View.GONE);
        vh.ivExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resourceId = (int)vh.ivExpand.getTag();
                if(resourceId == R.drawable.ic_action_next_item)
                {
                    vh.ivExpand.setImageResource(R.drawable.ic_action_expand);
                    vh.ivExpand.setTag(R.drawable.ic_action_expand);
                    vh.rlo.setVisibility(View.VISIBLE);
                }
                else
                {
                    vh.ivExpand.setImageResource(R.drawable.ic_action_next_item);
                    vh.ivExpand.setTag(R.drawable.ic_action_next_item);
                    vh.rlo.setVisibility(View.GONE);
                }
            }
        });


        vh.txtPhoneNumber.setText(currentObject.phoneNumber);
        vh.txtEmail.setText(currentObject.email);
        vh.txtAddress.setText(currentObject.address);
        vh.lvChild.setAdapter(childOrdersDetailsAdapter);
        CommonMethods.getListViewSize(vh.lvChild);


//        Log.d("batda log", String.valueOf(getItemHeightofListView(vh.lvChild, 5, childOrdersDetailsAdapter)));
//        int height = getItemHeightofListView(vh.lvChild, 5, childOrdersDetailsAdapter);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
//        vh.lvChild.setLayoutParams(layoutParams);

        vh.txtTemporarySum.setText(String.valueOf(childOrdersDetailsAdapter.temporarySum()));
        vh.txtDiscount.setText(String.valueOf(discount));
        vh.txtShipCost.setText(String.valueOf(currentObject.shippingCost));
        vh.txtSum.setText(String.valueOf(sum));

        vh.txtPhoneNumber.setText(currentObject.phoneNumber);
        return rowView;
    }


    static class ViewHolder{

        TextView txtCustomerName;
        TextView txtCity;
        TextView txtTime;
        TextView txtPrice;
        ImageView ivExpand;

        RelativeLayout rlo;
        TextView txtPhoneNumber;
        TextView txtEmail;
        TextView txtAddress;
        ListView lvChild;

        View vFooter;
        TextView txtTemporarySum;
        TextView txtDiscount;
        TextView txtShipCost;
        TextView txtSum;

        TextView edDescription;
        Button btnDeliveryConfirm;
        Button btnPayConfirm;
        Button btnStatus;
    }
}
