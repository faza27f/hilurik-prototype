package subhan.hilurik.activities;

import subhan.hilurik.R;
import subhan.hilurik.adapter.TutorialFragmentAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.PageIndicator;

public class ShowGalleryActivity extends SherlockFragmentActivity {

	public static final String TAG = "ShowGalleryActivity";

	TutorialFragmentAdapter mAdapter;
	ViewPager mPager;
	PageIndicator mPageIndicator;
	
	private ImageView ivHeadGallery;
	private TextView tvTitleGallery, tvContentGallery;
	LinearLayout linearLayout;
	
	int touchColor = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_gallery);
		
		ivHeadGallery = (ImageView)findViewById(R.id.iv_head_gallery);
		tvTitleGallery = (TextView)findViewById(R.id.tv_title_gallery);
		tvContentGallery = (TextView)findViewById(R.id.tv_content_gallery);
		
		linearLayout = (LinearLayout)findViewById(R.id.ll_image_how_to_know);
/*		ivHowToKnow1 = (ImageView)findViewById(R.id.iv_how_to_know1);
		ivHowToKnow2 = (ImageView)findViewById(R.id.iv_how_to_know2);
		ivHowToKnow3 = (ImageView)findViewById(R.id.iv_how_to_know3);*/
//		tvShowColor = (TextView)findViewById(R.id.tv_color_gallery);
//		etShowHexCode = (EditText)findViewById(R.id.et_hex_code);
		
		showGallery();
		
