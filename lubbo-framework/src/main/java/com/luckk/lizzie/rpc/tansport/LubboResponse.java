package com.luckk.lizzie.rpc.tansport;

import lombok.*;

/**
 * @FileName: LubboResponse
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 16:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LubboResponse {


    /**
     * 请求应该具有哪些信息，服务名称，ServiceBean内部的信息？
     * 请求的ID起码得有吧
     */

    private String requestId;


    private String className;

    private String rpcResult;
}
