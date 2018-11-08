package com.greer.springbootdemo2.IService;

import com.greer.springbootdemo2.bean.Mother;
import com.greer.springbootdemo2.dao.MotherDao;
import com.greer.springbootdemo2.service.MotherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotherServiceImpl implements MotherService {
    @Autowired
    private MotherDao motherDao ;
    @Override
    public Mother getMother(Integer id) {
        Mother mother = motherDao.getById(id) ;
        return mother;
    }
}
