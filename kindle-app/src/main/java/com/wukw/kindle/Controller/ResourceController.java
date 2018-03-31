package com.wukw.kindle.Controller;

import com.wukw.kindle.Model.DataInfo;
import com.wukw.kindle.Service.JiuMoResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("resource")
public class ResourceController {

    @Autowired
    JiuMoResourceService resourceService;

    @GetMapping("queryoriginpage")
    public List<DataInfo> queryOriginPage(String key){
        try {
            return resourceService.getOriginResource(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



}
