package subhan.hilurik.support;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Convert {
	
	//private static final String TAG = " Convert::";
	
	// Bitmap invers black to white
	public static Bitmap toNegative(Bitmap bitmap) {
		
		Bitmap result = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), bitmap.getConfig());
	
		for (int x = 0; x < bitmap.getWidth(); x++) {
			for (int y = 0; y < bitmap.getHeight(); y++) {
				int pixel = bitmap.getPixel(x, y);
				int alpha = Color.alpha(pixel);
				int red = 255 - Color.red(pixel);
				int blue = 255 - Color.blue(pixel);
				int green = 255 - Color.green(pixel);
				int pixel2 = Color.argb(alpha, red, green, blue);
				result.setPixel(x, y, pixel2);
			}
		}
		return result;
	}
	
	// binary bitmap nice for mode : integer my own version 7/2/2014, version 2 3/2/2014, version 3 20/4/2014 
	public static int[][] toBinary(Bitmap bitmap) {
		int[][] pixelArray = new int [bitmap.getWidth()][bitmap.getHeight()];
		for (int y = 0; y < bitmap.getHeight(); y++) {
			for (int x = 0; x < bitmap.getWidth(); x++) {
				pixelArray[x][y] = bitmap.getPixel(x, y) == 0xff000000 ? 1 : 0;
			}
		}
		
		return pixelArray;
	}
}
