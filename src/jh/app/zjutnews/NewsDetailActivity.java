package jh.app.zjutnews;

import jh.app.zjutnews.bean.NewsDetailBean;
import jh.app.zjutnews.dataservice.HttpData;
import jh.app.zjutnews.dataservice.XMLPaser;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class NewsDetailActivity extends Activity implements OnClickListener {

	private String url = "http://210.32.200.89:65080/zjutNews/getNewsDetailServlet?id=";
	private String newsId;
	private TextView title1, title2;
	private WebView myWebView;
	private NewsDetailBean newsDetail;
	private HttpData httpdata;
	private XMLPaser xmlpaser;
	private TextView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsdetail);

		newsId = getIntent().getStringExtra("newsId");
		title1 = (TextView) findViewById(R.id.newsTitle1);
		title2 = (TextView) findViewById(R.id.newsTitle2);
		myWebView = (WebView) findViewById(R.id.myWebView);
		back = (TextView) findViewById(R.id.NavigateBack);
		back.setOnClickListener(this);

		Toast.makeText(this, "正在加载数据，请稍候.....", Toast.LENGTH_SHORT).show();

		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				System.out.println("newsId=" + newsId);
				newsDetail = initNewsDetailData(newsId);
				if (newsDetail.getContent().trim() == "\\n") {
					new AlertDialog.Builder(NewsDetailActivity.this)
							.setTitle("提示").setMessage("抱歉，该新闻是视频新闻，无法显示!")
							.setPositiveButton("确定", null)
							.setNegativeButton("取消", null).show();
				}
				handler.sendEmptyMessage(0);
			}

		}.start();

	}

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				title1.setText(newsDetail.getTitle1());

				System.out.println("aaa=" + newsDetail.getTitle1());
				if (title2 != null) {
					title2.getLayoutParams().height = 50;
					title2.setText(newsDetail.getTitle2());
				}
				System.out.println("xxxx=" + newsDetail.getContent());
				myWebView.loadDataWithBaseURL(null, newsDetail.getContent(),
						"text/html", "utf-8", null);

				break;
			}
		}
	};

	public NewsDetailBean initNewsDetailData(String newsId) {

		httpdata = new HttpData();
		xmlpaser = new XMLPaser();
		newsDetail = new NewsDetailBean();

		try {
			newsDetail = xmlpaser.getNewsDetail(httpdata
					.getInputStreamFromUrl(url + newsId));
			System.out.println("sdssdsdsdsd=" + newsDetail.getTitle1());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newsDetail;
	}
	/**
	 * 点击事件
	 */
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.NavigateBack:
			this.finish();
			break;
		}
	}
}
