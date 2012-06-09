package org.tsinghua.xhf.ccnt;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * 
 * @author xuhongfeng
 * 
 */
public class CCNHandler extends SimpleChannelHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        ChannelBuffer buf = (ChannelBuffer) e.getMessage();
        while (buf.readable()) {
            System.out.println((char) buf.readByte());
            System.out.flush();
        }
    }

}
