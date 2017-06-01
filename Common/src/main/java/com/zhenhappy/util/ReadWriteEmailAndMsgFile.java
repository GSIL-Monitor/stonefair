package com.zhenhappy.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangxd on 2016/4/7.
 */
public class ReadWriteEmailAndMsgFile {
    public static BufferedReader bufread;
    //指定文件路径和名称
    private static String readStr ="";

    public final static File teaEmailFileName = new File("C:/Program Files/Apache Software Foundation/appendix/stone/EmailContent.txt");
    public final static File teaMsgFileName = new File("C:/Program Files/Apache Software Foundation/appendix/stone/MsgContent.txt");

    public final static File foshiEmailFileName = new File("C:/Program Files/Apache Software Foundation/appendix/foshi/EmailContent.txt");
    public final static File foshiMsgFileName = new File("C:/Program Files/Apache Software Foundation/appendix/foshi/MsgContent.txt");

    public final static File stoneEmailFileName = new File("C:/Program Files/Apache Software Foundation/appendix/stone/EmailContent.txt");
    public final static File stoneMsgFileName = new File("C:/Program Files/Apache Software Foundation/appendix/stone/MsgContent.txt");

    public ReadWriteEmailAndMsgFile() {

    }
    /**
     * 创建文本文件.
     * @throws IOException
     *
     */
    public static void creatTxtFile(File fileName) throws IOException {
        if (!fileName.exists()) {
            fileName.createNewFile();
            System.err.println(fileName + "已创建！");
        }
    }

    /**
     * 读取文本文件.
     *
     */
    public static String readTxtFile(File fileName){
        try
        {
            if(fileName.isFile()&&fileName.exists())
            {
                InputStreamReader read = new InputStreamReader(new FileInputStream(fileName),"utf-8");
                BufferedReader reader = new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null)
                {
                    readStr = readStr + line+ "\r\n";
                }
                read.close();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        //System.out.println("读取文件内容是:"+ "\r\n" + readStr);
        return readStr;
    }

    /**
     * 写文件.
     *
     */
    public static void writeTxtFile(String newStr, File fileName) throws IOException{
        try
        {
            if (!fileName.exists())
            {
                fileName.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(fileName),"utf-8");
            BufferedWriter writer=new BufferedWriter(write);
            //先读取原有文件内容，然后进行写入操作
            String filein = newStr + "\r\n" + readStr;
            //System.out.println("写入文件内容是:"+ filein);
            writer.write(filein);
            writer.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void setFileContentIsNull() {
        readStr ="";
    }

    /**
     * main方法测试
     * @param s
     * @throws IOException
     */
    public static void main(String[] s) throws IOException {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy年MM月dd日 EEE HH:mm:ss");
        Date date = new Date();
        String str = bartDateFormat.format(date);
        File fileName = new File("c:/emailAndMsg.txt");
        ReadWriteEmailAndMsgFile.creatTxtFile(fileName);
        ReadWriteEmailAndMsgFile.readTxtFile(fileName);
        ReadWriteEmailAndMsgFile.writeTxtFile(str + ", 给境内手机号为：13459298520发短信。", fileName);
        ReadWriteEmailAndMsgFile.readTxtFile(fileName);
        ReadWriteEmailAndMsgFile.writeTxtFile(str + ", 给境内手机号为：13358298510发短信。", fileName);
        ReadWriteEmailAndMsgFile.readTxtFile(fileName);
        ReadWriteEmailAndMsgFile.writeTxtFile(str + ", 给境内手机号为：13659298520发短信。", fileName);
    }
}
