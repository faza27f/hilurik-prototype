package subhan.hilurik.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import subhan.hilurik.R;
import subhan.hilurik.core.OtsuBinarize;
import subhan.hilurik.support.Convert;
import subhan.hilurik.support.Utility;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.edmodo.cropper.CropImageView;

public class HomeActivity extends SherlockActivity {

	public static final String TAG = "HomeActivity::";
	public static final int REQUEST_CODE_CAPTURE = 1;
	public static final int REQUEST_CODE_CHOOSE = 2;
	public static final String CHOOSE_KEY_STORE = "image_choose";
	public static final String CAPTURE_KEY_STORE = "image_capture";
	public static final String BITMAP_SAMPLE = "image_sample";
	public static final String BINARY_SAMPLE = "binary_sample";
		
	private static long startTime;

	public Button btnRecognition, btnCropImage, btnImageProcessing;
	private TextView tvShowSize;
	private CropImageView cropImageView;
	private ImageView ivShowImage;
	
	private Uri fileUri;
	private String logTimeImageProcessing;
	private boolean doIt = false;

	InputStream iStream = null;
	Bitmap imageSample, imageSampleUpload, imageSampleCamera, imageEdge, sampleSize;
	File file;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		getSupportActionBar().setDisplayShowTitleEnabled(false); // setting up simple theme actionbar

		cropImageView = (CropImageView)findViewById(R.id.crop_image_home);
		ivShowImage = (ImageView)findViewById(R.id.iv_show_image);
		tvShowSize = (TextView)findViewById(R.id.tv_size_image);
	}
	
	private void Notification(int width, int height) {
		if ( width > 200 && height > 200 ) {
			tvShowSize.setText("Lebar :"+width+"\n Tinggi :"+height+"\n Ukuran gambar cukup");
		} else if (width == 100 && height == 100){
			tvShowSize.setText("Lebar :"+width+"\n Tinggi :"+height+"\n Ukuran gambar pas");
		} else {
			tvShowSize.setText("Lebar :"+width+"\n Tinggi :"+height+"\n Ukuran gambar terlalu kecil");
		}
	}

	public void onCropImage(View view) {
		imageSample = cropImageView.getCroppedImage();
		Notification(imageSample.getWidth(),imageSample.getHeight());
		cropImageView.setImageBitmap(imageSample);
		imageSample = Bitmap.createScaledBitmap(imageSample, 300, 300, false);
		ivShowImage.setImageBitmap(imageSample);
		doIt = true;
	}
	
	public void onImageProcessing(View view) {
		if (imageSample != null) {
			new ImageProcessing().execute();
		} else {
			Toast.makeText(getApplicationContext(), " Masukan gambar terlebih dahulu",Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// save file url in bundle as it will be null on screen orientation
		// changes
		outState.putParcelable("file_uri", fileUri);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// get the file url
		fileUri = savedInstanceState.getParcelable("file_uri");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.item_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_camera:
			Intent iCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fileUri = getOutputMediaFileUri();
			iCapture.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			startActivityForResult(iCapture, REQUEST_CODE_CAPTURE);
			return true;
		case R.id.item_upload:
			Intent iUpload = new Intent(Intent.ACTION_PICK);
			iUpload.setType("image/*");
			startActivityForResult(iUpload, REQUEST_CODE_CHOOSE);
			return true;
		case R.id.item_tutorial:
			Intent iTutorial = new Intent(HomeActivity.this,
					TutorialActivity.class);
			startActivity(iTutorial);
			return true;
		case R.id.item_info:
			Intent iInfo = new Intent(HomeActivity.this, InfoActivity.class);
			startActivity(iInfo);
			return true;
		case R.id.item_gallery:
			Intent iGallery = new Intent(HomeActivity.this,
					GalleryActivity.class);
			startActivity(iGallery);
			return true;
		default:
			return true;
		}
	}

	public Uri getOutputMediaFileUri() {
		return Uri.fromFile(Utility.getOutputMediaFile());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case REQUEST_CODE_CAPTURE:
			if (resultCode == Activity.RESULT_OK) {
				try {
					// bimatp factory
					BitmapFactory.Options options = new BitmapFactory.Options();

					// downsizing image as it throws OutOfMemory Exception for
					// larger
					// images
					options.inSampleSize = 8;

					Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
							options);
					imageSampleCamera = bitmap;
					imageSampleCamera = Bitmap.createScaledBitmap(
							imageSampleCamera, 500,500, true);
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				cropImageView.setImageBitmap(imageSampleCamera);
			}
			break;
		case REQUEST_CODE_CHOOSE:
			if (resultCode == Activity.RESULT_OK) {
				try {
					if (imageSampleUpload != null) {
						imageSampleUpload.recycle();
					}
					iStream = getContentResolver().openInputStream(
							data.getData());
					imageSampleUpload = BitmapFactory.decodeStream(iStream);
					imageSampleUpload = Bitmap.createScaledBitmap(
							imageSampleUpload, 500, 500, true);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					if (iStream != null) {
						try {
							iStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				cropImageView.setImageBitmap(imageSampleUpload);
			}
			break;
		}
	}

	private class ImageProcessing extends AsyncTask<Void, Void, Void> {
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(HomeActivity.this);
			dialog.setMessage(" Proses Pengolahan... ");
			dialog.show();
			dialog.setCancelable(true); 
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			startTime = System.currentTimeMillis();
			imageEdge = Convert.toNegative(OtsuBinarize.binarize(OtsuBinarize.toGray(imageSample)));
			sampleSize = Bitmap.createScaledBitmap(imageEdge, 50, 50, true);
			DecimalFormat decimalFormat = new DecimalFormat("##.##");
			logTimeImageProcessing = String.valueOf(decimalFormat.format((System.currentTimeMillis() - startTime) * 1.666666671E-5));
			doIt = true;
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Log.i("Log Time","Image Processing: "+logTimeImageProcessing+" menit");
			dialog.cancel();
			Toast.makeText(getApplicationContext(), "Pengolahan gambar berhasil",Toast.LENGTH_SHORT).show();
			
			Intent PushToTrainningProcess = new Intent(HomeActivity.this, ResultActivity.class);
			Bundle bundle = new Bundle();
			bundle.putParcelable(BITMAP_SAMPLE, sampleSize);
			PushToTrainningProcess.putExtras(bundle);
			startActivity(PushToTrainningProcess);
		}
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setMessage("Anda yakin mau keluar ?")
				.setPositiveButton(" Ya ",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								HomeActivity.super.onBackPressed();
							}
						}).setNegativeButton(" Tidak ", null).create().show();
	}
}
