package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Adapter.CustomSlidingBarAdapter;
import Sample.SampleObject1;
import softs.hnt.com.batda.FragmentChangeActivity;
import softs.hnt.com.batda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorMenuFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] colors = getResources().getStringArray(R.array.color_names);
        //TODO: custom item of slidingbar her.Create a new cusotm adapter.
        CustomSlidingBarAdapter slidingBarAdapter = new CustomSlidingBarAdapter(getActivity(),sampleData() );
        setListAdapter(slidingBarAdapter);
//        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, colors);
//        setListAdapter(colorAdapter);
    }
    List<SampleObject1> sampleObject1List;
    private List<SampleObject1> sampleData()
    {
        sampleObject1List = new ArrayList<SampleObject1>();
        for (int i =0; i<19; i++)
        {
            sampleObject1List.add(new SampleObject1("Sample Item", "123", String.valueOf(i)));
        }
        return sampleObject1List;
    }

    @Override
    public void onListItemClick(ListView lv, View v, int position, long id) {
        Fragment newContent = null;
        switch (position) {
            case 0:
                newContent = new DashboardFragment();
//                newContent = new ColorFragment(R.color.red);
                break;
            case 1:
                newContent = new OrderDetailsFragmentWithExpandable();
//                newContent = new ColorFragment(R.color.green);
                break;
            case 2:
                newContent = new ColorFragment(R.color.blue);
                break;
            case 3:
                newContent = new ColorFragment(android.R.color.white);
                break;
            case 4:
                newContent = new ColorFragment(android.R.color.black);
                break;
        }
        if (newContent != null)
            switchFragment(newContent);
    }

    // the meat of switching the above fragment
    private void switchFragment(Fragment fragment) {
        if (getActivity() == null)
            return;

        if (getActivity() instanceof FragmentChangeActivity) {
            FragmentChangeActivity fca = (FragmentChangeActivity) getActivity();
            fca.switchContent(fragment);
        }


    }
}
