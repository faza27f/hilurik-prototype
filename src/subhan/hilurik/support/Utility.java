package subhan.hilurik.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import subhan.hilurik.R;
import subhan.hilurik.model.LurikData;
import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

public class Utility {

	private static final String TAG = "Utility::";
	//private static String send = null;
	public static final int SAMPLE_WIDTH = 50;
	public static final int SAMPLE_HEIGHT = 50;
	
	//private static File root = new File(Environment
	//		.getExternalStorageDirectory().getAbsolutePath() + "/HiLurik");

	// Path File to Url for Feature Camera
	public static File getOutputMediaFile() {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"HiLurik");

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(TAG + "Picture", "Oops! Failed create picture directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "SAMPLE_" + timeStamp + ".jpg");
		return mediaFile;
	}

	// my version load data from text
	public static ArrayList<LurikData> loadData(Context context) throws IOException {
		Log.d(TAG, "loading lurik");
		Resources resources = context.getResources();
		InputStream inputStream = resources.openRawResource(R.raw.data1);
		ArrayList<LurikData> arListLurik = new ArrayList<LurikData>();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		try {
			String word;
			
			while ((word = reader.readLine()) != null) {
				String[] strings = TextUtils.split(word, ":");

				String[] data = new String[2];
				for (int i = 0; i < strings.length; i = i + 2) {
					data[0] = strings[i];
					data[1] = strings[i + 1];
									
					LurikData lurikData = new LurikData(data[1], SAMPLE_WIDTH, SAMPLE_HEIGHT);
				
					arListLurik.add(lurikData);
					int index = 0;
					for (int y = 0; y < lurikData.getHeight(); y++) {
						for (int x = 0; x < lurikData.getWidth(); x++) {
							lurikData.setData(x, y, data[0].charAt(index++) == '1');
						}
					}
					
					//word = reader.readLine();
				}
			}
			
			reader.close();
			inputStream.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	
		Log.d(TAG, "DONE loading lurik");
		return arListLurik;
	}
}
