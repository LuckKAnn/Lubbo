package com.luckk.lizzie.rpc.tansport;

import lombok.*;

/**
 * @FileName: LubboRequest
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 15:38
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LubboRequest {


    /**
     * 请求应该具有哪些信息，服务名称，ServiceBean内部的信息？
     * 请求的ID起码得有吧
     */

    private String requestId;


    private String className;


    private String methodName;

    private  Class<?>[] methodParams;


    private Object[] paramsVal;




}
