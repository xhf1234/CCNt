package org.tsinghua.xhf.ccnt;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * 
 * @author xuhongfeng
 *
 */
public class PackageDecoder extends FrameDecoder {

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel,
            ChannelBuffer buffer) throws Exception {
        return null;//TODO
    }

}
