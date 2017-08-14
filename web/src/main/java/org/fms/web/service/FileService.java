package org.fms.web.service;

import org.fms.web.utils.results.ContentResults;
import org.fms.web.utils.results.Results;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by lion on 2017/8/11.
 */
public interface FileService {

    public Results upload(String destination, HttpServletRequest request);

    public Results preview(HttpServletResponse response, String path) throws IOException;

    public ContentResults<List> findList(String path);

    public Results mkdir(String newPath);

    public Results rename(String item,String newItemPath);

    public Results move(String newPath,List<String> items);

    public Results remove(List<String> items);
}
