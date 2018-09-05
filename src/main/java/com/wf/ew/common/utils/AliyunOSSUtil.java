package com.wf.ew.common.utils;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.wf.ew.common.config.ConstantProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * AliyunOSSUtil
 *
 * @author juan
 * @date 2018/8/2 10:52
 */
public class AliyunOSSUtil {
    public static final Logger logger = LoggerFactory.getLogger(AliyunOSSUtil.class);


    public static  String upload(File file){
        logger.info("=========>OSS文件上传开始："+file.getName());
        String endpoint=ConstantProperties.END_POINT;
        String accessKeyId= ConstantProperties.KEY_ID;
        String accessKeySecret=ConstantProperties.KEY_SECRET;
        String bucketName=ConstantProperties.BUCKET_NAME1;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(new Date());

        if(null == file){
            return null;
        }

        OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);
        try {
            //容器不存在，就创建
            if(! ossClient.doesBucketExist(bucketName)){
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            //创建文件路径
            String fileUrl = (dateStr.replace("-","") + "/" + UUID.randomUUID().toString().replace("-","")+"-"+file.getName());
            //上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            //设置权限 这里是公开读
            ossClient.setBucketAcl(bucketName,CannedAccessControlList.PublicRead);
            if(null != result){
                logger.info("==========>OSS文件上传成功,OSS地址："+fileUrl);
                return fileUrl;
            }
        }catch (OSSException oe){
            logger.error(oe.getMessage());
        }catch (ClientException ce){
            logger.error(ce.getMessage());
        }finally {
            //关闭
            ossClient.shutdown();
        }
        return null;
    }


    /**
     * 删除Object
     * @param fileKey
     * @return
     */
    public static String deletePicture(String fileKey){
        logger.info("=========>OSS文件删除开始");
        String endpoint=ConstantProperties.END_POINT;
        String accessKeyId=ConstantProperties.KEY_ID;
        String accessKeySecret=ConstantProperties.KEY_SECRET;
        String bucketName=ConstantProperties.BUCKET_NAME1;
        try {
            OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);

            if(!ossClient.doesBucketExist(bucketName)){
                logger.info("==============>您的Bucket不存在");
                return "您的Bucket不存在";
            }else {
                logger.info("==============>开始删除Object");
                ossClient.deleteObject(bucketName,fileKey);
                logger.info("==============>Object删除成功："+fileKey);
                return "==============>Object删除成功："+fileKey;
            }
        }catch (Exception ex){
            logger.info("删除Object失败",ex);
            return "删除Object失败";
        }
    }
}
