package jh.app.zjutnews;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import jh.app.zjutnews.bean.ImageNewsBean;

public class ImageAdapter extends BaseAdapter{

	private List<ImageNewsBean> imageList;
	private LayoutInflater inflater;
	
	public ImageAdapter(Context context,List<ImageNewsBean> imageList){
		this.imageList = imageList;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ItemView itemView;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_page, null);
			itemView = new ItemView();
			itemView.tv_des = (TextView)convertView.findViewById(R.id.tv_des);
			itemView.imageView1 = (ImageView)convertView.findViewById(R.id.imageView1);
			convertView.setTag(itemView);
		}else{
			itemView = (ItemView)convertView.getTag();
		}
		itemView.tv_des.setText(imageList.get(position).getTittle());
		itemView.imageView1.setImageBitmap(imageList.get(position).getBitmap());
		return convertView;
	}
	
	private static class ItemView{
		ImageView imageView1;
		TextView tv_des;
	}
}
