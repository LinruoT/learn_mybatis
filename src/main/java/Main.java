import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //增删改，需要设置自动提交=true，或者手动提交
        SqlSession session=sqlSessionFactory.openSession(true);

        Employee employee1=session.selectOne("selectEmp",1); //方式一：xml
        Employee employee2=session.getMapper(EmpMapper.class).selectEmp((long) 1); //方式二：xml+接口类
        Employee employee3=session.getMapper(EmpMapper.class).selectEmp2((long) 1); //方式三：注解+接口类
        Employee employee4=session.getMapper(EmpMapper.class).selectEmpByFirstnameAndLastname("billy","lin");
        Employee employee5=session.getMapper(EmpMapper.class).selectEmpByName("first","ruotian");

        //不分页
        List<Employee> employees=session.getMapper(EmpMapper.class).selectEmpByAge(23);

        //分页
        Page<Object> page = PageHelper.startPage(1, 5);
        List<Employee> employeesPage=session.getMapper(EmpMapper.class).selectEmpByAge(23);
        System.out.println("总数"+page.getTotal()+"总页数"+page.getPages()+"当前页"+page.getPageNum());
        //用pageInfo
        PageInfo<Employee> pageInfo=new PageInfo<Employee>(employeesPage);
        System.out.println("总数"+pageInfo.getTotal()+"总页数"+pageInfo.getPages()+"当前页"+pageInfo.getPageNum());
        //用pageInfo navigate可以得到'连续页码'
        PageInfo<Employee> pageInfo2=new PageInfo<Employee>(employeesPage,6);
        System.out.println("总数"+pageInfo.getTotal()+"总页数"+pageInfo.getPages()+"当前页"+pageInfo.getPageNum());
        int[] nums = pageInfo2.getNavigatepageNums();
        System.out.println("连续页码");
        for (int num:nums
             ) {
            System.out.println(num);
        }
        Employee employee6=session.getMapper(EmpMapper.class).selectEmpToResultMap((long)2);

        Employee employeeAdd1 = new Employee(null,"若天412","林",23,"male","ted_163mail@163.com");
        Employee employeeAdd2 = new Employee(null,"若天422","林",23,"male","ted_163mail@163.com");
        Employee employeeAdd3 = new Employee(null,"若天432","林",23,"male","ted_163mail@163.com");
        employeeAdd1.setDept(new Department((long)17,""));
        employeeAdd2.setDept(new Department((long)17,""));
        employeeAdd3.setDept(new Department((long)17,""));

        //session.getMapper(EmpMapper.class).insertEmp(employeeAdd);
        Department departmentAdd = new Department(null,"amd red team14");
        //session.getMapper(DeptMapper.class).insertDept(departmentAdd);

        Department department1 = session.getMapper(DeptMapper.class).selectDeptById((long)7);

        Employee employee7 = session.getMapper(EmpMapper.class).getEmpAndDeptStep((long)1);
        Employee employee8 = session.getMapper(EmpMapper.class).getEmpAndDept((long)2);
        List<Employee> employees2 = session.getMapper(EmpMapperDynamicSql.class).
                getEmpsByConditionIf(new Employee(null,"","",23,"male",null));

        session.getMapper(EmpMapperDynamicSql.class).updateEmp(
                new Employee((long)23,null,"23333333",1,null,null));
        List<Integer> ids=new ArrayList<Integer>();
        ids.add(1);
        ids.add(2);
        ids.add(24);
        ids.add(23);

        List<Employee> employees3 = session.getMapper(EmpMapperDynamicSql.class).getEmpsByConditionForeach(ids);
        List<Employee> emps=new ArrayList<Employee>();
        emps.add(employeeAdd1);
        emps.add(employeeAdd2);
        emps.add(employeeAdd3);
        session.getMapper(EmpMapperDynamicSql.class).addEmps(emps);

        session.close();

        //System.out.println("add: "+employeeAdd);
        //System.out.println("add: "+departmentAdd);
        System.out.println(employee1);
        System.out.println(employee2);
        System.out.println(employee3);
        System.out.println(employee4);
        System.out.println(employee5);


        //不用分页
        System.out.println("分页前");
        for (Employee emp:employees
             ) {
            System.out.println(emp);
        }
        //用分页
        System.out.println("分页后");
        for (Employee emp:employeesPage
        ) {
            System.out.println(emp);
        }


        System.out.println("测试choose得到的结果");
        for (Employee emp:employees2
             ) {
            System.out.println(emp);
        }
        System.out.println("测试foreach得到的结果");
        for (Employee emp:employees3
             ) {
            System.out.println(emp);
        }
        System.out.println(employee6);

        System.out.println(department1);

        System.out.println(employee7);
        System.out.println(employee8);

        //可以批量操作的session，耗时4600ms
//        SqlSession session2=sqlSessionFactory.openSession(ExecutorType.BATCH);
//        //非批量，SqlSession session2=sqlSessionFactory.openSession();
//        long start=System.currentTimeMillis();
//        for (int i=0;i<10000;i++){
//            session2.getMapper(EmpMapper.class).insertEmp(
//                new Employee(null, UUID.randomUUID().toString().substring(0,8),"lin",24,"female","123@123.com")
//                );
//        }
//        session2.commit();
//        long end = System.currentTimeMillis();
//        System.out.println("时间："+(end-start));
//        session2.close();

    }
}
