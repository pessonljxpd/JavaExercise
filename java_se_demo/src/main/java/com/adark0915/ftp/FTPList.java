package com.adark0915.ftp;

import com.adark0915.zip.CloseUtils;
import com.adark0915.zip.StringUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Shelly on 2017-10-17.
 */
public class FTPList {
    public FTPClient ftp;
    public ArrayList<String> arFiles;

    /**
     * 重载构造函数
     *
     * @param isPrintCommmand 是否打印与FTPServer的交互命令
     */
    public FTPList(boolean isPrintCommmand) {
        ftp = new FTPClient();
        this.ftp.setControlEncoding("GBK");
        this.ftp.setConnectTimeout(3000);
        this.ftp.setDataTimeout(3000);
        arFiles = new ArrayList<>();
        if (isPrintCommmand) {
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        }
    }

    /**
     * 登陆FTP服务器
     *
     * @param host     FTPServer IP地址
     * @param port     FTPServer 端口
     * @param username FTPServer 登陆用户名
     * @param password FTPServer 登陆密码
     * @return 是否登录成功
     * @throws IOException
     */
    public boolean login(String host, int port, String username, String password) throws IOException {
        this.ftp.connect(host, port);
        if (FTPReply.isPositiveCompletion(this.ftp.getReplyCode())) {
            if (this.ftp.login(username, password)) {
                return true;
            }
        }
        disConnection();
        return false;
    }

    /**
     * 关闭数据链接
     *
     * @throws IOException
     */
    public void disConnection() throws IOException {
        if (this.ftp.isConnected()) {
            this.ftp.disconnect();
        }
    }

    /**
     * 递归遍历目录下面指定的文件名
     *
     * @param pathName 需要遍历的目录，必须以"/"开始和结束
     * @param regex    文件名的匹配规则
     * @throws IOException
     */
    public void list(String pathName, String regex) throws IOException {
        if (pathName.startsWith("/") && pathName.endsWith("/")) {
            String directory = pathName;
            //更换目录到当前目录
            this.ftp.changeWorkingDirectory(directory);
            FTPFile[] files = this.ftp.listFiles();
            for (FTPFile file : files) {
                if (file.isFile()) {
                    if (file.getName().matches(regex)) {
                        arFiles.add(directory + file.getName());
                    }
                }
            }
        }
    }

