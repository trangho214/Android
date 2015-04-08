package adapter;

/**
 * Created by TrangHo on 01-10-2014.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import fragment.ExtraSmall_Fragment;
import fragment.Medium_Fragment;
import fragment.MyFavourite_Fragment;
import fragment.MyOwnPost_Fragment;
import fragment.Small_Fragment;
import softs.hnt.com.toyswap.R;

public class TabPageAdapter extends FragmentStatePagerAdapter{
  public   List<Fragment> fragments;
    public TabPageAdapter(FragmentManager fm, List<Fragment> fragments)
    {
        super(fm);
       this.fragments = fragments;
    }
    @Override
    public int getCount() {
        // TODO Take a look at getCount her.
        return fragments.size();
    }
    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }
}
