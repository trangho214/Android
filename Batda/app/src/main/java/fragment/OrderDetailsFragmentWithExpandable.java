package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import Adapter.OrdersDetailsExpandableAdapter;
import softs.hnt.com.batda.R;
import softs.hnt.com.batda.SampleData;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailsFragmentWithExpandable extends Fragment {


    public OrderDetailsFragmentWithExpandable() {
        // Required empty public constructor
    }
    ExpandableListView elv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_order_details_fragment_with_expandable, container, false);
        elv = (ExpandableListView)v.findViewById(R.id.elv_OrdersDetails);
        OrdersDetailsExpandableAdapter adapter = new OrdersDetailsExpandableAdapter(getActivity(), SampleData.getData());
        elv.setAdapter(adapter);

        elv.setIndicatorBounds(elv.getRight()-50, elv.getWidth());
        elv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previous =-1;
            @Override
            public void onGroupExpand(int groupPosition) {

                if(groupPosition!= previous && previous!=-1)
                    elv.collapseGroup(previous);
                previous = groupPosition;

            }
        });
        return v;
    }


}
