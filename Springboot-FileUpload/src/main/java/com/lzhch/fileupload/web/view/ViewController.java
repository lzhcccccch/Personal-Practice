package com.lzhch.fileupload.web.view;

import com.lzhch.fileupload.feign.dto.res.FileDownloadRes;
import com.lzhch.fileupload.web.controller.SlicesUploadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @packageName： com.lzhch.fileupload.controller.index
 * @className: ViewController
 * @description: TODO
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-07-20 10:16
 */
@Controller
@RequestMapping("/controller")
public class ViewController {

    @Autowired
    SlicesUploadController uploadController;

    // 往界面中传参时, 无论是 Map 还是 Model 或者 ModelAndView, 都必须在入参中定义, 不能在方法中定义
    @RequestMapping("/index")
    public String index(Map<String, Object> map) {
        //return "fileupload1";
        //return "fastdfsupload";
        List<FileDownloadRes> list = uploadController.getFile();
        map.put("data", list);
        return "slicesupload";
    }

    @RequestMapping("/test")
    public String test() {
        return "fileupload1";
    }
}
