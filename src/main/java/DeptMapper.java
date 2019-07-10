public interface DeptMapper {
    Department selectDeptById(Long id);
    void insertDept(Department department);
}
