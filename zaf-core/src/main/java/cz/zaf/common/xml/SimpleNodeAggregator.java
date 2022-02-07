package cz.zaf.common.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SimpleNodeAggregator
    implements NodeAggregator
{
    private final List<Element> nodes = new ArrayList<>();
    
    private final String nodeName;
    
    public SimpleNodeAggregator(final String nodeName) {
        this.nodeName = nodeName;
    }
    
    public String getNodeName() {
        return nodeName;
    }
    
    public List<Element> getNodes() {
        return nodes;
    }

    @Override
    public void visitNode(Node node) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            return;
        }
        Element element = (Element) node;
        String nodeName = node.getNodeName();
        if(this.nodeName.equals(nodeName)) {
            nodes.add(element);
        }
    }

    @Override
    public void onFinished() {
        // nop
    }

}
