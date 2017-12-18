/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.entity.respmsg;

/**
 * @Title: ImageMessage
 * @Author: tanggm
 * @Date: 2017/12/14 9:49
 * @Description: TODO 图片消息
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */

public class ImageMessage extends BaseMessage{
    private Image Image;

    public com.daqsoft.entity.respmsg.Image getImage() {
        return Image;
    }

    public void setImage(com.daqsoft.entity.respmsg.Image image) {
        Image = image;
    }
}
