package subhan.hilurik.adapter;

import subhan.hilurik.fragment.TutorialFragment;
import subhan.hilurik.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TutorialFragmentAdapter extends FragmentPagerAdapter {

	protected static final int[] images = new int[] { R.drawable.l_home,
			R.drawable.l_crop_image, R.drawable.l_result_crop_image, R.drawable.l_result_image_processing , R.drawable.l_result_recognize };
	
	protected static final String[] note = new String[] { "Memasukan citra",
		"Memotong citra","Hasil Memotong Citra", "Mengolah citra", "Mengenali citra"};

	private int mCount = images.length;

	public TutorialFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return new TutorialFragment(images[position], note[position]);
	}

	@Override
	public int getCount() {
		return mCount;
	}
	
	public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }

}
