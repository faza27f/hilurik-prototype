package subhan.hilurik.model;

public class Gallery {

	private String headline;
//	private Bitmap image;
	private int banner;

	public Gallery(String headline, int banner) {
		super();
		this.headline = headline;
		this.banner = banner;
	}

	public int getBanner() {
		return banner;
	}

	public void setBanner(int banner) {
		this.banner = banner;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}
}
