package com.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class HdfsTest {
    FileSystem fs = null;

    public static void createFile(String dst, byte[] contents) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path dstPath = new Path(dst);
        FSDataOutputStream outputStream = fs.create(dstPath);
        outputStream.write(contents);
        outputStream.flush();
        outputStream.close();
        fs.close();
        System.out.println("文件创建成功");
    }

    public static void uploadFile(String src, String dst) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path srcPath = new Path(src);
        Path dstPath = new Path(dst);
        fs.copyFromLocalFile(false, srcPath, dstPath);
        System.out.println("Upload to " + conf.get("fs.default.name"));
        System.out.println("----------------list files-------------");
        FileStatus[] fileStatus = fs.listStatus(dstPath);
        for (FileStatus file : fileStatus) {
            System.out.println(file.getPath());
        }
        fs.close();
    }

    public static void rename(String oldName, String newName) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path oldPath = new Path(oldName);
        Path newPath = new Path(newName);
        boolean isOk = fs.rename(oldPath, newPath);
        if (isOk)
            System.out.println("rename ok!");
        else
            System.out.println("rename failed!");
        fs.close();
    }

    public static void deleteFile(String dst) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path dstPath = new Path(dst);
        boolean isOk = fs.deleteOnExit(dstPath);
        if (isOk)
            System.out.println("delete ok!");
        else
            System.out.println("delete failed!");
        fs.close();
    }

    public static void mkdir(String dst) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path dstPath = new Path(dst);
        boolean isOk = fs.mkdirs(dstPath);
        if (isOk)
            System.out.println("mkdir ok!");
        else
            System.out.println("mkdir failed!");
        fs.close();
    }

    public static void readFile(String filePath) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path srcPath = new Path(filePath);
        InputStream in;
        in = fs.open(srcPath);
        try {
            IOUtils.copyBytes(in, System.out, 4096, false);
        } finally {
            IOUtils.closeStream(in);
        }
    }

    public static void main(String[] args) {
        try {
//            createFile("/user/wangyu/test_file", "hello,It's me".getBytes());
//            uploadFile("build.gradle", "/user/wangyu/");
//            rename("/user/wangyu/build.gradle", "/user/wangyu/test.gradle");
//            readFile("/user/wangyu/test/input/text.txt");
//            mkdir("/user/wangyu/mapreduce");
//            mkdir("/user/wangyu/mapreduce/data");
            uploadFile("data/NginxData.txt", "/user/wangyu/mapreduce/data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
