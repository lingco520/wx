package com.daqsoft.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tanggm
 * @description 微信信息service
 * @date 2017-12-14 11:47
 */
public interface WxMessageService {
    /**
     * 处理微信消息
     * @param request
     * @return
     */
    String processRequest(HttpServletRequest request);
}
