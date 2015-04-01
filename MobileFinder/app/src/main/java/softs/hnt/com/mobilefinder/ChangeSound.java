package softs.hnt.com.mobilefinder;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;

import java.io.IOException;
import java.lang.reflect.Field;

import utility.PlayListAdapter;
import utility.RadioClick;
import utility.SharedPreferencesManager;

/**
 * Created by TrangHo on 13-03-2015.
 */
public class ChangeSound extends ActionBarActivity implements RadioClick {
    PlayListAdapter adapter;
    //ArrayAdapter<String> adapter;
    ListView listView;
    MediaPlayer mp;
    String[] titles;
    Button btnCancel, btnDone;
    //this path is saved to SharedPreferences.
    String chosenSoundPath, soundTitle;
    int soundIndex;
    View currentSelectedView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_sound);
        init();
    }

    private void init()
    {
        getSupportActionBar().hide();
        listView = (ListView)findViewById(R.id.lvPlayList);
        btnCancel =(Button)findViewById(R.id.btnCancel_Playlist);
        btnDone = (Button)findViewById(R.id.btnDone_Playlist);
        btnDone.setOnClickListener(onClick);
        btnCancel.setOnClickListener(onClick);
        Field[] fields = R.raw.class.getFields();
        titles = new String[fields.length];
        for(int i =0; i<fields.length; i++)
        {
            titles[i] = fields[i].getName();
        }
        soundIndex = SharedPreferencesManager.getInt(this, SharedPreferencesManager.SOUND_INDEX);
        soundTitle =titles[soundIndex];
        adapter = new PlayListAdapter(this,R.layout.sound_item, titles, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
        mp = new MediaPlayer();
    }

    private void highlightCurrentRow(View rowView) {
        ((RadioButton)rowView.findViewById(R.id.rdPlay_SoundItem)).setChecked(true);
//        TextView textView = (TextView) rowView.findViewById(R.id.txtSoundTitle_SoundItem);
//        textView.setTextColor(getResources().getColor(R.color.line));
    }

    private void unHighlightCurrentRow(View rowView) {
        ((RadioButton)rowView.findViewById(R.id.rdPlay_SoundItem)).setChecked(false);
    }
    View view;
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            handleSelectedItem(position);
        }
    };

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent returnIntent = new Intent();
            if(mp.isPlaying())
            {
                mp.stop();
                mp.release();
            }
            switch (v.getId())
            {
                case R.id.btnCancel_Playlist:
                    setResult(RESULT_CANCELED, returnIntent);
                    break;
                case R.id.btnDone_Playlist:
                    SharedPreferencesManager.saveString(ChangeSound.this, chosenSoundPath, SharedPreferencesManager.SOUND_PATH);
                    SharedPreferencesManager.saveInt(ChangeSound.this, soundIndex, SharedPreferencesManager.SOUND_INDEX);
                    returnIntent.putExtra(Common.SOUND_TITLE, soundTitle);
                    setResult(RESULT_OK, returnIntent);
                    break;
            }
            finish();
        }
    };



    @Override
    protected void onDestroy() {
        if(mp!= null)
        {
            mp.release();
            mp = null;
        }
        super.onDestroy();
    }


    @Override
    public void onRadioClickListener(View v, int position) {
        handleSelectedItem(position);
    }

    private void handleSelectedItem(int position)
    {
        unHighlightCurrentRow(listView.getChildAt(soundIndex));
//            if(currentSelectedView!= null)
//                currentSelectedView.setBackgroundColor(getApplication().getResources().getColor(R.color.white));
        soundIndex = position;
        soundTitle = titles[position];
        chosenSoundPath = "android.resource://"+getPackageName()+"/raw/"+soundTitle;
        currentSelectedView = listView.getChildAt(soundIndex);
        highlightCurrentRow(currentSelectedView);
        Uri uri = Uri.parse(chosenSoundPath);
        if(mp.isPlaying())
        {
            mp.stop();
//                mp.release();
        }
        mp = new MediaPlayer();
//            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mp.setDataSource(ChangeSound.this, uri);
            mp.prepare();
            mp.setVolume(1.0f, 1.0f);
            mp.start();
//                    mp.setOnCompletionListener(onCompletionListener);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
