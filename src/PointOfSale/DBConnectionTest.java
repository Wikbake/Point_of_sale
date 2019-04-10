package PointOfSale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Simple tests of <code>DBConnection</code>.
 */

public class DBConnectionTest {

    @InjectMocks private DBConnection dbConnection;
    @Mock private Connection mockConnection;
    @Mock private Statement mockStatement;
    @Mock private ResultSet mockResultSet;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMockDBConnection() throws Exception {
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockConnection.createStatement().executeQuery(Mockito.any())).thenReturn(mockResultSet);
        Mockito.when(mockResultSet.getInt(Mockito.anyString())).thenReturn(123);

        ResultSet rs = dbConnection.executeQuery("");
        int value = rs.getInt("");
        System.out.println(value);
        Assert.assertEquals(value, 123);
        Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
    }
}
