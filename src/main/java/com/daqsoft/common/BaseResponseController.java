/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.common;

import com.daqsoft.commons.responseEntity.BaseResponse;
import com.daqsoft.commons.responseEntity.ResponseBuilder;
import com.github.pagehelper.PageInfo;

/**
 * @Title: BaseResponseController
 * @Author: tanggm
 * @Date: 2017/12/07 17:01
 * @Description: TODO
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */

public class BaseResponseController<T> {
    /**
     * 返回带分页数据
     * @return
     */
    public BaseResponse pageBuildSuccess(PageInfo pageInfo){
        return ResponseBuilder.custom().success().currPage(pageInfo.getPageNum()).pageSize(pageInfo.getPageSize())
                .totalCount((int)pageInfo.getTotal()).totalPage(pageInfo.getPages())
                .data(pageInfo.getList()).build();
    }
    /**
     * 返回实体信息
     *
     * @param obj
     * @return
     */
    public BaseResponse dataBulidSuccess(T obj) {
        return ResponseBuilder.custom().success().data(obj).build();
    }

    /**
     * 无数据返回
     *
     * @return
     */
    public BaseResponse bulidSuccess() {
        return ResponseBuilder.custom().success().build();
    }


    /**
     * 操作异常
     * 异常时，code 为1
     * @return
     */
    public BaseResponse bulidFailed(String msg) {
        return ResponseBuilder.custom().failed(msg, 1).build();
    }
    /**
     * 操作错误
     * 错误自定义code
     * @return
     */
    public BaseResponse bulidFailed(String msg, int code) {
        return ResponseBuilder.custom().failed(msg, code).build();
    }
}
