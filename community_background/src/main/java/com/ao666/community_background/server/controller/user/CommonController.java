package com.ao666.community_background.server.controller.user;

import com.ao666.community_background.common.constant.MessageConstant;
import com.ao666.community_background.common.result.Result;
import com.ao666.community_background.common.util.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/user/common/")
@Api(tags = "文件上传通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(@RequestBody MultipartFile file){ // 与前端提交的参数名保持一致
      log.info("文件上传:{}", file);

        try {
            // 原始文件名
            String originalFileName = file.getOriginalFilename();
            // 获取原始文件名的后缀 dasd.png
            String tail = originalFileName.substring(originalFileName.lastIndexOf("."));
            // 使用uuid构造新文件名称
            String objectName = UUID.randomUUID() + tail;


            //
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            log.info("上传的图片地址:{}", filePath);
            return Result.success(filePath);
        } catch (IOException e) {
            log.info("文件上传失败:{}", e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
