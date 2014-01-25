import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static junit.framework.Assert.assertEquals;

public class CustomerDMLTest {
    static Connection connection;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        String url = "jdbc:mariadb://localhost:3306/test";
        connection = DriverManager.getConnection(url,"tanbir","Deep@k123");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        connection.close();
    }

    @Before
    public void setUp() throws Exception {
        Statement statement = connection.createStatement();
        String createCustomerQuery =
                "create table customer ( " +
                        "id int primary key," +
                        "name varchar(20)," +
                        "address varchar(20)," +
                        "city varchar(20)," +
                        "state varchar(20)" +
                        ")";

        statement.execute(createCustomerQuery);
    }
    @After
    public void tearDown() throws Exception {
        Statement statement = connection.createStatement();
        String createCustomerQuery = "drop table customer;";
        statement.execute(createCustomerQuery);
    }

    @Test
    public void testInsertingRecordInCustomer() throws Exception {
        Statement statement = connection.createStatement();

        int result = statement.executeUpdate(
                "insert into customer (id,name,address,city,state) values (1,\"tannu\",\"bhn\",\"dgp\",\"wb\");");
        assertEquals(result,1);

        ResultSet record =statement.executeQuery("select name from customer where id = 1");
        record.next();
        assert("tannu".equals(record.getString("name")));
    }

    @Test
    public void testDeletingRecordFromCustomer() throws Exception {
        Statement statement = connection.createStatement();
        int result = statement.executeUpdate(
                "insert into customer (id,name,address,city,state) values (1,\"tannu\",\"bhn\",\"dgp\",\"wb\");");
        assertEquals(1,result);

        result = statement.executeUpdate("delete from customer where id=1;");
        assertEquals(1, result);

        ResultSet records = statement.executeQuery("select name from customer where id = 1;");
        assertEquals(records.next(),false);
    }
}

