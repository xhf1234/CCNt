package org.tsinghua.xhf.ccnt.data.packet;

import java.io.File;
import java.io.IOException;

import org.tsinghua.xhf.ccnt.cs.ContentStore;
import org.tsinghua.xhf.ccnt.data.CacheData;
import org.tsinghua.xhf.ccnt.io.IWritable;
import org.tsinghua.xhf.ccnt.io.Input;
import org.tsinghua.xhf.ccnt.io.Output;

/**
 * 
 * @author xuhongfeng
 *
 */
public class ContentObject implements IWritable {
    private CcnName ccnName = new CcnName();
    private CacheData data;

    @Override
    public void writeTo(Output out) throws IOException {
        ccnName.writeTo(out);
        out.writeBigData(data);
    }

    @Override
    public void readFrom(Input in) throws IOException {
        ccnName.readFrom(in);
        File cacheFile = ContentStore.getInstance().getCacheFile(ccnName);
        data = in.readBigData(cacheFile);
    }

}
