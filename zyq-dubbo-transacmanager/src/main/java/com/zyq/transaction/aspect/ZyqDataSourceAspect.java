package com.zyq.transaction.aspect;

import com.zyq.transaction.connection.ZyqConnection;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Aspect
@Component
public class ZyqDataSourceAspect {

    /**
     * 切面切在DataSource的getConnection上
     * @param point
     * @return
     */
    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection around(ProceedingJoinPoint point) throws Throwable {
        Connection connection = (Connection) point.proceed();
        return new ZyqConnection(connection);
    }
}
