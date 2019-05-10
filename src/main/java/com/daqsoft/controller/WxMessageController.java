/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller;

import cn.hutool.core.util.StrUtil;
import com.daqsoft.constant.WxConstant;
import com.daqsoft.service.WxMessageService;
import com.daqsoft.utils.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
@RequestMapping(value = "/weChat")
public class WxMessageController {
    @Autowired
    private WxMessageService wxMessageService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
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
    public Object msg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            Exception{
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
        String accessToken = stringRedisTemplate.opsForValue().get(WxConstant.BASIC + WxConstant.APP_ID);
        if (StrUtil.isEmpty(accessToken)) {
            accessToken = SignUtil.getAccessToken();
            if (StrUtil.isNotEmpty(accessToken)) {
                stringRedisTemplate.opsForValue().set(WxConstant.BASIC + WxConstant.APP_ID, accessToken, WxConstant
                                .ACCESS_TOKEN_EXPIRE,
                        TimeUnit.SECONDS);
            }
        }
//        String wechatIp = SignUtil.getWechatIP(accessToken);
//        System.out.println("微信服务器IP：" + wechatIp);
//        // 微信网络检测
//        String res = SignUtil.getWechatCheck(accessToken);
//        System.out.println("网络检测：" + res);
//        // 测试创建个性化菜单
//        String addConditionalMenu = SignUtil.addConditionalMenu(accessToken);
//        System.out.println("个性化菜单：" + addConditionalMenu);
//        // 测试 获取公众号的自动回复规则
//        String currentAutoreplyInfo = SignUtil.getCurrentAutoreplyInfo(accessToken);
//        System.out.println("公众号的自动回复规则：" + currentAutoreplyInfo);
//        System.out.println(SignUtil.getUserSummary(accessToken));
//        System.out.println(SignUtil.getUserCumulate(accessToken));
//        System.out.println(SignUtil.semproxySearch(accessToken));
//        System.out.println(SignUtil.addVoicetoreCofortext(accessToken));
//        accessToken = "21_S-KlriE_NysxZzh_pxbnKTWQPElOHk7DjfEX5NFxNaj0DodMTU8BSNoo99dZ2PtSoFdXHmpgu11Uvg1CAbeqny60n1FoNu7DPxLB43bvdsjurMlHXDE0dF4taatFpJtofQct3sSIaK9RYSyzJKXeAGAXYL";
        SignUtil.getUserInfo(accessToken, "ox6540yvn5ikGF6DtSrYBg-inWLA");
        return respXml;
    }
    /**
     * 网页授权获取用户信息
     * @param code 授权后的code，用于获取access_token（与普通access_token不同）
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getOauthUserInfo", method = RequestMethod.GET)
    public Object getOauth2UserInfo(String code){
        String accessToken = stringRedisTemplate.opsForValue().get(WxConstant.OAUTH + WxConstant.APP_ID);
        if (StrUtil.isEmpty(accessToken)) {
            accessToken = SignUtil.getAccessToken(code);
            if (StrUtil.isNotEmpty(accessToken)) {
                stringRedisTemplate.opsForValue().set(WxConstant.OAUTH + WxConstant.APP_ID, accessToken, WxConstant
                        .ACCESS_TOKEN_EXPIRE, TimeUnit.SECONDS);
            }
        }
        String openid = "";
        String snsapiUserinfo = SignUtil.getSnsapiUserinfo(accessToken, openid);
        System.out.println("授权获取用户信息：" + snsapiUserinfo);
        return "";
    }
}
