package org.peng.icu.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName ProtocolTouch
 * @Date 2020/3/14 17:40
 * @Author pengyifu
 */
@AllArgsConstructor
@Getter
public class Touch {
    String touchUsername;
    String touchIp;
    String touchPort;
}
