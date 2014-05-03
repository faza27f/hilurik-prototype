package subhan.hilurik.fragment;

import subhan.hilurik.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public final class TutorialFragment extends Fragment{
	 /* reference >> http://stackoverflow.com/questions/13796382/android-viewpager-as-image-slide-gallery */
	private static final String TAG = "TutorialFragment";
	
	private int imageResourceId;
	private String note;
	
	public TutorialFragment(int i, String note) {
		this.imageResourceId = i;
		this.note = note;
	}
	
    @SuppressWarnings("unused")
	private String mContent = "???";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 if ((savedInstanceState != null) && savedInstanceState.containsKey(TAG)) {
	            mContent = savedInstanceState.getString(TAG);
	     }
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.tutorial_view, container, false);
		
		ImageView ivImageTutorial = (ImageView)view.findViewById(R.id.iv_tutorial);
		TextView tvNote = (TextView)view.findViewById(R.id.tv_note);
		ivImageTutorial.setImageResource(imageResourceId);
		tvNote.setText(note);
		return view;
	}
	
}
