package softs.hnt.com.toyswap;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import model.Result;

/**
 * Created by TrangHo on 05-01-2015.
 */
public class ForgotPassword extends CommonActivity {
    EditText edEmail;
    TextView txtCaution;
    Button btnSend;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        init();
    }
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                new HttpHandler(ForgotPassword.this,UserAction.forgetPassword, edEmail.getText().toString(),"Checking...") {

                    @Override
                    public void doOnResponse(String result) {
                        Log.d("Result from http", result);
                        if(result.equals(""))
                        {
                            txtCaution.setText("Network connection error. Try again later!!!");
                        }
                        else
                        {
                            Result r = gson.fromJson(result, Result.class);
                            txtCaution.setText(r.getException());                        }
                    }
                }.execute();
        }
    };
    private void init()
    {
        edEmail = (EditText)findViewById(R.id.edEmail_ForgotPassword);
        btnSend = (Button)findViewById(R.id.btnSend_ForgotPassword);
        txtCaution = (TextView)findViewById(R.id.txtCaution_ForgotPassword);
        btnSend.setOnClickListener(onClick);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
