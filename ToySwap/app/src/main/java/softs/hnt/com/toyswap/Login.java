package softs.hnt.com.toyswap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import model.UserInfo;

/**
 * Created by TrangHo on 09-10-2014.
 */

public class Login extends CommonActivity {
    TextView txtCaution, txtForgotPassword;
    EditText edUserName, edPassword;
    Button btnLogin;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
    }
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnLogin_Login)
            {
                new HttpHandler(Login.this,UserAction.login, edUserName.getText().toString(),edPassword.getText().toString(),"Checking...") {

                    @Override
                    public void doOnResponse(String result) {
                        Log.d("Result from http", result);
                        if(result.equals(""))
                        {
                            txtCaution.setText("Username or password is not correct.");
                        }
                        else
                        {
                            UserInfo u = gson.fromJson(result, UserInfo.class);
                            SharePrefManager.saveToSharePref(Login.this, SharePrefManager.USER, result);
                           // SharePrefManager.saveUser(Login.this, u);
                            Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("userId", u.id);
                            startActivity(intent);
                        }
                    }
                }.execute();
            }
            else if(v.getId() == R.id.txtForgotPassword_Login)
            {
                startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
            }
        }
    };
    private void init()
    {
        gson = new Gson();
        txtCaution = (TextView)findViewById(R.id.txtCaution_Login);
        txtForgotPassword = (TextView)findViewById(R.id.txtForgotPassword_Login);
        txtForgotPassword.setOnClickListener(onClick);
        edUserName = (EditText)findViewById(R.id.edUserName_Login);
        edPassword = (EditText)findViewById(R.id.edPassword_Login);
        btnLogin = (Button)findViewById(R.id.btnLogin_Login);
        btnLogin.setOnClickListener(onClick);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
