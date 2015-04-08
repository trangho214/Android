package softs.hnt.com.toyswap;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import fragment.AboutFragment;
import fragment.ChangePasswordFragment;
import fragment.Drawer_HomeFragment;
import fragment.SettingsFragment;
import fragment.ShareFragment;
import model.UserInfo;

/**
 * Created by TrangHo on 04-11-2014.
 */
public class DrawerActivity extends ActionBarActivity  {
    int mPosition = 0;
//    String mTitle = "";

    // Array of strings storing title names
    ArrayList<String> nav_Titles;

    // Array of integers points to images stored in /res/drawable-ldpi/
    ArrayList<Integer> nav_ICons;


    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout mDrawer ;
    private List<HashMap<String,String>> mList ;
    private SimpleAdapter mAdapter;
    final private String TITLE = "title";
    final private String ICON = "icon";
    TextView txtUserName;
    UserInfo currentUser;
    //Guest gets 0 to currentUserId
    int currentUserId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        init();
    }

    private void init()
    {
        txtUserName =(TextView)findViewById(R.id.txtUserName_Drawer);
//        mTitle = (String)getTitle();
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        mDrawer = (RelativeLayout) findViewById(R.id.drawer);
        currentUser = SharePrefManager.getUser(DrawerActivity.this);
        if(currentUser== null)
            ifGuest();
        else
            ifUser();
        txtUserName.setOnClickListener(onClick);
        // Each row in the list stores title name, count and icon
        mList = new ArrayList<HashMap<String,String>>();
        for(int i=0;i<nav_Titles.size();i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put(TITLE, nav_Titles.get(i));
            hm.put(ICON, Integer.toString(nav_ICons.get(i)));
            mList.add(hm);
        }

        String[] from = {ICON, TITLE };
        int[] to = { R.id.ivIcon_DrawerListItem , R.id.txtTitle_DrawerListItem };
        mAdapter = new SimpleAdapter(this, mList, R.layout.drawer_list_item, from, to);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer , R.string.drawer_open,R.string.drawer_close){
            public void onDrawerClosed(View view) {
                highlightSelectedTitle();
                supportInvalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(getResources().getString(R.string.select_a_title));
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                showFragment(position);
                mDrawerLayout.closeDrawer(mDrawer);
            }
        });
        // Enabling Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.button)));

        // Setting the adapter to the listView
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setSelection(mPosition);
        mDrawerList.setItemChecked(0,true);
        showFragment(mPosition);
        highlightSelectedTitle();
    }
    private void ifGuest()
    {
        txtUserName.setText("Guest");
        nav_ICons = new ArrayList<Integer>(Arrays.asList(
                R.drawable.ic_question,
                R.drawable.ic_question,
                R.drawable.ic_question));
        nav_Titles = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.nav_drawer_guest_items)));
    }


    private void ifUser()
    {
        currentUserId = currentUser.id;
        txtUserName.setText(currentUser.userName);
        nav_ICons = new ArrayList<Integer>(Arrays.asList( R.drawable.ic_question,
                R.drawable.ic_question,
                R.drawable.ic_question,
                R.drawable.ic_question,
                R.drawable.ic_question,
                R.drawable.ic_question));
        nav_Titles = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.nav_drawer_items)));
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showFragment(int position){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(currentUserId != 0) {
            switch (position) {
                case 0:
//                mTitle = nav_Titles[position];
                    Drawer_HomeFragment cFragment = new Drawer_HomeFragment();
                    Bundle data = new Bundle();
                    data.putInt("currentUserId", currentUserId);
                    cFragment.setArguments(data);

                    ft.replace(R.id.content_frame, cFragment,"Home");
                    ft.commit();
                    break;
                case 1:
                    ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                    ft.replace(R.id.content_frame, changePasswordFragment,"ChangePassword");
                    ft.commit();
                    break;
                case 2:
                    SettingsFragment settingsFragment = new SettingsFragment();
                    ft.replace(R.id.content_frame, settingsFragment);
                    ft.commit();
                    break;
                case 3:
                    SharePrefManager.removeFromSharePref(DrawerActivity.this,SharePrefManager.USER);
                    Intent intent = new Intent(DrawerActivity.this, Splash.class);
                    //Clear all the task in backstack and set this activity as a new task
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
                case 4:
                    ShareFragment shareFragment = new ShareFragment();
                    ft.replace(R.id.content_frame, shareFragment);
                    ft.commit();
                    break;
                case 5:
                    AboutFragment aboutFragment = new AboutFragment();
                    ft.replace(R.id.content_frame, aboutFragment);
                    ft.commit();
                    break;
            }
        }
        else
        {
            switch (position) {
                case 0:
//                mTitle = nav_Titles[position];
                    Drawer_HomeFragment cFragment = new Drawer_HomeFragment();
                    Bundle data = new Bundle();
                    data.putInt("currentUserId", currentUserId);
                    cFragment.setArguments(data);
                    ft.replace(R.id.content_frame, cFragment);
                    ft.commit();
                    break;
                case 1:
                    ShareFragment shareFragment = new ShareFragment();
                    ft.replace(R.id.content_frame, shareFragment);
                    ft.commit();
                    break;
                case 2:
                    AboutFragment aboutFragment = new AboutFragment();
                    ft.replace(R.id.content_frame, aboutFragment);
                    ft.commit();
                    break;
            }
        }
    }

    public void highlightSelectedTitle(){
        int selectedItem = mDrawerList.getCheckedItemPosition();
        mPosition = selectedItem;
        mDrawerList.setItemChecked(mPosition, true);
        if(mPosition!=-1)
            //change title of actionbar based on selected item
            getSupportActionBar().setTitle(nav_Titles.get(mPosition));
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(currentUser!= null) {
                Intent intent = new Intent(DrawerActivity.this, SignUp.class);
                intent.putExtra("isEdit", true);
                startActivity(intent);
            }
        }
    };
}


