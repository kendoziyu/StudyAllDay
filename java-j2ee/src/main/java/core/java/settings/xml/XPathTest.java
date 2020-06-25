package core.java.settings.xml;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;

/**
 * 描述: Xpath 表达式 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/6/25 0025 <br>
 */
public class XPathTest {
    Document root;
    XPath path;

    @Before
    public void init() throws ParserConfigurationException, IOException, SAXException {
        XPathFactory factory = XPathFactory.newInstance();
        path = factory.newXPath();
        DocumentBuilderFactory domBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = domBuilderFactory.newDocumentBuilder();
        root = domBuilder.parse(ClassLoader.getSystemClassLoader().getResourceAsStream("xml/sample.xml"));
    }

    @Test
    public void nodeList() throws IOException, SAXException, XPathExpressionException {
        NodeList nodes = (NodeList) path.evaluate("/configuration/title", root, XPathConstants.NODESET);

        Node node = (Node) path.evaluate("/configuration/title/font", root, XPathConstants.NODE);
        System.out.println(node.getNodeName());

        String value = path.evaluate("/configuration/title/font/name", root);
        System.out.println(value);

        int size = ((Number) path.evaluate("/configuration/title/font/size", root, XPathConstants.NUMBER)).intValue();
        System.out.println(size);

        int imgCount = ((Number) path.evaluate("count(/configuration/body/image)", root, XPathConstants.NUMBER)).intValue();
        System.out.println(imgCount);
    }
}
