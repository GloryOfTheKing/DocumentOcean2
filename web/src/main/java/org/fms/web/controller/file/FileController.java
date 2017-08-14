package org.fms.web.controller.file;


import org.fms.web.service.FileService;
import org.fms.web.utils.results.ContentResults;
import org.fms.web.utils.results.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.*;

/**
 * Created by lion on 2017/8/13.
 */
@RestController
@RequestMapping("/file")
@ConfigurationProperties(prefix = "filepath")
public class FileController {

    //根目录
    public static String ROOT;

    @Autowired
    FileService fileService;


    //  展示文件列表
    @RequestMapping(value = "list/{path}",method = RequestMethod.GET)
    public ContentResults<List> list(@PathVariable String path/*需要显示的目录路径*/) throws ServletException {
        return fileService.findList(path);
    }

    //创建文件夹
    @RequestMapping(path = "/createFolder/{newPath}",method = RequestMethod.GET)
    public Results createFolder(@PathVariable String newPath) {
        return fileService.mkdir(newPath);
    }

    public static String getROOT() {
        return ROOT;
    }

    public static void setROOT(String ROOT) {
        FileController.ROOT = ROOT;
    }
}
