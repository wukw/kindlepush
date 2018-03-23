package com.wukw.kindle.kindle.Model;

import lombok.Data;

import java.util.List;

@Data
public class Sources {
    String  view_type;
    String  view_type_sidebar_priority;
    String  view_host;
    String  view_host_link;
    String  log;
    String  duration;
    List<Detail>  details;



}
