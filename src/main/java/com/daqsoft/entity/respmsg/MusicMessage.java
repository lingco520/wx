/**
 * @Copyright: <a htef="http://www.daqsoft.com
 * <p>
 * ">成都中科大旗软件有限公司Copyright  2004-2017蜀ICP备08010315号</a>
 * @Warning: 注意：本内容仅限于成都中科大旗软件有限公司内部传阅，禁止外泄以及用于其他的商业目的。
 */
package com.daqsoft.entity.respmsg;

/**
 * @Title: MusicMessage
 * @Author: tanggm
 * @Date: 2017/12/14 10:06
 * @Description: TODO 音乐消息
 * @Comment：
 * @see
 * @Version:
 * @since JDK 1.8
 * @Warning:
 */

public class MusicMessage extends BaseMessage{
    /**
     * 音乐
     */
    private Music Music;

    public com.daqsoft.entity.respmsg.Music getMusic() {
        return Music;
    }

    public void setMusic(com.daqsoft.entity.respmsg.Music music) {
        Music = music;
    }
}
