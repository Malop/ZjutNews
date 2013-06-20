package jh.app.zjutnews;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jh.app.zjutnews.bean.ImageNewsBean;
import jh.app.zjutnews.bean.NewsBean;
import jh.app.zjutnews.dataservice.ImageService;
import jh.app.zjutnews.dataservice.NetworkUtils;
import jh.app.zjutnews.dataservice.HttpData;
import jh.app.zjutnews.dataservice.XMLPaser;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressWarnings("deprecation")
@SuppressLint("HandlerLeak")
public class MainActivity extends Activity implements OnClickListener {

	private String url = "http://210.32.200.89:65080/zjutNews/getNewsTitleServlet?bigclassid=";
	//private String url = "http://192.168.1.112:8080/zjutNews/getNewsTitleServlet?bigclassid=";
	private ViewPager vp;// viwpage控件
	private MyPagerAdapter myAdapter;// viewpage的数据源适配器
	private List<View> vessel = new ArrayList<View>();// 为viewpager保存view界面的集合
	private View view1, view2, view3;// 装载2个校区listview的view
	private ListView lv1, lv2, lv3;
	private TextView news1, news2, news3;
	private ImageView cursor;// 动画图片
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度

	private Gallery gallery;
	private List<ImageNewsBean> imageList;
	private ImageAdapter imageAdapter;

	private TextView exit;

