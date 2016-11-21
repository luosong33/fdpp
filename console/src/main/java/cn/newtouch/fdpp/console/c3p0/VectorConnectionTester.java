package cn.newtouch.fdpp.console.c3p0;

import java.sql.*;
import java.util.*;

import com.mchange.v2.log.*;
import com.mchange.v2.c3p0.AbstractConnectionTester;
import com.mchange.v1.db.sql.ResultSetUtils;
import com.mchange.v1.db.sql.StatementUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VectorConnectionTester extends AbstractConnectionTester {
	final static Logger logger = LoggerFactory.getLogger(VectorConnectionTester.class);

	final static int HASH_CODE = VectorConnectionTester.class.getName()
			.hashCode();

	final static Set<String> INVALID_DB_STATES;

	static {
		Set<String> temp = new HashSet<String>();
		temp.add("08001"); // SQL State "Unable to connect to data source"
		temp.add("08007"); // SQL State "Connection failure during transaction"

		// MySql appently uses this state to indicate a stale, expired
		// connection when the database is fine, so we'll not presume
		// this SQL state signals an invalid database.
		// temp.add("08S01"); //SQL State "Communication link failure"

		INVALID_DB_STATES = Collections.unmodifiableSet(temp);
	}

	public int activeCheckConnection(Connection c, String query,
			Throwable[] rootCauseOutParamHolder) {

		Statement stmt = null;
		ResultSet rs = null;
		try {
			// if (Math.random() < 0.1)
			// throw new NullPointerException("Test.");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			rs = stmt.executeQuery(query);
			// rs.next();
			return CONNECTION_IS_OKAY;
		} catch (SQLException e) {
			if (rootCauseOutParamHolder != null)
				rootCauseOutParamHolder[0] = e;

			String state = e.getSQLState();
			if (INVALID_DB_STATES.contains(state)) {
				if (logger.isWarnEnabled())
					logger.warn(
							"SQL State '"
									+ state
									+ "' of Exception which occurred during a Connection test (test with query '"
									+ query
									+ "') implies that the database is invalid, "
									+ "and the pool should refill itself with fresh Connections.",
							e);
				return DATABASE_IS_INVALID;
			} else
				return CONNECTION_IS_INVALID;
		} catch (Exception e) {
			if (rootCauseOutParamHolder != null)
				rootCauseOutParamHolder[0] = e;

			return CONNECTION_IS_INVALID;
		} finally {
			ResultSetUtils.attemptClose(rs);
			StatementUtils.attemptClose(stmt);
			try {
				c.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	}

	public int statusOnException(Connection c, Throwable t, String query,
			Throwable[] rootCauseOutParamHolder) {
		try {
			if (t instanceof SQLException) {
				String state = ((SQLException) t).getSQLState();
				if (INVALID_DB_STATES.contains(state)) {
					if (logger.isWarnEnabled())
						logger.warn(
								"SQL State '"
										+ state
										+ "' of Exception tested by statusOnException() implies that the database is invalid, "
										+ "and the pool should refill itself with fresh Connections.",
								t);
					return DATABASE_IS_INVALID;
				} else
					return activeCheckConnection(c, query,
							rootCauseOutParamHolder);
			} else // something is broke
			{
				if (logger.isInfoEnabled())
					logger.info(
							"Connection test failed because test-provoking Throwable is an unexpected, non-SQLException.",
							t);
				if (rootCauseOutParamHolder != null)
					rootCauseOutParamHolder[0] = t;
				return CONNECTION_IS_INVALID;
			}
		} catch (Exception e) {
			if (rootCauseOutParamHolder != null)
				rootCauseOutParamHolder[0] = e;

			return CONNECTION_IS_INVALID;
		} finally {
			// if (Debug.DEBUG && Debug.TRACE == Debug.TRACE_MAX)
			// {
			// if ( logger.isLoggable( MLevel.FINER ) )
			// logger.finer("Exiting DefaultConnectionTester.statusOnException(Connection c, Throwable t, String query) "
			// + queryInfo(query));
			// }
		}
	}

	public boolean equals(Object o) {
		return (o != null && o.getClass() == VectorConnectionTester.class);
	}

	public int hashCode() {
		return HASH_CODE;
	}
}