/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.controller;

import com.daqsoft.utils.SignUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: WXCheckController
 * @Author: tanggm
 * @Date: 2017/12/07 20:52
 * @Description: TODO 微信校验
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Controller
@RequestMapping("/wx")
public class WXCheckController {
    /**
     * 验证配置有效性
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public Object authCheck(String signature, String timestamp, String nonce, String echostr){
        if(SignUtil.checkSignature(signature, timestamp, nonce)){
            // 如果验证成功，就原样返回 echostr字符串
            return echostr;
        }
        return "";
    }
}