	private ArrayList<NewsBean> newses1, newses2, newses3;
	private ListViewAdapter adapter1, adapter2, adapter3;
	private ArrayList<HashMap<String, Object>> listItem1, listItem2, listItem3;
	private HttpData httpdata = new HttpData();// 下载数据的http请求类
	private XMLPaser xmlpaser = new XMLPaser();// 解析xml的类

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		gallery = (Gallery) findViewById(R.id.gallery1);
		exit = (TextView) findViewById(R.id.NavigateBack);
		exit.setOnClickListener(this);
		System.out.println("1111111111111111");
		if (NetworkUtils.isNetWorkValiable(this)) {
			Toast.makeText(MainActivity.this, "正在载入数据，请稍候...",Toast.LENGTH_LONG).show();
			new Thread() {
				public void run() {
					super.run();
					initNewsData();// 下载数据
					myHandler.sendEmptyMessage(0);
				}
			}.start();
		} else {
			Toast.makeText(this, "亲，网络未连接！", Toast.LENGTH_SHORT).show();
		}

	}

	private Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// initNewsData();
				initImageView();// 动画图片初始化
				System.out.println("3333333333");

				init();// 初始化数据视图
				System.out.println("44444444444");
				break;
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 初始化滑动条
	 */
	public void initImageView() {

		cursor = (ImageView) findViewById(R.id.cursor);

		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.short_line).getWidth();// 获取图片宽度

		DisplayMetrics dm = new DisplayMetrics();

		getWindowManager().getDefaultDisplay().getMetrics(dm);

		int screenW = dm.widthPixels;// 获取屏幕分辨率宽度
		offset = (screenW / 3 - bmpW) / 2 + 1;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);// 平移到(0,0)点
		cursor.setImageMatrix(matrix);// 设置动画初始位置

	}

	/**
	 * 初始化新闻数据，从服务器上下载主要新闻列表
	 */
	public void initNewsData() {

		try {
			// 10表示新闻速递;25表示学术动态;1表示公告栏；29表示图片新闻
			// 得到新闻列表的list
			newses1 = xmlpaser.getNewsData(httpdata.getInputStreamFromUrl(url+ "10"));
			newses2 = xmlpaser.getNewsData(httpdata.getInputStreamFromUrl(url+ "25"));
			newses3 = xmlpaser.getNewsData(httpdata.getInputStreamFromUrl(url+ "1"));
			imageList = xmlpaser.getImageNewsData(httpdata.getInputStreamFromUrl(url + "29"));
			
			//先把图片加载好,就加载三个好了，主要是没写缓存到本地，移动网络太费流量，又慢！
			for (int i=0; i<4; i++) {
				imageList.get(i).setBitmap(ImageService.getBitmapFromServer(imageList.get(i).getImageLink()));				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 用来存放显示列表的list，里面放map
		listItem1 = new ArrayList<HashMap<String, Object>>();

		listItem2 = new ArrayList<HashMap<String, Object>>();

		listItem3 = new ArrayList<HashMap<String, Object>>();
		
		for (int i = 0; i < newses1.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("TITTLE", newses1.get(i).getTittle());
			map.put("TIME", newses1.get(i).getTime());
			listItem1.add(map);
		}
		for (int i = 0; i < newses2.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("TITTLE", newses2.get(i).getTittle());
			map.put("TIME", newses2.get(i).getTime());
			listItem2.add(map);
		}
		for (int i = 0; i < newses3.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("TITTLE", newses3.get(i).getTittle());
			map.put("TIME", newses3.get(i).getTime());
			listItem3.add(map);
		}
		
		adapter1 = new ListViewAdapter(this, listItem1);
		adapter2 = new ListViewAdapter(this, listItem2);
		adapter3 = new ListViewAdapter(this, listItem3);
		imageAdapter = new ImageAdapter(this, imageList);
	}

	/**
	 * 初始化视图
	 */
	public void init() {
		view1 = getLayoutInflater().inflate(R.layout.listview1, null);
		view2 = getLayoutInflater().inflate(R.layout.listview2, null);
		view3 = getLayoutInflater().inflate(R.layout.listview3, null);

		vessel.add(view1);
		vessel.add(view2);
		vessel.add(view3);

		vp = (ViewPager) findViewById(R.id.viewpagerLayout);
		myAdapter = new MyPagerAdapter();
		vp.setAdapter(myAdapter);
		vp.setCurrentItem(0);// 设置起始默认的值为0
		vp.setOnPageChangeListener(new MyOnPageChangeListener());
		cursor = (ImageView) findViewById(R.id.cursor);

		news1 = (TextView) findViewById(R.id.news1);
		news2 = (TextView) findViewById(R.id.news2);
		news3 = (TextView) findViewById(R.id.news3);

		news1.setOnClickListener(this);
		news2.setOnClickListener(this);
		news3.setOnClickListener(this);

		lv1 = (ListView) view1.findViewById(R.id.listivew_news1);
		lv2 = (ListView) view2.findViewById(R.id.listivew_news2);
		lv3 = (ListView) view3.findViewById(R.id.listivew_news3);

		lv1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Toast.makeText(MainActivity.this, "第" + position + "个选项",Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MainActivity.this,NewsDetailActivity.class);
				intent.putExtra("newsId", newses1.get(position).getLink().substring(19));
				startActivity(intent);
			}
		});
		lv2.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Toast.makeText(MainActivity.this, "第" + position + "个选项",Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MainActivity.this,NewsDetailActivity.class);
				intent.putExtra("newsId", newses2.get(position).getLink().substring(19));
				startActivity(intent);
			}
		});
		lv3.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Toast.makeText(MainActivity.this, "第" + position + "个选项",Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MainActivity.this,NewsDetailActivity.class);
				intent.putExtra("newsId", newses3.get(position).getLink().substring(19));
				startActivity(intent);
			}
		});
		
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "第" + position + "个选项",Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MainActivity.this,NewsDetailActivity.class);
				intent.putExtra("newsId", imageList.get(position).getNewsLink().substring(19));
				startActivity(intent);
			}
		});
		
		lv1.setTextFilterEnabled(true);
		lv2.setTextFilterEnabled(true);
		lv3.setTextFilterEnabled(true);
		
		lv1.setAdapter(adapter1);
		lv2.setAdapter(adapter2);
		lv3.setAdapter(adapter3);
		gallery.setAdapter(imageAdapter);

		System.out.println("ddddddddd");
		lv1.setSelection(0);
		lv2.setSelection(0);
		lv3.setSelection(0);
	}

	/**
	 * 页面滑动适配器
	 */
	public class MyPagerAdapter extends PagerAdapter {

		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(vessel.get(arg1), 0);
			return vessel.get(arg1);
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(vessel.get(arg1));
		}

		public int getCount() {
			// Log.i(out, "count");
			return vessel.size();
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			// Log.i(out, "isviewFrom");
			return arg0 == (arg1);
		}

	}

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量

		@Override
		public void onPageSelected(int arg0) {
			/* arg0 ==1的时候表示正在滑动，arg0==2的时候表示滑动完毕了，arg0==0的时候表示什么都没做，就是停在那。 */
			Animation animation = new TranslateAnimation(one * currIndex, one
					* arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(100);
			cursor.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	/**
	 * 点击事件
	 */
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.news1:
			vp.setCurrentItem(0);
			break;
		case R.id.news2:
			vp.setCurrentItem(1);
			break;
		case R.id.news3:
			vp.setCurrentItem(2);
			break;
		case R.id.NavigateBack:
			this.finish();
			break;
		}
	}
}
