package fragment;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import model.Result;
import model.UserInfo;
import softs.hnt.com.toyswap.HttpHandler;
import softs.hnt.com.toyswap.R;
import softs.hnt.com.toyswap.SharePrefManager;
import softs.hnt.com.toyswap.UserAction;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ChangePasswordFragment extends Fragment {

    Button btnSave;
    EditText edOldPass, edNewPass, edConfirm;
    TextView txtCaution;
    String oldPass, newPass, confirm;
    UserInfo user;
    String id;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }
    ActionBar actionBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);
        actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        init(v);
        return v;
    }

    private void init(View v)
    {
        btnSave = (Button)v.findViewById(R.id.btnSave_ChangePassword);
        btnSave.setOnClickListener(onClick);
        edOldPass = (EditText)v.findViewById(R.id.edOldPassword_ChangePassword);
        edNewPass = (EditText)v.findViewById(R.id.edNewPassword_ChangePassword);
        edConfirm =(EditText)v.findViewById(R.id.edConfirmPassword_ChangePassword);
        txtCaution = (TextView)v.findViewById(R.id.txtCaution_ChangePassword);
        user = SharePrefManager.getUser(getActivity());
        id = String.valueOf(user.id);
    }




    private boolean isValid()
    {
        oldPass = edOldPass.getText().toString();
        newPass = edNewPass.getText().toString();
        confirm = edConfirm.getText().toString();
        if(!newPass.equals(confirm))
        {
            txtCaution.setText("Passwords not match!!!");
            return false;
        }
        return true;
    }
    //
//
//
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isValid()) {
                httpHandler().execute();
            }
        }
    };

    private HttpHandler httpHandler()
    {
        return  new HttpHandler(getActivity(), UserAction.changePassword,id,oldPass,newPass, "Changing...") {
            @Override
            public void doOnResponse(String result) {
                Gson gson = new Gson();
                Result r = gson.fromJson(result, Result.class);
                if(r.getWasSuccessful()==1)
                {
                    txtCaution.setText("Password is changed!");
                }
                else
                {
                    txtCaution.setText("Wrong password!!!");
                }
            }
        };
    }
}
