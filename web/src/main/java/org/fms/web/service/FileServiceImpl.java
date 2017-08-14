package org.fms.web.service;


import org.fms.web.utils.results.ContentResults;
import org.fms.web.utils.results.Results;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lion on 2017/8/11.
 */

@Service
@ConfigurationProperties(prefix = "filepath")
public class FileServiceImpl implements FileService{

    private static String ROOT;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(FileService.class);


    @Override
    public Results upload(String destination, HttpServletRequest request) {
        try {
            // Servlet3.0方式上传文件
            Collection<Part> parts = request.getParts();

            for (Part part : parts) {
                if (part.getContentType() != null) {  // 忽略路径字段,只处理文件类型
                    String path = ROOT + destination;

                    File f = new File(path, org.fms.web.utils.file.FileUtils.getFileName(part.getHeader("content-disposition")));
                    if (!org.fms.web.utils.file.FileUtils.write(part.getInputStream(), f)) {
                        throw new Exception("文件上传失败");
                    }
                }
            }
            return new Results(200,"Upload file success");
        } catch (Exception e) {
            return new Results(500,e.getMessage());
        }
    }

    @Override
    public Results preview(HttpServletResponse response, String path) throws IOException {

        File file = new File(ROOT, path);
        if (!file.exists()) {
            return new Results(404,"Resource Not Found");
        }

        /*
         * 获取mimeType
         */
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", URLEncoder.encode(file.getName(), "UTF-8")));
        response.setContentLength((int) file.length());

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
        return new Results(200,"Resource Found");
    }


    //查看目录
    @Override
    public ContentResults<List> findList(String path) {
            // 返回的结果集
            List<Map> fileItems = new ArrayList<Map>();

            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(ROOT, path))) {

                String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat dt = new SimpleDateFormat(DATE_FORMAT);
                for (Path pathObj : directoryStream) {
                    // 获取文件基本属性
                    BasicFileAttributes attrs = Files.readAttributes(pathObj, BasicFileAttributes.class);

                    // 封装返回JSON数据
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("name", pathObj.getFileName().toString());
                    //  fileItem.put("rights", org.shaofan.utils.FileUtils.getPermissions(pathObj)); // 文件权限
                    map.put("date", dt.format(new Date(attrs.lastModifiedTime().toMillis())));
                    map.put("size", attrs.size());
                    map.put("type", attrs.isDirectory() ? "dir" : "file");
                    fileItems.add(map);
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
                return new ContentResults<List>(200,e.getMessage(),null);
            }
            return new ContentResults<List>(200,"Find Dir Success",fileItems);
    }

    //创建文件夹
    @Override
    public Results mkdir(String newPath) {
        try {
            File newDir = new File(ROOT + newPath);
            if (!newDir.mkdir()) {
                throw new Exception("不能创建目录: " + newPath);
            }
            return new Results(200,"Create Dir Success");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new Results(200,e.getMessage());
        }
    }

    //重命名
    @Override
    public Results rename(String path, String newPath) {
        try {

            File srcFile = new File(ROOT, path);
            File destFile = new File(ROOT, newPath);
            if (srcFile.isFile()) {
                FileUtils.moveFile(srcFile, destFile);
            } else {
                FileUtils.moveDirectory(srcFile, destFile);
            }
            return new Results(200,"Rename Dir or File Success");
        } catch (Exception e) {
            return new Results(500,e.getMessage());
        }
    }

    //文件移动
    @Override
    public Results move(String newpath, List<String> items) {
        try {

            for (int i = 0; i < items.size(); i++) {
                String path = items.get(i);
                File srcFile = new File(ROOT, path);
                File destFile = new File(ROOT + newpath, srcFile.getName());

                if (srcFile.isFile()) {
                    FileUtils.moveFile(srcFile, destFile);
                } else {
                    FileUtils.moveDirectory(srcFile, destFile);
                }
            }
            return new Results(200,"Move Dir or File Success");
        } catch (Exception e) {
            return new Results(500,e.getMessage());
        }
    }

    //删除文件或目录
    @Override
    public Results remove(List<String> items) {
        try {
            for (int i = 0; i < items.size(); i++) {
                String path = items.get(i);
                File srcFile = new File(ROOT, path);
                if (!FileUtils.deleteQuietly(srcFile)) {
                    throw new Exception("删除失败: " + srcFile.getAbsolutePath());
                }
            }
            return new Results(200,"Remove Dir or File Success");
        } catch (Exception e) {
            return new Results(500,e.getMessage());
        }
    }

    public static String getROOT() {
        return ROOT;
    }

    public static void setROOT(String ROOT) {
        FileServiceImpl.ROOT = ROOT;
    }
}
