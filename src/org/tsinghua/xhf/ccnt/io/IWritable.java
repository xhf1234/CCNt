package org.tsinghua.xhf.ccnt.io;

import java.io.IOException;


/**
 * 
 * @author xuhongfeng
 *
 */
public interface IWritable {
    public void writeTo(Output out) throws IOException;
    public void readFrom(Input in) throws IOException;
}
