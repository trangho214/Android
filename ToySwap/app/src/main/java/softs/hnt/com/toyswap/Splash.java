package softs.hnt.com.toyswap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import model.City;
import model.Country;
import model.State;
import model.UserInfo;

/**
 * Created by TrangHo on 09-10-2014.
 */
public class Splash extends CommonActivity {

    public UserInfo currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = new UserInfo();
        firstLaunchCheck();
    }

    private void checkIfLoggedIn()
    {
        currentUser =  SharePrefManager.getUser(this);
        if(currentUser != null)
        {
            Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
            //Clear all the task in backstack and set this activity as a new task
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("userId",currentUser.id);
            startActivity(intent);
        }
        else
        {
            setContentView(R.layout.splash);
            getActionBar().hide();
            this.startMyActivityFromId(R.id.splash_ComeAndSee, DrawerActivity.class,0);
            this.startMyActivityFromId(R.id.splash_Login, Login.class,0);
            this.startMyActivityFromId(R.id.splash_SignUp, SignUp.class,0);
        }
    }

    //check if is the first launch
    private void firstLaunchCheck()
    {
        //if isFirstLaunch == false this means it is not the first launch
        if(!SharePrefManager.isFirstLaunch(this))
        {
          //  SharePrefManager.removeFromSharePref(this, SharePrefManager.FIRSTlAUNCH);
            checkIfLoggedIn();
        }
        else
        {

            getPlaceHandler.execute();
            getGroupHandler.execute();
            SharePrefManager.saveFirstLaunch(Splash.this);
            checkIfLoggedIn();
        }
    }

    HttpHandler getGroupHandler = new HttpHandler(this, UserAction.getAllGroups,"Please wait for initiate") {
        @Override
        public void doOnResponse(String result) {
            SharePrefManager.saveToSharePref(Splash.this, SharePrefManager.GROUP, result);
        }
    };

    HttpHandler getPlaceHandler = new HttpHandler(this, UserAction.getAllPlaces,"Please wait for initiate") {
        @Override
        public void doOnResponse(String result) {
            //Places is saved here but is read in Sign up.
            //Because there is only sign up, working with places.
            Gson gson = new Gson();
            Country c = new Country();
            List<City> cities = new ArrayList<City>();
            try {
                Type listType = new TypeToken<List<Country>>(){}.getType();
                for(Country country : (List<Country>) gson.fromJson(result,listType))
                {
                    c = country;
                }
                for (State state: c.states)
                {
                    if(state.cities.size()>0)
                    {
                        for (City city: state.cities)
                        {
                            cities.add(city);
                        }
                    }
                }
                String citiesPrint = gson.toJson(cities);

                SharePrefManager.savePlaceToFile(Splash.this, citiesPrint);
                Log.d("City print", citiesPrint);
            }
            catch (Exception e)
            {
                Log.d("Country", e.getMessage());
            }
           // SharePrefManager.savePlaceToFile(Splash.this, result);
        }
    };
}
