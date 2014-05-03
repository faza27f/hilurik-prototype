package subhan.hilurik.activities;

import subhan.hilurik.R;
import subhan.hilurik.adapter.TutorialFragmentAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class TutorialActivity extends SherlockFragmentActivity  {

	TutorialFragmentAdapter mAdapter;
	ViewPager mPager;
	PageIndicator mPageIndicator;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        
        mAdapter = new TutorialFragmentAdapter(getSupportFragmentManager());
        
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mPageIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mPageIndicator.setViewPager(mPager);
        
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.item_tutorial, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_back_to_home:
			TutorialActivity.this.finish();
			break;
		}
		return true;
	}
}
