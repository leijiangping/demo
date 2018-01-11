package com.xunge.service.selfelec;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 切面类：需要放入IoC容器中(@Component)、再声明为一个切面(@Aspect)
 * 优先级：可以使用@Order(1)注解来指定切面的优先级，值越小优先级越高
 *      或 实现Ordered接口, getOrder()方法的返回值越小, 优先级越高
 * @author daniel
 */
@Aspect
@Component
public class DataAuthAspect {

    /**
     * 声明切入点表达式：@Pointcut、该方法不需要填入其他代码
     * 引用切入点表达式：com.qiaobc.spring.aop.impl.LoggingAspect.declareJoinPointExpression()
     */
    @Pointcut("execution(* com.xunge.dao.selfelec.*.selectByExample*(..))")
    private void declareJoinPointExpression(){}

    /**
     * 需求：在com.qiaobc.spring.aop.impl.ArithmeticCalculator接口的所有实现类的每个方法执行前打印日志
     * 前置通知：在目标方法开始之前执行，用@Before来声明
     * @param joinPoint : 用于让通知访问当前连接点的具体细节
     */
    @Before(value = "declareJoinPointExpression()")
    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        
        for(Object param : args){
        	
        	try{
        		Class clazz  = param.getClass();
            	Method createCriteriaMethod = clazz.getDeclaredMethod("createCriteria");
                Object criteriaObj = createCriteriaMethod.invoke(param); 
                Class clazz1  = criteriaObj.getClass();
                Method m2 = clazz1.getDeclaredMethod("andPrvIdEqualTo", String.class);
                
                m2.invoke(criteriaObj, "310000"); 
                System.out.println("增加 prvid 310000条件");
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	
        	
//        	Reflections.createCriteria
//        	if(param instanceof BaseEntity){
//        		BaseEntity ent = (BaseEntity)param;
//        		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
//        		String sql = dataScopeFilter(UserUtils.getUser(), "o5", "a5");
//        		System.out.println("The method " + methodName + " sql: " + sql);
//        		ent.getSqlMap().put("dsf", sql);
//        	}
        }
        
        System.out.println("The method " + methodName + " begins with " + args);
    }

    /**
     * 后置通知：在目标方法执行之后执行(无论该方法是否出现异常)，用@After来声明
     * 特别注意：在后置通知中无法访问目标方法的执行结果
     * @param joinPoint : 用于让通知访问当前连接点的具体细节
     */
    @After(value = "declareJoinPointExpression()")
    public void afterMethod(JoinPoint joinPoint) {
    }

    /**
     * 返回通知：在目标方法正常结束后执行的通知，其可以访问目标方法的返回值
     * @param joinPoint : 用于让通知访问当前连接点的具体细节
     * @param result : 用于获取目标方法的返回值
     */
    @AfterReturning(value = "declareJoinPointExpression()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
    }

    /**
     * 异常通知：在目标方法抛出异常时执行的通知
     * @param joinPoint : 用于让通知访问当前连接点的具体细节
     * @param exception : 用于获取目标方法执行所抛出的异常(该参数类型可以设置只有发生指定异常时才执行)
     */
    @AfterThrowing(value = "declareJoinPointExpression()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, ArithmeticException exception) {
    }

}
