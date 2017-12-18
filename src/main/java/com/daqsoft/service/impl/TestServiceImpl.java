/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.impl;

import com.daqsoft.entity.Test;
import com.daqsoft.mapper.TestMapper;
import com.daqsoft.service.TestService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title: TestServiceImpl
 * @Author: tanggm
 * @Date: 2017/12/07 16:13
 * @Description: TODO
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Service
public class TestServiceImpl implements TestService{
    @Autowired
    private TestMapper testMapper;
    @Override
    public PageInfo<Test> getList(int page, int limitPage, String name) {
        PageHelper.startPage(page, limitPage);
        List<Test> list = testMapper.getList(name);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
