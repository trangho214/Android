package softs.hnt.com.toyswap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import custom.EmailValidator;
import model.City;
import model.District;
import model.UserInfo;

/**
 * Created by TrangHo on 09-10-2014.
 */
//Share preferences is first create and saved when user gets respond about userId, which is returned from server
// after "sign me up", at Sign up and cleaned at Log Out.
// Then isLogIn is checked at Splash if True then go to dashboard, if FALSE then show Splash activity.
public class SignUp extends CommonActivity{
    int districtId,userId = 0;
    EmailValidator emailValidator;
    Spinner spCity, spDistrict;
    EditText edUserName, edPassword, edConfirmPass, edEmail, edStreetAndNumber, edPhoneNumber;
    Button btnSignMeUp;
    TextView txtCaution;
    String streetAndNumber, district, city, userName, password, confirmPass, email, phoneNumber,address;
    ArrayAdapter<District>  districtAdapter;
    ArrayAdapter<City> cityAdapter;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean isEdit = getIntent().getBooleanExtra("isEdit",false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        init();
        if(isEdit)
            ifIsEdit();
    }
    private void init()
    {
        getPlaceFromShareFile();
        emailValidator = new EmailValidator();
        spCity = (Spinner)findViewById(R.id.spCity_SigUp);
        spDistrict = (Spinner)findViewById(R.id.spDistrict_SigUp);
        edUserName = (EditText)findViewById(R.id.edUserName_SignUp);
        edPassword = (EditText)findViewById(R.id.edPassword_SignUp);
        edConfirmPass = (EditText)findViewById(R.id.edConfirmPassword_SignUp);
        edStreetAndNumber = (EditText)findViewById(R.id.edAddress_SigUp);
        edPhoneNumber = (EditText)findViewById(R.id.edPhoneNumber_SignUp);
        edEmail = (EditText)findViewById(R.id.edEmail_SignUp);
        txtCaution = (TextView)findViewById(R.id.txtCaution_SignUp);
        btnSignMeUp = (Button)findViewById(R.id.btnSignMeUp_SignUp);
        btnSignMeUp.setOnClickListener(onClick);
        cityAdapter = new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item,cities);
        spCity.setAdapter(cityAdapter);
        spCity.setOnItemSelectedListener(onItemSelectedListener);

