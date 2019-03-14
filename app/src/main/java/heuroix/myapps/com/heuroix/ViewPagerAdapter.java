package heuroix.myapps.com.heuroix;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FragmentOne();
        } else if (position == 1) {
            return new FragmentTwo();
        }
        throw new IllegalStateException("Position not valid");
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Videos";
        } else if (position == 1) {
            return "Photos";
        }
        throw new IllegalStateException("Position not valid");
    }
}
