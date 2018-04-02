package com.wukw.kindle.Dao;

import com.wukw.kindle.component.BaseDao;
import com.wukw.kindle.repo.model.Book;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Repository
public class BookDao extends BaseDao<Book> {

    public List<Book> selectBookPage (String key,Integer page,Integer pageSize){
        Example example =new Example(Book.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name",key);
        return  this.selectPageByExample(page,pageSize,example).getResult();
    }


}
