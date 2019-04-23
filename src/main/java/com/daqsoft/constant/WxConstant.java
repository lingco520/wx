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
    String APP_ID = "wx92707db34947e8cd";
    /**
     * 公众号appSecret
     */
    String APP_SECRET = "f92e39f24c7473f96dc6635b65175cb2";
    /**
     * 获取access_token接口
     */
    String ACCESS_TOKEN_URL = "https://api.weixin.qq" +
            ".com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    /**
     * 获取微信IP地址接口
     */
    String WECHAT_IP_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=%s";
    /**
     * 微信网络检测接口
     */
    String WECHAT_CHECK_URL = "https://api.weixin.qq.com/cgi-bin/callback/check?access_token=%s";
    /**
     * 创建个性化菜单
     */
    String ADD_CONDITIONAL_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=%s";
}
