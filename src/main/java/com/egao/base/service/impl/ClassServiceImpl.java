package com.egao.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egao.base.entity.Scroom;
import com.egao.base.mapper.ScroomMapper;
import com.egao.common.core.exception.BusinessException;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.mapper.ClassMapper;
import com.egao.base.entity.ClassA;
import com.egao.base.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 班级信息服务实现类
 * Created by cy on 2020-05-06 01:33:22
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, ClassA> implements ClassService {

    @Autowired
    private ScroomMapper scroomMapper;

    @Override
    public PageResult<ClassA> listPage(PageParam<ClassA> page) {
        List<ClassA> records = baseMapper.listPage(page);
        return new PageResult<>(records, page.getTotal());
    }

    @Override
    public List<ClassA> listAll(Map<String, Object> page) {
        return baseMapper.listAll(page);
    }

    @Override
    public boolean save(ClassA entity) {
        Scroom scroom = scroomMapper.selectById(Integer.parseInt(entity.getScroom()));
        if ("1".equals(scroom.getStatus())) {
            throw new BusinessException("该教室正在使用");
        }
        if (baseMapper.selectCount(new QueryWrapper<ClassA>().eq("class_no", entity.getClassNo())) > 0) {
            throw new BusinessException("该班级编号已使用");
        }
        //将教室修改为使用状态
        updateScroom(entity.getScroom(), "1");
        return baseMapper.insert(entity) > 0;
    }

    @Override
    public boolean updateById(ClassA entity) {
        if (baseMapper.selectCount(new QueryWrapper<ClassA>().eq("class_no", entity.getClassNo()).ne("id", entity.getId())) > 0) {
            throw new BusinessException("该班级编号已使用");
        }
        //将原教室修改为空闲状态
        ClassA obj = baseMapper.selectById(entity.getId());
        updateScroom(obj.getScroom(), "0");
        //将现在使用的教师
        updateScroom(entity.getScroom(), "1");
        return baseMapper.updateById(entity) > 0;
    }

    /**
     * 修改教室使用状态
     *
     * @param scroomId
     */
    private void updateScroom(String scroomId, String status) {
        Scroom scroom = new Scroom();
        scroom.setId(Integer.parseInt(scroomId));
        scroom.setStatus(status);
        scroomMapper.updateById(scroom);
    }

    @Override
    public boolean updateStatus(Integer scroom, Integer id) {
        ClassA classA = new ClassA();
        classA.setScroom("0");
        classA.setId(id);
        updateScroom(scroom + "", "0");
        return baseMapper.updateById(classA) > 0;
    }
}
