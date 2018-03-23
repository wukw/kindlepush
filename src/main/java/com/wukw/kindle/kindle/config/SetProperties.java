package com.wukw.kindle.kindle.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("SetProperties")
@Data
public class SetProperties {
    @Value("${file.savepath}")
    String savepath;
    @Value("${originurl}")
    String originurl;


}
