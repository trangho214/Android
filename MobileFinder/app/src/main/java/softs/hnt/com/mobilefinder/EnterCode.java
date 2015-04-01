package softs.hnt.com.mobilefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import utility.SharedPreferencesManager;


public class EnterCode extends ActionBarActivity {
    Button btnSave;
    TextView txtCaution;
    EditText edAlarmCode, edConfirmCode;
    String alarm_code, confirm_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_code);
        init();
    }

    private void init()
    {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        alarm_code = SharedPreferencesManager.getString(this, SharedPreferencesManager.ALARM_CODE);
        btnSave = (Button)findViewById(R.id.btnSave_Main);
        txtCaution = (TextView)findViewById(R.id.txtCaution_EnterCode);
        edAlarmCode = (EditText)findViewById(R.id.edCode_EnterCode);
        edConfirmCode = (EditText)findViewById(R.id.edConfirmCode_EnterCode);
        btnSave.setOnClickListener(onClick);
    }
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            alarm_code = edAlarmCode.getText().toString();
            confirm_code = edConfirmCode.getText().toString();
            if(!alarm_code.equals(confirm_code)|| alarm_code.equals("") || alarm_code == null || alarm_code =="")
            {
                txtCaution.setText("Codes not match!!!");
            }
            else
            {
                SharedPreferencesManager.saveString(EnterCode.this, alarm_code, SharedPreferencesManager.ALARM_CODE);
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
