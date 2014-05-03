package subhan.hilurik.core;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Reference :
 * Image binarization - Otsu algorithm
 *
 * Author: Bostjan Cigan (http://zerocool.is-a-geek.net)
 *
 */
  
public class OtsuBinarize {
 
    // Return histogram of grayscale image
    private static int[] imageHistogram(Bitmap input) {
 
        int[] histogram = new int[256];
 
        for(int i=0; i<histogram.length; i++) histogram[i] = 0;
 
        for(int i=0; i<input.getWidth(); i++) {
            for(int j=0; j<input.getHeight(); j++) {
                int red = Color.red(input.getPixel(i, j));
                histogram[red]++;
            }
        }
 
        return histogram;
 
    }
 
    // The luminance method
    public static Bitmap toGray(Bitmap original) {
 
        int alpha, red, green, blue;
        int newPixel;
 
        Bitmap lum = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());
 
        for(int i=0; i<original.getWidth(); i++) {
            for(int j=0; j<original.getHeight(); j++) {
                // Get pixels by R, G, B
                alpha = Color.alpha(original.getPixel(i, j));
                red = Color.red(original.getPixel(i, j));
                green = Color.green(original.getPixel(i, j));
                blue = Color.blue(original.getPixel(i, j));
 
                red = (int) (0.3f * red + 0.59f * green + 0.11f * blue);
                // Return back to original format
                newPixel = colorToRGB(alpha, red, red, red);
 
                // Write pixels into image
                lum.setPixel(i, j, newPixel);
            }
        }
 
        return lum;
 
    }
 
    // Get binary treshold using Otsu's method
    private static int otsuTreshold(Bitmap original) {
 
        int[] histogram = imageHistogram(original);
        int total = original.getHeight() * original.getWidth();
 
        float sum = 0;
        for(int i=0; i<256; i++) sum += i * histogram[i];
 
        float sumB = 0;
        int wB = 0;
        int wF = 0;
 
        float varMax = 0;
        int threshold = 0;
 
        for(int i=0 ; i<256 ; i++) {
            wB += histogram[i];
            if(wB == 0) continue;
            wF = total - wB;
 
            if(wF == 0) break;
 
            sumB += (float) (i * histogram[i]);
            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;
 
            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
 
            if(varBetween > varMax) {
                varMax = varBetween;
                threshold = i;
            }
        }
 
        return threshold;
 
    }
 
    public static Bitmap binarize(Bitmap original) {
 
        int red;
        int newPixel;
 
        int threshold = otsuTreshold(original);
 
        Bitmap binarized = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());
 
        for(int i=0; i<original.getWidth(); i++) {
            for(int j=0; j<original.getHeight(); j++) {
 
                // Get pixels
                red = Color.red(original.getPixel(i, j));
                int alpha = Color.alpha(original.getPixel(i, j));
                if(red > threshold) {
                    newPixel = 255;
                }
                else {
                    newPixel = 0;
                }
                newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
                binarized.setPixel(i, j, newPixel); 
 
            }
        }
 
        return binarized;
 
    }
 
    // Convert R, G, B, Alpha to standard 8 bit
    private static int colorToRGB(int alpha, int red, int green, int blue) {
 
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red; 
        newPixel = newPixel << 8;
        newPixel += green; 
        newPixel = newPixel << 8;
        newPixel += blue;
 
        return newPixel;
    }
}
