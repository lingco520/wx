/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.service.impl;

import com.daqsoft.entity.respmsg.Image;
import com.daqsoft.entity.respmsg.ImageMessage;
import com.daqsoft.entity.respmsg.TextMessage;
import com.daqsoft.entity.respmsg.Video;
import com.daqsoft.entity.respmsg.VideoMessage;
import com.daqsoft.entity.respmsg.Voice;
import com.daqsoft.entity.respmsg.VoiceMessage;
import com.daqsoft.service.WxMessageService;
import com.daqsoft.utils.MessageUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Title: WxMessageServiceImpl
 * @Author: tanggm
 * @Date: 2017/12/14 11:48
 * @Description: TODO 微信消息处理实现
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */
@Service
public class WxMessageServiceImpl implements WxMessageService{
    /**
     * 处理微信发来的请求
     * @param request
     * @return
     */
    @Override
    public String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent = "未知的消息类型！";
        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号
            String fromUserName = requestMap.get("FromUserName");
            // 开发者微信号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 时间
            long time = System.currentTimeMillis();
            // 文本消息
            if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(msgType)) {
                // 回复文本消息
                TextMessage textMessage = new TextMessage();
                textMessage.setToUserName(fromUserName);
                textMessage.setFromUserName(toUserName);
                textMessage.setCreateTime(time);
                textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                // 文本内容
                String content = requestMap.get("Content");
                respContent = "您发送的是文本消息！"+ content;
                // 设置文本消息的内容
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml
                respXml = MessageUtil.messageToXml(textMessage);
            }
            // 图片消息
            else if (MessageUtil.REQ_MESSAGE_TYPE_IMAGE.equals(msgType)) {
                String picUrl = requestMap.get("PicUrl");
                System.out.println(picUrl);
                ImageMessage imageMessage = new ImageMessage();
                imageMessage.setToUserName(fromUserName);
                imageMessage.setFromUserName(toUserName);
                imageMessage.setCreateTime(time);
                imageMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_IMAGE);
                Image image = new Image();
                image.setMediaId(requestMap.get("MediaId"));
                imageMessage.setImage(image);
                respXml = MessageUtil.messageToXml(imageMessage);
            }
            // 语音消息
            else if (MessageUtil.REQ_MESSAGE_TYPE_VOICE.equals(msgType)) {
                // 语音消息微信转换成文字内容
                String recognition = requestMap.get("Recognition");
                System.out.println(recognition);
                VoiceMessage voiceMessage = new VoiceMessage();
                voiceMessage.setToUserName(fromUserName);
                voiceMessage.setFromUserName(toUserName);
                voiceMessage.setCreateTime(time);
                voiceMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_VOICE);
                Voice voice = new Voice();
                voice.setMediaId(requestMap.get("MediaId"));
                voiceMessage.setVoice(voice);
                respXml = MessageUtil.messageToXml(voiceMessage);
            }
            // 视频消息
            else if (MessageUtil.REQ_MESSAGE_TYPE_VIDEO.equals(msgType)) {
                // 发送过来的视频封面图的 媒体id
                String thumbmediaId = requestMap.get("ThumbMediaId");
                VideoMessage videoMessage = new VideoMessage();
                videoMessage.setFromUserName(toUserName);
                videoMessage.setToUserName(fromUserName);
                videoMessage.setCreateTime(time);
                videoMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_VIDEO);
                Video video = new Video();
                video.setMediaId(requestMap.get("MediaId"));
                video.setThumbMediaId(thumbmediaId);
                videoMessage.setVideo(video);
            }
            // 小视频消息
            else if (MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO.equals(msgType)) {
                // 发送过来的视频封面图的 媒体id
                String thumbmediaId = requestMap.get("ThumbMediaId");
                VideoMessage videoMessage = new VideoMessage();
                videoMessage.setFromUserName(toUserName);
                videoMessage.setToUserName(fromUserName);
                videoMessage.setCreateTime(time);
                videoMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_SHORT_VIDEO);
                Video video = new Video();
                video.setMediaId(requestMap.get("MediaId"));
                video.setThumbMediaId(thumbmediaId);
                videoMessage.setVideo(video);
            }
            // 地理位置消息
            else if (MessageUtil.REQ_MESSAGE_TYPE_LOCATION.equals(msgType)) {
                // 地理位置纬度
                String location_x = requestMap.get("Location_X");
                // 地理位置经度
                String location_y = requestMap.get("Location_Y");
                // 地理位置信息
                String label = requestMap.get("Label");
                // 地图缩放大小
                String scale = requestMap.get("Scale");
                TextMessage textMessage = new TextMessage();
                textMessage.setToUserName(fromUserName);
                textMessage.setFromUserName(toUserName);
                textMessage.setCreateTime(time);
                textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                respContent = "上报地理位置！\n" +
                        "经度："+ location_y + "\n" +
                        "纬度：" + location_x + "\n" +
                        "位置：" + label;
                // 设置文本消息的内容
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml
                respXml = MessageUtil.messageToXml(textMessage);

            }
            // 链接消息
            else if (MessageUtil.REQ_MESSAGE_TYPE_LINK.equals(msgType)) {
                String title = requestMap.get("Title");
                String description = requestMap.get("Description");
                String url = requestMap.get("Url");
                TextMessage textMessage = new TextMessage();
                textMessage.setToUserName(fromUserName);
                textMessage.setFromUserName(toUserName);
                textMessage.setCreateTime(time);
                textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                respContent = "您发送的是链接消息！\n" +
                "title：" + title + "\n"+
                "description：" + description + "\n"+
                "url：" + url + "\n";
                // 设置文本消息的内容
                textMessage.setContent(respContent);
                // 将文本消息对象转换成xml
                respXml = MessageUtil.messageToXml(textMessage);
            }
            // 事件推送
            else if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 关注
                if (MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {
                    TextMessage textMessage = new TextMessage();
                    textMessage.setToUserName(fromUserName);
                    textMessage.setFromUserName(toUserName);
                    textMessage.setCreateTime(time);
                    textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                    respContent = "来了，老弟！欢迎关注公众号。\n";
                    // 设置文本消息的内容
                    textMessage.setContent(respContent);
                    // 将文本消息对象转换成xml
                    respXml = MessageUtil.messageToXml(textMessage);
                    System.out.println(respContent);
                }
                // 取消关注
                else if (MessageUtil.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)) {
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                    respContent = "取消关注！";
                    System.out.println(respContent);
                }
                // 扫描带参数二维码
                else if (MessageUtil.EVENT_TYPE_SCAN.equals(eventType)) {
                    // TODO 处理扫描带参数二维码事件
                    respContent = "扫描带参数二维码事件！";
                }
                // 上报地理位置
                else if (MessageUtil.EVENT_TYPE_LOCATION.equals(eventType)) {
                    // TODO 处理上报地理位置事件
                    respContent = "处理上报地理位置事件！";
                    System.out.println(requestMap);
                }
                // 自定义菜单
                else if (MessageUtil.EVENT_TYPE_CLICK.equals(eventType)) {
                    // TODO 处理菜单点击事件
                    respContent = "处理菜单点击事件！";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }
}
