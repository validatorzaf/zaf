package cz.zaf.sipvalidator.nsesss2017;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import cz.zaf.common.xml.SimpleNodeAggregator;

public class NamedNodeAggregator extends SimpleNodeAggregator {

    private Map<String, List<Node>> nodeQueryCache;

    public NamedNodeAggregator(final String nodeName, 
                               final Map<String, List<Node>> nodeQueryCache) {
        super(nodeName);
        this.nodeQueryCache = nodeQueryCache;
    }
    
    @Override
    public void onFinished() {
        // nop
        nodeQueryCache.put(getNodeName(), getNodes());
    }

}
