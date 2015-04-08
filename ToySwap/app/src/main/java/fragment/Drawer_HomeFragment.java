package fragment;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import adapter.TabPageAdapter;
import softs.hnt.com.toyswap.SharePrefManager;
import model.Post;
import model.UserInfo;
import softs.hnt.com.toyswap.CreatePost;
import softs.hnt.com.toyswap.DataClass;
import softs.hnt.com.toyswap.HttpHandler;
import softs.hnt.com.toyswap.R;
import softs.hnt.com.toyswap.UserAction;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Drawer_HomeFragment extends Fragment {

    TabPageAdapter tabAdapter;
    int currentPosition; long userId; double lat,lng;
    ActionBar actionBar;
    ViewPager Tab;
    View v;
    UserInfo currentUser;
    int currentUserId;
    List<Fragment> fragments = new ArrayList<Fragment>();
    Gson gson ;
    DataClass dataClass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        dataClass = DataClass.getInstance();
        v = inflater.inflate(R.layout.fragment_drawer_home, container, false);
        Tab = (ViewPager) v.findViewById(R.id.vpTab);
        //setHasOptionMenu to true to be available to show menu.my on action bar.
        setHasOptionsMenu(true);
        actionBar = getActivity().getActionBar();
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        currentUser = SharePrefManager.getUser(getActivity());
        //Call removeAllTabs to get init run again when Home reached onDestroy
        if(actionBar.getTabCount() >0)
        actionBar.removeAllTabs();
        if(currentUser== null)
        {
            userId =0;
            //Now 0 is used as temporary value to lat and lng.
            //they would be the current coordinates of guest if there is more time to this project.
            lat = 0;
            lng = 0;
        }
        else
        {
            userId = currentUser.id;
            lat = currentUser.latitude;
            lng = currentUser.longitude;
        }

            getAllPosts().execute();

        currentUserId = getArguments().getInt("currentUserId");
        Log.d("Home fragment onCreateView","onCreateView" );
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my,menu);
        if(userId ==0)
        {
            //hide action new if  Guest
            MenuItem item = menu.findItem(R.id.action_new);
            item.setVisible(false);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_new:
                //Create a new post
                Intent intent = new Intent(getActivity(), CreatePost.class);
                intent.putExtra("currentUserId", currentUserId);
                startActivity(intent);
                break;
            case R.id.action_refresh:
                //get the fragment of the current tab.
                    getAllPosts().execute();
        }
        return super.onOptionsItemSelected(item);
    }

    public HttpHandler getAllPosts()
    {
        return new HttpHandler(getActivity(), UserAction.getAllPosts, userId, lat, lng,"Loading...") {
            @Override
            public void doOnResponse(String result) {
                Type listType = new TypeToken<List<Post>>() {}.getType();
                List<Post> posts1 = new ArrayList<Post>();
                List<Post> posts2 = new ArrayList<Post>();
                List<Post> posts3 = new ArrayList<Post>();
                List<Post> myPost = new ArrayList<Post>();
                for(Post post: (List<Post>) gson.fromJson(result,listType))
                {
                    if(post.userId == userId)
                    {
                        myPost.add(post);
                    }
                    switch (post.groupId)
                    {
                        case 1:
                            posts1.add(post);
                            break;
                        case 2:
                            posts2.add(post);
                            break;
                        case 3:
                            posts3.add(post);
                            break;
                    }

                }
                //Data is renewed after doing httpHandler.
                dataClass.setExtraSmall_Posts(posts1);
                dataClass.setSmall_Posts(posts2);
                dataClass.setMedium_Posts(posts3);
                dataClass.setMyPosts(myPost);
                if(actionBar.getTabCount() == 0)
                {
                    init();
                }
            }
        };
    }
    private void init()
    {
        fragments.add(new ExtraSmall_Fragment());
        fragments.add(new Small_Fragment());
        fragments.add(new Medium_Fragment());
        if(userId!= 0)
        fragments.add(new MyOwnPost_Fragment());

        Tab = (ViewPager) v.findViewById(R.id.vpTab);
        tabAdapter = new TabPageAdapter(getActivity().getSupportFragmentManager(),fragments);
        Tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                });
        Tab.setAdapter(tabAdapter);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabReselected(android.app.ActionBar.Tab tab,
                                        FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                currentPosition = tab.getPosition();
                Tab.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(android.app.ActionBar.Tab tab,
                                        FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }
        };
        actionBar.addTab(actionBar.newTab().setText("1-3").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("4-6").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("7-9").setTabListener(tabListener));
        if(userId != 0)
        actionBar.addTab(actionBar.newTab().setText(getResources().getString(R.string.my_posts)).setTabListener(tabListener));
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Home fragment onCreate", "onCreate");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d("Home fragment onPause", "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Home fragment Destroy","Destroy" );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Home fragment onDestroyView","onDestroyView" );
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Home fragment onResume","onResume" );
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Home fragment onStart","onStart" );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Home fragment onStop","onStop" );
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Home fragment onSaveInstanceState","onSaveInstanceState" );
    }

    @Override
    public void setInitialSavedState(SavedState state) {
        super.setInitialSavedState(state);
        Log.d("Home fragment setInitialSavedState","setInitialSavedState" );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Home fragment onDetach","onDetach" );
    }
}
