package com.wf.ew.system.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * UploadFileService
 *
 * @author juan
 * @date 2018/8/30 13:56
 */
public interface UploadFileService {
    String uploadFile(MultipartFile file);

    String deletePicture(String fileKey);
}
