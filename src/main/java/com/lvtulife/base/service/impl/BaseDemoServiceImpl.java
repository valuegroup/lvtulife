package com.lvtulife.base.service.impl;

import org.springframework.stereotype.Service;

import com.lvtulife.base.model.BaseDemo;
import com.lvtulife.base.service.BaseDemoServiceI;

@Service(value="BaseDemoService")
public class BaseDemoServiceImpl extends BaseServiceImpl<BaseDemo> implements BaseDemoServiceI {

}
