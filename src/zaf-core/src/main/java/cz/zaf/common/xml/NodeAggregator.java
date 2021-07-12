package cz.zaf.common.xml;

import org.w3c.dom.Node;

public interface NodeAggregator {
    public void visitNode(Node node);
    
    public void onFinished();
}
