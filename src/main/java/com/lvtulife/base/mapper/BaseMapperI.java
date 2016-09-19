package com.lvtulife.base.mapper;

import com.lvtulife.base.service.BaseServiceI;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lvtulife on 2016-05-22 .
 */
public interface BaseMapperI<T> extends BaseServiceI<T> {

    /**
     * 新增(不会将序列生成的ID,注入)
     * 效率较save(T t)高
     *
     * @param t
     */
    //void createToMyBatis(T t);

    /**
     * 批量新增(不会将序列生成的ID,注入)
     * 效率较saveOfBatch(List<T> tList)高
     *
     * @param tList
     */
    //void createOfBatchToMyBatis(List<T> tList);

    /**
     * 批量新增(不会将序列生成的ID,注入并且不会进行flushStatements)
     * 效率较saveOfBatch(List<T> tList)高
     *
     * @param tList
     */
    //public void createOfBatchNonFlushToMyBatis(List<T> tList);

    /**
     * 新增(会将序列生成的ID,注入)
     *
     * @param t
     */
    //Integer saveToMyBatis(T t);

    /**
     * 批量新增(会将序列生成的ID,注入)
     *
     * @param tList
     */
    //List<Integer> saveOfBatchToMyBatis(List<T> tList);

    /**
     * 根据ID进行删除
     *
     * @param id
     */
    //void removeByIdToMyBatis(Integer id);

    /**
     * 根据ids进行批量删除
     *
     * @param ids
     */
    //void removeOfBatchToMyBatis(List<Integer> ids);


    //void removeAllToMyBatis();

    /**
     * 更新,字段为空，则不进行更新
     *
     * @param t
     * @param 是否忽略空值,默认为true. 如为false,则会将不赋值的字段置为NULL
     */
    //void modifyToMyBatis(T t, Boolean... ignoreNull);

    /**
     * 批量更新
     *
     * @param tList
     * @param 是否忽略空值,默认为true. 如为false,则会将不赋值的字段置为NULL
     */
    //void modifyOfBatchToMyBatis(List<T> tList, Boolean... ignoreNull);

    /**
     * 根据ID获取对象
     *
     * @param id
     * @return
     */
    T findOneByIdToMyBatis(Integer id);

    /**
     * 根据字段名进行查询
     *
     * @param columnName 字段名
     * @param val        对应的值
     * @param symbol     连接符号
     */
    T findOneByColumnNameToMyBatis(String columnName, Object val, String... symbol);

    /**
     * 根据字段名进行查询
     *
     * @param columnName 字段名
     * @param val        对应的值
     * @param symbol     连接符号
     */
    List<T> findListByColumnNameToMyBatis(String columnName, Object val, String... symbol);

    /**
     * 获取所有的对象
     *
     * @return
     */
    List<T> findAllToMyBatis();

    /**
     * 获取记录数
     *
     * @return
     */
    Long findAllCountToMyBatis();

    /**
     * 根据特定字段删除数据
     * fieldName 用于当做条件的字段名称请用大写
     */
    //void removeObjOfBatchByColumnToMyBatis(List<T> list, String fieldName);


    /**
     * 根据字段名进行删除
     *
     * @param columnName 字段名
     * @param val        对应的值
     * @param symbol     连接符号
     */
    //void removeByColumnNameToMyBatis(String columnName, Object val, String... symbol);

    /**
     * 根据字段名进行删除
     *
     * @param columnName 字段名
     * @param vals       对应的值
     */
    //void removeOfBatchByColumnNameToMyBatis(String columnName, Object... vals);


    /**
     * 是否存在
     *
     * @param id
     * @return
     */
    Boolean isExistsToMyBatis(Integer id);

    /**
     * 是否存在
     *
     * @param columnName
     * @param val
     * @param symbol
     * @return
     */
    Boolean isExistsToMyBatis(String columnName, Object val, String... symbol);

}
