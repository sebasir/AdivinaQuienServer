package controller;

public enum Urls {
	publicKey("_4di1v1n4Qu13n"),
	algorithm("PBEWithMD5AndDES"),
	charset("ISO-8859-1");

	private String url;

	private Urls(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}