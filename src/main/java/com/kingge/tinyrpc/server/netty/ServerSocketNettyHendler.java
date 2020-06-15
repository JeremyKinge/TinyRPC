package com.kingge.tinyrpc.server.netty;

import com.kingge.tinyrpc.entity.ClassInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ChannelHandler.Sharable
public class ServerSocketNettyHendler extends ChannelInboundHandlerAdapter {

    public static ServerSocketNettyHendler serverSocketNettyHendler=new ServerSocketNettyHendler();


    private static ExecutorService executorService= Executors.newFixedThreadPool(1000);

    //得到某个接口下的实现类
    public String getImplClassName(ClassInfo classInfo) throws Exception {
        //服务器接口与实现类地址;
        String iName="com.kingge.tinyrpc.server.service";//这个是服务端TestService接口所在的包路径
        int i = classInfo.getClassName().lastIndexOf(".");
        String className=classInfo.getClassName().substring(i);
        Class aClass = Class.forName(iName + className);//最终拼接得到的是服务端这边TestService接口的全路径
        Reflections reflections=new Reflections(iName);
        Set<Class<?>> classes=reflections.getSubTypesOf(aClass);//获取服务端TestService接口的实现类，可能有多个
        if(classes.size()==0){
            System.out.println("未找到实现类");
            return null;
        }else if(classes.size()>1){
            System.out.println("找到多个实现类，未明确使用哪个实现类");
            return null;
        }else{
            Class[] classes1 = classes.toArray(new Class[0]);
            return classes1[0].getName();
        }
    }


    //读取数据事件
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    ClassInfo classInfo= (ClassInfo) msg;//获取客户端请求的数据，因为客户端传递过来的就是ClassInfo
                    //类型的数据，所以这里直接强转即可
                    Object o = Class.forName(getImplClassName(classInfo)).newInstance();//通过反射获取客户端访问接口的实现类
                    Method method = o.getClass().getMethod(classInfo.getMethodName(), classInfo.getClazzType());//开始调用方法
                    Object invoke = method.invoke(o, classInfo.getArgs());
                    ctx.channel().writeAndFlush(invoke);//获取数据，返回给客户端。最终invoke数据，会在客户端的Pipeline中，经过所有出站处理器
                    //最终通过pipeline的头结点，返回数据给客户端
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
