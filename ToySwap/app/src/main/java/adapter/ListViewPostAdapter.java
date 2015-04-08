package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.List;

import model.Post;
import model.UserInfo;
import softs.hnt.com.toyswap.R;

/**
 * Created by TrangHo on 11-10-2014.
 */
public class ListViewPostAdapter extends ArrayAdapter<Post>{

    Context context;
    List<Post> posts;
    AQuery aq;

    public ListViewPostAdapter(Context context, int resource, List<Post> posts) {
        super(context, resource, posts);
        this.context = context;
        this.posts = posts;
        aq = new AQuery(context);
    }

    @Override
    public Post getItem(int position) {
        return posts.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        {
            ViewHolder viewHolder;
            if(convertView== null)
            {
                convertView = ((LayoutInflater)LayoutInflater.from(context)).inflate(R.layout.row_item,null);
                viewHolder = new ViewHolder();
                viewHolder.ivImage = (ImageView)convertView.findViewById(R.id.ivImage_PostItem);
                viewHolder.progressBar = (ProgressBar)convertView.findViewById(R.id.progress_postItem);
                viewHolder.txtTitle = (TextView)convertView.findViewById(R.id.txtTitle_PostItem);
                viewHolder.txtDescription = (TextView)convertView.findViewById(R.id.txtDescription_PostItem);
                viewHolder.txtDistance = (TextView)convertView.findViewById(R.id.txtDistance_PostItem);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            Post post = posts.get(position);
            AQuery aQuery = aq.recycle(convertView);
            aQuery.id(viewHolder.ivImage).progress(viewHolder.progressBar).image(post.imageUrl,true,true,0,0,null,0,1.0f);
            aQuery.id(viewHolder.txtTitle).text(post.title);
            aQuery.id(viewHolder.txtDescription).text(post.description);
            aQuery.id(viewHolder.txtDistance).text(distanceConvert(post.userInfo.distance));
            return  convertView;
        }
    }

    private String distanceConvert(int distance)
    {
        if(distance < 1000)
        {
            return distance + " m";
        }
        else {
            return String.valueOf((double)distance/1000) + " km";
        }
    }
    class ViewHolder
    {
        ImageView ivImage;
        ProgressBar progressBar;
        TextView txtTitle;
        TextView txtDescription;
        TextView txtDistance;
    }
}
