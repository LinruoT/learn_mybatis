import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisTest {

    @Test
    public void testFirstLevelCache() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session =sqlSessionFactory.openSession();

        try{
            Employee employee1=session.getMapper(EmpMapper.class).getEmpAndDept((long)24);
            System.out.println(employee1);

            //一些代码

            //再次查询
            Employee employee2=session.getMapper(EmpMapper.class).getEmpAndDept((long)24);
            System.out.println(employee2);


        } finally {
            session.close();
        }

        SqlSession session2 =sqlSessionFactory.openSession();
        try{
            Employee employee1=session2.getMapper(EmpMapper.class).getEmpAndDept((long)24);
            System.out.println(employee1);

            //一些代码

            //再次查询
            Employee employee2=session2.getMapper(EmpMapper.class).getEmpAndDept((long)24);
            System.out.println(employee2);


        } finally {
            session2.close();
        }
    }
}
