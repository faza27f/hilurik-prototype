package subhan.hilurik.activities;

import java.io.IOException;
import java.util.ArrayList;

import subhan.hilurik.R;
import subhan.hilurik.core.KohonenNetworkNeural;
import subhan.hilurik.core.ReportNeuralNetwork;
import subhan.hilurik.core.TrainningSet;
import subhan.hilurik.model.LurikData;
import subhan.hilurik.support.Convert;
import subhan.hilurik.support.Utility;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class ResultActivity extends SherlockActivity implements ReportNeuralNetwork {

	private static final String TAG = "ResultActivity::";
	public static final String BITMAP_SAMPLE = "image_sample";
	public static final String NAME_PATTERN = "pattern";
	public static final int SAMPLE_SIZE_WIDTH = 50;
	public static final int SAMPLE_SIZE_HEIGHT = 50;
	
	private ImageView ivResult, ivSample;
	private TextView tvContent;
	
	KohonenNetworkNeural knn;
	ArrayList<LurikData> arListData;
	
	Trainning trainning;
	Bitmap sampleImage;
	String profil, pattern;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_result);
		
		ivSample = (ImageView)findViewById(R.id.iv_sample_lurik);
		ivResult = (ImageView) findViewById(R.id.iv_image_lurik);
		tvContent = (TextView) findViewById(R.id.tv_profile_kain_lurik);

		showImage();
	}

	public void showImage() {
		Intent intent = getIntent();
		Bundle extBundle = intent.getExtras();
		
		sampleImage = (Bitmap) extBundle.getParcelable(BITMAP_SAMPLE);
	
		ivSample.setImageBitmap(sampleImage);
		
		new Trainning().execute();
	}
	
	private class Trainning extends AsyncTask<Void, Void, Void> {

		ProgressDialog dialog;
		double[] input = new double[SAMPLE_SIZE_HEIGHT*SAMPLE_SIZE_WIDTH];
		int best;
		String pattern;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(ResultActivity.this);
			dialog.setMessage(" Melakukan Pengenalan... ");
			dialog.show();
			dialog.setCancelable(true);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				arListData = Utility.loadData(ResultActivity.this);
				// Log.i("Data Lurik", " Count : "+arListData.size());
				int inputNeuron = SAMPLE_SIZE_WIDTH * SAMPLE_SIZE_HEIGHT;
				int outputNeuron = arListData.size();
				TrainningSet trainningSet = new TrainningSet(inputNeuron, outputNeuron);
				trainningSet.setTrainningCount(arListData.size());
				
				for (int i = 0; i < arListData.size(); i++) {
					int go = 0;
					for (int y = 0; y < arListData.get(i).getHeight(); y++) {
						for (int x = 0; x < arListData.get(i).getWidth(); x++) {
							trainningSet.setInputCount(i, go++, arListData.get(i).getData(x, y)? .5 : -.5);
							// Log.i("Data Lurik","["+x+"] ["+y+"] : "+(arListData.get(i).getData(x, y)? .5 : -.5)); // show value after normalitation in trainning
						}
					}
				}
				
				knn = new KohonenNetworkNeural(inputNeuron, outputNeuron, ResultActivity.this);
				knn.setTrainningSet(trainningSet);
				knn.learn();
				
				int i = 0;
				int[][] binary = Convert.toBinary(sampleImage);
				
				for (int y = 0; y < binary[0].length; y++) {
					for (int x = 0; x < binary.length; x++) {
						input[i++] = (binary[x][y] == 1? .5 : -.5);
						//Log.i(TAG+"binary image","id:"+i+"["+x+"] ["+y+"]"+/*"binary:"+binary[x][y]+*/" value:"+(input[i++] = (binary[x][y] == 1? .5 : -.5)));
					}
				}
				
				
				
				double[] normaFac = new double[1];
				double[] synth = new double[1];
				
				best = knn.winner(input, normaFac, synth);
				//Log.i(TAG+"winner"," "+input[index++]);
				String[] map = mapNeurons();
				pattern = map[best];
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
	
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.cancel();
			//Toast.makeText(getApplication(), "Pembelajaran berhasil", Toast.LENGTH_SHORT).show();
			Toast.makeText(getApplication(), "Berhasil", Toast.LENGTH_SHORT).show();
			findPatternLurik(pattern);
			
		}
	}
	
	public void onRecognition(View view) {
		
	}
	
