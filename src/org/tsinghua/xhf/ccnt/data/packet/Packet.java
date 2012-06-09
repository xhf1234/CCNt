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
public class Packet<T extends IWritable> implements IWritable{
    private int type;
    private T payload;
    
    @Override
    public void writeTo(Output out) throws IOException {
        out.writeInt(type);
        payload.writeTo(out);
    }
    @Override
    public void readFrom(Input in) throws IOException {
        type = in.readInt();
        payload.readFrom(in);
    }
}
