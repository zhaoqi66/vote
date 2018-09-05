package com.wf.ew.system.service.impl;



import com.wf.ew.common.utils.AliyunOSSUtil;
import com.wf.ew.system.service.UploadFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * UploadFileServiceImpl
 *
 * @author juan
 * @date 2018/7/18 22:53
 */
@Service
@Transactional
public class UploadFileServiceImpl implements UploadFileService {


    @Override
    public String uploadFile(MultipartFile file) {
        String uploadUrl =null;
        try {
            if (null != file) {
                String filename = file.getOriginalFilename();
                String newFileName= UUID.randomUUID().toString()+"."+filename.substring(filename.lastIndexOf(".")+1);
                if (!"".equals(newFileName.trim())) {
                    File newFile = new File(newFileName);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    //上传到OSS
                    uploadUrl = AliyunOSSUtil.upload(newFile);
                    if (newFile.exists()) {
                        newFile.delete();
                    }

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "https://vote-mall.oss-cn-beijing.aliyuncs.com/"+uploadUrl;
    }

    @Override
    public String deletePicture(String fileKey) {
        String deletePicture = AliyunOSSUtil.deletePicture(fileKey);
        return deletePicture;
    }

}
