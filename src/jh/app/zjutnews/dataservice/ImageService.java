package jh.app.zjutnews.dataservice;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageService {

	public static Bitmap getBitmapFromServer(String imagePath) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://www.zjut.edu.cn/zjutnews/"+imagePath.trim());
		HttpResponse httpResponse = null;
		InputStream is = null;
		HttpEntity responseEntity = null;
		Bitmap pic = null;
		try {
			httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				responseEntity = httpResponse.getEntity();
				is = responseEntity.getContent();
				pic = BitmapFactory.decodeStream(is);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pic = null;
		}
		return pic;
	}

	public static byte[] getImage(String path) throws Exception {

		URL url = new URL("http://www.zjut.edu.cn/zjutnews/" + path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		InputStream inStream = conn.getInputStream();
		return readInputStream(inStream);
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// ����һ��������
		byte[] buffer = new byte[1024];
		int len = -1;
		// �ж������������Ƿ����-1�����ǿ�
		while ((len = inStream.read(buffer)) != -1) {
			// �ѻ�����������д�뵽������У���0��ʼ��ȡ������Ϊlen
			outStream.write(buffer, 0, len);
		}
		// �ر�������
		inStream.close();
		return outStream.toByteArray();
	}
}
