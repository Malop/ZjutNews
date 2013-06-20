package jh.app.zjutnews.dataservice;

import java.io.InputStream;
import java.util.ArrayList;

import jh.app.zjutnews.bean.ImageNewsBean;
import jh.app.zjutnews.bean.NewsBean;
import jh.app.zjutnews.bean.NewsDetailBean;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class XMLPaser {

	public ArrayList<NewsBean> getNewsData(InputStream inputStream) throws Exception{
		ArrayList<NewsBean> newses = null;
		NewsBean news = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inputStream,"UTF-8");
		
		int event = parser.getEventType();//产生第一个事件  
        while(event!=XmlPullParser.END_DOCUMENT){  
            switch(event){  
            case XmlPullParser.START_DOCUMENT://判断当前事件是否是文档开始事件  
                newses = new ArrayList<NewsBean>();//初始化newes集合  
                break;  
            case XmlPullParser.START_TAG://判断当前事件是否是标签元素开始事件  
                if("news".equals(parser.getName())){//判断开始标签元素是否是news,实例化对象  
                    news = new NewsBean();  
                }  
                if(news!=null){  
                    if("tittle".equals(parser.getName())){//判断开始标签元素是否是tittle  
                        news.setTittle(parser.nextText());
                    }else if("newslink".equals(parser.getName())){//判断开始标签元素是否是newlink  
                        news.setLink(parser.nextText());  
                    }else if("date".equals(parser.getName())){//判断开始标签元素是否是date  
                    	news.setTime(parser.nextText());
                    }
                }  
                break;  
            case XmlPullParser.END_TAG://判断当前事件是否是标签元素结束事件  
                if("news".equals(parser.getName())){//判断结束标签元素是否是news  
                    newses.add(news);//将news添加到newses集合  
                    news = null;  
                }  
                break;  
            }  
            event = parser.next();//进入下一个元素并触发相应事件  
        }//end while  
		
		return newses;
	}
	
	public NewsDetailBean getNewsDetail(InputStream inputStream) throws Exception{
		NewsDetailBean newsDetail = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inputStream,"UTF-8");
		
		int event = parser.getEventType();//产生第一个事件  
        while(event!=XmlPullParser.END_DOCUMENT){  
            switch(event){  
            /*case XmlPullParser.START_DOCUMENT://判断当前事件是否是文档开始事件  
                break;*/
            case XmlPullParser.START_TAG://判断当前事件是否是标签元素开始事件  
                if("news".equals(parser.getName())){//判断开始标签元素是否是news,实例化对象  
                	newsDetail = new NewsDetailBean();
                }  
                if(newsDetail!=null){  
                    if("tittle1".equals(parser.getName())){
                    	newsDetail.setTitle1(parser.nextText());
                    }else if("tittle2".equals(parser.getName())){
                    	newsDetail.setTitle2(parser.nextText());
                    }else if("content".equals(parser.getName())){
                    	newsDetail.setContent(parser.nextText());;
                    }
                }  
                break;  
           /* case XmlPullParser.END_TAG://判断当前事件是否是标签元素结束事件  
            	
                break;*/  
            }  
            event = parser.next();//进入下一个元素并触发相应事件  
        }
		
		return newsDetail;
	}
	
	public ArrayList<ImageNewsBean> getImageNewsData(InputStream inputStream) throws Exception{
		ArrayList<ImageNewsBean> imageNewses = null;
		ImageNewsBean imageNews = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inputStream,"UTF-8");
		
		int event = parser.getEventType();//产生第一个事件  
        while(event!=XmlPullParser.END_DOCUMENT){  
            switch(event){  
            case XmlPullParser.START_DOCUMENT://判断当前事件是否是文档开始事件  
            	imageNewses = new ArrayList<ImageNewsBean>();//初始化ImageNewsBean集合  
                break;  
            case XmlPullParser.START_TAG://判断当前事件是否是标签元素开始事件  
                if("news".equals(parser.getName())){//判断开始标签元素是否是news,实例化对象  
                	imageNews = new ImageNewsBean();  
                }  
                if(imageNews!=null){  
                    if("tittle".equals(parser.getName())){//判断开始标签元素是否是tittle  
                    	imageNews.setTittle(parser.nextText());
                    }else if("imagelink".equals(parser.getName())){//判断开始标签元素是否是newlink  
                    	imageNews.setImageLink(parser.nextText());  
                    }else if("newslink".equals(parser.getName())){//判断开始标签元素是否是date  
                    	imageNews.setNewsLink(parser.nextText());
                    }
                }  
                break;  
            case XmlPullParser.END_TAG://判断当前事件是否是标签元素结束事件  
                if("news".equals(parser.getName())){//判断结束标签元素是否是news  
                	imageNewses.add(imageNews);//将news添加到newses集合  
                    imageNews = null;  
                }  
                break;  
            }  
            event = parser.next();//进入下一个元素并触发相应事件  
        }//end while  
		
		return imageNewses;
	}
}
