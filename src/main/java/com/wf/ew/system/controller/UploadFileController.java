package com.wf.ew.system.controller;


import com.wf.ew.common.JsonResult;
import com.wf.ew.common.PageResult;
import com.wf.ew.system.service.UploadFileService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * UploadFileController
 * <p>
 * api:单张图片上传接口 /miniApi/picture/uploadOne post
 * api:删除图片接口  /miniApi/picture/delete  delete
 *
 * @author juan
 * @date 2018/7/18 22:51
 */
@RestController
@RequestMapping("miniApi")
@Api(description = "图片上传模块")
public class UploadFileController {

    private static final Logger logger = LoggerFactory.getLogger(UploadFileController.class);

    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/picture/uploadOne")
    @ApiOperation(value = "单图片上传",response = JsonResult.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "成功上传"),
            @ApiResponse(code =500,message = "上传失败")
    })
    @ApiImplicitParam(name = "file",value = "新增图片")
    public JsonResult uploadOnePicture(MultipartFile file) {
        logger.info("============>单图片上传 file={}", file);
        String uploadFile = uploadFileService.uploadFile(file);
        return JsonResult.ok(uploadFile);
    }

    @DeleteMapping("/picture/delete")
    @ApiOperation(value = "图片删除")
    public JsonResult deletePicture(String fileKey) {
        logger.info("============>图片删除 fileKey={}", fileKey);
        String deletePicture = uploadFileService.deletePicture(fileKey);
        return JsonResult.ok(deletePicture);
    }


}
