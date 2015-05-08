package Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Sample.SampleObject1;
import softs.hnt.com.batda.R;

/**
 * Created by TrangHo on 24-04-2015.
 */

//This adapter is used for 2 different listviews in 2 different viewpager in DashboardFragment
public class CustomListAdapter extends BaseAdapter {
    Activity activity;
    List<SampleObject1> data;
    int vp_number;

    public CustomListAdapter(Activity activity, List<SampleObject1> data, int vp_number)
    {
        this.activity = activity;
        this.data = data;
        this.vp_number = vp_number;
    }
    public CustomListAdapter(Activity activity, List<SampleObject1> data)
    {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.rowitem_dashhboard,parent,false);
            ViewHolder vh = new ViewHolder();
            vh.iv = (ImageView)convertView.findViewById(R.id.iv_RowView1);
            vh.txt_title = (TextView) convertView.findViewById(R.id.txtTitle_RowView1);
            vh.txt_price = (TextView) convertView.findViewById(R.id.txtPrice_RowView1);
            vh.txt_time = (TextView) convertView.findViewById(R.id.txtDateTIme_RowView1);
            if(vp_number==2)
            {
                vh.txt_price.setVisibility(View.GONE);
                vh.txt_time.setVisibility(View.GONE);
            }

            convertView.setTag(vh);
        }
        SampleObject1 objectItem = data.get(position);
        ViewHolder viewHolder = (ViewHolder)convertView.getTag();
        if(vp_number==1) {
            viewHolder.txt_price.setText(objectItem.price);
            viewHolder.txt_time.setText(objectItem.date);
        }
        viewHolder.txt_title.setText(objectItem.title);

        viewHolder.iv.setImageResource(objectItem.imageId);

        return convertView;
    }
    static class ViewHolder{
        ImageView iv;
        TextView txt_title;
        TextView txt_time;
        TextView txt_price;
    }
}
