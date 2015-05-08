package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Sample.SampleObject1;
import softs.hnt.com.batda.R;

/**
 * Created by TrangHo on 27-04-2015.
 */

//this is used for slidingbar.
public class CustomSlidingBarAdapter extends ArrayAdapter<SampleObject1> {

    public CustomSlidingBarAdapter(Context context, List<SampleObject1> objects) {
        super(context,0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }
        ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
        icon.setImageResource(getItem(position).imageId);
        TextView title = (TextView) convertView.findViewById(R.id.row_title);
        title.setText(getItem(position).title);

        return convertView;
    }
}

