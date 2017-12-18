/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.entity.respmsg;

/**
 * @Title: VoiceMessage
 * @Author: tanggm
 * @Date: 2017/12/14 9:53
 * @Description: TODO 语音消息
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */

public class VoiceMessage extends BaseMessage{
    /**
     * 语音
     */
    private Voice Voice;

    public com.daqsoft.entity.respmsg.Voice getVoice() {
        return Voice;
    }

    public void setVoice(com.daqsoft.entity.respmsg.Voice voice) {
        Voice = voice;
    }
}
