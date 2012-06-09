package org.tsinghua.xhf.ccnt.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.tsinghua.xhf.ccnt.data.CacheData;


/**
 * 
 * @author xuhongfeng
 *
 */
public class ChannelInput implements Input {
    private ChannelBuffer channelBuffer;

    public ChannelInput(ChannelBuffer channelBuffer) {
        this.channelBuffer = channelBuffer;
    }
    
    private int tmpLength = -1;
    private CacheData tmpCacheData;

    @Override
    public String readString() throws IOException, DataNotReadyException {
        if(tmpLength == -1) {// length not read yet
            tmpLength = innerReadInt();
        }
        checkReadableBytes(tmpLength);
        byte[] bytes = new byte[tmpLength];
        tmpLength = -1;
        channelBuffer.readBytes(bytes);
        return new String(bytes);
    }

    @Override
    public int readInt() throws IOException {
        return innerReadInt();
    }
    
    private int innerReadInt() throws DataNotReadyException {
        checkReadableBytes(4);
        return channelBuffer.readInt();
    }
    
    private void checkReadableBytes(int length) throws DataNotReadyException {
        if(channelBuffer.readableBytes() < length) {
            throw new DataNotReadyException();
        }
    }

    private int writeIndex = 0;
    private FileOutputStream fos;
    @Override
    public CacheData readBigData(File cacheFile) throws IOException {
        if(tmpCacheData == null) {
            int length = innerReadInt();
            tmpCacheData = new CacheData(cacheFile, length);
        }
        int len = channelBuffer.readableBytes();
        int unWrite = tmpCacheData.getLength()-writeIndex;
        if(len > unWrite) len=unWrite;
        if(len > 0) {
            if(tmpCacheData.inMemory()) {
                byte[] bytes = tmpCacheData.getData();
                channelBuffer.readBytes(bytes, writeIndex, len);
            } else {
                if(fos == null) {
                    fos = new FileOutputStream(tmpCacheData.getCacheFile());
                }
                channelBuffer.readBytes(fos, len);
                fos.flush();
            }
            writeIndex += len;
        }
        if(writeIndex == tmpCacheData.getLength()) {//finish
            CacheData e = tmpCacheData;
            tmpCacheData = null;
            if(fos != null) {
                fos.close();
                fos = null;
            }
            writeIndex = 0;
            return e;
        } else {
            throw new DataNotReadyException();
        }
    }

    public static class DataNotReadyException extends IOException {
        private static final long serialVersionUID = -2665320275226100980L;
    }
}
