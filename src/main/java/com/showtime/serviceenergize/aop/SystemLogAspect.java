package com.showtime.serviceenergize.aop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.showtime.serviceenergize.annotation.SystemControllerLog;
import com.showtime.serviceenergize.annotation.SystemServiceLog;
import com.showtime.serviceenergize.entity.StEmpowermentUserTable;
import com.showtime.serviceenergize.entity.StEnergizeLog;
import com.showtime.serviceenergize.service.StEmpowermentUserTableService;
import com.showtime.serviceenergize.service.StEnergizeLogService;
import com.showtime.serviceenergize.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 系统日志切点类
 *
 * @author linrx
 */
@Aspect
@Component
public class SystemLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    private static final ThreadLocal<Date> beginTimeThreadLocal =
            new NamedThreadLocal<Date>("ThreadLocal beginTime");
    private static final ThreadLocal<StEnergizeLog> logThreadLocal =
            new NamedThreadLocal<StEnergizeLog>("ThreadLocal log");

    private static final ThreadLocal<StEmpowermentUserTable> currentUser = new NamedThreadLocal<>("ThreadLocal user");
    //required = false：表示忽略当前要注入的bean，如果有直接注入，没有跳过，不会报错。Autowired如果不设置required 默认required = true（必须保证注入的bean存在）
    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    /**
     * 赋能操作日志 服务类
     */
    @Autowired
    private StEnergizeLogService logService;
    /**
     * 赋能管理后台用户表 服务类
     */
    @Autowired
    private StEmpowermentUserTableService userTableService;

    /**
     * Service层切点
     */
    @Pointcut("@annotation(com.showtime.serviceenergize.annotation.SystemServiceLog)")
    public void serviceAspect() {
    }

    /**
     * Controller层切点 注解拦截
     */
    @Pointcut("@annotation(com.showtime.serviceenergize.annotation.SystemControllerLog)")
    public void controllerAspect() {
    }

    /**
     * 方法规则拦截
     */
    @Pointcut("execution(* com.showtime.serviceenergize.controller.*.*(..))")
    public void controllerPointerCut() {
    }

    /**
     * 方法规则拦截
     */
    @Pointcut("execution(* com.showtime.serviceenergize.service.*.*.*(..))")
    public void servicePointerCut() {
    }


    /**
     * 前置通知 用于拦截Controller层记录用户的操作的开始时间
     *
     * @param joinPoint 切点
     * @throws InterruptedException
     */
    @Before("controllerAspect()")
    public void doBeforeController(JoinPoint joinPoint) throws InterruptedException {
        Date beginTime = new Date();
        beginTimeThreadLocal.set(beginTime);
        //debug模式下 显式打印开始时间用于调试
        if (logger.isDebugEnabled()) {
            logger.debug("开始计时: {}  URI: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                    .format(beginTime), request.getRequestURI());
        }
        //先从token中拿用户，如果没有再从请求中拿用户
        String token = request.getHeader(UserConst.AUTHORIZATION);
        String userPhone = null;
        if (token == null){
            //从请求中拿到数据
            Map<String, String[]> params = request.getParameterMap(); //请求提交的参数
            userPhone = params.get("account")[0];
        }else {
            userPhone = JWTUtil.getUsername(token);
        }
        StEmpowermentUserTable user = userTableService.getOne(new QueryWrapper<StEmpowermentUserTable>().eq("user_phone", userPhone));
        currentUser.set(user);

    }


    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @SuppressWarnings("unchecked")
    @After("controllerAspect()")
    public void doAfterController(JoinPoint joinPoint) {
        StEmpowermentUserTable user = currentUser.get();
        //登入login操作 前置通知时用户未校验 所以session中不存在用户信息
        if (user == null) {
//    		HttpSession session = request.getSession();
//    	    user = (StAdminUser) session.getAttribute("ims_user");
            Subject current = SecurityUtils.getSubject();
            user = (StEmpowermentUserTable) current.getPrincipal();
            if (user == null) {
                return;
            }
        }
        Object[] args = joinPoint.getArgs();
        System.out.println(args);

        String title = "";
        String type = "info";                          //日志类型(info:入库,error:错误)
        String remoteAddr = CusAccessObjectUtil.getIpAddress(request);//请求的IP
        String requestUri = request.getRequestURI();//请求的Uri
        String method = request.getMethod();          //请求的方法类型(post/get)
        Map<String, String[]> params = request.getParameterMap(); //请求提交的参数
        try {
            title = getControllerMethodDescription2(joinPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // debu模式下打印JVM信息。
        long beginTime = beginTimeThreadLocal.get().getTime();//得到线程绑定的局部变量（开始时间）
        long endTime = System.currentTimeMillis();    //2、结束时间
        if (logger.isDebugEnabled()) {
            logger.debug("计时结束：{}  URI: {}  耗时： {}   最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(endTime),
                    request.getRequestURI(),
                    DateUtils.formatDateTime(endTime - beginTime),
                    Runtime.getRuntime().maxMemory() / 1024 / 1024,
                    Runtime.getRuntime().totalMemory() / 1024 / 1024,
                    Runtime.getRuntime().freeMemory() / 1024 / 1024,
                    (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);
        }

        StEnergizeLog log = new StEnergizeLog();
        log.setLogId(UUIDUtil.getUuid());
        log.setLogTitle(title);
        log.setLogType(type);
        log.setLogIp(remoteAddr);
        log.setLogRequestUri(requestUri);
        log.setLogMethod(method);
        log.mapToParams(params);
        log.setUserId(user.getUserId());
        log.setCreateName(user.getUserUserName());
        //赋能后台暂时没有角色分类，默认设置为管理员
        log.setRoleType(1);
        Date operateDate = beginTimeThreadLocal.get();
        log.setCreateTime(System.currentTimeMillis()+"");
        log.setLogTimeout(DateUtils.formatDateTime(endTime - beginTime));

        //1.直接执行保存操作
        //this.logService.createSystemLog(log);

        //2.优化:异步保存日志
        //new SaveLogThread(log, logService).start();

        //3.再优化:通过线程池来执行日志保存
        threadPoolTaskExecutor.execute(new SaveLogThread(log, logService));
        logThreadLocal.set(log);


    }

    /**
     * 异常通知
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public void doAfterThrowingController(JoinPoint joinPoint, Throwable e) {
        StEnergizeLog log = logThreadLocal.get();
        if (log != null) {
            log.setLogType("error");
            log.setLogException(e.toString());
            new UpdateLogThread(log, logService).start();
        }
    }


    /**
     * 前置通知 用于拦截Controller层记录用户的操作的开始时间
     *
     * @param joinPoint 切点
     * @throws InterruptedException
     */
    @Before("serviceAspect()")
    public void doBeforeService(JoinPoint joinPoint) throws InterruptedException {
        Date beginTime = new Date();
        beginTimeThreadLocal.set(beginTime);
        //debug模式下 显式打印开始时间用于调试
        if (logger.isDebugEnabled()) {
            logger.debug("开始计时: {}  URI: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                    .format(beginTime), request.getRequestURI());
        }

        //读取session中的用户
//		HttpSession session = request.getSession();
//	    StAdminUser user = (StAdminUser) session.getAttribute("ims_user");
        Subject current = SecurityUtils.getSubject();
        StEmpowermentUserTable user = (StEmpowermentUserTable) current.getPrincipal();
        currentUser.set(user);

    }


    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @SuppressWarnings("unchecked")
    @After("serviceAspect()")
    public void doAfterService(JoinPoint joinPoint) {
        StEmpowermentUserTable user = currentUser.get();
        //登入login操作 前置通知时用户未校验 所以session中不存在用户信息
        if (user == null) {
//    		HttpSession session = request.getSession();
//    	    user = (StAdminUser) session.getAttribute("ims_user");
            Subject current = SecurityUtils.getSubject();
            user = (StEmpowermentUserTable) current.getPrincipal();
            if (user == null) {
                return;
            }
        }
        Object[] args = joinPoint.getArgs();
        System.out.println(args);

        String title = "";
        String type = "info";                          //日志类型(info:入库,error:错误)
        String remoteAddr = CusAccessObjectUtil.getIpAddress(request);//请求的IP
        String requestUri = request.getRequestURI();//请求的Uri
        String method = request.getMethod();          //请求的方法类型(post/get)
        Map<String, String[]> params = request.getParameterMap(); //请求提交的参数
        try {
            title = getServiceMthodDescription2(joinPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // debu模式下打印JVM信息。
        long beginTime = beginTimeThreadLocal.get().getTime();//得到线程绑定的局部变量（开始时间）
        long endTime = System.currentTimeMillis();    //2、结束时间
        if (logger.isDebugEnabled()) {
            logger.debug("计时结束：{}  URI: {}  耗时： {}   最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(endTime),
                    request.getRequestURI(),
                    DateUtils.formatDateTime(endTime - beginTime),
                    Runtime.getRuntime().maxMemory() / 1024 / 1024,
                    Runtime.getRuntime().totalMemory() / 1024 / 1024,
                    Runtime.getRuntime().freeMemory() / 1024 / 1024,
                    (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);
        }

        StEnergizeLog log = new StEnergizeLog();
        log.setLogId(UUIDUtil.getUuid());
        log.setLogTitle(title);
        log.setLogType(type);
        log.setLogIp(remoteAddr);
        log.setLogRequestUri(requestUri);
        log.setLogMethod(method);
        log.mapToParams(params);
        log.setUserId(user.getUserId());
        log.setCreateName(user.getUserUserName());
        //赋能后台暂时没有角色分类，默认设置为管理员
        log.setRoleType(1);
        Date operateDate = beginTimeThreadLocal.get();
        log.setCreateTime(System.currentTimeMillis()+"");
        log.setLogTimeout(DateUtils.formatDateTime(endTime - beginTime));

        //1.直接执行保存操作
        //this.logService.createSystemLog(log);

        //2.优化:异步保存日志
        //new SaveLogThread(log, logService).start();

        //3.再优化:通过线程池来执行日志保存
        threadPoolTaskExecutor.execute(new SaveLogThread(log, logService));
        logThreadLocal.set(log);


    }

    /**
     * 异常通知
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowingService(JoinPoint joinPoint, Throwable e) {
        StEnergizeLog log = logThreadLocal.get();
        if (log != null) {
            log.setLogType("error");
            log.setLogException(e.toString());
            new UpdateLogThread(log, logService).start();
        }
    }


    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     */
    public static String getControllerMethodDescription2(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SystemControllerLog controllerLog = method
                .getAnnotation(SystemControllerLog.class);
        String discription = controllerLog.description();
        return discription;
    }

    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return discription
     */
    public static String getServiceMthodDescription2(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SystemServiceLog serviceLog = method
                .getAnnotation(SystemServiceLog.class);
        String discription = serviceLog.description();
        return discription;
    }


    /**
     * 保存日志线程
     *
     * @author lin.r.x
     */
    private static class SaveLogThread implements Runnable {
        private StEnergizeLog log;
        private StEnergizeLogService logService;

        public SaveLogThread(StEnergizeLog log, StEnergizeLogService logService) {
            this.log = log;
            this.logService = logService;
        }

        @Override
        public void run() {
            logService.createLog(log);
        }
    }

    /**
     * 日志更新线程
     *
     * @author lin.r.x
     */
    private static class UpdateLogThread extends Thread {
        private StEnergizeLog log;
        private StEnergizeLogService logService;

        public UpdateLogThread(StEnergizeLog log, StEnergizeLogService logService) {
            super(UpdateLogThread.class.getSimpleName());
            this.log = log;
            this.logService = logService;
        }

        @Override
        public void run() {
            this.logService.updateLog(log);
        }
    }
}
