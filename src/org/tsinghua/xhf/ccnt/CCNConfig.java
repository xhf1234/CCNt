package org.tsinghua.xhf.ccnt;

import java.io.File;

public class CCNConfig {
    private static final int DEFAULT_CCNT_PORT = 9696;
    private static final File DEFAULT_CONTENT_STORE = new File("");
    
    private int ccntPort = DEFAULT_CCNT_PORT;
    private File contentStoreDir = DEFAULT_CONTENT_STORE;

    public CCNConfig() {
    }

    public int getCcntPort() {
        return ccntPort;
    }

    public void setCcntPort(int ccntPort) {
        this.ccntPort = ccntPort;
    }
    
    public File getContentStoreDir() {
        return contentStoreDir;
    }
    
    public void setContentStoreDir(File dir) {
        contentStoreDir = dir;
    }
}
