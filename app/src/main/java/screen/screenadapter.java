package screen;

import com.panaceasoft.admotors.ui.user.UserLoginFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class screenadapter extends FragmentPagerAdapter {
    public screenadapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new screen.screenfrgone();
            case 1:
                return new screen.screenfrgtwo();
            case 2:
                return new screen.screenfrgthree();
                default: return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
