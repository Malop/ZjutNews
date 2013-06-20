package jh.app.zjutnews.bean;

import android.graphics.Bitmap;

public class ImageNewsBean {

	private String newsLink;
	private String imageLink;
	private String tittle;
	private Bitmap bitmap;
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public String getNewsLink() {
		return newsLink;
	}
	public void setNewsLink(String newsLink) {
		this.newsLink = newsLink;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public String getTittle() {
		return tittle;
	}
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
	
	
	
}
