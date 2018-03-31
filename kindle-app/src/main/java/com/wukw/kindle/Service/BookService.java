package com.wukw.kindle.Service;

import com.wukw.kindle.Dao.BookDao;
import com.wukw.kindle.repo.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookDao bookDao;

    public List<Book> quertBookList(String key,Integer page,Integer pageSize){
        return bookDao.selectBookPage(key,page,pageSize);
    }
}
