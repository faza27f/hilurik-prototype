package subhan.hilurik.activities;

import subhan.hilurik.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class InfoActivity extends SherlockActivity {

	Intent push = null;
	String myEmail = "subhan.nooriansyah@gmail.com";
	String urlMuseum = "http://museumtekstiljakarta.com/";
	EditText etTo, etSubject, etMessage;
	TextView tvDeveloper;
	LinearLayout formMessageToDeveloper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		etSubject = (EditText)findViewById(R.id.et_subject);
		etMessage = (EditText)findViewById(R.id.et_message);
		formMessageToDeveloper = (LinearLayout)findViewById(R.id.ll_message_to_developer);
		tvDeveloper = (TextView) findViewById(R.id.tv_developer);
		
		tvDeveloper.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (formMessageToDeveloper.getVisibility() == View.GONE) {
					formMessageToDeveloper.setVisibility(View.VISIBLE);
				} else if (formMessageToDeveloper.getVisibility() == View.VISIBLE) {
					formMessageToDeveloper.setVisibility(View.GONE);
				}
			}
		});

	}
		
	public void onDeveloper(View view){
		
		String subject = etSubject.getText().toString();
		String message = etMessage.getText().toString();
		
		push = new Intent(Intent.ACTION_SEND);
		push.putExtra(Intent.EXTRA_EMAIL, new String[]{ myEmail });
		push.putExtra(Intent.EXTRA_SUBJECT,subject);
		push.putExtra(Intent.EXTRA_TEXT,message);
		
		push.setType("message/rfc822");
		
		startActivity(Intent.createChooser(push,"Pilih aplikasi : "));

	}
	
	public void onMuseum(View view) {
		push = new Intent(Intent.ACTION_VIEW);
		push.setData(Uri.parse(urlMuseum));
		startActivity(push);
	}

}
