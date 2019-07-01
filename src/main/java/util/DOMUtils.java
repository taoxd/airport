package util;


import config.Constant;
import config.Menu;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * @Description: 基于dom4j的工具包
 * @Author: taoxudong
 * @CreateDate: 2019/6/26 16:05
 * @Version: 1.0
 */
public class DOMUtils {

    /**
     * 根据xml文件路径取得document对象
     *
     * @param xmlPath
     * @return
     * @throws DocumentException
     */
    public static Document getDocument(String xmlPath) {
        if (xmlPath == null || xmlPath.equals(""))
            return null;

        File file = new File(xmlPath);
        if (!file.exists()) {
            return createEmptyXmlFile(xmlPath);
        }

        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(xmlPath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 初始化没有跟节点
     * @param xmlPath
     * @return
     */
    public static Document getDocumentTemplate(String xmlPath) {
        if (xmlPath == null || xmlPath.equals(""))
            return null;

        File file = new File(xmlPath);
        if (!file.exists()) {
            return createTemplateEmptyXmlFile(xmlPath);
        }

        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(xmlPath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 通过xml字符获取document文档
     *
     * @param xmlstr 要序列化的xml字符
     * @return 返回文档对象
     * @throws DocumentException
     */
    public static Document getXMLByString(String xmlstr) throws DocumentException {
        if (xmlstr == "" || xmlstr == null) {
            return null;
        }
        Document document = DocumentHelper.parseText(xmlstr);
        return document;
    }


    /**
     * 根据xml英文名获取中文xml
     * @param engXMLName 英文xml
     * @return 中文xml
     */
    public static String getChnXMLName(String engXMLName){
        Document document = getDocument(Constant.TEMP_PATH + Constant.TEMP_FILE);
        Element resourceElement = (Element)document.selectSingleNode("//resource[@engXML='" + engXMLName + "']");
       return resourceElement.attributeValue("chnXML");
    }


    /**
     * 根据中文xml获取英文xml
     * @param chnXMLName 中文xml
     * @return 英文xml
     */
    public static String getEngXMLName(String chnXMLName){
        Document document = getDocument(Constant.TEMP_PATH + Constant.TEMP_FILE);
        Element resourceElement = (Element)document.selectSingleNode("//resource[@chnXML='" + chnXMLName + "']");
       return resourceElement.attributeValue("engXML");
    }






    /**
     * 获取某个元素的所有的子节点
     *
     * @param node 制定节点
     * @return 返回所有的子节点
     */
    public static List<Element> getChildElements(Element node) {
        if (null == node) {
            return null;
        }
        @SuppressWarnings("unchecked")
        List<Element> lists = node.elements();
        return lists;
    }

    /**
     * 获取指定节点的指定子节点
     *
     * @param node      父节点
     * @param childnode 指定名称的子节点
     * @return 返回指定的子节点
     */
    public static Element getChildElement(Element node, String childnode) {
        if (null == node || null == childnode || "".equals(childnode)) {
            return null;
        }
        return node.element(childnode);
    }

    /**
     * @param parent
     * @param childName
     * @方法功能描述: 根据子节点名称得到指定的子节点列表
     * @方法名:getChildElement
     * @返回类型：Element
     * @时间：2011-4-14上午11:18:03
     */
    @SuppressWarnings("unchecked")
    public static List<Element> getChildElements(Element parent, String childName) {
        childName = childName.trim();
        if (parent == null)
            return null;
        childName += "//";
        List<Element> childElements = parent.selectNodes(childName);
        return childElements;
    }

    /**
     * 获取子节点，子节点不能为空，否则抛错
     *
     * @param parent 父节点
     * @param tag    想要获取的子节点
     * @return 子节点
     * @throws ParseMessageException
     */
    public final static Element elementNotNull(Element parent, String tag) {
        Element e = parent.element(tag);
        if (e == null)
            throw new NullPointerException("节点为空");
        else
            return e;
    }


    /**
     * @param node
     * @return @参数描述 :
     * @方法功能描述：TODO
     * @方法名:getChildList
     * @返回类型：List<Element>
     */
    public static List<Element> getChildList(Element node) {
        if (node == null)
            return null;
        Iterator<Element> itr = getIterator(node);
        if (itr == null)
            return null;
        List<Element> childList = new ArrayList<Element>();
        while (itr.hasNext()) {
            Element kidElement = itr.next();
            if (kidElement != null) {
                childList.add(kidElement);
            }
        }
        return childList;
    }

    /**
     * @param parent
     * @param nodeNodeName
     * @return @参数描述 : 父节点，子节点名称
     * @方法功能描述 : 查询没有子节点的节点，使用xpath方式
     * @方法名:getSingleNode
     * @返回类型：Node
     */
    public static Node getSingleNode(Element parent, String nodeNodeName) {
        nodeNodeName = nodeNodeName.trim();
        String xpath = "//";
        if (parent == null)
            return null;
        if (nodeNodeName == null || nodeNodeName.equals(""))
            return null;
        xpath += nodeNodeName;
        Node kid = parent.selectSingleNode(xpath);
        return kid;
    }

    /**
     * @param parent
     * @param childName
     * @return @参数描述 :
     * @方法功能描述：根据属性名，属性值，得到子节点，不使用xpath
     * @方法名:getChild
     * @返回类型：Element
     */
    @SuppressWarnings("rawtypes")
    public static Element getChild(Element parent, String childName, String attribute, String value) {
        childName = childName.trim();
        if (parent == null)
            return null;
        if (childName == null || childName.equals(""))
            return null;
        Element e = null;
        Iterator<Element> it = getIterator(parent);
        while (it != null && it.hasNext()) {
            Element k = it.next();
            if (k == null) continue;
            if (k.getName().equalsIgnoreCase(childName) && value.equalsIgnoreCase(k.attributeValue(attribute))) {
                e = k;
                break;
            }
        }
        return e;
    }

    /**
     * @param xmlFilePath
     * @return @参数描述 :
     * 遍历根节点下，没有子节点的元素节点，并将此节点的text值放入map中返回
     * @方法名:getSingleNodeText
     * @返回类型：Map<String,String>
     */
    public static Map<String, String> getSingleNodeText(String xmlFilePath) {
        xmlFilePath = xmlFilePath.trim();
        if (xmlFilePath == null || xmlFilePath.equals("")) {
            return null;
        }
        Element rootElement = getRootNode(xmlFilePath);
        if (rootElement == null || !hasChild(rootElement)) {
            return null;
        }
        return getSingleNodeText(rootElement);
    }

    /**
     * @param xmlFileName
     * @param nodeName
     * @return @参数描述 :
     * 根据xml文件名路径和指定的节点名称得到指定节点所有子节点的所有属性集合
     * @方法名:getNameNodeAllAttributeMap
     * @返回类型：Map<Integer,Object>
     */
    @SuppressWarnings("unchecked")
    public static <T> T getNameNodeAllAttributeMap(String xmlFilePath, String nodeName, Flag flag) {
        nodeName = nodeName.trim();
        Map<String, String> allAttrMap = null;
        Map<Integer, Map<String, String>> mostKidsAllAttriMap = new HashMap<Integer, Map<String, String>>();
        if (xmlFilePath == null || nodeName == null || xmlFilePath.equals("") || nodeName.equals(""))
            return null;
        switch (flag) {
            case one:
                Element nameNode = getNameNode(xmlFilePath, nodeName, Flag.one);
                allAttrMap = getNodeAttrMap(nameNode);
                return (T) allAttrMap;
            case more:
                List<Element> nameKidsElements = getNameNode(xmlFilePath, nodeName, Flag.more);
                for (int i = 0; i < nameKidsElements.size(); i++) {
                    Element kid = nameKidsElements.get(i);
                    allAttrMap = getNodeAttrMap(kid);
                    mostKidsAllAttriMap.put(i, allAttrMap);
                }
                return (T) mostKidsAllAttriMap;
        }
        return null;
    }

    /**
     * @param element @参数描述 :
     * @方法功能描述:遍历指定的节点下所有的节点
     * @方法名:ransack
     * @返回类型：void
     */
    public static List<Element> ransack(Element element, List<Element> allkidsList) {
        if (element == null)
            return null;
        if (hasChild(element)) {
            List<Element> kids = getChildList(element);
            for (Element e : kids) {
                allkidsList.add(e);
                ransack(e, allkidsList);
            }
        }
        return allkidsList;
    }

    /**
     * @param element
     * @param nodeName
     * @return @参数描述 :
     * 得到指定节点下的指定节点集合
     * @方法名:getNameElement
     * @返回类型：Element
     */
    public static List<Element> getNameElement(Element element, String nodeName) {
        nodeName = nodeName.trim();
        List<Element> kidsElements = new ArrayList<Element>();
        if (element == null)
            return null;
        if (nodeName == null || nodeName.equals(""))
            return null;
        List<Element> allKids = ransack(element, new ArrayList<Element>());
        if (allKids == null)
            return null;
        for (int i = 0; i < allKids.size(); i++) {
            Element kid = allKids.get(i);
            if (nodeName.equals(kid.getName()))
                kidsElements.add(kid);
        }
        return kidsElements;
    }

    /**
     * 获取指定节点的所有的属性值(属性数组)
     *
     * @param node
     * @param arg
     * @return
     */
    public static Map<String, String> getAttributes(Element node, String... arg) {
        if (node == null || arg.length == 0) {
            return null;
        }
        Map<String, String> attrMap = new HashMap<String, String>();
        for (String attr : arg) {
            String attrValue = node.attributeValue(attr);
            attrMap.put(attr, attrValue);
        }
        return attrMap;
    }

    /**
     * 获取指定节点的单个属性
     *
     * @param node 需要获取属性的节点对象
     * @param attr 需要获取的属性值
     * @return 返回属性的值
     */
    public static String getAttribute(Element node, String attr) {
        if (null == node || attr == null || "".equals(attr)) {
            return "";
        }
        return node.attributeValue(attr);
    }

    /**
     * 获取element对象的text的值
     *
     * @param e   节点的对象
     * @param tag 节点的tag
     * @return
     */
    public final static String getText(Element e, String tag) {
        Element _e = e.element(tag);
        if (_e != null)
            return _e.getText();
        else
            return null;
    }

    /**
     * 获取element对象去除空格的text的值
     *
     * @param e
     * @param tag
     * @return
     */
    public final static String getTextTrim(Element e, String tag) {
        Element _e = e.element(tag);
        if (_e != null)
            return _e.getTextTrim();
        else
            return null;
    }

    /**
     * 获取节点的值.节点必须不能为空，否则抛错
     *
     * @param parent 父节点
     * @param tag    想要获取的子节点
     * @return 返回子节点
     * @throws ParseMessageException
     */
    public final static String getTextTrimNotNull(Element parent, String tag) {
        Element e = parent.element(tag);
        if (e == null)
            throw new NullPointerException("节点为空");
        else
            return e.getTextTrim();
    }

    /**
     * @param e
     * @return 节点属性的list集合
     * @方法功能描述：遍历指定节点的所有属性
     * @方法名:getAttributeList
     * @返回类型：List<Attribute>
     */
    public static List<Attribute> getAttributeList(Element e) {
        if (e == null)
            return null;
        List<Attribute> attributeList = new ArrayList<Attribute>();
        Iterator<Attribute> atrIterator = getAttrIterator(e);
        if (atrIterator == null)
            return null;
        while (atrIterator.hasNext()) {
            Attribute attribute = atrIterator.next();
            attributeList.add(attribute);
        }
        return attributeList;
    }

    /**
     * @param e
     * @param attrName
     * @方法功能描述:获取指定节点指定属性的值
     * @方法名:attrValue
     * @返回类型：String
     * @时间：2011-4-14下午02:36:48
     */
    public static String attrValue(Element e, String attrName) {
        attrName = attrName.trim();
        if (e == null)
            return null;
        if (attrName == null || attrName.equals(""))
            return null;
        return e.attributeValue(attrName);
    }

    /**
     * @return 属性集合
     * 得到指定节点的所有属性及属性值
     * @方法名:getNodeAttrMap
     * @返回类型：Map<String,String>
     */
    public static Map<String, String> getNodeAttrMap(Element e) {
        Map<String, String> attrMap = new HashMap<String, String>();
        if (e == null) {
            return null;
        }
        List<Attribute> attributes = getAttributeList(e);
        if (attributes == null) {
            return null;
        }
        for (Attribute attribute : attributes) {
            String attrValueString = attrValue(e, attribute.getName());
            attrMap.put(attribute.getName(), attrValueString);
        }
        return attrMap;
    }

    /**
     * @param e
     * @return @参数描述 :
     * 遍历指定节点的下没有子节点的元素的text值
     * @方法名:getSingleNodeText
     * @返回类型：Map<String,String>
     */
    public static Map<String, String> getSingleNodeText(Element e) {
        Map<String, String> map = new HashMap<String, String>();
        if (e == null)
            return null;
        List<Element> kids = getChildList(e);
        for (Element e2 : kids) {
            if (e2.getTextTrim() != null) {
                map.put(e2.getName(), e2.getTextTrim());
            }
        }
        return map;
    }

    /**
     * @param xmlFilePath
     * @param tagName
     * @param flag        : 指定元素的个数
     * @方法功能描述:根据xml路径和指定的节点的名称，得到指定节点,从根节点开始找
     * @方法名:getNameNode
     * @返回类型：Element 指定的节点
     * @时间：2011-4-15下午12:22:35
     */

    public enum Flag {one, more}

    @SuppressWarnings("unchecked")
    public static <T> T getNameNode(String xmlFilePath, String tagName, Flag flag) {
        xmlFilePath = xmlFilePath.trim();
        tagName = tagName.trim();
        if (xmlFilePath == null || tagName == null || xmlFilePath.equals("") || tagName.equals(""))
            return null;
        Element rootElement = getRootNode(xmlFilePath);
        if (rootElement == null)
            return null;
        List<Element> tagElementList = getNameElement(rootElement, tagName);
        if (tagElementList == null)
            return null;
        switch (flag) {
            case one:
                return (T) tagElementList.get(0);
        }
        return (T) tagElementList;
    }

    /**
     * @param e
     * @return @参数描述 :
     * 得到指定节点下所有子节点的属性集合
     * @方法名:getNameNodeAllAttributeMap
     * @返回类型：Map<Integer,Object>
     */
    public static List<Map<String, String>> getNameNodeAllKidsAttributeMap(Element parent) {
        List<Map<String, String>> list = new ArrayList<>();

        if (parent == null)
            return null;
        List<Element> childlElements = getChildList(parent);
        if (childlElements == null)
            return null;
        for (int i = 0; i < childlElements.size(); i++) {
            Element childElement = childlElements.get(i);
            Map<String, String> attrMap = getNodeAttrMap(childElement);
            list.add(attrMap);
        }
        return list;
    }

    /**
     * 为指定节点添加孩子节点元素，并可以设值
     *
     * @param parent     父节点
     * @param childName  孩子节点名称
     * @param childValue 孩子节点值
     * @return 新增节点
     */
    public static Element addChild(Element parent, String childName, String childValue) {
        Element child = parent.addElement(childName);// 添加节点元素
        child.setText(childValue == null ? "" : childValue); // 为元素设值
        return child;
    }

    /**
     * DOM4j的Document对象转为XML报文串
     *
     * @param document
     * @param charset
     * @return 经过解析后的xml字符串
     */
    public static String documentToString(Document document, String charset) {
        StringWriter stringWriter = new StringWriter();
        OutputFormat format = OutputFormat.createPrettyPrint();// 获得格式化输出流
        format.setEncoding(charset);// 设置字符集,默认为UTF-8
        XMLWriter xmlWriter = new XMLWriter(stringWriter, format);// 写文件流
        try {
            xmlWriter.write(document);
            xmlWriter.flush();
            xmlWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringWriter.toString();
    }

    /**
     * 去掉声明头的(即<?xml...?>去掉)XML报文串
     *
     * @param document
     * @param charset
     * @return
     */
    public static String documentToStringNoDeclaredHeader(Document document, String charset) {
        String xml = documentToString(document, charset);
        return xml.replaceFirst("\\s*<[^<>]+>\\s*", "");
    }


    /**
     * @param xmlPath
     * @return
     * @throws DocumentException @参数描述 :
     * @方法功能描述: 根据路径直接拿到根节点
     * @方法名:getRootElement
     * @返回类型：Element
     * @时间：2011-4-14下午03:01:14
     */
    public static Element getRootNode(String xmlPath) {
        if (xmlPath == null || (xmlPath.trim()).equals(""))
            return null;
        Document document = getDocument(xmlPath);
        if (document == null)
            return null;
        return getRootNode(document);
    }

    /**
     * @param DOC对象
     * @方法功能描述：根据Document得到根节点
     * @方法名:getRootEleme
     * @返回类型：Element
     * @时间：2011-4-8下午12:54:02
     */
    public static Element getRootNode(Document document) {
        if (document == null)
            return null;

        Element root = document.getRootElement();
        return root;
    }


    /**
     * 将文档对象写入对应的文件中
     *
     * @param document 文档对象
     * @param path     写入文档的路径
     * @throws IOException
     */
    public final static void writeXMLToFile(Document document, String path) throws IOException {
        if (document == null || path == null) {
            return;
        }
        //OutputFormat format=OutputFormat.createCompactFormat();  //紧凑格式:去除空格换行
        OutputFormat format = OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
        XMLWriter writer = new XMLWriter(new FileWriter(path), format);
        writer.write(document);
        writer.close();
    }

    /**
     * @param xmlPath 生成空的xml文件头，resources根节点
     * @方法名:createEmptyXmlFile
     * @返回类型：Document
     */
    public static Document createEmptyXmlFile(String xmlPath) {
        if (xmlPath == null || xmlPath.equals(""))
            return null;

        XMLWriter output;
        Document document = DocumentHelper.createDocument();
        document.addElement("resources");
        OutputFormat format = OutputFormat.createPrettyPrint();
        try {
            output = new XMLWriter(new FileWriter(xmlPath), format);
            output.write(document);
            output.close();
        } catch (IOException e) {
            return null;
        }
        return document;
    }

    public static Document createTemplateEmptyXmlFile(String xmlPath) {
        if (xmlPath == null || xmlPath.equals(""))
            return null;

        XMLWriter output;
        Document document = DocumentHelper.createDocument();
        OutputFormat format = OutputFormat.createPrettyPrint();
        try {
            output = new XMLWriter(new FileWriter(xmlPath), format);
            output.write(document);
            output.close();
        } catch (IOException e) {
            return null;
        }
        return document;
    }


    /**
     * @param parent
     * @方法功能描述:得到指定元素的迭代器
     * @方法名:getIterator
     * @返回类型：Iterator<Element>
     * @时间：2011-4-14上午11:29:18
     */
    @SuppressWarnings("unchecked")
    public static Iterator<Element> getIterator(Element parent) {
        if (parent == null)
            return null;
        Iterator<Element> iterator = parent.elementIterator();
        return iterator;
    }

    /**
     * @param e
     * @方法功能描述：判断节点是否还有子节点
     * @方法名:hasChild
     * @返回类型：boolean
     */
    public static boolean hasChild(Element e) {
        if (e == null)
            return false;
        return e.hasContent();
    }

    /**
     * @param e
     * @方法功能描述：得到指定节点的属性的迭代器
     * @方法名:getAttrIterator
     * @返回类型：Iterator<Attribute>
     * @时间：2011-4-14下午01:42:38
     */
    @SuppressWarnings("unchecked")
    public static Iterator<Attribute> getAttrIterator(Element e) {
        if (e == null)
            return null;
        Iterator<Attribute> attrIterator = e.attributeIterator();
        return attrIterator;
    }


    /**
     * @param element
     * @方法功能描述:验证节点是否唯一
     * @方法名:validateSingle
     * @返回类型：int 节点唯一返回1, 节点不唯一返回大于一的整型数据
     */
    public static int validateSingle(Element element) {
        int j = 1;
        if (element == null)
            return j;
        Element parent = element.getParent();
        List<Element> kids = getChildList(parent);
        for (Element kid : kids) {
            if (element.equals(kid))
                j++;
        }
        return j;
    }

}
