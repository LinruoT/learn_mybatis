import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//保存EmpStatus的code到数据库
public class MyEnumEmpStatusTypeHandler implements TypeHandler<EmpStatus> {
    public void setParameter(PreparedStatement preparedStatement, int i, EmpStatus empStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i,String.valueOf(empStatus.getCode()));
    }

    public EmpStatus getResult(ResultSet resultSet, String s) throws SQLException {
        int code = resultSet.getInt(s);
        EmpStatus status = EmpStatus.getEmpStatusByCode(code);

        return status;
    }

    public EmpStatus getResult(ResultSet resultSet, int i) throws SQLException {
        int code = resultSet.getInt(i);
        EmpStatus status = EmpStatus.getEmpStatusByCode(code);

        return status;
    }

    public EmpStatus getResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        EmpStatus status = EmpStatus.getEmpStatusByCode(code);

        return status;
    }
}
