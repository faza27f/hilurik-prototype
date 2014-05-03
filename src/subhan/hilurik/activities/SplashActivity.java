package subhan.hilurik.activities;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.actionbarsherlock.app.SherlockActivity;

import subhan.hilurik.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplashActivity extends SherlockActivity {

	private static final int WHITE = R.color.white;
	private long myTime = 3000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		/* Text View promotion */
		TextView tv = (TextView)findViewById(R.id.tv_promotion);
		RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl_bg_splashscreen);
		
		/* Set Random Text */
		Random random = new Random();		
		int randomPromo = 0;
		
		for (int i = 0; i <= 2; i++) {
			randomPromo = random.nextInt(3);
		}
	
		switch (randomPromo) {
			case 0:
				tv.setText(R.string.promotion1);
				tv.setTextColor(WHITE);
				rl.setBackgroundColor(getResources().getColor(R.color.aqua));
				break;
			case 1:
				tv.setText(R.string.promotion2);
				tv.setTextColor(WHITE);
				rl.setBackgroundColor(getResources().getColor(R.color.Azure));
				break;
			case 2:
				tv.setText(R.string.promotion3);
				tv.setTextColor(WHITE);
				rl.setBackgroundColor(getResources().getColor(R.color.yellow));
				break;
			default:
				break;
		}
		
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				finish();
				Intent iHome = new Intent(SplashActivity.this, HomeActivity.class);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				startActivity(iHome);
				
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(task,myTime);
	}
}
