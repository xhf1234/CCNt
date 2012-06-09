package org.tsinghua.xhf.ccnt.cs;

import java.io.File;

import org.tsinghua.xhf.ccnt.CCNConfig;
import org.tsinghua.xhf.ccnt.CCNt;
import org.tsinghua.xhf.ccnt.data.packet.CcnName;

/**
 * 
 * @author xuhongfeng
 *
 */
public class ContentStore {
    
    //singleton
    private ContentStore(){}
    
    private static ContentStore me;
    public static ContentStore getInstance() {
        if(me != null) {
            return me;
        }
        synchronized (ContentStore.class) {
            if(me==null) {
                me = new ContentStore();
            }
        }
        return me;
    }
    
    public File getCacheFile(CcnName ccnName) {
        File dir = getConfig().getContentStoreDir();
        return new File(dir, parseFile(ccnName));
    }
    
    private CCNConfig getConfig() {
        return CCNt.getInstance().getConfig();
    }
    
    /**
     * parse the file name in content store
     * @param ccnName
     * @return
     */
    private String parseFile(CcnName ccnName) {
        return ccnName.getName().replace('/', '-');
    }
}
