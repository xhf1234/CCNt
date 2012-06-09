package org.tsinghua.xhf.ccnt;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.tsinghua.xhf.ccnt.data.packet.Interest;

/**
 * 
 * @author xuhongfeng
 * 
 */
public class CCNt implements ICCNt {
    private CCNConfig ccnConfig = new CCNConfig();
    
    //singleton
    private CCNt(){}

    private static CCNt me;
    public static CCNt getInstance() {
        if(me != null) {
            return me;
        }
        synchronized (CCNt.class) {
            if(me == null) {
                me = new CCNt();
            }
        }
        return me;
    }
    
    public static void main(String[] args) {
        CCNt.getInstance().start();
    }

    @Override
    public void sendInterest(Interest intentest) {

    }

    @Override
    public void start() {
        ChannelFactory factory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());

        ServerBootstrap bootstrap = new ServerBootstrap(factory);

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                return Channels.pipeline(new CCNHandler());
            }
        });

        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);

        bootstrap.bind(new InetSocketAddress(getConfig().getCcntPort()));
    }

    @Override
    public void setConfig(CCNConfig config) {
        ccnConfig = config;
    }

    @Override
    public CCNConfig getConfig() {
        return ccnConfig;
    }
}
