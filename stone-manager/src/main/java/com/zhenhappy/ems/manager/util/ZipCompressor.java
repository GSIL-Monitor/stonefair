package com.zhenhappy.ems.manager.util;

/**
 * Created by Administrator on 2016/12/5.
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCompressor {
    static final int BUFFER = 8192;

    private File zipFile;

    public ZipCompressor(String pathName) {
        zipFile = new File(pathName);
    }

    public static void compressForManyDirectory(String filePath, String[] pathNameArray, String targetZipFile) {
        ZipOutputStream out = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(targetZipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
            out = new ZipOutputStream(cos);
            String basedir = "";
            for (int i=0;i<pathNameArray.length;i++){
                compress(new File(filePath + "\\" + pathNameArray[i]), out, basedir);
            }
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*public static void compress(String... pathName) {
        ZipOutputStream out = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
                    new CRC32());
            out = new ZipOutputStream(cos);
            String basedir = "";
            for (int i=0;i<pathName.length;i++){
                compress(new File(pathName[i]), out, basedir);
            }
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    /*public static void compress(String srcPathName) {
        File file = new File(srcPathName);
        if (!file.exists())
            throw new RuntimeException(srcPathName + "不存在！");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
                    new CRC32());
            ZipOutputStream out = new ZipOutputStream(cos);
            String basedir = "";
            compress(file, out, basedir);
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    private static void compress(File file, ZipOutputStream out, String basedir) {
        /* 判断是目录还是文件 */
        if (file.isDirectory()) {
            //System.out.println("压缩：" + basedir + file.getName());
            compressDirectory(file, out, basedir);
        } else {
            //System.out.println("压缩：" + basedir + file.getName());
            compressFile(file, out, basedir);
        }
    }

    /** 压缩一个目录 */
    private static void compressDirectory(File dir, ZipOutputStream out, String basedir) {
        if (!dir.exists())
            return;

        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            /* 递归 */
            compress(files[i], out, basedir + dir.getName() + "/");
        }
    }

    /** 压缩一个文件 */
    private static void compressFile(File file, ZipOutputStream out, String basedir) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(basedir + file.getName());
            out.putNextEntry(entry);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            bis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        ZipCompressor zc = new ZipCompressor("C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\发票.zip");
        /*zc.compress("C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\安徽省优丽斯石英建材有限公司",
                "C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\柏司得环保科技（上海）有限公司",
                "C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\保特罗玻璃工业（佛山）有限公司",
                "C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\郑州多磨超硬材料有限公司");*/
        /*String[] zipList = new String[4];
        zipList[0] = "C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\安徽省优丽斯石英建材有限公司";
        zipList[1] = "C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\柏司得环保科技（上海）有限公司";
        zipList[2] = "C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\保特罗玻璃工业（佛山）有限公司";
        zipList[3] = "C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\郑州多磨超硬材料有限公司";
        zc.compress(zipList);

        zc.compressForManyDirectory(zipList, "C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\发票.zip");*/

        /*zc.compress(new String[]{"C:\\\\Program Files\\\\Apache Software Foundation\\\\appendix\\\\stone\\\\exhibitor_invisitor\\\\out\\\\安徽省优丽斯石英建材有限公司",
                "C:\\\\Program Files\\\\Apache Software Foundation\\\\appendix\\\\stone\\\\exhibitor_invisitor\\\\out\\\\柏司得环保科技（上海）有限公司",
        "C:\\\\Program Files\\\\Apache Software Foundation\\\\appendix\\\\stone\\\\exhibitor_invisitor\\\\out\\\\保特罗玻璃工业（佛山）有限公司",
                "C:\\\\Program Files\\\\Apache Software Foundation\\\\appendix\\\\stone\\\\exhibitor_invisitor\\\\out\\\\郑州多磨超硬材料有限公司"});*/
        /*zc.compress("C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\安徽省优丽斯石英建材有限公司");
        zc.compress("C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\柏司得环保科技（上海）有限公司");
        zc.compress("C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\保特罗玻璃工业（佛山）有限公司");
        zc.compress("C:\\Program Files\\Apache Software Foundation\\appendix\\stone\\exhibitor_invisitor\\out\\郑州多磨超硬材料有限公司");*/
    }
}
