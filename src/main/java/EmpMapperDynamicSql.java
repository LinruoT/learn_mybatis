import java.util.List;

public interface EmpMapperDynamicSql {

    public List<Employee> getEmpsByConditionIf(Employee employee);
    public List<Employee> getEmpsByConditionTrim(Employee employee);
}
