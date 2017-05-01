import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException,
            ParserConfigurationException, SAXException, XPathExpressionException {

        ArrayList<String> obj = new ArrayList<String>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("Tags.xml");
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("//tags/row[@TagName]");
        NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nl.getLength(); i++) {
            Node currentItem = nl.item(i);
            String key = currentItem.getAttributes().getNamedItem("TagName").getNodeValue();
            obj.add(key);
            // System.out.println(key);
        }


        Properties props = new Properties();

        props.setProperty("annotators", "tokenize, ssplit, pos");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation annotation = new Annotation("This is a project on mysql and java");
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                System.out.println(word + "/" + pos);
            }
        }
    }
    // System.out.println(obj);

}
