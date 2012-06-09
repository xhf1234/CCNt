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
public class CcnName implements IWritable {
    private String name;

    @Override
    public void writeTo(Output out) throws IOException {
        out.writeString(name);
    }

    @Override
    public void readFrom(Input in) throws IOException {
        name = in.readString();
    }

    public String getName() {
        return name;
    }
}
