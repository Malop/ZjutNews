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
		
		int event = parser.getEventType();//������һ���¼�  
        while(event!=XmlPullParser.END_DOCUMENT){  
            switch(event){  
            case XmlPullParser.START_DOCUMENT://�жϵ�ǰ�¼��Ƿ����ĵ���ʼ�¼�  
                newses = new ArrayList<NewsBean>();//��ʼ��newes����  
                break;  
            case XmlPullParser.START_TAG://�жϵ�ǰ�¼��Ƿ��Ǳ�ǩԪ�ؿ�ʼ�¼�  
                if("news".equals(parser.getName())){//�жϿ�ʼ��ǩԪ���Ƿ���news,ʵ��������  
                    news = new NewsBean();  
                }  
                if(news!=null){  
                    if("tittle".equals(parser.getName())){//�жϿ�ʼ��ǩԪ���Ƿ���tittle  
                        news.setTittle(parser.nextText());
                    }else if("newslink".equals(parser.getName())){//�жϿ�ʼ��ǩԪ���Ƿ���newlink  
                        news.setLink(parser.nextText());  
                    }else if("date".equals(parser.getName())){//�жϿ�ʼ��ǩԪ���Ƿ���date  
                    	news.setTime(parser.nextText());
                    }
                }  
                break;  
            case XmlPullParser.END_TAG://�жϵ�ǰ�¼��Ƿ��Ǳ�ǩԪ�ؽ����¼�  
                if("news".equals(parser.getName())){//�жϽ�����ǩԪ���Ƿ���news  
                    newses.add(news);//��news��ӵ�newses����  
                    news = null;  
                }  
                break;  
            }  
            event = parser.next();//������һ��Ԫ�ز�������Ӧ�¼�  
        }//end while  
		
		return newses;
	}
	
	public NewsDetailBean getNewsDetail(InputStream inputStream) throws Exception{
		NewsDetailBean newsDetail = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inputStream,"UTF-8");
		
		int event = parser.getEventType();//������һ���¼�  
        while(event!=XmlPullParser.END_DOCUMENT){  
            switch(event){  
            /*case XmlPullParser.START_DOCUMENT://�жϵ�ǰ�¼��Ƿ����ĵ���ʼ�¼�  
                break;*/
            case XmlPullParser.START_TAG://�жϵ�ǰ�¼��Ƿ��Ǳ�ǩԪ�ؿ�ʼ�¼�  
                if("news".equals(parser.getName())){//�жϿ�ʼ��ǩԪ���Ƿ���news,ʵ��������  
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
           /* case XmlPullParser.END_TAG://�жϵ�ǰ�¼��Ƿ��Ǳ�ǩԪ�ؽ����¼�  
            	
                break;*/  
            }  
            event = parser.next();//������һ��Ԫ�ز�������Ӧ�¼�  
        }
		
		return newsDetail;
	}
	
	public ArrayList<ImageNewsBean> getImageNewsData(InputStream inputStream) throws Exception{
		ArrayList<ImageNewsBean> imageNewses = null;
		ImageNewsBean imageNews = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inputStream,"UTF-8");
		
		int event = parser.getEventType();//������һ���¼�  
        while(event!=XmlPullParser.END_DOCUMENT){  
            switch(event){  
            case XmlPullParser.START_DOCUMENT://�жϵ�ǰ�¼��Ƿ����ĵ���ʼ�¼�  
            	imageNewses = new ArrayList<ImageNewsBean>();//��ʼ��ImageNewsBean����  
                break;  
            case XmlPullParser.START_TAG://�жϵ�ǰ�¼��Ƿ��Ǳ�ǩԪ�ؿ�ʼ�¼�  
                if("news".equals(parser.getName())){//�жϿ�ʼ��ǩԪ���Ƿ���news,ʵ��������  
                	imageNews = new ImageNewsBean();  
                }  
                if(imageNews!=null){  
                    if("tittle".equals(parser.getName())){//�жϿ�ʼ��ǩԪ���Ƿ���tittle  
                    	imageNews.setTittle(parser.nextText());
                    }else if("imagelink".equals(parser.getName())){//�жϿ�ʼ��ǩԪ���Ƿ���newlink  
                    	imageNews.setImageLink(parser.nextText());  
                    }else if("newslink".equals(parser.getName())){//�жϿ�ʼ��ǩԪ���Ƿ���date  
                    	imageNews.setNewsLink(parser.nextText());
                    }
                }  
                break;  
            case XmlPullParser.END_TAG://�жϵ�ǰ�¼��Ƿ��Ǳ�ǩԪ�ؽ����¼�  
                if("news".equals(parser.getName())){//�жϽ�����ǩԪ���Ƿ���news  
                	imageNewses.add(imageNews);//��news��ӵ�newses����  
                    imageNews = null;  
                }  
                break;  
            }  
            event = parser.next();//������һ��Ԫ�ز�������Ӧ�¼�  
        }//end while  
		
		return imageNewses;
	}
}
