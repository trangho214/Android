package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import Adapter.OrdersDetailsAdapter;
import softs.hnt.com.batda.R;
import softs.hnt.com.batda.SampleData;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersDetailsFragment extends Fragment {

    ListView lv;
    public OrdersDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_orders_details, container, false);
        lv = (ListView)v.findViewById(R.id.lv_OrdersDetails);
        OrdersDetailsAdapter ordersDetailsAdapter = new OrdersDetailsAdapter(getActivity(), SampleData.getData());
        lv.setAdapter(ordersDetailsAdapter);
        return v;
    }


}
