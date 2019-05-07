/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller;

import cn.hutool.json.JSONObject;
import com.daqsoft.service.WxMessageService;
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
        // 调用核心业务类接收消息、处理消息
        String respXml = wxMessageService.processRequest(request);
        // 调试获取access_token
        String accessTokenObj = SignUtil.getAccessToken();
        System.out.println("accessToken：" + accessTokenObj);
        // 获取微信服务器IP地址
        JSONObject jsonObject = new JSONObject(accessTokenObj);
        String accessToken = jsonObject.getStr("access_token");
        String wechatIp = SignUtil.getWechatIP(accessToken);
        System.out.println("微信服务器IP：" + wechatIp);
        // 微信网络检测
        String res = SignUtil.getWechatCheck(accessToken);
        System.out.println("网络检测：" + res);
        // 测试创建个性化菜单
        String addConditionalMenu = SignUtil.addConditionalMenu(accessToken);
        System.out.println("个性化菜单：" + addConditionalMenu);
        // 测试 获取公众号的自动回复规则
        String currentAutoreplyInfo = SignUtil.getCurrentAutoreplyInfo(accessToken);
        System.out.println("公众号的自动回复规则：" + currentAutoreplyInfo);
        return respXml;
    }
}
