package jh.app.zjutnews;

 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	private List<HashMap<String, Object>> items;
	private LayoutInflater inflater;
	
	public ListViewAdapter(Context context, ArrayList<HashMap<String, Object>> items) {
		this.items = items;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item, null);
			listItemView = new ListItemView();
			
			//获取控件对象
			listItemView.tittle = (TextView) convertView.findViewById(R.id.tittle);
			listItemView.time = (TextView) convertView.findViewById(R.id.time);
			listItemView.go = (TextView) convertView.findViewById(R.id.go);
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else{
			listItemView = (ListItemView) convertView.getTag();
		}
		//设置文字和图片
		listItemView.tittle.setText(items.get(position).get("TITTLE").toString());
		listItemView.time.setText(items.get(position).get("TIME").toString());
		listItemView.go.setBackgroundResource(R.drawable.go);	
		
		return convertView;
	}
	
	/**
	 * @param item
	 */
	public void addItem(HashMap<String, Object> item) {
		items.add(item);
	}
	
	static class ListItemView{
		public TextView tittle;
		public TextView time;
		public TextView go;
	}
}