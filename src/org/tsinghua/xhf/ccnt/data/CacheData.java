package org.tsinghua.xhf.ccnt.data;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;

/**
 * 
 * @author xuhongfeng
 * 
 *         a structure to store bytes, if the bytes.length>THRESHOLD, then store
 *         the bytes in a cacheFile.
 * 
 */
public class CacheData {
    public static final int THRESHOLD = 10 * 1024 * 1024;// 10M

    private int length;
    private byte[] data;
    private File cacheFile;

    public CacheData(File cacheFile, int length) {
        this.length = length;
        this.cacheFile = cacheFile;
        if (inMemory()) {
            data = new byte[length];
        }
    }
//
//    private int writeIndex = 0;
//    private FileOutputStream fos;
//    public void writeBytes(byte[] bytes, int offset, int len) throws IOException {
//        if (data != null) {
//            int writed = 0;
//            while (writed < len) {
//                data[writeIndex++] = bytes[offset++];
//                writed++;
//            }
//        } else {
//            if(fos == null) {
//                fos = new FileOutputStream(cacheFile);
//            }
//            fos.write(bytes, offset, len);
//            writeIndex += len;
//            if(isAllBytesWritten()) {
//                fos.close();
//            }
//        }
//    }
//    public void writeBytes(ChannelBuffer buffer, int len) throws IOException {
//        if (data != null) {
//            int writed = 0;
//            while (writed < len) {
//                data[writeIndex++] = buffer.readByte();
//                writed++;
//            }
//        } else {
//            if(fos == null) {
//                fos = new FileOutputStream(cacheFile);
//            }
//            buffer.readBytes(fos, len);
//            writeIndex += len;
//            if(isAllBytesWritten()) {
//                fos.close();
//                fos = null;
//            }
//        }
//    }
//    
//    private int readIndex=0;
//    public void readBytes(ChannelBuffer buffer, int len) {
//        if (data != null) {
//            buffer.writeBytes(data, readIndex, len);
//            readIndex += len;
//        } else {
//            if(fos == null) {
//                fos = new FileOutputStream(cacheFile);
//            }
//            buffer.readBytes(fos, len);
//            writeIndex += len;
//            if(isAllBytesWritten()) {
//                fos.close();
//                fos = null;
//            }
//        }
//    }
//    
//    public int unWriteLength() {
//        return length-writeIndex;
//    }
//    
//    public boolean isAllBytesWritten() {
//        return writeIndex==length;
//    }
    
    public int getLength() {
        return length;
    }
    
    public File getCacheFile() {
        return cacheFile;
    }
    
    public byte[] getData() {
        return data;
    }
    
    public boolean inMemory() {
        return length<=THRESHOLD;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CacheData other = (CacheData) obj;
        if(other.length != length) return false;
        if(!other.cacheFile.getAbsoluteFile()
                .equals(cacheFile.getAbsoluteFile())) return false;
        if(FileUtils.sizeOf(cacheFile) != FileUtils.sizeOf(other.cacheFile)) return false;
        if(data==null && other.data!=null) return false;
        if(other.data==null && data!=null) return false;
        if(data!=null && other.data!=null) {
            if(!ArrayUtils.isEquals(data, other.data)) return false;
        }
        return true;
    }
    
    public void setData(byte[]  bytes) {
        if(data==null || bytes==null || data.length!=bytes.length) {
            throw new RuntimeException();
        }
        data = bytes;
    }
}
