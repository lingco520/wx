package com.daqsoft.service;

import com.daqsoft.entity.Test;
import com.github.pagehelper.PageInfo;

/**
 * @author tanggm
 * @description
 * @date 2017-12-07 16:13
 */
public interface TestService {
    /**
     * 查询列表
     * @param page
     * @param limitPage
     * @param name
     * @return
     */
    PageInfo<Test> getList(int page, int limitPage, String name);
}
