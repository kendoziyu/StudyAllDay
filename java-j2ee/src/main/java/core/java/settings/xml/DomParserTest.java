package core.java.settings.xml;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * 描述: DOM 解析器 <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/6/25 0025 <br>
 */
public class DomParserTest {

    DocumentBuilder builder;

    @Before
    public void init() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
    }

    @Test
    public void parseFile() throws IOException, SAXException {
        Document document = builder.parse(new File("src/main/java/core/java/settings/xml/sample.xml"));
    }

    @Test
    public void parseURL() throws IOException, SAXException {
        Document document = builder.parse("D:\\gitcode\\github\\StudyAllDay\\java-j2ee\\src\\main\\java\\core\\java\\settings\\xml\\sample.xml");
        Element root = document.getDocumentElement();

        iterateChildNodes(root, 0);
    }

    @Test
    public void parseInputStream() throws IOException, SAXException {
        Document document = builder.parse(DomParserTest.class.getClassLoader().getResourceAsStream("xml/sample.xml"));
        Element root = document.getDocumentElement();

        iterateChildNodes2(root, 0);
    }

    /**
     * 递归遍历子元素的节点列表
     * @param node
     * @param queryStack
     */
    void iterateChildNodes(Node node, int queryStack) {
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (queryStack > 0) {
                for (int tab = 0; tab < queryStack; tab++) {
                    System.out.print("  ");
                }
            }
            System.out.println(item);
            if (item.hasChildNodes()) {
                iterateChildNodes(item, ++queryStack);
            }
        }
    }

    /**
     * 逐个遍历第一个子元素节点的兄弟节点
     * @param node
     * @param queryStack
     */
    void iterateChildNodes2(Node node, int queryStack) {
        for (Node childNode = node.getFirstChild(); childNode != null; childNode = childNode.getNextSibling()) {
            if (queryStack > 0) {
                for (int tab = 0; tab < queryStack; tab++) {
                    System.out.print("  ");
                }
            }
            System.out.println(childNode);
            iterateChildNodes2(childNode, ++queryStack);

        }
    }


    @Test
    public void testAttr() throws IOException, SAXException {
        Document document = builder.parse(DomParserTest.class.getClassLoader().getResourceAsStream("xml/attributes.xml"));
        Element root = document.getDocumentElement();
        NamedNodeMap attributes = root.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node item = attributes.item(i);
            System.out.println(item.getNodeName());
            System.out.println(item.getNodeValue());
        }
    }

}
