/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller;

import com.daqsoft.common.BaseResponseController;
import com.daqsoft.entity.Test;
import com.daqsoft.service.TestService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: TestController
 * @Author: tanggm 测试程序
 * @Date: 2017/12/06 17:22
 * @Description: TODO
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Api(value = "测试")
@RestController
public class TestController extends BaseResponseController{

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String home(){
        System.out.println("方法进来了！");
        return "login";
    }
    @ApiOperation(value = "列表", notes = "列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "limitPage", value = "每页记录数", defaultValue = "10", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object getList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limitPage, String name){
        PageInfo<Test> pageInfo = testService.getList(page, limitPage, name);
        return pageBuildSuccess(pageInfo);
    }
}
