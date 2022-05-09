package com.luckk.lizzie.rpc;

/**
 * @FileName: Invocation
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 15:41
 */

import lombok.*;

/**
 * 把它当做配置信息的对象？
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Invocation {


    private String version;

    private String group;

    private String timeout;



}
