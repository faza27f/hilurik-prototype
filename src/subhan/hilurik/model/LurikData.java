package subhan.hilurik.model;

public class LurikData/* implements Comparable<LurikData>, Cloneable */{

	protected boolean[][] binary;
	/* protected int[] color; */
	protected String pattern;

	public LurikData(String pattern, int width, int height) {
		this.binary = new boolean[width][height];
		/* this.color = new int[inColor.length]; */
		this.pattern = pattern;
	}

	public void setData(int x, int y, boolean v) {
		this.binary[x][y] = v;
	}

	public boolean getData(int x, int y) {
		return this.binary[x][y];
	}

	public int getHeight() {
		return this.binary[0].length;
	}

	public int getWidth() {
		return this.binary.length;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	/*
	 * public int[] getColor() { return this.color; }
	 */

	/*
	 * public int[] setColor(final int[] input) { return this.color = new
	 * int[input.length]; }
	 */

	/*public void clear() {
		for (int x = 0; x < binary.length; x++) {
			for (int y = 0; y < binary[0].length; y++) {
				binary[x][y] = false;
			}
		}
	}

	@Override
	public Object clone() {

		LurikData lurikData = new LurikData(pattern, getHeight(), getWidth());

		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				lurikData.setData(x, y, getData(x, y));
			}
		}
		
		 * for (int i = 0; i < getColor().length; i++) {
		 * data.setColor(getColor()); }
		 
		return lurikData;
	}

	@Override
	public int compareTo(LurikData another) {

		LurikData lurikData = (LurikData) another;

		if (this.getPattern().charAt(0) > lurikData.getPattern().charAt(0)) {
			return 1;
		} else {
			return -1;
		}
	}*/
}
