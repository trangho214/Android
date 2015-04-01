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

/**
 * Created by TrangHo on 13-03-2015.
 */
public class ChangeCode extends ActionBarActivity {
    Button btnCancel, btnDone;
    TextView txtError;
    EditText edOldCode, edNewCode, edConfirmCode;
    String oldCode, newCode, confirmCode,savedCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_code);
        init();
    }

    private void init()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnCancel = (Button)findViewById(R.id.btnCancel_ChangeCode);
        btnDone = (Button)findViewById(R.id.btnDone_ChangeCode);
        edOldCode = (EditText)findViewById(R.id.edOldCode_ChangeCode);
        edNewCode = (EditText)findViewById(R.id.edNewCode_ChangeCode);
        edConfirmCode = (EditText)findViewById(R.id.edConfirmCode_ChangeCode);
        txtError = (TextView)findViewById(R.id.txtError);
        btnCancel.setOnClickListener(onClick);
        btnDone.setOnClickListener(onClick);
        savedCode = SharedPreferencesManager.getString(this, SharedPreferencesManager.ALARM_CODE);
    }
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent returnIntent = new Intent();
            switch (v.getId()) {
                case R.id.btnCancel_ChangeCode:
                    setResult(RESULT_CANCELED, returnIntent);
                    finish();
                    break;
                case R.id.btnDone_ChangeCode:
                    oldCode = edOldCode.getText().toString();
                    newCode = edNewCode.getText().toString();
                    confirmCode = edConfirmCode.getText().toString();
                    if(!savedCode.equals(oldCode))
                    {
                        txtError.setText("Old code is wrong!!!");
                        edOldCode.setBackgroundColor(getResources().getColor(R.color.line));
                        edOldCode.setFocusable(true);
                    }
                    else {
                        if(!newCode.equals(confirmCode))
                        {
                            txtError.setText(getResources().getString(R.string.not_match_code));
                            edNewCode.setFocusable(true);
                        }
                        else {
                            SharedPreferencesManager.saveString(ChangeCode.this, newCode,SharedPreferencesManager.ALARM_CODE);
                            setResult(RESULT_OK, returnIntent);
                            finish();
                        }
                    }
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
