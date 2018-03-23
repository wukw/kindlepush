package com.wukw.kindle.kindle.Model;

import lombok.Data;

import java.util.List;
@Data
public class Resource {


        private String status;
        private String status_extra;
        private String log;
        private int count;
        private List<Sources> sources;



}
