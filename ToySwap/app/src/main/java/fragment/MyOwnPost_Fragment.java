package fragment;

/**
 * Created by TrangHo on 01-10-2014.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import adapter.ListViewPostAdapter;
import model.Post;
import softs.hnt.com.toyswap.CreatePost;
import softs.hnt.com.toyswap.DataClass;
import softs.hnt.com.toyswap.HttpHandler;
import softs.hnt.com.toyswap.R;
import softs.hnt.com.toyswap.ShowPost;
import softs.hnt.com.toyswap.UserAction;


public class MyOwnPost_Fragment extends Fragment{
    ListViewPostAdapter postAdapter;
    DataClass dataClass;
    ListView listView;
    Post currentPost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_own_post_fragment,container,false);
        Log.d("Extra fragment onCreateView", "onCreateView");
        dataClass = DataClass.getInstance();
        listView = (ListView) view.findViewById(R.id.lvMyOwnPost);
        postAdapter = new ListViewPostAdapter(getActivity(),R.layout.row_item, dataClass.getMyPosts());
        listView.setAdapter(postAdapter);
        listView.setOnItemClickListener(onItemClick);
        listView.setOnItemLongClickListener(onItemLongClick);
        return view;
    }
    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//            intent.putExtra("postItem", posts.get(position));

            if(parent.getId() == R.id.lvMyOwnPost) {
                Intent intent = new Intent(getActivity(),ShowPost.class);
                currentPost = postAdapter.getItem(position);
                intent.putExtra("postItem", currentPost);
//                intent.putExtra("postItem", dataClass.getMyPosts().get(position));
                getActivity().startActivity(intent);
            }
            else
            {
                switch (position)
                {
                    //In case post is going to be edited.
                    case 0:
                        Intent intent = new Intent(getActivity(), CreatePost.class);
                        intent.putExtra("postItem",currentPost);
                        getActivity().startActivity(intent);
                        break;
                    case 1:
                        confirmDialog();

                        break;
                }
            }
        }
    };

    private AdapterView.OnItemLongClickListener onItemLongClick =new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            currentPost = postAdapter.getItem(position);
            chooserDialog();
            return true;
        }
    };
    AlertDialog chooserAlertDialog;
    //Show chooser alertdialog with editing of deleting when do longclick on listItem.
    private void chooserDialog()
    {
        String[] actions = {getResources().getString(R.string.edit_post), getResources().getString(R.string.delete_post)};
       chooserAlertDialog = new AlertDialog.Builder(getActivity()).create();
        ListView lv = new ListView(getActivity());
        lv.setOnItemClickListener(onItemClick);
        chooserAlertDialog.setView(lv);
        chooserAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        chooserAlertDialog.setTitle(getResources().getString(R.string.choose_action));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,actions);
        lv.setAdapter(adapter);
        chooserAlertDialog.show();

    }

    private void confirmDialog()
    {
        AlertDialog.Builder confirmAlertDialog = new AlertDialog.Builder(MyOwnPost_Fragment.this.getActivity());
        confirmAlertDialog.setTitle("Confirm");
        confirmAlertDialog.setMessage(getResources().getString(R.string.confirm_text) + " " + currentPost.title + " ?")
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       doHttpDeletePost().execute();

                    }
                }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chooserAlertDialog.dismiss();
//                Fragment fragment= getActivity().getSupportFragmentManager().findFragmentByTag("Home");
//                if(fragment!=null)
//                {
//                    ((Drawer_HomeFragment)fragment).getAllPosts().execute();
//                }
            }
        });
        confirmAlertDialog.create();
        confirmAlertDialog.show();
    }

    private HttpHandler doHttpDeletePost()
    {
        return new HttpHandler(getActivity(), UserAction.deletePost,currentPost.id, "Deleting...") {
            @Override
            public void doOnResponse(String result) {
                Log.d("My owm post fragment result", result);
                chooserAlertDialog.dismiss();

               Fragment fragment= getActivity().getSupportFragmentManager().findFragmentByTag("Home");
                if(fragment!=null)
                {
//                    ((Drawer_HomeFragment)fragment).getAllPosts().execute();
//                    postAdapter.clear();
//                    postAdapter.addAll(dataClass.getMyPosts());
                    postAdapter.remove(currentPost);
                    postAdapter.notifyDataSetChanged();
                    switch (currentPost.groupId)
                    {
                        case 1:
                            dataClass.getExtraSmall_Posts().remove(currentPost);
                            break;
                        case 2:
                            dataClass.getSmall_Posts().remove(currentPost);
                            break;
                        case 3:
                            dataClass.getMedium_Posts().remove(currentPost);
                            break;
                    }
                }
                //It looks ugly with starting an activity after deleting.
                //should try to find out another way to do this.
                //TODO: Make an interface to back to Drawer_HomeFragment......
//                Intent intent = new Intent(getActivity(), DrawerActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
            }
        };
    }



}
