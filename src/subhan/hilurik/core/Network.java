package subhan.hilurik.core;

import java.util.Random;

abstract public class Network {
	protected double output[];
	protected double totalError;
	protected int inputNeuronCount;
	protected int outputNeuronCount;
	protected Random random = new Random(System.currentTimeMillis());

	public abstract void trial(double[] input);

	public abstract void learn() throws RuntimeException;

	public double[] getOutput() {
		return output;
	}

	public static double vectorLength(double[] vector) {
		double rtn = 0.0;
		for (int i = 0; i < vector.length; i++) {
			rtn += vector[i] * vector[i];
		}
		return rtn;
	}

	public double dotProduct(double[] vector1, double[] vector2) {
		int k, v;
		double rtn;
		
		k = vector1.length;
		v = 0;
		rtn = 0.0;

		while ((k--) > 0) {
			rtn += vector1[v] * vector2[v];
			v++;
		}
		return rtn;
	}

	public void randomizeWeights(double[][] weight) {
		double r;
		
		int temp = (int)(3.464101615 / (2. * Math.random()));
		
		for (int y = 0; y < weight.length; y++) {
			for (int x = 0; x < weight[0].length; x++) {
				r = (double) random.nextInt(Integer.MAX_VALUE) + (double)random.nextInt(Integer.MAX_VALUE) - 
					(double) random.nextInt(Integer.MAX_VALUE) - (double)random.nextInt(Integer.MAX_VALUE);
				weight[y][x] = temp * r;
			}
		}
	}
}
