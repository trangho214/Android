package fragment;

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

/**
 * Created by TrangHo on 01-10-2014.
 */


public class Small_Fragment extends Fragment{
    AQuery aq;
    List<String> stringList;
    //    ArrayAdapter<String> arrayAdapter;
    ListViewPostAdapter postAdapter;
//    List<Post> posts= new ArrayList<Post>();
    ListView listView;
    DataClass dataClass;
    Gson gson;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.small_fragment, container, false);
        aq = new AQuery(this.getActivity());
        dataClass = DataClass.getInstance();
        listView = (ListView) view.findViewById(R.id.lvSmall);
        postAdapter = new ListViewPostAdapter(getActivity(),R.layout.row_item,dataClass.getSmall_Posts());
        listView.setAdapter(postAdapter);
        gson = new Gson();
        listView.setOnItemClickListener(onItemClick);
        return view;
    }
    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(),ShowPost.class);
            intent.putExtra("postItem", dataClass.getSmall_Posts().get(position));
            getActivity().startActivity(intent);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Small fragment onPause","onPause" );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Small fragment Destroy","Destroy" );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Small fragment onDetach","onDetach" );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Small fragment onDestroyView","onDestroyView" );
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Small fragment onResume","onResume" );
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Small fragment onStart","onStart" );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Small fragment onStop","onStop" );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Small fragment onCreate","onCreate" );
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Small fragment onSaveInstanceState","onSaveInstanceState" );
    }

    @Override
    public void setInitialSavedState(SavedState state) {
        super.setInitialSavedState(state);
        Log.d("Small fragment setInitialSavedState","setInitialSavedState" );
    }
}
