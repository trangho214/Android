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
import android.widget.ListView;

import com.androidquery.AQuery;
import com.google.gson.Gson;

import java.util.List;

import adapter.ListViewPostAdapter;
import softs.hnt.com.toyswap.DataClass;
import softs.hnt.com.toyswap.R;
import softs.hnt.com.toyswap.ShowPost;


public class Medium_Fragment extends Fragment{
    AQuery aq;
    List<String> stringList;
    ListViewPostAdapter postAdapter;
    ListView listView;
    Gson gson;
    DataClass dataClass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medium_fragment,container,false);
        aq = new AQuery(this.getActivity());
        dataClass = DataClass.getInstance();
        listView = (ListView) view.findViewById(R.id.lvMedium);
        postAdapter = new ListViewPostAdapter(getActivity(),R.layout.row_item,dataClass.getMedium_Posts());
        listView.setAdapter(postAdapter);
        gson = new Gson();
        listView.setOnItemClickListener(onItemClick);
        return view;
    }
    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(),ShowPost.class);
            intent.putExtra("postItem", dataClass.getMedium_Posts().get(position));
            getActivity().startActivity(intent);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Medium fragment onPause","onPause" );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Medium fragment Destroy","Destroy" );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Medium fragment onDestroyView","onDestroyView" );
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Medium fragment onResume","onResume" );
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Medium fragment onStart","onStart" );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Medium fragment onStop","onStop" );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Medium fragment onCreate","onCreate" );
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Medium fragment onSaveInstanceState","onSaveInstanceState" );
    }

    @Override
    public void setInitialSavedState(SavedState state) {
        super.setInitialSavedState(state);
        Log.d("Medium fragment setInitialSavedState","setInitialSavedState" );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Medium fragment onDetach","onDetach" );
    }
}

