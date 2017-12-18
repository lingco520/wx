/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller;

import com.daqsoft.service.WxMessageService;
import com.daqsoft.utils.MessageUtil;
import com.daqsoft.utils.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Title: WxMessageController
 * @Author: tanggm
 * @Date: 2017/12/14 11:42
 * @Description: TODO 来接收微信服务器传来信息
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Controller
public class WxMessageController {
    @Autowired
    private WxMessageService wxMessageService;

    /**
     * 微信服务器地址验证，消息事件处理
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/chat", method = {RequestMethod.GET, RequestMethod.POST})
    public Object msg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // 判断微信是有效性验证还是消息、事件处理(有效性验证：echostr不为空，消息事件时echostr为空)
        String echostr = request.getParameter("echostr");
        if(!StringUtils.isEmpty(echostr)){
            // 验证服务器地址
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            if(SignUtil.checkSignature(signature, timestamp, nonce)){
                // 如果验证成功，就原样返回 echostr字符串，否则返回空字符串
                return echostr;
            }
            return "";
        }
        // 下面代码对微信消息或事件进行处理
        // 消息的接收、处理、响应
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        /*request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");*/
        // 调用核心业务类接收消息、处理消息
        String respXml = wxMessageService.processRequest(request);
        return respXml;
    }
}
