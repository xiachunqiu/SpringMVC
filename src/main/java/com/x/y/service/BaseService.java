package com.x.y.service;

import com.x.y.dao.IDaoImpl.CommonDaoImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Service
public class BaseService {
    @Autowired
    private CommonDaoImpl commonDao;
}