package Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import Sample.SampleObject1;
import softs.hnt.com.batda.R;

/**
 * Created by TrangHo on 27-04-2015.
 */
public class CustomListAdapter2 extends CustomListAdapter {


    public CustomListAdapter2(Activity activity, List<SampleObject1> data) {
        super(activity, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.rowitem_dashhboard,parent,false);
            ViewHolder vh = new ViewHolder();
            vh.iv = (ImageView)convertView.findViewById(R.id.iv_RowView1);


            convertView.setTag(vh);
        }
        SampleObject1 objectItem = data.get(position);
        ViewHolder viewHolder = (ViewHolder)convertView.getTag();
        viewHolder.txt_title.setText(objectItem.title);
        viewHolder.iv.setImageResource(objectItem.imageId);

        return convertView;
    }
}
