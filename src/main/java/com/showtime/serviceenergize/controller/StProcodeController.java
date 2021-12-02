package com.showtime.serviceenergize.controller;


import com.showtime.common.model.dto.ResponseJsonCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * 书唐省市区代码表 前端控制器
 * </p>
 *
 * @author cjb
 * @since 2020-01-17
 */
@RestController
@RequestMapping("/stProcode")
@Api(tags = "测试下图片上传")
public class StProcodeController {

    private String imgPath = "F:\\存入的图片位置";

    @PostMapping("testUplod")
    public ResponseJsonCode testUplod(@RequestParam(value="file",required=false) MultipartFile file, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        File targetFile=null;
        //返回存储路径
        String url="";
        int code=1;
        System.out.println(file);
        //获取文件名加后缀
        String fileName=file.getOriginalFilename();
        if(fileName!=null&&fileName!=""){
            //存储路径
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/"+imgPath;
            //文件存储位置
            String path = imgPath;
            //文件后缀
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            //新的文件名
            fileName=System.currentTimeMillis()+"_"+new Random().nextInt(1000)+fileF;

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            File file1 =new File(path+"/"+fileAdd);
            //如果文件夹不存在则创建
            if(!file1 .exists()  && !file1 .isDirectory()){
                file1 .mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);
                url=returnUrl+fileAdd+"/"+fileName;
                map.put("url", url);
                map.put("fileName", fileName);
                return ResponseJsonCode.creatResponseJsonCode(200,url);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseJsonCode.cusResponseJsonCode(500,"上传失败");
            }
        }
        return ResponseJsonCode.cusResponseJsonCode(500,"上传失败");
    }

}

