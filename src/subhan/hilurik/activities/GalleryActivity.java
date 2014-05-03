package subhan.hilurik.activities;

import java.util.ArrayList;

import subhan.hilurik.R;
import subhan.hilurik.adapter.GalleryAdapter;
import subhan.hilurik.model.Gallery;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockActivity;

public class GalleryActivity extends SherlockActivity {

	public static final String TAG = "GalleryActivity";

	public int[] banner;
	public String[] headline;

	GridView gvContent;
	Gallery listGallery;
	GalleryAdapter galleryListViewAdapter;
	ArrayList<Gallery> arrayList = new ArrayList<Gallery>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		banner = new int[] { R.drawable.lurik_1,
				R.drawable.lurik_2, 
				R.drawable.lurik_3,
				R.drawable.lurik_4,
				R.drawable.lurik_5,
				R.drawable.lurik_6, 
				R.drawable.lurik_7,
				R.drawable.lurik_8,
				R.drawable.lurik_9, 
				R.drawable.lurik_10};
		
		headline = new String[] {
				getResources().getString(R.string.lurik_1),
				getResources().getString(R.string.lurik_2),
				getResources().getString(R.string.lurik_3),
				getResources().getString(R.string.lurik_4),
				getResources().getString(R.string.lurik_5),
				getResources().getString(R.string.lurik_6), 
				getResources().getString(R.string.lurik_7),
				getResources().getString(R.string.lurik_8),
				getResources().getString(R.string.lurik_9),
				getResources().getString(R.string.lurik_10)};

//		lvContent = (ListView) findViewById(R.id.lv_gallery);
		gvContent = (GridView)findViewById(R.id.gv_gallery);

		for (int i = 0; i < banner.length; i++) {
		    listGallery = new Gallery(headline[i],banner[i]);
			arrayList.add(listGallery);
		}

		galleryListViewAdapter = new GalleryAdapter(this, arrayList);
		
		gvContent.setAdapter(galleryListViewAdapter);
		
		gvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int id, long position) {
				Intent intent = new Intent(GalleryActivity.this, ShowGalleryActivity.class);
				intent.putExtra("title", arrayList.get(id).getHeadline());
//				Log.i(TAG, arrayList.get(id).getHeadline());
				startActivity(intent);
			}
		});

/*		lvContent.setAdapter(galleryListViewAdapter);
		
		lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int id, long position) {
				Intent intent = new Intent(GalleryActivity.this, ShowGalleryActivity.class);
				intent.putExtra("title", arrayList.get(id).getHeadline());
//				Log.i(TAG, arrayList.get(id).getHeadline());
				startActivity(intent);
			}
		}); */
	}
}
