package linxin.www.test.dom;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by RainBowAndStar on 2018/4/4.
 */

public class DomHelper {

    public static ArrayList<App> queryXML(Context context)
    {
        ArrayList<App> apps = new ArrayList<App>();
        try {
            //①获得DOM解析器的工厂示例:
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            //②从Dom工厂中获得dom解析器
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            //③把要解析的xml文件读入Dom解析器
            Document doc = dbBuilder.parse(context.getAssets().open("TestXML"));
            System.out.println("处理该文档的DomImplemention对象=" + doc.getImplementation());
            //④得到文档中名称为apps的元素的结点列表
            NodeList nList = doc.getElementsByTagName("app");      //TODO 这里不用一层层获取，不用获取到apps然后再获取app
            //⑤遍历该集合,显示集合中的元素以及子元素的名字
            for(int i = 0;i < nList.getLength();i++)
            {
                //先从元素开始解析
                Element personElement = (Element) nList.item(i);
                App app = new App();
           //     app.setId(Integer.valueOf(personElement.getAttribute("id")));

                //获取person下的name和age的Note集合
                NodeList childNoList = personElement.getChildNodes();        //3
                for(int j = 0;j < childNoList.getLength();j++)
                {
                    Node childNode = childNoList.item(j);
                    //判断子note类型是否为元素Note
                    if(childNode.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element childElement = (Element) childNode;
                        if("id".equals(childElement.getNodeName()))
                            app.setId(childElement.getFirstChild().getNodeValue());
                        else if("name".equals(childElement.getNodeName()))
                            app.setName(childElement.getFirstChild().getNodeValue());
                        else if("version".equals(childElement.getNodeName())){
                            app.setVersion(childElement.getFirstChild().getNodeValue());
                        }
                    }
                }
                apps.add(app);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int k = 0 ;k<apps.size();k++){
            Log.d("Dom",apps.get(k).getId());
            Log.d("Dom",apps.get(k).getName());
            Log.d("Dom",apps.get(k).getVersion());
        }
        return apps;
    }
}
