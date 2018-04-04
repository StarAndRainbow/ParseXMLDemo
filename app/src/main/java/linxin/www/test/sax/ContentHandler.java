package linxin.www.test.sax;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by RainBowAndStar on 2018/4/3.
 */

public class ContentHandler extends DefaultHandler {
    private String nodeName;
    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;
    private String TAG = "SAX";

    //开始解析 XML 文档,一般做初始化操作
    @Override
    public void startDocument() throws SAXException {
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
    }
    //开始解析结点
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //localName 记录当前结点的名字
        nodeName = localName;
    }
    //开始提取结点中内容
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if("id".equals(nodeName))
            id.append(ch,start,length);
        else if("name".equals(nodeName))
            name.append(ch,start,length);
        else if("version".equals(nodeName))
            version.append(ch,start, length);
    }
    //结束结点解析
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if("app".equals(localName)){
            //可能包含回车或换行符
            Log.d(TAG, "endElement: "+nodeName);
            Log.d(TAG, "endElement: id = " + id.toString().trim());
            Log.d(TAG, "endElement: name = "+ name.toString().trim());
            Log.d(TAG, "endElement: version = "+ version.toString().trim());
            //打印结束后清空，否则会影响下一次内容的读取
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
    }
    //结束XML文档解析
    @Override
    public void endDocument() throws SAXException {
        Log.e(TAG, "endDocument: 文档结束" );
    }
}