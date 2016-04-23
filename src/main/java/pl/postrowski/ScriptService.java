package pl.postrowski;

import org.apache.ibatis.jdbc.ScriptRunner;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;

/**
 * Created by postrowski on 2016-04-22.
 */
@Startup
@Singleton
public class ScriptService
{
    public static final String CONFIGURATION_DS_JNDI = "java:jboss/datasources/ConfigurationDS";

    @PostConstruct
    public void init()
    {
        try
        {
            final DataSource dataSource = lookupDataSource( CONFIGURATION_DS_JNDI );
            final Connection connection = dataSource.getConnection();

            final ScriptRunner scriptRunner = new ScriptRunner( connection );

            final InputStream inputStream =
                this.getClass().getClassLoader().getResourceAsStream( "sql/data.sql" );

            final Reader reader = new InputStreamReader( inputStream );

            // Exctute script
            scriptRunner.runScript( reader );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    private static DataSource lookupDataSource( final String name )
    {
        try
        {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource)initialContext.lookup( name );
            return dataSource;
        }
        catch( Exception ex )
        {
            throw new IllegalStateException( "Error during lookup data source : " + name, ex );
        }
    }
}