/*	private class Recognition extends AsyncTask<Void, Void, Void> {
		
		ProgressDialog dialog;
		String map[];
		int best;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(ResultActivity.this);
			dialog.setMessage(" Melakukan Pengenalan... ");
			dialog.show();
			dialog.setCancelable(true);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.cancel();
			
			findPatternLurik(pattern);
		}
	}*/
	
	private String[] mapNeurons() throws IOException {
		arListData = Utility.loadData(ResultActivity.this);
		
		String[] map = new String[arListData.size()];
		
		double[] normaFac = new double[1];
		double[] synth = new double[1];
		
		for (int i = 0; i < map.length; i++) {
			map[i] = "?";
		}
		
		for (int i = 0; i < arListData.size(); i++) {
			double[] input = new double[50*50];
			int index = 0;
			for (int y = 0; y < arListData.get(i).getHeight(); y++) {
				for (int x = 0; x < arListData.get(i).getWidth(); x++) {
					input[index++] = arListData.get(i).getData(x,y)? .5 : -.5;
				}
			}

			int best = knn.winner(input, normaFac, synth);
			map[best] = arListData.get(i).getPattern();
		}
		return map;
	}
	

	@Override
	public void update(int retry, double totalError, double bestError) {
		// TODO Auto-generated method stub
		
	}
	
	private void findPatternLurik(String name) {
		
		if (name.equals("telu-pat")) {
			profil = getResources().getString(R.string.lurik_11_content);
			tvContent.setText(Html.fromHtml(" Corak dan Nama : "+name+" "+profil/*+"\n Neuron :"+best2*/));
		} else if (name.equalsIgnoreCase("loro-pat")) {
			profil = getResources().getString(R.string.lurik_12_content);
			tvContent.setText(Html.fromHtml(" Corak dan Nama : "+name+" "+profil/*+"\n Neuron :"+best2*/));
		} else if (name.equalsIgnoreCase("dam-daman")) {
			profil = getResources().getString(R.string.lurik_13_content);
			tvContent.setText(Html.fromHtml(" Corak dan Nama : "+name+" "+profil/*+"\n Neuron :"+best2*/));
		} else if (name.equalsIgnoreCase("ojo lali")) {
		    profil = getResources().getString(R.string.lurik_14_content);
			tvContent.setText(Html.fromHtml(" Corak dan Nama : "+name+" "+profil/*+"\n Neuron :"+best2*/));
		} else if (name.equalsIgnoreCase("telu-telu")) {
			profil = getResources().getString(R.string.lurik_15_content);
			tvContent.setText(Html.fromHtml(" Corak dan Nama : "+name+" "+profil/*+"\n Neuron :"+best2*/));
		} else if (name.equalsIgnoreCase("dom kecer")) {
			profil = getResources().getString(R.string.lurik_16_content);
			tvContent.setText(Html.fromHtml(" Corak dan Nama : "+name+" "+profil/*+"\n Neuron :"+best2*/));
		} else if (name.equalsIgnoreCase("tolak-bala")) {
			profil = getResources().getString(R.string.lurik_17_content);
			tvContent.setText(Html.fromHtml(" Corak dan Nama : "+name+" "+profil/*+"\n Neuron :"+best2*/));
		} else if (name.equalsIgnoreCase("bribil")) {
			profil = getResources().getString(R.string.lurik_18_content);
			tvContent.setText(Html.fromHtml(" Corak dan Nama : "+name+" "+profil/*+"\n Neuron :"+best2*/));
		} else if (name.equalsIgnoreCase("sodo sak ler")) {
			profil = getResources().getString(R.string.lurik_19_content);
			tvContent.setText(Html.fromHtml(" Corak dan Nama : "+name+" "+profil/*+"\n Neuron :"+best2*/));
		} else if (name.equalsIgnoreCase("obar abir")) {
			profil = getResources().getString(R.string.lurik_20_content);
			tvContent.setText(Html.fromHtml(" Corak dan Nama : "+name+" "+profil/*+"\n Neuron :"+best2*/));
		} else {
			tvContent.setText(" Corak : Tidak Ada \n Profil : Tidak Ada");
		}
	}
}
