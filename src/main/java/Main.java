import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

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
        List<Employee> employees=session.getMapper(EmpMapper.class).selectEmpByAge(23);
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
                getEmpsByConditionIf(new Employee(null,"","li",23,"",null));

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
        for (Employee emp:employees
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
    }
}
