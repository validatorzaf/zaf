package cz.zaf.sipvalidator.nsesss;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import cz.zaf.common.xml.SimpleNodeAggregator;

public class NamedNodeAggregator extends SimpleNodeAggregator {

    private Map<String, List<Element>> nodeQueryCache;

    public NamedNodeAggregator(final String nodeName, 
                               final Map<String, List<Element>> nodeQueryCache) {
        super(nodeName);
        this.nodeQueryCache = nodeQueryCache;
    }
    
    @Override
    public void onFinished() {
        // nop
        nodeQueryCache.put(getNodeName(), getNodes());
    }

}
