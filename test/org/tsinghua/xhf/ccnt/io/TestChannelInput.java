package org.tsinghua.xhf.ccnt.io;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.tsinghua.xhf.ccnt.data.CacheData;
import org.tsinghua.xhf.ccnt.io.ChannelInput.DataNotReadyException;

public class TestChannelInput {
    private static final Log log = LogFactory.getLog(TestChannelInput.class);
    
    @Test
    public void test() {
        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
        
        //test readInt
        buffer.writeInt(1);
        ChannelInput input = new ChannelInput(buffer);
        while(true) {
            try {
                int a = input.readInt();
                Assert.assertEquals(a, 1);
                break;
            } catch (IOException e) {
                if(!(e instanceof DataNotReadyException)) {
                    Assert.fail("", e);
                }
            }
        }
        
        //test readString
        String s = "asdlfmiwejlfn;ansd;ofiworigosdfao";
        buffer.writeInt(s.getBytes().length);
        buffer.writeBytes(s.getBytes());
        while(true) {
            try {
                String a = input.readString();
                Assert.assertEquals(a, s);
                break;
            } catch (IOException e) {
                if(!(e instanceof DataNotReadyException)) {
                    Assert.fail("", e);
                }
            }
        }
        
        //test readBigData in memory
        int len = 1000;
        buffer.writeInt(len);
        for(int i=0; i<len; i++) {
            buffer.writeByte(1);
        }
        File cacheFile = new File("test-file/test-channel-input.file");
        if(cacheFile.exists()) {
           cacheFile.delete(); 
        }
        try {
            FileUtils.forceMkdir(cacheFile.getParentFile());
            cacheFile.createNewFile();
        } catch (IOException e1) {
            Assert.fail(cacheFile.getAbsolutePath(), e1);
        }
        while(true) {
            try {
                CacheData cacheData = input.readBigData(cacheFile);
                Assert.assertEquals(cacheData.getLength(), len);
                Assert.assertTrue(cacheData.inMemory());
                byte[] bytes = cacheData.getData();
                Assert.assertNotNull(bytes);
                Assert.assertEquals(bytes.length, len);
                for(int i=0; i<bytes.length; i++) {
                    Assert.assertEquals(bytes[i], 1);
                }
                break;
            } catch (IOException e) {
                if(!(e instanceof DataNotReadyException)) {
                    Assert.fail("", e);
                }
                log.info("data not ready...");
            }
        }
        
        //test read big data in file
        len = CacheData.THRESHOLD+1;
        buffer.writeInt(len);
        for(int i=0; i<len; i++) {
            buffer.writeByte(1);
        }
        if(cacheFile.exists()) {
           cacheFile.delete(); 
        }
        try {
            FileUtils.forceMkdir(cacheFile.getParentFile());
            cacheFile.createNewFile();
        } catch (IOException e1) {
            Assert.fail(cacheFile.getAbsolutePath(), e1);
        }
        while(true) {
            try {
                CacheData cacheData = input.readBigData(cacheFile);
                Assert.assertEquals(cacheData.getLength(), len);
                Assert.assertTrue(!cacheData.inMemory());
                Assert.assertEquals(FileUtils.sizeOf(cacheFile), len);
                byte[] bytes = FileUtils.readFileToByteArray(cacheFile);
                Assert.assertEquals(bytes.length, len);
                for(int i=0; i<bytes.length; i++) {
                    Assert.assertEquals(bytes[i], 1);
                }
                break;
            } catch (IOException e) {
                if(!(e instanceof DataNotReadyException)) {
                    Assert.fail("", e);
                }
                log.info("data not ready...");
            }
        }
    }
}
