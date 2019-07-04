import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session=sqlSessionFactory.openSession();

        Employee employee1=session.selectOne("selectEmp",1); //方式一：xml
        Employee employee2=session.getMapper(EmpMapper.class).selectEmp((long) 1); //方式二：xml+接口类
        Employee employee3=session.getMapper(EmpMapper.class).selectEmp2((long) 1); //方式三：注解+接口类
        Employee employee4=session.getMapper(EmpMapper.class).selectEmpByFirstnameAndLastname("billy","lin");
        Employee employee5=session.getMapper(EmpMapper.class).selectEmpByName("last","lin");
        session.close();
        System.out.println(employee1);
        System.out.println(employee2);
        System.out.println(employee3);
        System.out.println(employee4);
        System.out.println(employee5);
    }
}
