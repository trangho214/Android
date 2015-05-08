package Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Sample.SampleObject1;
import softs.hnt.com.batda.R;

/**
 * Created by TrangHo on 24-04-2015.
 */
public class ViewPager1_Adapter extends PagerAdapter {
    Activity activity;
    List<SampleObject1> sampleObject1List;
    LayoutInflater inflater;
    int vp_number;
    public ViewPager1_Adapter(Activity activity, List<SampleObject1> sampleObject1List, int vp_number)
    {
        this.activity = activity;
        this.sampleObject1List = sampleObject1List;
        this.vp_number = vp_number;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Object instantiateItem(ViewGroup container, int position)
    {
        View itemView = inflater.inflate(R.layout.list1,container,false);
        ListView lv =(ListView) itemView.findViewById(R.id.lv1);
        CustomListAdapter adapter = new CustomListAdapter(activity, getData(position),vp_number);
        lv.setAdapter(adapter);
        container.addView(itemView);
        return itemView;
    }

    private List<SampleObject1> getData(int position)
    {
        int countItem = sampleObject1List.size();
        List<SampleObject1> data = new ArrayList<SampleObject1>();
        int startIndex =0;
        if(countItem >0)
        {
            if(countItem%5==0)
            {
                startIndex = position*5;
                for (int j =startIndex; j<startIndex+5; j++)
                {
                    data.add(sampleObject1List.get(j));
                }
            }
            else
            {
                if(position != getCount()-1)
                {
                    startIndex = position*5;
                    for (int j =startIndex; j<startIndex+5; j++)
                    {
                        data.add(sampleObject1List.get(j));
                    }
                }
                else
                {
                    startIndex = position*5;
                    {
                        for (int j = startIndex; j<countItem; j++)
                        {
                            data.add(sampleObject1List.get(j));
                        }
                    }
                }
            }
        }
        return data;
    }


    @Override
    public int getCount() {
        if(sampleObject1List.size()%5 ==0)
        {
            return sampleObject1List.size()/5;
        }
        else
            return sampleObject1List.size()/5+1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==((LinearLayout)object) ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
