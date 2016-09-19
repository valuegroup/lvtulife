package com.lvtulife.base.service;

import com.lvtulife.base.model.BaseDictionary;

import java.util.List;

/**
 * 资源表业务
 * @author valuegroup
 *
 */
public interface BaseDictionaryServiceI extends BaseServiceI<BaseDictionary>{
	public List<BaseDictionary> findDictionarys();
}
