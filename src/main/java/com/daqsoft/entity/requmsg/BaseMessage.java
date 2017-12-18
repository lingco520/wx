/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.entity.requmsg;

/**
 * @Title: BaseMessage
 * @Author: tanggm
 * @Date: 2017/12/08 9:59
 * @Description: TODO 请求消息父类
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */

public class BaseMessage {
    /**
     * 开发者微信号
     */
    private String ToUserName;
    /**
     * 发送方账号（一个OpenID）
     */
    private String FromUserName;
    /**
     * 小心创建时间（整型）
     */
    private long CreateTime;
    /**
     * 消息类型（text/image/location/link...）
     */
    private String MsgType;
    /**
     * 消息id，64位整型
     */
    private long MsgId;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public long getMsgId() {
        return MsgId;
    }

    public void setMsgId(long msgId) {
        MsgId = msgId;
    }
}
