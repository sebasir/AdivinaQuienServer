package controller;

public enum Urls {
	publicKey("_4d1v1n4Qu13n"),
	algorithm("PBEWithMD5AndDES"),
	charset("UTF-8");

	private String url;

	private Urls(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}