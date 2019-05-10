/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.daqsoft.constant.WxConstant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    /**
     * 与接口配置中的token一致
     */
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
     * 获取普通access_token
     *
     * @return access_token
     */
    public static String getAccessToken() {
        String url = String.format(WxConstant.ACCESS_TOKEN_URL, WxConstant.APP_ID, WxConstant.APP_SECRET);
        String accessTokenObj = HttpUtil.get(url);
        // 获取微信服务器IP地址
        JSONObject jsonObject = new JSONObject(accessTokenObj);
        String accessToken = jsonObject.getStr("access_token");
        return accessToken;
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

    /**
     * 获取公众号拉黑用户列表
     *
     * @param accessToken accessToken
     * @return 用户openid列表
     */
    public static String membersBlacklist(String accessToken) {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist?access_token=%s",
                accessToken);
        Map param = new HashMap(2);
        param.put("begin_openid", "");
        String result = HttpRequest.post(url).body(JSONUtil.toJsonStr(param)).header(Header.CONTENT_TYPE,
                "application/json").execute().body();
        return result;
    }

    /**
     * 批量拉黑用户
     *
     * @param accessToken accessToken
     * @return 操作结果
     */
    public static String membersBatchblacklist(String accessToken) {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=%s",
                accessToken);
        Map param = new HashMap(2);
        param.put("openid_list", "ox6540yvn5ikGF6DtSrYBg-inWLA");
        String result = HttpRequest.post(url).body(JSONUtil.toJsonStr(param)).header(Header.CONTENT_TYPE,
                "application/json").execute().body();
        return result;
    }

    /**
     * 批量取消拉黑的用户
     *
     * @param accessToken accessToken
     * @return 操作结果
     */
    public static String membersBatchunblacklist(String accessToken) {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=%s",
                accessToken);
        Map param = new HashMap(2);
        param.put("openid_list", "ox6540yvn5ikGF6DtSrYBg-inWLA");
        String result = HttpRequest.post(url).body(JSONUtil.toJsonStr(param)).header(Header.CONTENT_TYPE,
                "application/json").execute().body();
        return result;
    }

    /**
     * 创建临时二维码ticket
     *
     * @param accessToken
     * @return 包含ticket的数据
     */
    public static String getQrcodeQrScene(String accessToken) throws Exception {
        String qrcodeUrl = String.format("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s",
                accessToken);
        Map param = new HashMap(3);
        param.put("expire_seconds", 200);
        param.put("action_name", "QR_SCENE");
        Map sceneStr = new HashMap(1);
        sceneStr.put("scene_str", UUID.randomUUID());
        Map scene = new HashMap(1);
        scene.put("scene", sceneStr);
        param.put("action_info", scene);
        String paramStr = JSONUtil.toJsonStr(param);
        String result = HttpRequest.post(qrcodeUrl).body(paramStr).header(Header.CONTENT_TYPE,
                "application/json").execute().body();
        // 通过ticket换取二维码图片
        Map resMap = new HashMap(3);
        String ticket = "";
        String expireSeconds = "";
        String url = "";
        if (StrUtil.isNotEmpty(result)) {
            JSONObject jsonObject = new JSONObject(result);
            ticket = jsonObject.getStr("ticket");
            expireSeconds = jsonObject.getStr("expire_seconds");
            url = jsonObject.getStr("url");
            // 官方文档说明，ticket必须 UrlEncode， 官方生成方式，不方便使用(摒弃)
//            ticket = URLEncoder.encode(ticket, "UTF-8");
//            String ticketUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
        }
        // 二维码生成采用hutool工具类生成
        QrConfig config = new QrConfig(300, 300);
        // 生成二维码到文件，也可以到流
        QrCodeUtil.generate(url, config, FileUtil.file("e:/qrcode.jpg"));

        resMap.put("ticket", ticket);
        resMap.put("expire_seconds", expireSeconds);
        resMap.put("url", url);
        return JSONUtil.toJsonStr(resMap);
    }

    /**
     * 创建永久二维码ticket
     *
     * @param accessToken
     * @return 包含ticket的数据
     */
    public static String getQrcodeQrLimitScene(String accessToken) throws Exception {
        String qrcodeUrl = String.format("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s",
                accessToken);
        Map param = new HashMap(2);
        param.put("action_name", "QR_LIMIT_STR_SCENE");
        Map sceneStr = new HashMap(1);
        sceneStr.put("scene_str", UUID.randomUUID());
        Map scene = new HashMap(1);
        scene.put("scene", sceneStr);
        param.put("action_info", scene);
        String paramStr = JSONUtil.toJsonStr(param);
        String result = HttpRequest.post(qrcodeUrl).body(paramStr).header(Header.CONTENT_TYPE,
                "application/json").execute().body();
        // 通过ticket换取二维码图片
        Map resMap = new HashMap(3);
        String ticket = "";
        String expireSeconds = "";
        String url = "";
        if (StrUtil.isNotEmpty(result)) {
            JSONObject jsonObject = new JSONObject(result);
            ticket = jsonObject.getStr("ticket");
            expireSeconds = jsonObject.getStr("expire_seconds");
            url = jsonObject.getStr("url");
        }
        // 二维码生成采用hutool工具类生成
        QrConfig config = new QrConfig(300, 300);
        // 生成二维码到文件，也可以到流
        QrCodeUtil.generate(url, config, FileUtil.file("e:/qrcode1.jpg"));
        resMap.put("ticket", ticket);
        resMap.put("expire_seconds", expireSeconds);
        resMap.put("url", url);
        return JSONUtil.toJsonStr(resMap);
    }

    /**
     * 获取用户增减数据
     *
     * @param accessToken
     * @return 用户增减数据
     */
    public static String getUserSummary(String accessToken) {
        String url = String.format("https://api.weixin.qq.com/datacube/getusersummary?access_token=%s",
                accessToken);
        Map param = new HashMap(2);
        param.put("begin_date", "2019-05-01");
        param.put("end_date", "2019-05-07");
        String paramStr = JSONUtil.toJsonStr(param);
        String result = HttpRequest.post(url).body(paramStr).header(Header.CONTENT_TYPE,
                "application/json").execute().body();
        return result;
    }

    /**
     * 获取用户累计数据
     *
     * @param accessToken
     * @return 用户累计数据
     */
    public static String getUserCumulate(String accessToken) {
        String url = String.format("https://api.weixin.qq.com/datacube/getusercumulate?access_token=%s",
                accessToken);
        Map param = new HashMap(2);
        param.put("begin_date", "2019-05-01");
        param.put("end_date", "2019-05-07");
        String paramStr = JSONUtil.toJsonStr(param);
        String result = HttpRequest.post(url).body(paramStr).header(Header.CONTENT_TYPE,
                "application/json").execute().body();
        return result;
    }

    /**
     * 语义理解(查询)
     *
     * @param accessToken
     * @return 结果数据
     */
    public static String semproxySearch(String accessToken) {
        String url = String.format("https://api.weixin.qq.com/semantic/semproxy/search?access_token=%s",
                accessToken);
        Map param = new HashMap(2);
        param.put("query", "查一下明天从北京到上海的南航机票");
        param.put("city", "北京市");
        param.put("category", "flight");
        param.put("appid", WxConstant.APP_ID);
        param.put("uid", "ox6540yvn5ikGF6DtSrYBg-inWLA");
        String paramStr = JSONUtil.toJsonStr(param);
        String result = HttpRequest.post(url).body(paramStr).header(Header.CONTENT_TYPE,
                "application/json").execute().body();
        return result;
    }

    /**
     * AI开放接口--提交语音
     * 注意：添加完文件之后10s内调用语音识别接口(queryRecoResultFortext())
     *
     * @param accessToken
     * @return 结果数据
     */
    public static String addVoicetoreCofortext(String accessToken) {
        // 下面是提交语音到微信公众平台
        String voiceId = UUID.randomUUID().toString();
        String url = "http://api.weixin.qq.com/cgi-bin/media/voice/addvoicetorecofortext?access_token=%s" +
                "&format=%s&voice_id=%s&lang=%s";
        url = String.format(url, accessToken, "mp3", voiceId, "zh_CN");
        File file = FileUtil.file("E:\\aaaa.mp3");
        Map param = new HashMap(2);
        param.put("file", file);
        String paramStr = JSONUtil.toJsonStr(param);
        FileInputStream fis = null;
        byte[] buffer = null;
        try {
            fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = HttpRequest.post(url).body(buffer).header(Header.CONTENT_TYPE,
                "application/json").execute().body();
        JSONObject jsonObject = new JSONObject(result);
        System.out.println("提交语音结果：" + result);
        String res = "";
        if (jsonObject != null && jsonObject.getInt("errcode") == 0) {
            // 十秒之内必须调用识别接口
            res = queryRecoResultFortext(accessToken, voiceId);
            System.out.println("识别语音结果：" + result);
        }
        return res;
    }

    /**
     * AI开放接口--获取提交语音的识别结果
     *
     * @param accessToken
     * @param voiceId     语音媒体id
     * @return 结果数据
     */
    private static String queryRecoResultFortext(String accessToken, String voiceId) {
        String url = "http://api.weixin.qq.com/cgi-bin/media/voice/queryrecoresultfortext?access_token=%s&voice_id" +
                "=%s&lang=zh_CN";
        url = String.format(url, accessToken, voiceId);
        String result = HttpRequest.post(url).header(Header.CONTENT_TYPE,
                "application/json").execute().body();
        return result;
    }

    /**
     * 获取素材列表
     *
     * @param accessToken
     * @return
     */
    public static String getBatchgetMaterial(String accessToken) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=%s";
        url = String.format(url, accessToken);
        Map param = new HashMap(2);
        param.put("type", "voice");
        param.put("offset", 0);
        param.put("count", 500);
        String paramStr = JSONUtil.toJsonStr(param);
        String result = HttpRequest.post(url).body(paramStr).header(Header.CONTENT_TYPE,
                "application/json").execute().body();
        return result;
    }

    /**
     * 多媒体素材上传接口
     *
     * @param accessToken
     * @return
     */
    public static String addMaterial(String accessToken) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_material";
        Map paramMap = new HashMap(1);
        paramMap.put("media", FileUtil.file("E:\\aaaa.mp3"));
        paramMap.put("access_token", accessToken);
        paramMap.put("type", "voice");
        String result = HttpUtil.post(url, paramMap);
        return result;
    }

    /**
     * 获取用户信息，通过UnionID机制
     *
     * @param accessToken token
     * @param openid      用户openid
     * @return 用户数据
     */
    public static String getUserInfo(String accessToken, String openid) {
        String url = String.format(WxConstant.USER_INFO, accessToken, openid);
        String result = HttpUtil.get(url);
        JSONObject jsonObject = new JSONObject(result);
        String unionid = jsonObject.getStr("unionid");
        System.out.println("用户unionid：" + unionid);
        System.out.println(result);
        return result;
    }

    /**
     * 获取微信网页授权access_token
     *
     * @param code 授权换取的code
     * @return access_token数据
     */
    public static String getAccessToken(String code) {
        String url = String.format(WxConstant.OATH2_ACCESS_TOKEN, WxConstant.APP_ID, WxConstant.APP_SECRET, code);
        String result = HttpUtil.get(url);
        String accessToken = "";
        if (StrUtil.isNotBlank(result)) {
            JSONObject jsonObject = new JSONObject(result);
            accessToken = jsonObject.getStr("access_token");
        }
        System.out.println("网页授权accessToken：" + accessToken);
        return accessToken;
    }

    /**
     * 微信网页授权获取用户信息
     * @param accessToken accessToken
     * @param openid 用户唯一标识openid
     * @return 用户数据
     */
    public static String getSnsapiUserinfo(String accessToken, String openid) {
        String url = String.format(WxConstant.SNSAPI_USERINFO, accessToken, openid);
        String result = HttpUtil.get(url);
        System.out.println("网页授权用户信息：" + result);
        return result;
    }
}
