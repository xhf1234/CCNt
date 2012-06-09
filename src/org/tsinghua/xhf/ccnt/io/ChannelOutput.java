package org.tsinghua.xhf.ccnt.io;

import java.io.FileInputStream;
import java.io.IOException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.tsinghua.xhf.ccnt.data.CacheData;

/**
 * 
 * @author xuhongfeng
 *
 */
public class ChannelOutput implements Output {
    private ChannelBuffer channelBuffer;

    public ChannelOutput(ChannelBuffer channelBuffer) {
        this.channelBuffer = channelBuffer;
    }

    @Override
    public void writeInt(int v) throws IOException {
        innerWriteInt(v);
    }
    
    private void innerWriteInt(int v) throws OutputNotReadyException {
        checkWritableBytes(4);
        channelBuffer.writeInt(v);
    }

    @Override
    public void writeString(String v) throws IOException {
        innerWriteInt(v.getBytes().length);
        channelBuffer.writeBytes(v.getBytes());
    }

    private boolean isLengthWritten = false;
    private int readIndex = 0;
    private FileInputStream fis;
    @Override
    public void writeBigData(CacheData cacheData) throws IOException {
        if(!isLengthWritten) {
            innerWriteInt(cacheData.getLength());
            isLengthWritten = true;
        }
        int len = channelBuffer.writableBytes();
        int unRead = cacheData.getLength() - readIndex;
        if(len > unRead) len=unRead;
        if(len > 0) {
            if(cacheData.inMemory()) {
                byte[] bytes = cacheData.getData();
                channelBuffer.writeBytes(bytes, readIndex, len);
            } else {
                if(fis == null) {
                    fis = new FileInputStream(cacheData.getCacheFile());
                }
                channelBuffer.writeBytes(fis, len);
            }
            readIndex += len;
        } else {
            channelBuffer.discardReadBytes();
        }
        if(readIndex == cacheData.getLength()) {//finish
            isLengthWritten = false;
            if(fis != null) {
                fis.close();
                fis = null;
            }
            readIndex = 0;
        } else {
            throw new OutputNotReadyException();
        }
    }
    
    private void checkWritableBytes(int len) throws OutputNotReadyException {
        if(channelBuffer.writableBytes() < len) {
            throw new OutputNotReadyException();
        }
    }

    public static class OutputNotReadyException extends IOException {
        private static final long serialVersionUID = 1968359362262740620L;
        
    }
}
