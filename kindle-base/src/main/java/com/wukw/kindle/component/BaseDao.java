package com.wukw.kindle.component;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wukw.kindle.repo.dto.YbPageInfoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public abstract class BaseDao<T> implements IDao<T> {

	public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    public int save(T entity) {
        return mapper.insert(entity);
    }

    public int delete(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    public int updateNotNull(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    public YbPageInfoResult<List<T>> selectPageByExample(Integer page, Integer pageSize, Object example) {
        YbPageInfoResult<List<T>> result =new YbPageInfoResult();
        Page<T> pagetotal = null;
        if(page!=null&&pageSize!=null) {
            pagetotal  =  PageHelper.startPage(page, pageSize, true);
        }
        List<T> list=mapper.selectByExample(example);
        result.setResult(list);
        if(pagetotal != null) {
            result.setPageNum(pagetotal.getPageNum());
            result.setPageTotalNum(pagetotal.getPages());
            result.setTotalNum(pagetotal.getTotal());
        }
        return result;
    }
    
    //TODO 其他...
}

