package org.tsinghua.xhf.ccnt.data.packet;

import java.io.IOException;

import org.tsinghua.xhf.ccnt.io.IWritable;
import org.tsinghua.xhf.ccnt.io.Input;
import org.tsinghua.xhf.ccnt.io.Output;

/**
 * 
 * @author xuhongfeng
 *
 */
public class Interest implements IWritable {
    private CcnName ccnName = new CcnName();

    @Override
    public void writeTo(Output out) throws IOException {
        ccnName.writeTo(out);
    }

    @Override
    public void readFrom(Input in) throws IOException {
        ccnName.readFrom(in);
    }


}