    //    /**
//     * 递归遍历目录下面指定的文件名
//     *
//     * @param pathName 需要遍历的目录，必须以"/"开始和结束
//     * @param ext      文件的扩展名
//     * @throws IOException
//     */
//    public void list(String pathName,String ext) throws IOException{
//        if(pathName.startsWith("/")&&pathName.endsWith("/")){
//            String directory = pathName;
//            //更换目录到当前目录
//            this.ftp.changeWorkingDirectory(directory);
//            FTPFile[] files = this.ftp.listFiles();
//            for(FTPFile file:files){
//                if(file.isFile()){
//                    if(file.getName().endsWith(ext)){
//                        arFiles.add(directory+file.getName());
//                    }
//                }else if(file.isDirectory()){
//                    list(directory+file.getName()+"/",ext);
//                }
//            }
//        }
//    }
    public static void main(String[] args) throws IOException {
        FTPList f = new FTPList(true);
        if (f.login("192.168.151.4", 21, "anonymous", "gwap")) {
            String regex = "(b|f)(\\d*_)*\\d*.(html|.txt)";
            f.list("/error/", regex);
            f.list("/", regex);
        }
//        Iterator<String> it = f.arFiles.iterator();
//        while (it.hasNext()) {
//            System.out.println(it.next());
//        }

        System.out.println("=================================================");
        System.out.println("=================================================");

        String[] faultDataFilePath = f.getFaultDataFilePath();
        for (int i = 0; i < faultDataFilePath.length; i++) {
            System.out.println(faultDataFilePath[i]);
        }

        File file = new File(LOCAL_PATH);
        String[] localFile = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches("(b|f)(\\d*_)*\\d*.(html|.txt)");
            }
        });

        //去除本地已经存在的文件，避免重复下载
        ArrayList<String> list1  = (ArrayList<String>) StringUtils.arrayToList(faultDataFilePath);
        ArrayList<String> list2 = (ArrayList<String>) StringUtils.arrayToList(localFile);
        list1.removeAll(list2);

        faultDataFilePath = new String[list1.size()];
        for (int i = 0; i < list1.size(); i++) {
            faultDataFilePath[i] = list1.get(i);
        }

        f.downloadFile(faultDataFilePath);

        f.disConnection();
    }

    private String[] getFaultDataFilePath() {
        List<String> fFilePaths = new ArrayList<>();
        List<String> bFilePaths = new ArrayList<>();
        for (int i = 0; i < arFiles.size(); i++) {
            String filePath = arFiles.get(i);
            String[] split = filePath.split("/");
            String fileName = split[split.length - 1];
            if (fileName.startsWith("f")) {
                fFilePaths.add(filePath);
            }
            if (fileName.startsWith("b")) {
                bFilePaths.add(filePath);
            }
        }

        String fFilePathRecently = getRecentlyFilePath(fFilePaths);
        String bFilePathRecently = getRecentlyFilePath(bFilePaths);

        return distinctFile(new String[]{fFilePathRecently, bFilePathRecently});
    }

    private String[] distinctFile(String[] pStrings) {
        // local file
        File file = new File(LOCAL_PATH);
        String[] localFile = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches("(b|f)(\\d*_)*\\d*.(html|.txt)");
            }
        });

        LinkedList<String> strings = new LinkedList<>();
        for (int i = 0; i < pStrings.length; i++) {
            strings.add(pStrings[i]);
        }

        LinkedList<String> tmp = new LinkedList<>();

        for (int i = 0; i < strings.size(); i++) {
            for (int j = 0; j < localFile.length; j++) {
                if (pStrings[i].endsWith(localFile[j])) {
                    tmp.add(strings.get(i));
                    break;
                }
            }
        }

        strings.removeAll(tmp);

        return strings.toArray(new String[strings.size()]);
    }

    private String getRecentlyFilePath(List<String> pFilePaths) {
        String filePathRecently = null;
        long dateRecently = 0L;
        for (int i = 0; i < pFilePaths.size(); i++) {
            String filePath = pFilePaths.get(i);
            String[] split = filePath.split("/");
            String fileName = split[split.length - 1];
            long date = Long.valueOf(fileName.substring(1, 16).replaceAll("_", ""));
            if (dateRecently < date) {
                dateRecently = date;
                filePathRecently = filePath;
            }
        }

        return filePathRecently;
    }


    private static String LOCAL_PATH = "D:\\001\\";

    private void downloadFile(String[] pFilePaths) {
        for (int i = 0; i < pFilePaths.length; i++) {
            boolean unableRename = false;
            String[] split = pFilePaths[i].split("/");
            String fileName = split[split.length - 1];
            File destFile = new File(LOCAL_PATH + fileName);
            File tmpFile = new File(LOCAL_PATH + UUID.randomUUID().toString() + ".tmp");
            BufferedOutputStream bufferedOutputStream = null;
            BufferedInputStream bufferedInputStream = null;
            byte[] buff = new byte[1024 * 1024];
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(tmpFile);
                bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                InputStream   inputStream = ftp.retrieveFileStream(pFilePaths[i]);
                bufferedInputStream = new BufferedInputStream(inputStream);

                int len;
                while ((len = bufferedInputStream.read(buff)) != -1) {
                    bufferedOutputStream.write(buff, 0, len);
                }

                bufferedOutputStream.flush();
            } catch (IOException pE) {
                unableRename = true;
                pE.printStackTrace();
            } finally {
                CloseUtils.closeIO(bufferedOutputStream, bufferedInputStream);
                try {
                    ftp.completePendingCommand();
                } catch (IOException pE) {
                    pE.printStackTrace();
                }
                if (!unableRename && tmpFile.exists() && tmpFile.isFile()) {
                    boolean renameTo = tmpFile.renameTo(destFile);
                    if (!renameTo) { //说明故障文件已经存在，无需重复下载
                        tmpFile.delete();
                    }
                }
            }
        }
    }

}
