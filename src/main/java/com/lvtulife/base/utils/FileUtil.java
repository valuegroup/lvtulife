package com.lvtulife.base.utils;

import java.awt.Image;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.lvtulife.base.component.LabelValue;
import com.lvtulife.base.component.constants.SysConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class FileUtil {
    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * @param args
     * @方法描述：
     * @参数描述：
     */
    public static void main(String[] args) throws IOException {
        List list = reader("d:\\FileUploadBean.java");
    }

    /**
     * @param path
     * @return 一系列的文件名, 每个文件名之间用, 隔开
     * @作者:network 时间：2007-11-19
     * @方法描述：通过文件路径获取文件夹下面的文件名称
     * @参数描述：path:文件夹的路径
     */
    public static String getFilesNames(String path) {
        File file = new File(path);
        String fileName = "";
        if (file.exists() && file.isDirectory()) {
            String[] fs = file.list();
            for (int i = 0; i < fs.length; i++) {
                fileName = fileName + fs[i] + ",";
            }
        }
        return fileName;
    }

    /**
     * 通过文件路径获取文件夹下面的文件名称
     *
     * @param path
     * @return
     */
    public static List<String> getFilesNamesList(String path) {
        File file = new File(path);
        List<String> fileNameList = new ArrayList<String>();
        if (file.exists() && file.isDirectory()) {
            String[] fs = file.list();
            for (int i = 0; i < fs.length; i++) {
                fileNameList.add(fs[i]);
            }
        }
        return fileNameList;
    }

    /**
     * 通过文件路径获取文件夹下面文件的个数
     *
     * @param path
     * @return
     */
    public static int getFilesNum(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return file.listFiles().length;
        } else {
            return 0;
        }
    }

    /**
     * 在给定路径path下面查找文件名，如果存在则返回该文件所在的路径文件夹目录
     *
     * @param dir
     * @param fileName
     * @return
     */
    public static String findFileNameInDir(String dir, String fileName) {
        File file = new File(dir);
        if (file == null)
            return null;
        if (file.isDirectory()) {
            String[] fileNameArray = file.list();
            if (fileNameArray != null && fileNameArray.length > 0) {
                for (int i = 0; i < fileNameArray.length; i++) {
                    if (fileNameArray[i].equals(fileName)) {
                        return dir;
                    } else {
                        String newDir = dir + "/" + fileNameArray[i];
                        String rt = findFileNameInDir(newDir, fileName);
                        if (rt != null) {
                            return rt;
                        }
                    }
                }
            }
        } else {
            if (file.getName().equals(fileName)) {
                return dir;
            }
        }
        return null;
    }

    /**
     * 在给定路径path下面查找文件夹目录,只进行二级查询，返回级联结果，如果存在dir，则返回级联结果，不存在则直接返回查找目录path
     */
    public static String findDirNameInDir(String dir, String dirName) {
        File file = new File(dir);
        if (file == null)
            return null;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            String[] fileNameArray = file.list();
            if (files != null && files.length > 0) {
                for (File tmpFile : files) {
                    if (tmpFile.isDirectory()) {
                        if (tmpFile.getName().equals(dirName)) {// 如果是文件夹，且满足条件，则判断该文件夹下是否还存在满足条件的dir，否则就返回本身
                            String newDir = dir + "/" + tmpFile.getName();
                            return findDirNameInDir(newDir, dirName);
                        }
                    }
                }
            }
        }
        return dir;
    }

    /**
     * 一行一行读取文本文件，并把数据保存到List中
     *
     * @param fileNamePath
     * @return
     */
    public final static List reader(String fileNamePath) {
        InputStream is = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        try {
            List list = new ArrayList();
            is = new FileInputStream(fileNamePath);
            StringBuffer buffer = new StringBuffer();
            reader = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(reader);
            String s1 = null;
            while ((s1 = br.readLine()) != null) {
                // logger.info("line=" + s1);
                list.add(s1);
            }
            br.close();
            reader.close();
            br.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (is != null) {
                    is.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 一行一行读取文本文件，并把数据保存到List中
     *
     * @param list
     * @return
     */
    public final static List<String> getNotNullReader(List<String> list) {
        List<String> rlist = new ArrayList();
        if (list != null) {
            for (int i = list.size() - 1; i >= 0; i--) {
                if (FileUtil.nullOrBlank(list.get(i))) {
                    list.remove(i);
                }
            }
            for (String str : list) {
                rlist.add(str);
            }
        }

        return rlist;
    }

    public static boolean nullOrBlank(String param) {
        return (param == null || param.length() == 0 || param.trim().equals("")) ? true : false;
    }

    public final static List readerGbk(String fileNamePath) {
        InputStream is = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        try {
            List list = new ArrayList();
            is = new FileInputStream(fileNamePath);
            StringBuffer buffer = new StringBuffer();
            reader = new InputStreamReader(is, "GBK");
            br = new BufferedReader(reader);
            String s1 = null;
            while ((s1 = br.readLine()) != null) {
                // logger.info("line=" + s1);
                s1.getBytes("UTF-8");
                list.add(s1);
            }
            br.close();
            reader.close();
            br.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (is != null) {
                    is.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 该程序的操作日志 ,为了方便自己查找应用程序中的错误
     *
     * @param syslog       写入日志的内容 设置文件的大小，如果超过大小设置，则把当前文件备份，重新写文件。 该方法可以自定义文件名，文件大小，输出路径 在INI配置文件中定义
     * @param fileNamePath 文件名绝对路径
     */
    public static void writeTwoColor(String fileNamePath, String syslog) {
        File LogFile = new File(fileNamePath);
        // 控制该方法是否有用:如果设置为FALSE，在调用该方法的地方不用修改程序，该方法也不会起作用；
        // 如果设置为TRUE，则该方法正常调用
        boolean rcflag = true;
        if (rcflag) {
            try {
                /* 如果参数日志文件存在则打开该文件，在该文件中继续写日志，如果不存在则新建该文件 */
                if (LogFile.exists()) {
                    FileWriter logreadandwrite = new FileWriter(LogFile, true);
                    PrintWriter logfilewrite = new PrintWriter(logreadandwrite);
                    logfilewrite.print(syslog + "\n"); // +n换行
                    logfilewrite.close();
                    logreadandwrite.close();
                    logger.info("文件" + LogFile + "存在并写入成功");
                } else {
                    logger.info("文件" + LogFile + "不存在,新建...");
                    FileWriter logreadandwrite = new FileWriter(LogFile, true);
                    PrintWriter logfilewrite = new PrintWriter(logreadandwrite);
                    logfilewrite.print(syslog + "\n"); // +n换行
                    logfilewrite.close();
                    logreadandwrite.close();
                    logger.info("文件" + LogFile + "不存在,新建并写入成功");
                }
            } catch (IOException e) {
                logger.info("读写日志文件" + LogFile + "失败");
            }
        }
    }

    public static void writePOJOCHSXml(String fileNamePath, String name, String template) {
        try {
            // 1.得到DOM解析器的工厂实例
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // 2.从DOM工厂里获取DOM解析器
            DocumentBuilder db = dbf.newDocumentBuilder();
            // 3.解析XML文档，得到document，即DOM树
            Document doc = db.parse(fileNamePath);
            // 创建节点
            Element brandElement = doc.createElement("definition");
            brandElement.setAttribute("name", name);
            brandElement.setAttribute("template", template);
            Element phoneElement = (Element) doc.getElementsByTagName("tiles-definitions").item(0);
            phoneElement.appendChild(brandElement);
            // 保存xml文件
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            // 设置编码类型
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            StreamResult result = new StreamResult(new FileOutputStream(fileNamePath));
            // 把DOM树转换为xml文件
            transformer.transform(domSource, result);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
    }

    public static void writeXml(String fileNamePath, String name, String template) {
        try {
            // 1.得到DOM解析器的工厂实例
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // 2.从DOM工厂里获取DOM解析器
            DocumentBuilder db = dbf.newDocumentBuilder();
            // 3.解析XML文档，得到document，即DOM树
            Document doc = db.parse(fileNamePath);
            // 创建节点
            Element brandElement = doc.createElement("definition");
            brandElement.setAttribute("name", name);
            brandElement.setAttribute("template", template);
            Element phoneElement = (Element) doc.getElementsByTagName("tiles-definitions").item(0);
            phoneElement.appendChild(brandElement);
            // 保存xml文件
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            // 设置编码类型
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            StreamResult result = new StreamResult(new FileOutputStream(fileNamePath));
            // 把DOM树转换为xml文件
            transformer.transform(domSource, result);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
    }

    /**
     * 该程序的操作日志 ,为了方便自己查找应用程序中的错误
     * 写入日志的内容 设置文件的大小，如果超过大小设置，则把当前文件备份，重新写文件。 该方法可以自定义文件名，文件大小，输出路径 在INI配置文件中定义
     * 文件名及绝对路径
     */
    public static void writeLine(String fileNamePath, String lineStr) {
        File file = new File(fileNamePath);
        // 控制该方法是否有用:如果设置为FALSE，在调用该方法的地方不用修改程序，该方法也不会起作用；
        // 如果设置为TRUE，则该方法正常调用
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fileNamePath, true), "UTF-8");
            osw.write(lineStr + "\r\n");
            osw.close();
        } catch (IOException e) {
            logger.info("读写文件" + file + "失败");
        }
    }

    /**
     * 该程序的操作日志 ,为了方便自己查找应用程序中的错误
     *
     * @param syslog       写入日志的内容 设置文件的大小，如果超过大小设置，则把当前文件备份，重新写文件。 该方法可以自定义文件名，文件大小，输出路径 在INI配置文件中定义
     * @param fileNamePath 文件名及绝对路径
     */
    public static void writeLog(String fileNamePath, String syslog) {
        String systime = DateUtil.getToday();
        File LogFile = new File(fileNamePath);
        // 控制该方法是否有用:如果设置为FALSE，在调用该方法的地方不用修改程序，该方法也不会起作用；
        // 如果设置为TRUE，则该方法正常调用
        boolean rcflag = true;
        if (rcflag) {
            try {
				/* 如果参数日志文件存在则打开该文件，在该文件中继续写日志，如果不存在则新建该文件 */
                if (LogFile.exists()) {
                    FileWriter logreadandwrite = new FileWriter(LogFile, true);
                    // FileReader fr = new FileReader(logfilename);
                    PrintWriter logfilewrite = new PrintWriter(logreadandwrite);
                    logfilewrite.print(systime + " : " + syslog + "\n"); // +n换行
                    logfilewrite.close();
                    logreadandwrite.close();
                    // logger.info("文件" + LogFile + "存在并写入成功");
                } else {
                    // logger.info("文件" + LogFile + "不存在,新建...");
                    FileWriter logreadandwrite = new FileWriter(LogFile, true);
                    // FileReader fr = new FileReader(logfilename);
                    PrintWriter logfilewrite = new PrintWriter(logreadandwrite);
                    logfilewrite.print(systime + syslog + "\n"); // +n换行
                    logfilewrite.close();
                    logreadandwrite.close();
                    // logger.info("文件" + LogFile + "不存在,新建并写入成功");
                }
            } catch (IOException e) {
                // logfilewrite.print(systime+syslog+"\n"); //+n换
                logger.info("读写日志文件" + LogFile + "失败");
            }
        }
    }

    /**
     * @param fileNamePath 文件名及绝对路径
     * @param args
     * @return
     * @throws IOException
     * @方法描述：向文件里面写入数据args
     * @参数描述：
     */
    public static boolean writeFile(String fileNamePath, String[] args) throws IOException {
        if (null == fileNamePath || null == args || "".equals(fileNamePath.trim()) || args.length < 1)
            return false;
        File file = new File(fileNamePath);
        if (!file.exists()) {
            System.err.println("Error! The fileNamePath=" + fileNamePath + " not existed!");
            return false;
        }
        FileWriter fw = new FileWriter(fileNamePath);
        PrintWriter out = new PrintWriter(fw);
        for (int i = 0; i < args.length; i++) {
            out.write(args[i]);
            out.println();
            out.flush();
        }
        fw.close();
        out.close();
        return true;
    }

    /**
     * @param fileNamePath 文件名及绝对路径
     * @param content      写入的文本内容
     * @param encoding     编码格式，例如"UTF-8","GBK"
     * @return
     * @throws IOException
     * @date 2008-12-4
     * @author network
     * @see 方法描述:新建文件，并写入内容。如果已经存在该文件，则覆盖
     */
    public static void writeFile(String fileNamePath, String content, String encoding) throws IOException {
        if (null == fileNamePath || null == content || "".equals(fileNamePath.trim()) || "".equals(content.trim()))
            return;
        if (encoding == null || "".equals(encoding)) {
            encoding = "UTF-8";
        }
        File file = new File(fileNamePath);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new OutputStreamWriter(fos, encoding);// 利用这个可以设置写入文件Stream的encoding
        out.write(content);
        out.close();
        fos.close();
    }

    /**
     * @param srcFileNamePath 源文件名，绝对路径
     * @param desFileNamePath 拷贝到目标文件名，绝对路径
     * @方法描述：复制单个文件
     * @参数描述：
     */
    public static boolean copyFile(String srcFileNamePath, String desFileNamePath) {
        try {
            logger.info(srcFileNamePath);
            logger.info(desFileNamePath);
            int bytesum = 0;
            int byteread = 0;
            File srcfile = new File(srcFileNamePath);
            if (srcfile.exists()) { // 文件存在时
                makeDir(desFileNamePath.substring(0, desFileNamePath.lastIndexOf("/") + 1));
                InputStream inStream = new FileInputStream(srcFileNamePath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(desFileNamePath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                srcfile = null;
                return true;
            } else {
                logger.info("源文件" + srcFileNamePath + "不存在!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("源文件" + srcFileNamePath + "文件拷贝到" + desFileNamePath + "失败!");
        }
        return false;
    }

    /**
     * @param srcDir 准备拷贝的源目录，绝对路径
     * @param desDir 目标目录，绝对路径
     * @return
     * @方法描述：复制源文件夹下的全部内容到目标目录下
     * @参数描述：
     */
    public static void copyDir(String srcDir, String desDir) {
        try {
            new File(desDir).mkdirs(); // 如果文件夹不存在 则建立新文件夹
            File a = new File(srcDir);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (srcDir.endsWith(File.separator)) {
                    temp = new File(srcDir + file[i]);
                } else {
                    temp = new File(srcDir + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(desDir + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyDir(srcDir + "/" + file[i], desDir + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("目录" + srcDir + "中文件拷贝到" + desDir + "失败!");
        }
    }

    /**
     * @param srcFileNamePath 准备移动的源文件，绝对路径
     * @param desFileNamePath 目标文件名，绝对路径
     * @return
     * @方法描述：移动文件到目标文件
     * @参数描述：
     */
    public static void moveFile(String srcFileNamePath, String desFileNamePath) {
        copyFile(srcFileNamePath, desFileNamePath);
        delFile(srcFileNamePath);
    }

    /**
     * @param srcDir 准备拷贝的源目录，绝对路径
     * @param desDir 目标目录，绝对路径
     * @return
     * @方法描述：移动源文件夹下的全部内容到目标目录下
     * @参数描述：
     */
    public static void moveDir(String srcDir, String desDir) {
        copyDir(srcDir, desDir);
        delDir(srcDir);
    }

    /**
     * @param dir 文件夹名，绝对路径
     * @作者:network 时间：2007-9-17
     * @方法描述：如果文件夹不存在，则级联新建文件夹
     * @参数描述：
     */
    public static void makeDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * @param dir
     * @作者:network 时间：2007-9-17
     * @方法描述：如果文件夹不存在，则新建文件夹，如果文件不是文件夹，则删除文件，创建文件夹
     * @参数描述：
     */
    public static void makeDirIfExistFileDelete(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();// 级联创建
        }
        if (!file.isDirectory()) {
            file.delete();
            file.mkdir();
        }
    }

    /**
     * @param fileNamePath 文件名，绝对路径
     * @方法描述：删除文件
     * @参数描述：
     */
    public static void delFile(String fileNamePath) {
        if (fileNamePath == null || "".equals(fileNamePath))
            return;
        File aFile = new File(fileNamePath);
        if (!aFile.exists()) {
            logger.info("文件" + fileNamePath + "不存在或路径不合法");
            return;
        }
        try {
            if (aFile.delete()) {
                logger.info("文件[" + fileNamePath + "]删除成功");
            } else {
                logger.info("文件[" + fileNamePath + "]删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param dir 文件夹,绝对路径
     * @方法描述：删除文件夹
     * @参数描述：
     */
    public static void delDir(String dir) {
        try {
            delAllFileAndDirFromDir(dir); // 删除完里面所有内容
            File myFilePath = new File(dir);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("文件夹目录" + dir + "删除失败!");
        }
    }

    /**
     * @param dir 文件夹,绝对路径
     * @return
     * @方法描述：删除指定文件夹下的所有文件，保留该目录下的文件夹
     * @参数描述：
     */
    public static void delAllFileNotDirFromDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (dir.endsWith(File.separator)) {
                temp = new File(dir + tempList[i]);
            } else {
                temp = new File(dir + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                continue;
            }
        }
    }

    /**
     * @param dir 文件夹完整绝对路径
     * @return
     * @方法描述：删除指定文件夹下所有文件和文件夹
     * @参数描述：
     */
    public static void delAllFileAndDirFromDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (dir.endsWith(File.separator)) {
                temp = new File(dir + tempList[i]);
            } else {
                temp = new File(dir + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFileAndDirFromDir(dir + "/" + tempList[i]);// 先删除文件夹里面的文件
                delDir(dir + "/" + tempList[i]);// 再删除空文件夹
            }
        }
    }

    /**
     * 取文件名后缀，包含.，如果输入"file.txt",返回".txt"
     *
     * @param fileName
     * @return
     */
    public static String getFiletype(String fileName) {
        String type = "";
        if (fileName == null || fileName.equals(""))
            return type;
        int position = fileName.lastIndexOf(".");
        if (position >= 0) {
            type = fileName.substring(position, fileName.length());
            type = type.toLowerCase();
        }
        if (type.length() > 10)
            return "";
        return type;
    }

    /**
     * 根据文件大小返回适当的单位，比如G，M，K
     *
     * @param size
     */
    public static String getFileSizeDesc(long size) {
        DecimalFormat df = new DecimalFormat("0.00");
        float fsize = 0;
        if (size > 1024 * 1024 * 1024) {
            fsize = size / (1024 * 1024 * 1024f);
            return df.format(fsize) + "G";
        } else if (size > 1024 * 1024) {
            fsize = size / (1024 * 1024f);
            return df.format(fsize) + "M";
        } else if (size > 1024) {
            fsize = size / 1024f;
            return df.format(fsize) + "K";
        } else {
            return size + "Byte";
        }
    }

    /**
     * 根据文件大小返回适当的单位，比如G，M，K
     *
     * @param size
     */
    public static LabelValue getFileSizeDescLabelValue(long size) {
        DecimalFormat df = new DecimalFormat("0.00");
        float fsize = 0;
        if (size > 1024 * 1024 * 1024) {
            fsize = size / (1024 * 1024 * 1024f);
            return new LabelValue(df.format(fsize), "GB");
        } else if (size > 1024 * 1024) {
            fsize = size / (1024 * 1024f);
            return new LabelValue(df.format(fsize), "MB");
        } else if (size > 1024) {
            fsize = size / 1024f;
            return new LabelValue(df.format(fsize), "KB");
        } else {
            return new LabelValue(String.valueOf(size), "Byte");
        }
    }

    /**
     * @param fromPathFileName 原始文件（绝对路径）
     * @param toPathFileName   修改之后的文件保存路径（绝对路径）
     * @param maxWHSize        长宽最大的尺寸，一般以像素为单位，如果该值为0或-1，则不缩放
     * @return
     * @方法描述：修改指定图片文件的长宽比例，如果长宽不大于最大值，则不处理，否则按比例进行缩放
     * @参数描述：
     */
    public static void changeImageWHSize(String fromPathFileName, String toPathFileName, int maxWHSize) {
        if (maxWHSize <= 0) {
            FileUtil.copyFile(fromPathFileName, toPathFileName);
        }
        File fromFile = new File(fromPathFileName);
        if (!fromFile.exists()) {
            logger.info("源文件" + fromPathFileName + "不存在");
        }
        BufferedInputStream bis = null;
        Image image = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(fromFile));
            image = javax.imageio.ImageIO.read(bis);
            // 图片的原始大小
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            // 图片按比例缩小的大小
            int smallWid = 0;
            int smallHgt = 0;
            if (width < maxWHSize && height < maxWHSize) {
                // 原始图片拷贝
                copyFile(fromPathFileName, toPathFileName);
            } else {
                makeDir(toPathFileName.substring(0, toPathFileName.lastIndexOf("/") + 1));
                File toFile = new File(toPathFileName);
                // 考虑图片的大小,如果是高>宽,就把高变成最大值,宽按高的比例缩放
                if (height >= width) {
                    smallHgt = maxWHSize;
                    smallWid = (smallHgt * width) / height;
                } else {// 考虑图片的大小,如果是高<宽,就把宽变成最大值,高按宽的比例缩放
                    smallWid = maxWHSize;
                    smallHgt = (smallWid * height) / width;
                }
                // 缩放图片
                BufferedImage bi = new BufferedImage(smallWid, smallHgt, BufferedImage.TYPE_INT_RGB);
                bi.getGraphics().drawImage(image, 0, 0, smallWid, smallHgt, null);
                bos = new BufferedOutputStream(new FileOutputStream(toFile));
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
                encoder.encode(bi);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("源图片文件" + fromPathFileName + "缩放到" + toPathFileName + "失败!");
        } finally {
            try {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * @param fromPathFileName 原始文件（绝对路径）
     * @param toPathFileName   修改之后的文件保存路径（绝对路径）
     * @param maxSizeWidth     长最大的尺寸，一般以像素为单位，如果该值为0或-1，则不缩放
     * @param maxSizeHeight    宽最大的尺寸，一般以像素为单位，如果该值为0或-1，则不缩放
     * @return
     * @方法描述：修改指定图片文件的长宽比例，如果长宽不大于最大值，则不处理，否则按比例进行缩放
     * @参数描述：
     */
    public static void changeImageWHSize(String fromPathFileName, String toPathFileName, int maxSizeWidth, int maxSizeHeight) {
        if (maxSizeWidth <= 0 && maxSizeHeight <= 0) {
            FileUtil.copyFile(fromPathFileName, toPathFileName);
        }
        File fromFile = new File(fromPathFileName);
        if (!fromFile.exists()) {
            logger.info("源文件" + fromPathFileName + "不存在");
        }
        BufferedInputStream bis = null;
        Image image = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(fromFile));
            image = javax.imageio.ImageIO.read(bis);
            // 图片的原始大小
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            // 图片按比例缩小的大小
            int smallWid = 0;
            int smallHgt = 0;
            if (width < maxSizeWidth && height < maxSizeHeight) {
                // 原始图片拷贝
                copyFile(fromPathFileName, toPathFileName);
            } else {
                makeDir(toPathFileName.substring(0, toPathFileName.lastIndexOf("/") + 1));
                File toFile = new File(toPathFileName);
                // 考虑图片的大小,如果是高>宽,就把高变成最大值,宽按高的比例缩放
                if (height >= width) {
                    smallHgt = maxSizeHeight;
                    smallWid = (smallHgt * width) / height;
                } else {// 考虑图片的大小,如果是高<宽,就把宽变成最大值,高按宽的比例缩放
                    smallWid = maxSizeWidth;
                    smallHgt = (smallWid * height) / width;
                }
                // 缩放图片
                BufferedImage bi = new BufferedImage(smallWid, smallHgt, BufferedImage.TYPE_INT_RGB);
                bi.getGraphics().drawImage(image, 0, 0, smallWid, smallHgt, null);
                bos = new BufferedOutputStream(new FileOutputStream(toFile));
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
                encoder.encode(bi);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("源图片文件" + fromPathFileName + "缩放到" + toPathFileName + "失败!");
        } finally {
            try {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 判断给定文件路径和文件名是否存在
     *
     * @param fileNamePath
     * @return
     */
    public static boolean isExist(String fileNamePath) {
        if (fileNamePath == null)
            return false;
        File file = new File(fileNamePath);
        boolean bl = false;
        if (file.exists()) {
            bl = true;
        }
        file = null;
        return bl;
    }

    /**
     * 获取文件大小
     *
     * @param fileNamePath
     * @return
     */
    public static long getFileSize(String fileNamePath) {
        try {
            File file = new File(fileNamePath);
            if (!file.exists()) {
                return 0;
            }
            long size = file.length();
            return size;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 列出给定文件夹的文件名清单，给定文件夹，绝对路径,返回的是包含文件路径的文件名清单
     *
     * @param dir
     * @return
     */
    public static List<String> getFileListByDir(String dir) {
        File file = new File(dir);
        String[] fileNameArray = file.list();
        if (fileNameArray != null && fileNameArray.length > 0) {
            List<String> fileNameList = new ArrayList<String>();
            for (String string : fileNameArray) {
                fileNameList.add(dir + "/" + string);
            }
            return fileNameList;
        }
        return null;
    }

    /**
     * 列出给定文件夹的文件名清单，给定文件夹，绝对路径,返回的是包含文件名的文件路径，文件所在路径，文件名带后缀名，文件名,路径序号
     *
     * @param dir
     * @param index
     * @return
     */
    public static List<String[]> getFileListByDirName(String dir, String index) {
        File file = new File(dir);
        String[] fileNameArray = file.list();
        if (fileNameArray != null && fileNameArray.length > 0) {
            List<String[]> fileNameList = new ArrayList<String[]>();
            for (String fileName : fileNameArray) {
                fileNameList.add(new String[]{dir + "/" + fileName, dir, fileName, fileName.substring(0, fileName.lastIndexOf(".")), index});
            }
            return fileNameList;
        }
        return null;
    }

    /**
     * 返回文件名，不包括文件扩展名
     *
     * @param fileNamePath
     * @return
     */
    public static String getFileName(String fileNamePath) {
        File file = new File(fileNamePath);
        if (file.isFile()) {
            String fileName = file.getName();
            if (fileName.indexOf(".") > -1) {
                return fileName.substring(0, fileName.lastIndexOf("."));
            } else {
                return fileName;
            }
        }
        return null;
    }

    /**
     * 获取文件的最后更新时间,Date类型的文件最后更新时间(文件不存在的话返回空)
     *
     * @param file
     * @return
     */
    public static Date getFileLastModifiedTime(File file) {
        if (file == null)
            return null;
        if (file.exists() == false)
            return null;
        return new Date(file.lastModified());
    }

    public static LabelValue getImageWidthHeight(String imageFilePathName) {
        File fromFile = new File(imageFilePathName);
        if (!fromFile.exists()) {
            logger.info("源文件" + imageFilePathName + "不存在");
            return null;
        }
        BufferedInputStream bis = null;
        Image image = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(fromFile));
            image = javax.imageio.ImageIO.read(bis);
            // 图片的原始大小
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            logger.info("width=" + width + "  height=" + height);
            return new LabelValue(String.valueOf(width), String.valueOf(height));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (bis != null)
                    bis.close();
                if (image != null)
                    image.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 判断图片文件大小是否大于给定的长宽大小
     *
     * @param imageUrl
     * @param width
     * @param height
     * @return
     */
    public static boolean isValidImageWidthHeight(String imageUrl, int width, int height) {
        BufferedInputStream bis = null;
        Image image = null;
        URL url = null;
        try {
            url = new URL(imageUrl);
            bis = new BufferedInputStream(url.openStream());
            image = javax.imageio.ImageIO.read(bis);
            // 图片的原始大小
            int w = image.getWidth(null);
            int h = image.getHeight(null);
            logger.info("w=" + w + "  h=" + h);
            if (w >= width && h >= height)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null)
                    bis.close();
                if (image != null)
                    image.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 删除基本路径里面的文件
     *
     * @param fileName
     */
    public static void delBasePathFile(String fileName) {
        try {
            String pathFileName = SysConstants.ROOTPATH + fileName;
            delFile(pathFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件夹下面的文件数目
     *
     * @param abFolderPath
     * @return
     */
    public static int getFileNumInFolder(String abFolderPath) {
        if (isExist(abFolderPath)) {
            int fileCount = 0;
            File d = new File(abFolderPath);
            File list[] = d.listFiles();
            for (int i = 0; i < list.length; i++) {
                if (list[i].isFile())
                    fileCount++;
            }
            return fileCount;
        } else
            return 0;
    }

    /**
     * 获取文件夹下面的文件夹数目
     *
     * @param abFolderPath
     * @return
     */
    public static int getFolderNumInFolder(String abFolderPath) {
        if (isExist(abFolderPath)) {
            int folderCount = 0;
            File d = new File(abFolderPath);
            File list[] = d.listFiles();
            for (int i = 0; i < list.length; i++) {
                if (!list[i].isFile())
                    folderCount++;
            }
            return folderCount;
        } else
            return 0;
    }
}
