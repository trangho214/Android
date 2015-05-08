package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.List;

import Adapter.ViewPager1_Adapter;
import Sample.SampleObject1;
import softs.hnt.com.batda.FragmentChangeActivity;
import softs.hnt.com.batda.R;
import softs.hnt.com.batda.SampleData;


public class DashboardFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ViewPager vp1;
    ViewPager vp2;
    List<SampleObject1> sampleObject1List;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        vp1 = (ViewPager)v.findViewById(R.id.vg1_Dashboard);
        vp2 = (ViewPager)v.findViewById(R.id.vg2_Dashboard);
        ViewPager1_Adapter adapter = new ViewPager1_Adapter(getActivity(), SampleData.getData(),1);
        ViewPager1_Adapter adapter2 = new ViewPager1_Adapter(getActivity(), SampleData.getData(),2);
        vp1.setAdapter(adapter);
        vp1.setOnTouchListener(onTouch);
        vp1.setOnPageChangeListener(onPageChangeListener);
        vp2.setAdapter(adapter2);
        vp2.setOnTouchListener(onTouch);
        vp2.setOnPageChangeListener(onPageChangeListener);
        return v;
    }

    int currentPage;
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    try {
                        ((FragmentChangeActivity) getActivity()).sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    } catch (Exception e) {
                    }
                    break;
                default:
                    try {
                        ((FragmentChangeActivity) getActivity()).sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
                    } catch (Exception e) {
                    }
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private View.OnTouchListener onTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }
}
