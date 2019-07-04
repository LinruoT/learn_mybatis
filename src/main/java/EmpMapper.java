import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface EmpMapper {

    Employee selectEmp(Long id);

    @Select("select * from employee where id=#{id}")
    Employee selectEmp2(Long id);

    Employee selectEmpByFirstnameAndLastname(@Param("firstName") String firstName, @Param("lastName") String lastName);

    Employee selectEmpByName(@Param("whichName")String whichName,@Param("name")String name);
}
