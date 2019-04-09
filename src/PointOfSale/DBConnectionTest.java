package PointOfSale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
//import java.sql.ResultSet;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnectionTest {

    @InjectMocks private DBConnection dbConnection;
    @Mock private Connection mockConnection;
    @Mock private Statement mockStatement;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMockDBConnection() throws Exception {
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockConnection.createStatement().executeUpdate(Mockito.any())).thenReturn(123);

        ResultSet rs = dbConnection.executeQuery("SELECT * FROM article");
        int value = rs.getInt("id");
        System.out.println(value);
        Assert.assertEquals(value, 123);
        Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
    }
}
