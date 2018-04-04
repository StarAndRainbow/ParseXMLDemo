package linxin.www.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;

import linxin.www.test.dom.DomHelper;
import linxin.www.test.sax.ContentHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  getData();
        //TODO Dom
        DomHelper.queryXML(this);
    }





    private void getData() {
        StringBuffer sb = new StringBuffer();
        try {
            InputStream inputStream= getAssets().open("TestXML");
            byte[] bytes = new byte[1024];
            if(inputStream.read(bytes)!= 0 ){
                sb.append(new String (bytes,0,bytes.length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       // paraseXMLWithSAX(sb.toString().trim());
        parseXMLWithPull(sb.toString().trim());
    }




    //TODO  用Pull方式解析XML
    private void parseXMLWithPull(String xmlData){
        String TAG ="PULL";
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            //设置输入的内容
            xmlPullParser.setInput(new StringReader(xmlData));
            //获取当前解析事件，返回的是数字
            int eventType = xmlPullParser.getEventType();
            //保存内容
            String id = "";
            String name = "";
            String version="";

            while (eventType != (XmlPullParser.END_DOCUMENT)){
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    //开始解析XML
                    case XmlPullParser.START_TAG:{
                        //nextText()用于获取结点内的具体内容
                        if("id".equals(nodeName))
                            id = xmlPullParser.nextText();
                        else if("name".equals(nodeName))
                            name = xmlPullParser.nextText();
                        else if("version".equals(nodeName))
                            version = xmlPullParser.nextText();
                    } break;
                    //结束解析
                    case XmlPullParser.END_TAG:{
                        if("app".equals(nodeName)){
                            Log.d(TAG, "parseXMLWithPull: id is "+ id);
                            Log.d(TAG, "parseXMLWithPull: name is "+ name);
                            Log.d(TAG, "parseXMLWithPull: version is "+ version);
                        }
                    } break;
                    default: break;
                }
                //下一个
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //TODO 用SAX方式解析XML
    private void paraseXMLWithSAX(String xmlData){
        Log.d("SAX", "paraseXMLWithSAX: "+xmlData);
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader reader = factory.newSAXParser().getXMLReader();
            ContentHandler handler = new ContentHandler();
            //将contentHandler的实例设置到XMLReader中
            reader.setContentHandler(handler);
            //开始解析
            InputSource is = new InputSource(new StringReader(xmlData));
            is.setEncoding("ISO-8859-15");
            reader.parse(is);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
