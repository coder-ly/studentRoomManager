package com.egao.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egao.common.core.web.PageParam;
import com.egao.common.core.web.PageResult;
import com.egao.base.entity.SchoolCard;
import com.egao.common.system.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 校园卡表服务类
 * Created by cy on 2020-05-06 01:33:22
 */
public interface SchoolCardService extends IService<SchoolCard> {

    /**
     * 分页查询
     */
    PageResult<SchoolCard> listPage(PageParam<SchoolCard> page);

    /**
     * 查询所有
     */
    List<SchoolCard> listAll(Map<String, Object> page);

    /**
     * 添加
     *
     * @param entity
     * @param user
     * @return
     */
    boolean save(SchoolCard entity, User user);

    /**
     * 充值
     *
     * @param entity
     * @return
     */
    boolean cz(SchoolCard entity);
}
