package softs.hnt.com.mobilefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Field;

import utility.SharedPreferencesManager;

/**
 * Created by TrangHo on 14-03-2015.
 */
public class Splash extends ActionBarActivity {
    private final static int ENTER_CODE_RESULT = 1;
    private final static int CHANE_CODE_RESULT = 2;
    private final static int CHANGE_SOUND_RESULT = 3;
    Field[] fields;
    Button btnEnter_ChangeCode, btnChangeSound;
    String alarmCode,soundTitle;
    int soundIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        init();
    }
    private void init()
    {
        getSupportActionBar().hide();
        btnEnter_ChangeCode = (Button)findViewById(R.id.btnEnterCode_Splash);
        btnChangeSound = (Button)findViewById(R.id.btnChangeSound_Splash);
        btnChangeSound.setOnClickListener(onClick);
        btnEnter_ChangeCode.setOnClickListener(onClick);

        fields = R.raw.class.getFields();
        soundIndex = SharedPreferencesManager.getInt(this, SharedPreferencesManager.SOUND_INDEX);
        soundTitle = fields[soundIndex].getName();
        btnChangeSound.setText((soundTitle));
        alarmCode = SharedPreferencesManager.getString(this, SharedPreferencesManager.ALARM_CODE);
        if(alarmCode.equals("default"))
            btnEnter_ChangeCode.setText(getResources().getString(R.string.enter_code));
        else
            btnEnter_ChangeCode.setText(getResources().getString(R.string.change_code));
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            //it's used for first time after entering code and user want to change it right after.
            alarmCode = SharedPreferencesManager.getString(Splash.this, SharedPreferencesManager.ALARM_CODE);
            switch (v.getId())
            {
                case R.id.btnEnterCode_Splash:

                    if(!alarmCode.equals("default"))
                    {
                        intent = new Intent(Splash.this, ChangeCode.class);
                        startActivityForResult(intent,CHANE_CODE_RESULT);
                    }
                    else
                    {
                        intent = new Intent(Splash.this, EnterCode.class);
                        startActivityForResult(intent, ENTER_CODE_RESULT);
                    }
                    break;
                case R.id.btnChangeSound_Splash:
                    intent = new Intent(Splash.this, ChangeSound.class);
                    startActivityForResult(intent,CHANGE_SOUND_RESULT);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case CHANE_CODE_RESULT:
                if(resultCode == RESULT_OK)
                    Toast.makeText(this,getResources().getString(R.string.code_is_change),Toast.LENGTH_LONG).show();
                break;
            case ENTER_CODE_RESULT:
                if(resultCode == RESULT_OK)
                    Toast.makeText(this,getResources().getString(R.string.code_is_saved),Toast.LENGTH_LONG).show();
                    btnEnter_ChangeCode.setText(getResources().getString(R.string.change_code));

                break;
            case CHANGE_SOUND_RESULT:
                if(resultCode ==RESULT_OK)
                {
                    soundTitle = data.getStringExtra(Common.SOUND_TITLE);
                    btnChangeSound.setText(soundTitle);
                }
        }
    }
}
