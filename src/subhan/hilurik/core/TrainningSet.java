package subhan.hilurik.core;

public class TrainningSet {

	private int inputCount;
	private int outputCount;
	private double[][] inputValue;
	private double[][] outputValue;
	private int trainCount;

	public TrainningSet(int inputCount, int outputCount) {
		this.inputCount = inputCount;
		this.outputCount = outputCount;
		trainCount = 0;
	}

	public void setTrainningCount(int trainSet) {
		this.trainCount = trainSet;
		inputValue = new double[trainSet][inputCount];
		outputValue = new double[trainSet][outputCount];
	}

	public int getTrainning() {
		return trainCount;
	}

	public int getInputCount() {
		return inputCount;
	}

	public void setInputCount(int set, int index, double value)
			throws RuntimeException {
		if ((set < 0) || (set >= trainCount)) {
			throw (new RuntimeException(" Training set out of range:" + set));
		}

		if ((index < 0) || (index >= inputCount)) {
			throw (new RuntimeException(" Training input index out of range:"
					+ index));
		}

		inputValue[set][index] = value;
	}

	public int getOutputCount() {
		return outputCount;
	}

	public void setOutputCount(int set, int index, double value)
			throws RuntimeException {
		if ((set < 0) || (set >= trainCount)) {
			throw (new RuntimeException(" Training set out of range:" + set));
		}

		if ((index < 0) || ( set >= outputCount)) {
			throw (new RuntimeException(" Training input index out of range:"
					+ index));
		}

		outputValue[set][index] = value;
	}

	public double getInputValue(int set, int index) throws RuntimeException {

		if ((set < 0) || (set >= trainCount)) {
			throw (new RuntimeException(" Training set out of range:" + set));
		}

		if ((index < 0) || (index >= inputCount)) {
			throw (new RuntimeException(" Training input index out of range:"
					+ index));
		}
		return inputValue[set][index];
	}

	public double getOutputValue(int set, int index) throws RuntimeException {
		if ((set < 0) || (set >= trainCount)) {
			throw (new RuntimeException(" Training set out of range:" + set));
		}

		if ((index < 0) || (set >= outputCount)) {
			throw (new RuntimeException(" Training input index out of range:"
					+ index));
		}
		return outputValue[set][index];
	}

	public double[] getOutputSet(int set) throws RuntimeException {
		if ((set < 0) || (set >= trainCount)) {
			throw (new RuntimeException("Training set out of range:" + set));
		}
		return outputValue[set];
	}
	
	public double[] getInputSet(int set) throws RuntimeException {
		if ((set < 0) || (set >= trainCount)) {
			throw (new RuntimeException("Training set out of range:" + set));
		}
		return inputValue[set];
	}
}
