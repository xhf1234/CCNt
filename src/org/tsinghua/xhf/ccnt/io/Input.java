package org.tsinghua.xhf.ccnt.io;

import java.io.File;
import java.io.IOException;

import org.tsinghua.xhf.ccnt.data.CacheData;

/**
 * 
 * @author xuhongfeng
 *
 */
public interface Input {
    public String readString() throws IOException;
    public int readInt() throws IOException;
    
    /**
     * 
     * read some big bytes and store in the cacheData
     * the cacheData may store the bytes in a file if the
     * bytes.lengh>CacheData.THRESHOLD and not be able to
     * store in memory
     * 
     * @return
     */
    public CacheData readBigData(File cacheFile) throws IOException;
}
