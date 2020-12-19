package org.geekbang.thinking.in.spring.verify;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.StreamUtils;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/9/6
 */
public class EncodedFileSystemResourceDemo {
    public static void main(String[] args) throws IOException {
        String currentJavaFilePath = System.getProperty("user.dir") + "/thinking-in-spring/resource/src/main/java/org/geekbang/thinking/in/spring/verify/EncodedFileSystemResourceDemo.java";
        File currentJavaFile = new File(currentJavaFilePath);
        FileSystemResource fileSystemResource = new FileSystemResource(currentJavaFile);
        EncodedResource encodedResource = new EncodedResource(fileSystemResource, "UTF-8");

        Reader reader = encodedResource.getReader();

        CharArrayWriter writer = new CharArrayWriter();

        System.out.println(IOUtils.toString(reader));


    }
}
