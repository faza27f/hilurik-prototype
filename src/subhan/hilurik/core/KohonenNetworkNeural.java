package subhan.hilurik.core;


public class KohonenNetworkNeural extends Network {

	private double[][] outputWeights;
	protected int learnMethod = 1;
	protected double learnRate = 0.5;
	protected double quitError = 0.1;
	protected int retries = 2500; // do it again L:40 X T:20 >> 800 //
									// 3/3/2014 "bismillah bisa"
	protected double reduction = .99;
	protected ReportNeuralNetwork reportNeuralNetwork;
	protected TrainningSet trainningSet;
	public boolean stop = false;
	@SuppressWarnings("unused")
	private int iteration;

	public KohonenNetworkNeural(int inputCount, int outputCount,
			ReportNeuralNetwork reportNeuralNetwork) {
		totalError = 1.0;

		this.inputNeuronCount = inputCount;
		this.outputNeuronCount = outputCount;
		this.outputWeights = new double[outputNeuronCount][inputNeuronCount+1];
		this.output = new double[outputNeuronCount];
		this.reportNeuralNetwork = reportNeuralNetwork;
	}

	public void setTrainningSet(TrainningSet trainningSet) {
		this.trainningSet = trainningSet;
	}

	private static void copyWeights(KohonenNetworkNeural dest,
			KohonenNetworkNeural source) {
		for (int i = 0; i < source.outputWeights.length; i++) {
			System.arraycopy(source.outputWeights[i], 0, dest.outputWeights[i],
					0, source.outputWeights[i].length);
		}
	}

	public void clearWeights() {
		totalError = 1.0;
		for (int y = 0; y < outputWeights.length; y++) {
			for (int x = 0; x < outputWeights[0].length; x++) {
				outputWeights[y][x] = 0;
			}
		}

	}

	public void normalizeInput(final double[] input, double[] normalFac,
			double[] synth) {
		double length;
		length = vectorLength(input);
		if (length < 1.E-30) {
			length = 1.E-30;
		}

		normalFac[0] = 1.0 / Math.sqrt(length);
		synth[0] = 0.0;
	}

	public void normalizeWeight(double[] inputWeight) {
		double length;
		length = vectorLength(inputWeight);

		if (length < 1.E-30) {
			length = 1.E-30;
		}

		length = 1.0 / Math.sqrt(length);
		for (int i = 0; i < inputNeuronCount; i++) {
			inputWeight[i] *= length;
		}

		inputWeight[inputNeuronCount] = 0;

	}

	@Override
	public void trial(double[] input) {
		double[] normaFac = new double[1];
		double[] synth = new double[1];
		double[] optr;

		normalizeInput(input, normaFac, synth);

		for (int i = 0; i < outputNeuronCount; i++) {
			optr = outputWeights[i];
			output[i] = dotProduct(input, optr) * normaFac[0] + synth[0]
					* optr[inputNeuronCount];

			output[i] = 0.5 * (output[i] + 1.0);

			if (output[i] > 1.0) {
				output[i] = 1.0;
			}

			if (output[i] < 0.0) {
				output[i] = 0.0;
			}

		}

	}

	public int winner(double[] input, double[] normaFac, double[] synth) {
		int win = 0;
		double biggest;
		double[] optr;

		normalizeInput(input, normaFac, synth);

		biggest = -1.E30;

		for (int i = 0; i < outputNeuronCount; i++) {
			optr = outputWeights[i];
			output[i] = dotProduct(input, optr) * normaFac[0] + synth[0]
					* optr[inputNeuronCount];

			output[i] = 0.5 * (output[i] + 1.0);

			if (output[i] > biggest) {
				biggest = output[i];
				win = i;
			}

			if (output[i] > 1.0) {
				output[i] = 1.0;
			}

			if (output[i] < 0.0) {
				output[i] = 0.0;
			}
		}

		return win;
	}

	public void evaluateErrors(double rate, int learnMethod, int[] won,
			double[] bigError, double[][] correct, double[] work)
			throws RuntimeException {

		int best;
		double[] dptr;
		double[] normaFac = new double[1];
		double[] synth = new double[1];
		double[] cptr;
		double[] wptr;
		double length, diff;

		for (int y = 0; y < correct.length; y++) {
			for (int x = 0; x < correct[0].length; x++) {
				correct[y][x] = 0;
			}
		}

		for (int i = 0; i < won.length; i++) {
			won[i] = 0;
		}

		bigError[0] = 0.0;

		for (int tset = 0; tset < trainningSet.getTrainning(); tset++) {
			dptr = trainningSet.getInputSet(tset);
			best = winner(dptr, normaFac, synth);
			won[best]++;
			wptr = outputWeights[best];
			cptr = correct[best];
			length = 0.0;

			for (int i = 0; i < inputNeuronCount; i++) {
				diff = dptr[i] * normaFac[0] - wptr[i];
				length += diff * diff;

				if (learnMethod != 0) {
					cptr[i] += diff;
				} else {
					work[i] = rate * dptr[i] * normaFac[0] + wptr[i];
				}
			}

			diff = synth[0] - wptr[inputNeuronCount];
			length += diff * diff;

			if (learnMethod != 0) {
				cptr[inputNeuronCount] += diff;
			} else {
				work[inputNeuronCount] = rate * synth[0]
						+ wptr[inputNeuronCount];
			}

			if (length > bigError[0]) {
				bigError[0] = length;
			}
			
			if (learnMethod == 0) {
				normalizeWeight(work);

				for (int i = 0; i <= inputNeuronCount; i++) {
					cptr[i] += work[i] - wptr[i];
				}
			}
		}
		bigError[0] = Math.sqrt(bigError[0]);
	}

