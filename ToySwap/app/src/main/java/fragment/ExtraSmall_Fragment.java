package fragment;

/**
 * Created by TrangHo on 01-10-2014.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import adapter.ListViewPostAdapter;
import model.Post;
import model.Result;
import softs.hnt.com.toyswap.DataClass;
import softs.hnt.com.toyswap.HttpHandler;
import softs.hnt.com.toyswap.ShowPost;
import softs.hnt.com.toyswap.R;
import softs.hnt.com.toyswap.UserAction;


public class ExtraSmall_Fragment extends Fragment{
    ListViewPostAdapter postAdapter;
    DataClass extraSmallData;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Extra fragment onCreateView", "onCreateView");
        View view = inflater.inflate(R.layout.extra_small_fragment, container, false);
        extraSmallData = DataClass.getInstance();
        listView = (ListView) view.findViewById(R.id.lvExtraSmall);
        postAdapter = new ListViewPostAdapter(getActivity(),R.layout.row_item,extraSmallData.getExtraSmall_Posts());
        listView.setAdapter(postAdapter);
        listView.setOnItemClickListener(onItemClick);
        return view;
    }

    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(),ShowPost.class);
//            intent.putExtra("postItem", posts.get(position));
            intent.putExtra("postItem", extraSmallData.getExtraSmall_Posts().get(position));
            getActivity().startActivity(intent);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Extra fragment onPause","onPause" );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Extra fragment Destroy","Destroy" );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Extra fragment onDestroyView","onDestroyView" );
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Extra fragment onResume","onResume" );
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Extra fragment onStart","onStart" );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Extra fragment onStop","onStop" );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Extra fragment onCreate","onCreate" );
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Extra fragment onSaveInstanceState","onSaveInstanceState" );
    }

    @Override
    public void setInitialSavedState(SavedState state) {
        super.setInitialSavedState(state);
        Log.d("Extra fragment setInitialSavedState","setInitialSavedState" );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Extra fragment onDetach","onDetach" );
    }
}
