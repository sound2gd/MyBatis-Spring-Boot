package com.sound2gd.common.crud;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 基础CRUD的封装
 * Created by cris on 2017/6/29.
 */
public abstract class CRUDService<D extends BaseMapper<T>, T extends BaseEntity> {

    @Autowired
    protected D dao;

    /**
     * 条件查找
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
        if (entity.getPage() != null && entity.getRows() != null) {
            PageHelper.startPage(entity.getPage(), entity.getRows());
        }
        return dao.selectAll();
    }

    /**
     * 根据id查找记录
     *
     * @param id
     * @return
     */
    public T getById(Object id) {
        return dao.selectByPrimaryKey(id);
    }

    public void deleteById(Object id) {
        dao.deleteByPrimaryKey(id);
    }

    /**
     * 保存记录，根据有无Id来判断是使用Insert还是Update
     *
     * @param entity
     */
    public void save(T entity) {
        if (entity.getId() != null) {
            if (entity.getUpdateTime() == null) {
                entity.setUpdateTime(new Date());
            }
            dao.updateByPrimaryKeySelective(entity);
        } else {
            Date now = new Date();
            if (entity.getCreateTime() == null) {
                entity.setCreateTime(now);
            }
            if (entity.getUpdateTime() == null) {
                entity.setUpdateTime(now);
            }
            dao.insert(entity);
        }
    }
}
