package com.daqsoft.constant;

/**
 * 微信常量
 *
 * @author TangGuangMing
 * @version 4.0
 * @date 2019-04-22 11:03
 * @since JDK 1.8
 */

public interface WxConstant {
    /**
     * 基础access_token拼接前缀
     */
    String BASIC = "basic";
    /**
     * 网页授权access_token拼接前缀
     */
    String OAUTH = "oauth";
    /**
     * 公众号appId
     */
    String APP_ID = "wx92707db34947e8cd";
    /**
     * 公众号appSecret
     */
    String APP_SECRET = "f92e39f24c7473f96dc6635b65175cb2";
    /**
     * 微信公众号access_token过期时间(秒)，官方是 2小时(7200秒)，这里设置7000
     */
    long ACCESS_TOKEN_EXPIRE = 7000;
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
    /**
     * 获取公众号的自动回复规则
     */
    String GET_CURRENT_AUTOREPLY_INFO_URL = "https://api.weixin.qq" +
            ".com/cgi-bin/get_current_autoreply_info?access_token=%s";
    /**
     * 获取用户基本信息(UnionID机制)
     */
    String USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
    /**
     * 网页授权获取access_token
     */
    String OATH2_ACCESS_TOKEN = "https://api.weixin.qq" +
            ".com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    /**
     * 网页授权获取用户基本信息
     */
    String SNSAPI_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
}
