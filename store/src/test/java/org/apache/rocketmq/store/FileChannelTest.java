package org.apache.rocketmq.store;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.NumberFormat;

/**
 * Created by sier.pys on 2018/7/22.
 *
 * @Copyright 2018 alibaba.com All Rights Reserved
 * @Vesion v1.0
 */


//
//    file channel write / read
//
public class FileChannelTest {


    @Test
    public void numberFormat() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumIntegerDigits(20);
        numberFormat.setMaximumFractionDigits(0);
        numberFormat.setGroupingUsed(false);
        PrintWriter printWriter = new PrintWriter(System.out);
        printWriter.write(numberFormat.format(22222220));
        printWriter.flush();
    }


    @Test
    public void fileChannel() throws IOException {
        RandomAccessFile raFile = new RandomAccessFile("./nio-data.txt", "rw");
        FileChannel channel = raFile.getChannel();

        String data = "New String to write to file..." + System.currentTimeMillis();

        PrintWriter printWriter = new PrintWriter(System.out);
        printWriter.write("data: = " + data.length());
        printWriter.flush();

        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(data.getBytes());

        buf.flip();


        while (buf.hasRemaining()) {
            channel.write(buf);
        }
        long position = channel.position();

        printWriter.write("position: = " + position);
        printWriter.flush();


        channel.force(false);


        FileDescriptor descriptor = raFile.getFD();

        printWriter.write("descriper: = " + descriptor.toString());

        printWriter.flush();

        FileChannel channel_sub = channel.position(30);
        ByteBuffer allocate = ByteBuffer.allocate(48);

        channel_sub.read(allocate);

//        while (allocate.hasRemaining()) {
//            printWriter.write("\r\n");
//            printWriter.write(allocate.toString());
//            printWriter.flush();
//
//        }

        printWriter.write("size := " + channel.size());

        printWriter.flush();


        channel.close();
    }
}
