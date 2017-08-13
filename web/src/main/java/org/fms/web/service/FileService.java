package org.fms.web.service;

/**
 * Created by lion on 2017/8/11.
 */
public interface FileService {
    public void fileDownload();
    public void fileUpload();
    public void fileDelete();
    public void fileMove();
    public void mkdir();
    public void rmdir();
    public void dir();
}
