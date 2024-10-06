package cz.zaf.common.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DFDocumentWalker {
    List<NodeAggregator> aggregators = new ArrayList<>();
    
    public void addAggregator(NodeAggregator aggregator) {
        aggregators.add(aggregator);
    }
    
    public void walk(Document doc) {
        List<Node> nodes = new ArrayList<>();
        nodes.add(doc);
        
        while(!nodes.isEmpty()) {
            Node node = nodes.remove(nodes.size()-1);
            // visit node
            for(NodeAggregator agg: aggregators) {
                agg.visitNode(node);
            }
                        
            NodeList chldNodes = node.getChildNodes();
            if(chldNodes.getLength()==0) {
                continue;
            }
            for(int i = chldNodes.getLength()-1; i>=0; i--) {
                Node n = chldNodes.item(i);
                short nt = n.getNodeType();
                if(nt==Node.ELEMENT_NODE) {                    
                    nodes.add(n); 
                }
            }
        }

        
        // Last node finished
        for(NodeAggregator agg: aggregators) {
            agg.onFinished();
        }
    }
    
}
