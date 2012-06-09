package org.tsinghua.xhf.ccnt.io;

import java.io.IOException;

import org.tsinghua.xhf.ccnt.data.CacheData;

/**
 * 
 * @author xuhongfeng
 *
 */
public interface Output {
    public void writeInt(int v) throws IOException;
    public void writeString(String v) throws IOException;
    public void writeBigData(CacheData v) throws IOException;
}
