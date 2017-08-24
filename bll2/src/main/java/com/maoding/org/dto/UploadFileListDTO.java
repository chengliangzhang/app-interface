package com.maoding.org.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;

public class UploadFileListDTO extends BaseDTO{

   List<UploadFileDTO> fileList = new ArrayList<UploadFileDTO>();

    public List<UploadFileDTO> getFileList() {
        return fileList;
    }

    public void setFileList(List<UploadFileDTO> fileList) {
        this.fileList = fileList;
    }
}