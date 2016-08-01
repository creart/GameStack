package fr.creart.gamestack.common.connection.database.sql;

import fr.creart.gamestack.common.connection.database.AbstractDatabase;
import fr.creart.gamestack.common.lang.ClassUtil;
import fr.creart.gamestack.common.log.CommonLogger;
import fr.creart.gamestack.common.misc.ConnectionData;
import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.commons.lang3.Validate;

/**
 * Represents a SQL database
 *
 * @author Creart
 */
public abstract class SQLDatabase extends AbstractDatabase<Connection, SQLRequest> {

    protected String databaseSystemName;
    protected String driver;

    public SQLDatabase(int threads)
    {
        super(threads);
    }

    @Override
    protected boolean connect(ConnectionData connectionData)
    {
        try {
            connection = DriverManager.
                    getConnection("jdbc:" + databaseSystemName + "://" + connectionData.getHost() + ":"
                            + connectionData.getPort() + "/" + connectionData.getDatabaseName());
            return true;
        } catch (Exception e) {
            CommonLogger.error("Failed to connect to the database.", e);
            return false;
        }
    }

    @Override
    protected void end()
    {
        try {
            if (isEstablished())
                connection.close();
        } catch (Exception e) {
            CommonLogger.error("Could not close SQL connection.", e);
        }
    }

    private boolean isEstablished()
    {
        try {
            return connection != null && !connection.isClosed();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isConnectionEstablished()
    {
        return super.isConnectionEstablished() && isEstablished();
    }

    @Override
    public void executeRequest(SQLRequest request)
    {
        Validate.notNull(request, "request can't be null");
        Validate.notEmpty(request.getRequest(), "the sql request can't be empty or null");
        Validate.notNull(request.getType(), "request's type can't be null");


    }

    @Override
    protected final void initializeDriver()
    {
        ClassUtil.loadClass(driver);
    }

}
