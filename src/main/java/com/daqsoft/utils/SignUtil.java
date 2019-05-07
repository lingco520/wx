/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.utils;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.daqsoft.constant.WxConstant;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: SignUtil
 * @Author: tanggm
 * @Date: 2017/12/07 20:58
 * @Description: TODO 校验signature工具类
 * @Comment：
 * @Version:
 * @Warning:
 * @see
 * @since JDK 1.8
 */

public class SignUtil {
    // 与接口配置中的token一致
    private static String token = "lingco";

    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        // 1.将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);

        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
        // 3.将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

    /**
     * 获取access_token
     *
     * @return access_token
     */
    public static String getAccessToken() {
        String url = String.format(WxConstant.ACCESS_TOKEN_URL, WxConstant.APP_ID, WxConstant.APP_SECRET);
        return HttpUtil.get(url);
    }

    /**
     * 获取access_token
     *
     * @return access_token
     */
    public static String getWechatIP(String accessToken) {
        String url = String.format(WxConstant.WECHAT_IP_URL, accessToken);
        return HttpUtil.get(url);
    }

    /**
     * 微信网络检测
     *
     * @param accessToken
     * @return
     */
    public static String getWechatCheck(String accessToken) {
        String url = String.format(WxConstant.WECHAT_CHECK_URL, accessToken);
        Map parMap = new HashMap<>(2);
        // action	是	执行的检测动作，允许的值：dns（做域名解析）、ping（做ping检测）、all（dns和ping都做）
        // check_operator	是	指定平台从某个运营商进行检测，允许的值：CHINANET（电信出口）、UNICOM（联通出口）、CAP（腾讯自建出口）、DEFAULT（根据ip来选择运营商）
        parMap.put("action", "ping");
        parMap.put("check_operator", "DEFAULT");
        return HttpUtil.post(url, parMap);
    }

    /**
     * 创建个性化菜单
     *
     * @param accessToken
     * @return
     */
    public static String addConditionalMenu(String accessToken) {
        String menu = "{\n" +
                "    \"button\": [\n" +
                "        {\n" +
                "            \"type\": \"click\", \n" +
                "            \"name\": \"今日歌曲\", \n" +
                "            \"key\": \"V1001_TODAY_MUSIC\"\n" +
                "        }, \n" +
                "        {\n" +
                "            \"name\": \"菜单\", \n" +
                "            \"sub_button\": [\n" +
                "                {\n" +
                "                    \"type\": \"view\", \n" +
                "                    \"name\": \"搜索\", \n" +
                "                    \"url\": \"http://www.soso.com/\"\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"type\": \"miniprogram\", \n" +
                "                    \"name\": \"wxa\", \n" +
                "                    \"url\": \"http://mp.weixin.qq.com\", \n" +
                "                    \"appid\": \"wx286b93c14bbf93aa\", \n" +
                "                    \"pagepath\": \"pages/lunar/index\"\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"type\": \"click\", \n" +
                "                    \"name\": \"赞一下我们\", \n" +
                "                    \"key\": \"V1001_GOOD\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ], \n" +
                "    \"matchrule\": {\n" +
                "        \"tag_id\": \"2\", \n" +
                "        \"sex\": \"1\", \n" +
                "        \"country\": \"中国\", \n" +
                "        \"province\": \"四川\", \n" +
                "        \"city\": \"成都\", \n" +
                "        \"client_platform_type\": \"2\", \n" +
                "        \"language\": \"zh_CN\"\n" +
                "    }\n" +
                "}";
        String url = String.format(WxConstant.ADD_CONDITIONAL_MENU_URL, accessToken);
        String result = HttpRequest.post(url).body(menu).header(Header.CONTENT_TYPE,
                "application/json").execute().body();
        return result;
    }

    /**
     * 获取公众号的自动回复规则
     *
     * @param accessToken accessToken
     * @return 自动回复规则
     */
    public static String getCurrentAutoreplyInfo(String accessToken) {
        String url = String.format(WxConstant.GET_CURRENT_AUTOREPLY_INFO_URL, accessToken);
        String result = HttpUtil.get(url);
        return result;
    }
}
