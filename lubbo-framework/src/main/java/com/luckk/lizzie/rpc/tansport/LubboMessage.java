package com.luckk.lizzie.rpc.tansport;

import lombok.*;

/**
 * @FileName: LubboMessage
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 16:52
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LubboMessage {

    public static final Integer  RPC_REQUEST = 0;
    public static final Integer  RPC_HEARTBEAT = 1;


    public String type;

    public String ClassName;










}