        districtAdapter = new ArrayAdapter<District>(this, android.R.layout.simple_spinner_item,districts);
        spDistrict.setAdapter(districtAdapter);
    }

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent.getId() == R.id.spCity_SigUp) {
                districts.clear();
                for (District district1 : cities.get(position).districts) {

                    districts.add(district1);
                    if(districtAdapter!= null)
                    {
                        districtAdapter.notifyDataSetChanged();
                        if(districtEditPosition!=0)
                        spDistrict.setSelection(districtEditPosition);
                    }
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    List<City> cities = new ArrayList<City>();
    List<District> districts = new ArrayList<District>();
    private void getPlaceFromShareFile()
    {
        gson = new Gson();
        String json = SharePrefManager.getPlaceFromFile(SignUp.this);
        Type listType = new TypeToken<List<City>>(){}.getType();
        Log.d("Can not get file", json);
        if(json != null)
            cities  = gson.fromJson(json, listType);
    }

    private boolean isDataValid()
    {
        userName = edUserName.getText().toString();
        password = edPassword.getText().toString();
        confirmPass = edConfirmPass.getText().toString();
        email = edEmail.getText().toString();
        phoneNumber = edPhoneNumber.getText().toString();
        streetAndNumber = edStreetAndNumber.getText().toString();
        district = spDistrict.getSelectedItem().toString();
        districtId = ((District)spDistrict.getSelectedItem()).id;
        city = spCity.getSelectedItem().toString();
        //Address in form of fx: Nyborgvej 1, Odense SØ, at the server side, city and country will be added to the address
        //to find out the the coordinates.
        address = streetAndNumber + "," + district ;
        address = CommonMethodClass.specialLetterToNormal(address);
        if(userName.contains(" "))
        {
            txtCaution.setText("No space in username");
        }
        if(userName.length()==0|| password.length()==0|| email.length()== 0 || streetAndNumber.length()==0)
        {
            txtCaution.setText("Please fill up all of field");
            return false;
        }
//        address = streetAndNumber + " "  + number + "," + district + "," + city + ",Denmark" ;
        if(!password.equals(confirmPass))
        {
            txtCaution.setText("Password not match!!!");
            edPassword.setFocusable(true);
            return false;
        }
        if(!emailValidator.isValid(email))
        {
            txtCaution.setText("Wrong email!!!");
            edEmail.setFocusable(true);
            return false;
        }

        return true;
    }

    UserInfo userInfo;
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Then send data to server and get user Id back
            //Check if userId != -1. start save user info tp share preferences.
            //it will be implement later after web service is set up :)
            if(isDataValid()) {
                userInfo = new UserInfo(userId, userName, password,email,phoneNumber, address, true, true, "http://userImageURL.com", districtId);
                // userInfo = new UserInfo(0, "trangabc", "abc","trang@gmail.com","123456", "Nyborgvej 22, odense SØ", true, true, "http://userImageURL.com", 5);
                if(((Button)v).getText().toString().equals("Save"))
                    sendRequest(UserAction.updateUser).execute();
                else sendRequest(UserAction.createUser).execute();
            }
        }
    };
    private HttpHandler sendRequest(UserAction userAction)
    {

        return new HttpHandler(this,userAction,userInfo,"Uploading"){
            @Override
            public void doOnResponse(String result) {
                Log.d("Result from server", result);
                if(result.equals(""))
                {
                    edUserName.setHintTextColor(getResources().getColor(R.color.caution));
                    edUserName.getText().clear();
                    edUserName.setHint("Username existed!!!");
                    txtCaution.setHint("Username existed!!!");
                    edUserName.setFocusable(true);
                }
                else
                {
                    userInfo = gson.fromJson(result, UserInfo.class);
                //    SharePrefManager.saveUser(SignUp.this, userInfo);
                    SharePrefManager.saveToSharePref(SignUp.this, SharePrefManager.USER, result);
                    Intent intent = new Intent(SignUp.this, DrawerActivity.class);
                    intent.putExtra("userId", userInfo.id);
                    Log.d("Result Userid",String.valueOf(userInfo.id));
                    startActivity(intent);
                }
            }
        };
    }

    //edit region
    private void ifIsEdit()
    {
        setTitle("Edit Account");
        edPassword.setEnabled(false);
        edConfirmPass.setEnabled(false);
        UserInfo user = SharePrefManager.getUser(SignUp.this);
        userId = user.id;
        edEmail.setText(user.email);
        edPhoneNumber.setText(user.phoneNumber);
        edUserName.setText(user.userName);
        edPassword.setText(user.password);
        edConfirmPass.setText(user.password);
        int lastIndexOf = user.address.lastIndexOf(",");
        edStreetAndNumber.setText(user.address.substring(0, lastIndexOf));
        findCityAndDistrictFromDistrictId(user.districtId);
        btnSignMeUp.setText("Save");
    }


    int districtEditPosition=0;
    private void findCityAndDistrictFromDistrictId(int id)
    {
        int cityEditPosition=0;
        for (int i = 0 ; i<cities.size(); i++)
        {
            for (int j = 0; j <cities.get(i).districts.size(); j++)
            {
                if((cities.get(i).districts).get(j).id == id)
                {
                    districtId = id;
                    districtEditPosition = j;
                    cityEditPosition = i;
                    break;
                }
            }
        }
        spCity.setSelection(cityEditPosition);
        //The only way to get "spDistrict.setSelection(districtEditPosition);" to work
        //is put it inside OnItemSelectedListener
    }
}