//		ivHeadGallery.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View view, MotionEvent event) {
//				float eventX = event.getX();
//				float eventY = event.getY();
//				float[] eventXY = new float[] { eventX, eventY };
//
//				Matrix invertMatrix = new Matrix();
//
//				((ImageView) view).getImageMatrix().invert(invertMatrix);
//
//				invertMatrix.mapPoints(eventXY);
//				int x = Integer.valueOf((int) eventXY[0]);
//				int y = Integer.valueOf((int) eventXY[1]);
//
//				Drawable imgDrawable = ((ImageView) view).getDrawable();
//				Bitmap bitmap = ((BitmapDrawable) imgDrawable).getBitmap();
//
//				// Limit x, y range within bitmap
//				if (x < 0) {
//					x = 0;
//				} else if (x > bitmap.getWidth() - 1) {
//					x = bitmap.getWidth() - 1;
//				}
//
//				if (y < 0) {
//					y = 0;
//				} else if (y > bitmap.getHeight() - 1) {
//					y = bitmap.getHeight() - 1;
//				}
//
//				touchColor = bitmap.getPixel(x, y);
//				tvShowColor.setBackgroundColor(touchColor);
//				int alpha = Color.alpha(touchColor);
//				int red = Color.red(touchColor);
//				int blue = Color.blue(touchColor);
//				int green = Color.green(touchColor);
//				etShowHexCode.setText(Integer.toHexString(Color.argb(alpha, red, green, blue)));
//				
//				return true;
//			}
//		});
	}
	
	/*public void HowToKnowLurik(View view) {
		if (linearLayout.getVisibility() == View.GONE) {
			linearLayout.setVisibility(View.VISIBLE);
		} else {
			linearLayout.setVisibility(View.GONE);
		}
	}
	*/
	public void showGallery() {
		Intent intent = getIntent();
		String title = intent.getStringExtra("title");

		if (title.equalsIgnoreCase("Udan Liris")) {
			tvTitleGallery.setText(title);
			ivHeadGallery.setImageResource(R.drawable.lurik_1); // sample 11
			tvContentGallery.setText(R.string.lurik_1_content);
/*			ivHowToKnow1.setImageResource(R.drawable.lurik_1);
			ivHowToKnow2.setImageResource(R.drawable.lurik_1);
			ivHowToKnow3.setImageResource(R.drawable.lurik_1);*/
		} else if (title.equalsIgnoreCase("Dengklung")) {
			tvTitleGallery.setText(title);
			ivHeadGallery.setImageResource(R.drawable.lurik_2); // sample 12
			tvContentGallery.setText(R.string.lurik_2_content);
/*			ivHowToKnow1.setImageResource(R.drawable.lurik_2);
			ivHowToKnow2.setImageResource(R.drawable.lurik_2);
			ivHowToKnow3.setImageResource(R.drawable.lurik_2);*/
		} else if (title.equalsIgnoreCase("Palen")) {
			tvTitleGallery.setText(title);
			ivHeadGallery.setImageResource(R.drawable.lurik_3);
			tvContentGallery.setText(R.string.lurik_3_content);
/*			ivHowToKnow1.setImageResource(R.drawable.lurik_3);
			ivHowToKnow2.setImageResource(R.drawable.lurik_3);
			ivHowToKnow3.setImageResource(R.drawable.lurik_3);*/
		} else if (title.equalsIgnoreCase("Gedog Madu")) {
			tvTitleGallery.setText(title);
			ivHeadGallery.setImageResource(R.drawable.lurik_4);
			tvContentGallery.setText(R.string.lurik_4_content);
/*			ivHowToKnow1.setImageResource(R.drawable.lurik_4);
			ivHowToKnow2.setImageResource(R.drawable.lurik_4);
			ivHowToKnow3.setImageResource(R.drawable.lurik_4);*/
		} else if (title.equalsIgnoreCase("Tuluh Watuh")) {
			tvTitleGallery.setText(title);
			ivHeadGallery.setImageResource(R.drawable.lurik_5);
			tvContentGallery.setText(R.string.lurik_5_content);
/*			ivHowToKnow1.setImageResource(R.drawable.lurik_5);
			ivHowToKnow2.setImageResource(R.drawable.lurik_5);
			ivHowToKnow3.setImageResource(R.drawable.lurik_5);*/
		} else if (title.equalsIgnoreCase("Bodro")) {
			tvTitleGallery.setText(title);
			ivHeadGallery.setImageResource(R.drawable.lurik_6);
			tvContentGallery.setText(R.string.lurik_6_content);
/*			ivHowToKnow1.setImageResource(R.drawable.lurik_6);
			ivHowToKnow2.setImageResource(R.drawable.lurik_6);
			ivHowToKnow3.setImageResource(R.drawable.lurik_6);*/
		} else if (title.equalsIgnoreCase("Mlati Seconthong")) {
			tvTitleGallery.setText(title);
			ivHeadGallery.setImageResource(R.drawable.lurik_7);
			tvContentGallery.setText(R.string.lurik_7_content);
/*			ivHowToKnow1.setImageResource(R.drawable.lurik_7);
			ivHowToKnow2.setImageResource(R.drawable.lurik_7);
			ivHowToKnow3.setImageResource(R.drawable.lurik_7);*/
		} else if (title.equalsIgnoreCase("Sapit Urang")) {
			tvTitleGallery.setText(title);
			ivHeadGallery.setImageResource(R.drawable.lurik_8);
			tvContentGallery.setText(R.string.lurik_8_content);
/*			ivHowToKnow1.setImageResource(R.drawable.lurik_8);
			ivHowToKnow2.setImageResource(R.drawable.lurik_8);
			ivHowToKnow3.setImageResource(R.drawable.lurik_8);*/
		} else if (title.equalsIgnoreCase("Jaran Dawuk")) {
			tvTitleGallery.setText(title);
			ivHeadGallery.setImageResource(R.drawable.lurik_9);
			tvContentGallery.setText(R.string.lurik_9_content);
/*			ivHowToKnow1.setImageResource(R.drawable.lurik_9);
			ivHowToKnow2.setImageResource(R.drawable.lurik_9);
			ivHowToKnow3.setImageResource(R.drawable.lurik_9);*/
		} else if (title.equalsIgnoreCase("Gambang Suling")) {
			tvTitleGallery.setText(title);
			ivHeadGallery.setImageResource(R.drawable.lurik_10);
			tvContentGallery.setText(R.string.lurik_10_content);
/*			ivHowToKnow1.setImageResource(R.drawable.lurik_10);
			ivHowToKnow2.setImageResource(R.drawable.lurik_10);
			ivHowToKnow3.setImageResource(R.drawable.lurik_10);*/
		} 
	}
}
