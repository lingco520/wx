package com.daqsoft.constant;

/**
 * 微信常量
 * @author TangGuangMing
 * @version 4.0
 * @date 2019-04-22 11:03
 * @since JDK 1.8
 */

public interface WxConstant {
    /**
     * 公众号appId
     */
    String APP_ID = "wxdd73ee9404ee140b";
    /**
     * 公众号appSecret
     */
    String APP_SECRET = "1c8851ff6bcf42c3a7eeeb5b6fc2a6b9";
    /**
     * 获取access_token接口
     */
    String ACCESS_TOKEN_URL = "https://api.weixin.qq" +
            ".com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
}
