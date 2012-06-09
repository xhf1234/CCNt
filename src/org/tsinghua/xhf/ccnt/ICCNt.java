package org.tsinghua.xhf.ccnt;

import org.tsinghua.xhf.ccnt.data.packet.Interest;

/**
 * 
 * @author xuhongfeng
 *
 */
public interface ICCNt {
    public void start();
    public void setConfig(CCNConfig config);
    public CCNConfig getConfig();
    public void sendInterest(Interest intentest);
}
