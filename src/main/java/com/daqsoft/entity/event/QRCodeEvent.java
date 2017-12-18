/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.entity.event;

/**
 * @Title: QRCodeEvent
 * @Author: tanggm
 * @Date: 2017/12/14 9:29
 * @Description: TODO 扫码带参数二维码事件
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */

public class QRCodeEvent extends BaseEvent{
    /**
     * 事件KEY值
     */
    private String EventKey;
    /**
     * 用于换取二维码图片
     */
    private String Ticket;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }
}
