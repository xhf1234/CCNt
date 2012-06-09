package org.tsinghua.xhf.ccnt.io;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tsinghua.xhf.ccnt.data.CacheData;
import org.tsinghua.xhf.ccnt.io.ChannelInput.DataNotReadyException;
import org.tsinghua.xhf.ccnt.io.ChannelOutput.OutputNotReadyException;

/**
 * 
 * @author xuhongfeng
 * 
 */
public class TestChannelOutput {
    private static final Log log = LogFactory.getLog(TestChannelOutput.class);

    @Test
    public void test() {
        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
        ChannelOutput output = new ChannelOutput(buffer);
        final ChannelInput input = new ChannelInput(buffer);

        // test write int
        while (true) {
            try {
                output.writeInt(1);
                break;
            } catch (IOException e) {
                if (!(e instanceof OutputNotReadyException)) {
                    Assert.fail("", e);
                }
            }
        }
        while (true) {
            try {
                int a = input.readInt();
                Assert.assertEquals(a, 1);
                break;
            } catch (IOException e) {
                if (!(e instanceof DataNotReadyException)) {
                    Assert.fail("", e);
                }
            }
        }
        // test write String
        String s = "asdnoiwfosna'sdlf'asdn'ongr;sado";
        while (true) {
            try {
                output.writeString(s);
                break;
            } catch (IOException e) {
                if (!(e instanceof OutputNotReadyException)) {
                    Assert.fail("", e);
                }
            }
        }
        while (true) {
            try {
                String a = input.readString();
                Assert.assertEquals(a, s);
                break;
            } catch (IOException e) {
                if (!(e instanceof DataNotReadyException)) {
                    Assert.fail("", e);
                }
            }
        }

        // test write big data in memory
        final File cacheFile = new File("test-file/test-channel-input.file");
        int len = 1000;
        byte[] bytes = new byte[len];
        for(int i=0; i<len; i++) {
            bytes[i]=1;
        }
        final CacheData cacheData = new CacheData(cacheFile, len);
        cacheData.setData(bytes);
        Thread readThread = new Thread(){
            @Override
            public void run() {
                while (true) {
                    try {
//                        LogUtil.info(this, "read start");
                        CacheData a = input.readBigData(cacheFile);
//                        LogUtil.info(this, "read Finish");
                        Assert.assertEquals(a, cacheData);
                        break;
                    } catch (IOException e) {
                        if (!(e instanceof DataNotReadyException)) {
                            Assert.fail("", e);
                        }
                        log.info("data not ready ...");
                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException e1) {
                        }
                    }
                }
            }
        };
        readThread.start();
        while (true) {
            try {
                output.writeBigData(cacheData);
                break;
            } catch (IOException e) {
                if (!(e instanceof OutputNotReadyException)) {
                    Assert.fail("", e);
                }
                log.info("output not ready ...");
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e1) {
                }
            }
        }
        try {
            readThread.join();
        } catch (InterruptedException e) {
        }
    }
}