	private void adjustWeights(double rate, int learnMethod, int[] won,
			double[] bigCorrect, double[][] correct) {
		double correct2;
		double[] cptr;
		double[] wptr;
		double length;
		double f;

		bigCorrect[0] = 0.0;

		for (int i = 0; i < outputNeuronCount; i++) {
			if (won[i] == 0) {
				continue;
			}

			wptr = outputWeights[i];
			cptr = correct[i];

			f = 1.0 / (double) won[i];

			if (learnMethod != 0) {
				f *= rate;
			}

			length = 0.0;

			for (int j = 0; j <= inputNeuronCount; j++) {
				correct2 = f * cptr[j];
				wptr[j] += correct2;
				length += correct2 * correct2;
			}

			if (length > bigCorrect[0]) {
				bigCorrect[0] = length;
			}
		}

		bigCorrect[0] = Math.sqrt(bigCorrect[0]) / rate;
	}

	private void forceWin(int[] won) throws RuntimeException {
		int i, best, which = 0;
		double[] dptr, normFac = new double[1];
		double[] synth = new double[1];
		double dist;
		double[] optr;

		dist = 1.E30;

		for (int tset = 0; tset < trainningSet.getTrainning(); tset++) {
			dptr = trainningSet.getInputSet(tset);
			best = winner(dptr, normFac, synth);

			if (output[best] < dist) {
				dist = output[best];
				which = tset;
			}
		}

		dptr = trainningSet.getInputSet(which);
		best = winner(dptr, normFac, synth);

		dist = -1.E30;
		i = outputNeuronCount;
		while ((i--) > 0) {
			if (won[i] != 0) {
				continue;
			}

			if (output[i] > dist) {
				dist = output[i];
				which = i;
			}
		}

		optr = outputWeights[which];

		System.arraycopy(dptr, 0, optr, 0, dptr.length);

		optr[inputNeuronCount] = synth[0] / normFac[0];
		normalizeWeight(optr);
	}

	@Override
	public void learn() throws RuntimeException {
		int i, n_retry;
		int[] won;
		int winners;
		double[] work;
		double[][] correct;
		double rate, bestError;
		double[] dptr;
		double[] bigError = new double[1];
		double[] bigCorrect = new double[1];

		KohonenNetworkNeural bestNet;

		totalError = 1.0;

		for (int tset = 0; tset < trainningSet.getTrainning(); tset++) {
			dptr = trainningSet.getInputSet(tset);

			if (vectorLength(dptr) < -1.E30) {
				throw (new RuntimeException(
						"Multiplicative normalization has null trainning case"));
			}
		}

		bestNet = new KohonenNetworkNeural(inputNeuronCount, outputNeuronCount,
				reportNeuralNetwork);

		won = new int[outputNeuronCount];
		correct = new double[outputNeuronCount][inputNeuronCount + 1];

		if (learnMethod == 0) {
			work = new double[inputNeuronCount + 1];
		} else {
			work = null;
		}

		rate = learnRate;

		initialize();
		bestError = 1.E30;

		n_retry = 0;
		for (iteration = 0;; iteration++) {
			evaluateErrors(rate, learnMethod, won, bigError, correct, work);

			totalError = bigError[0];
			if (totalError < bestError) {
				bestError = totalError;
				copyWeights(bestNet, this);
			}

			winners = 0;
			for (i = 0; i < won.length; i++) {
				if (won[i] != 0) {
					winners++;
				}
			}

			if (bigError[0] < quitError) {
				break;
			}

			if ((winners < outputNeuronCount)
					&& winners < trainningSet.getTrainning()) {
				forceWin(won);
				continue;
			}

			adjustWeights(rate, learnMethod, won, bigCorrect, correct);

			reportNeuralNetwork.update(n_retry, totalError, bestError);

			if (stop) {
				reportNeuralNetwork.update(n_retry, totalError, bestError);
				break;
			}

			Thread.yield();

			if (bigCorrect[0] < 1.E-5) {
				if (++n_retry > retries) {
					break;
				}
				initialize();
				iteration = -1;
				rate = learnRate;
				continue;
			}

			if (rate > 0.001) {
				rate *= reduction;
			}

		}

		copyWeights(this, bestNet);

		for (i = 0; i < outputNeuronCount; i++) {
			normalizeWeight(outputWeights[i]);
		}

		stop = true;
		n_retry++;
		reportNeuralNetwork.update(n_retry, totalError, bestError);

	}

	private void initialize() { 
		double[] optr;
		
		clearWeights();
		randomizeWeights(outputWeights);
		
		for (int i = 0; i < outputNeuronCount; i++) {
			optr = outputWeights[i];
			normalizeWeight(optr);
		}

	}
}
