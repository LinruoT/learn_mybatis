package plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "parameterize", args = Statement.class)})
public class MyFirstPlugin implements Interceptor {
    private Properties properties = new Properties();

    public Object intercept(Invocation invocation) throws Throwable {

        System.out.println("第一个插件：intercept方法："+invocation.getTarget());
        System.out.println("一些参数:"+properties);
        return invocation.proceed();
    }

    public Object plugin(Object o) {
        System.out.println("第一个插件：plugin方法，包装");
        System.out.println("一些参数:"+properties);
        return Plugin.wrap(o,this);
    }

    public void setProperties(Properties properties) {
        System.out.println("第一个插件：setProperties方法");
        this.properties=properties;
    }
}
