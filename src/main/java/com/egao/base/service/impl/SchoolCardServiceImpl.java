package com.egao.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egao.common.core.exception.BusinessException;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.mapper.SchoolCardMapper;
import com.egao.base.entity.SchoolCard;
import com.egao.base.service.SchoolCardService;
import com.egao.common.system.entity.User;
import com.egao.common.system.mapper.UserMapper;
import com.egao.common.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 校园卡表服务实现类
 * Created by cy on 2020-05-06 01:33:22
 */
@Service
public class SchoolCardServiceImpl extends ServiceImpl<SchoolCardMapper, SchoolCard> implements SchoolCardService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public PageResult<SchoolCard> listPage(PageParam<SchoolCard> page) {
        List<SchoolCard> records = baseMapper.listPage(page);
        return new PageResult<>(records, page.getTotal());
    }

    @Override
    public List<SchoolCard> listAll(Map<String, Object> page) {
        return baseMapper.listAll(page);
    }

    @Override
    public boolean save(SchoolCard entity, User user) {
        if (entity.getCardNo() != null && baseMapper.selectCount(new QueryWrapper<SchoolCard>()
                .eq("card_no", entity.getCardNo())) > 0) {
            throw new BusinessException("该校园卡已被绑定");
        }
        //判断是管理员还是普通学生
        if (user.getEmailVerified() == 1) {
            //学生
            entity.setStudentNo(user.getIdCard());
            if (baseMapper.selectCount(new QueryWrapper<SchoolCard>().eq("student_no", entity.getStudentNo())) > 0) {
                throw new BusinessException("只能添加一张校园卡");
            }
        } else {
            //管理员
            if (entity.getStudentNo() != null && userMapper.selectCount(new QueryWrapper<User>()
                    .eq("id_card", entity.getStudentNo()).eq("email_verified", 1)) == 0) {
                throw new BusinessException("该学号不存在");
            }

            if (baseMapper.selectCount(new QueryWrapper<SchoolCard>().eq("student_no", entity.getStudentNo())) > 0) {
                throw new BusinessException("该学生已绑定校园卡");
            }
        }
        return baseMapper.insert(entity) > 0;
    }

    @Override
    public boolean cz(SchoolCard entity) {
        SchoolCard schoolCard = baseMapper.selectById(entity.getId());
        //校验支付密码
        if (!schoolCard.getPassword().equals(entity.getPassword())) {
            //密码错误
            throw new BusinessException("支付密码错误！");
        }
        //累加余额
        BigDecimal balance = schoolCard.getBalance();
        entity.setBalance(balance.add(entity.getBalance()));
        return baseMapper.updateById(entity) > 0;
    }
}
