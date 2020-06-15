package com.kingge.tinyrpc.client.netty;


import com.kingge.tinyrpc.client.service.TestService;

public class ClientSocketNetty {
    public static void main(String[] args) throws InterruptedException {
        while (true){
            long start = System.currentTimeMillis();
            TestService o = (TestService) ClientRpcProxy.create(TestService.class);
            System.out.println(o.listAll());
            long end = System.currentTimeMillis();
            System.out.println(end-start);
            Thread.sleep(1000);
        }
        //功能当前接口，创建代理类，代理类内部通过netty方式调用服务端获取数据
//        TestService testService = (TestService) ClientRpcProxy.create(TestService.class);
//        System.out.println(testService.listByid(0));
    }
}
