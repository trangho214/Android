package utility;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import softs.hnt.com.mobilefinder.R;

/**
 * Created by TrangHo on 13-03-2015.
 */
public class PlayListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] titles;
    int layoutId;
    int soundIndex;
    RadioClick radioClick;

    static class ViewHolder{
        public TextView text;
        public RadioButton radioButton;
    }

    public PlayListAdapter(Activity context, int layoutId,  String[] titles, RadioClick radioClick) {
        super(context, R.layout.sound_item,layoutId, titles        );
        this.context = context;
        this.titles = titles;
        this.layoutId = layoutId;
        this.radioClick = radioClick;
        soundIndex = SharedPreferencesManager.getInt(context,SharedPreferencesManager.SOUND_INDEX);
        Log.d("incoming constructor","constructor" );
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        int i =0;
        //reuse views
        if(rowView == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(layoutId, null);
            //configure viewholder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView)rowView.findViewById(R.id.txtSoundTitle_SoundItem);
            viewHolder.radioButton = (RadioButton)rowView.findViewById(R.id.rdPlay_SoundItem);
            rowView.setTag(viewHolder);
            //Need to set this row and call getId later or list will go crazy. work with line 64
            // ( without this row the first item in the list is always marked or checked..)
            rowView.setId(position);
        }


        ViewHolder holder = (ViewHolder)rowView.getTag();
        i = (int)rowView.getId();
        String s = titles[position];
        holder.text.setText(s);
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioClick.onRadioClickListener(v, position);
            }
        });

// if the following line is used the list goes wrong.
//       if(position ==SharedPreferencesManager.getInt(context,SharedPreferencesManager.SOUND_INDEX))
        //It's important to check condition
        if(i ==SharedPreferencesManager.getInt(context,SharedPreferencesManager.SOUND_INDEX))
        {
            holder.radioButton.setChecked(true);
            Log.d("incoming", String.valueOf(i));
        }

        return rowView;
    }

}

