package cz.zaf.common.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

public class SimpleNodeAggregator
    implements NodeAggregator
{
    private final List<Node> nodes = new ArrayList<>();
    
    private final String nodeName;
    
    public SimpleNodeAggregator(final String nodeName) {
        this.nodeName = nodeName;
    }
    
    public String getNodeName() {
        return nodeName;
    }
    
    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public void visitNode(Node node) {
        String nodeName = node.getNodeName();
        if(this.nodeName.equals(nodeName)) {
            nodes.add(node);
        }
    }

    @Override
    public void onFinished() {
        // nop
    }

}
