import com.ailian.crud.bean.Department;
import com.ailian.crud.bean.Employee;
import com.ailian.crud.mapper.DepartmentMapper;
import com.ailian.crud.mapper.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @author aiLian
 * @date on 2022-07-03 21:20:00
 * @describe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {

    @Autowired(required = true)
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SqlSession sqlSession;

    @Test
    public void testCRUD() {
        /*ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        DepartmentMapper bean = context.getBean(DepartmentMapper.class);*/

//        departmentMapper.insertSelective(new Department(null,"开发部"));
//        departmentMapper.insertSelective(new Department(null,"测试部"));

        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for (int i = 0; i < 1000; i++) {
            String s = UUID.randomUUID().toString().substring(0, 5) + "" + i;
            mapper.insertSelective(new Employee(null, s, "M", s + "@qq.com", 1));
        }
    }
}
