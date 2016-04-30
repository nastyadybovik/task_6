package by.epam.training.dao.impl.connectionpool.impl;


import by.epam.training.dao.impl.connectionpool.ConnectionPool;
import by.epam.training.dao.impl.connectionpool.ConnectionPoolException;
import by.epam.training.dao.impl.connectionpool.DBResourceManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.*;

public class ConnectionPoolImpl implements ConnectionPool {
	//продумать ленивую инициализацию
	private static ConnectionPoolImpl instance;

	private BlockingQueue<Connection> connectionsQueue;
	private BlockingQueue<Connection> workingConnectionsQueue;
	private String url;
	private String user;
	private String password;
	private String location_of_driver;
	private int connection_amount;

	private static final String KEY_URL = "db.url";
	private static final String KEY_USER = "db.user";
	private static final String KEY_PASSWORD = "db.password";
	private static final String KEY_LOCATION_OF_DRIVER = "db.driver";
	private static final String KEY_CONNECTION_AMOUNT="db.amount";
	private static final int DEFAULT_AMOUNT = 5;

	public static ConnectionPoolImpl getInstance(){
		if(instance == null){
			instance = new ConnectionPoolImpl();
		}
		return instance;
	}

	public ConnectionPoolImpl() {

		DBResourceManager dbResourceManager = DBResourceManager.getInstance();

		this.url = dbResourceManager.getValue(KEY_URL);
		this.user = dbResourceManager.getValue(KEY_USER);
		this.password = dbResourceManager.getValue(KEY_PASSWORD);
		this.location_of_driver = dbResourceManager.getValue(KEY_LOCATION_OF_DRIVER);
		try {
			this.connection_amount = Integer.parseInt(dbResourceManager.getValue(KEY_CONNECTION_AMOUNT));
		} catch(NumberFormatException e){
			this.connection_amount = DEFAULT_AMOUNT;
		}
		this.connectionsQueue = new ArrayBlockingQueue<Connection>(connection_amount);
		this.workingConnectionsQueue = new ArrayBlockingQueue<Connection>(connection_amount);
	}

	@PreDestroy
	private void init() throws ConnectionPoolException {
		System.out.println("Create Connection pool");
		try {

			Class.forName(location_of_driver);

			for(int i=0; i<connection_amount; i++) {
				Connection connection = DriverManager.getConnection(url, user, password);
				ConnectionWrapper connectionWrapper = new ConnectionWrapper(connection);
				connectionsQueue.put(connectionWrapper);
				System.out.println("Connection "+i+" is created and put to queue.");
			}

		} catch (ClassNotFoundException | SQLException | InterruptedException e) {
			System.err.println("ConnectionPoolImpl ClassNotFound");
			throw new ConnectionPoolException();
		}
	}


	@PostConstruct
	private void destroy() {
		////!!!!!!!!!
		boolean errorStatus = false;
		System.out.println("Destroy Connection pool");
		try {
			closeConnectionQueue(connectionsQueue);
		} catch (ConnectionPoolException e) {
			errorStatus = true;
		}
		try {
			closeConnectionQueue(workingConnectionsQueue);
		} catch (ConnectionPoolException e) {
			errorStatus = true;
		}
		if(errorStatus){
			//логировать ошибку
			//throw new ConnectionPoolException("Can not correctly destroy connection pool.");
		}
	}

	private void closeConnectionQueue(BlockingQueue<Connection> queue) throws ConnectionPoolException {
		//poll = return + delete
		/*Connection connection = queue.take();
		while(connection != null){
			if(!connection.getAutoCommit()){
				connection.commit();
			}
			((ConnectionWrapper)connection).dispose();
			connection = queue.poll();
		}*/
		boolean errorStatus = false;
		for(Connection connection: queue){
			try {
				if (connection != null) { //есть ли смысл в этом условии
					if (!connection.getAutoCommit()) {
						connection.commit();
					}
					((ConnectionWrapper) connection).dispose();
				}
			} catch (SQLException e){
				errorStatus = true;
			}
		}
		if(errorStatus){
			throw new ConnectionPoolException("Can not close connection queue.");
		}
	}

	@Override
	public Connection takeConnection() throws ConnectionPoolException {
		Connection connection = null;
		try {
			connection = connectionsQueue.remove();
			workingConnectionsQueue.put(connection);
		} catch (InterruptedException e) {
			throw new ConnectionPoolException("Can not take connection.", e);
		}
		System.out.println("Take connection.");
		return connection;
	}

	@Override
	public void returnConnection(Connection connection) throws ConnectionPoolException {
		try {
			connectionsQueue.put(connection);
			workingConnectionsQueue.remove(connection);
			System.out.println("Return connection.");
		} catch (InterruptedException e) {
			//может быть нужно закрыть connection
			throw new ConnectionPoolException("Time is out. Can not put Connection.");
			/*try {
				((ConnectionWrapper)connection).dispose();
			} catch (SQLException e1) {
				throw new ConnectionPoolException("Can not close connection.", e1);
			}*/
		}

	}

	private class ConnectionWrapper implements Connection{
		//переопределим close и destroy
		private Connection connection;

		public ConnectionWrapper(Connection connection) {
			this.connection = connection;
		}

		@Override
		public Statement createStatement() throws SQLException {
			return connection.createStatement();
		}

		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException {
			return connection.prepareStatement(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {
			return connection.prepareCall(sql);
		}

		@Override
		public String nativeSQL(String sql) throws SQLException {
			return connection.nativeSQL(sql);
		}

		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			connection.setAutoCommit(autoCommit);
		}

		@Override
		public boolean getAutoCommit() throws SQLException {
			return connection.getAutoCommit();
		}

		@Override
		public void commit() throws SQLException {
			connection.commit();
		}

		@Override
		public void rollback() throws SQLException {
			connection.rollback();
		}

		@Override
		public void close() throws SQLException {
			///возвращаем коннекшн в пул
			if(connection.isClosed()){
				throw new SQLException("Can not close closed exception.");
			}
            try {
                returnConnection(connection);
            } catch (ConnectionPoolException e) {
                dispose();
            }
        }

        private void dispose() throws SQLException {
            this.connection.close();
        }

		@Override
		public boolean isClosed() throws SQLException {
			return connection.isClosed();
		}

		@Override
		public DatabaseMetaData getMetaData() throws SQLException {
			return connection.getMetaData();
		}

		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			connection.setReadOnly(readOnly);
		}

		@Override
		public boolean isReadOnly() throws SQLException {
			return connection.isReadOnly();
		}

		@Override
		public void setCatalog(String catalog) throws SQLException {
			connection.setCatalog(catalog);
		}

		@Override
		public String getCatalog() throws SQLException {
			return connection.getCatalog();
		}

		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			connection.setTransactionIsolation(level);
		}

		@Override
		public int getTransactionIsolation() throws SQLException {
			return connection.getTransactionIsolation();
		}

		@Override
		public SQLWarning getWarnings() throws SQLException {
			return connection.getWarnings();
		}

		@Override
		public void clearWarnings() throws SQLException {
			connection.clearWarnings();
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			return connection.getTypeMap();
		}

		@Override
		public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
			connection.setTypeMap(map);
		}

		@Override
		public void setHoldability(int holdability) throws SQLException {
			connection.setHoldability(holdability);
		}

		@Override
		public int getHoldability() throws SQLException {
			return connection.getHoldability();
		}

		@Override
		public Savepoint setSavepoint() throws SQLException {
			return connection.setSavepoint();
		}

		@Override
		public Savepoint setSavepoint(String name) throws SQLException {
			return connection.setSavepoint(name);
		}

		@Override
		public void rollback(Savepoint savepoint) throws SQLException {
			connection.rollback();
		}

		@Override
		public void releaseSavepoint(Savepoint savepoint) throws SQLException {
			connection.releaseSavepoint(savepoint);
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
			return null;
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
			return connection.prepareStatement(sql, columnIndexes);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
			return connection.prepareStatement(sql, columnNames);
		}

		@Override
		public Clob createClob() throws SQLException {
			return connection.createClob();
		}

		@Override
		public Blob createBlob() throws SQLException {
			return connection.createBlob();
		}

		@Override
		public NClob createNClob() throws SQLException {
			return connection.createNClob();
		}

		@Override
		public SQLXML createSQLXML() throws SQLException {
			return connection.createSQLXML();
		}

		@Override
		public boolean isValid(int timeout) throws SQLException {
			return connection.isValid(timeout);
		}

		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			connection.setClientInfo(name, value);
		}

		@Override
		public void setClientInfo(Properties properties) throws SQLClientInfoException {
			connection.setClientInfo(properties);
		}

		@Override
		public String getClientInfo(String name) throws SQLException {
			return connection.getClientInfo(name);
		}

		@Override
		public Properties getClientInfo() throws SQLException {
			return connection.getClientInfo();
		}

		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			return connection.createArrayOf(typeName, elements);
		}

		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			return connection. createStruct(typeName, attributes);
		}

		@Override
		public void setSchema(String schema) throws SQLException {
			connection.setSchema(schema);
		}

		@Override
		public String getSchema() throws SQLException {
			return connection.getSchema();
		}

		@Override
		public void abort(Executor executor) throws SQLException {
			connection.abort(executor);
		}

		@Override
		public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
			connection.setNetworkTimeout(executor, milliseconds);
		}

		@Override
		public int getNetworkTimeout() throws SQLException {
			return connection.getNetworkTimeout();
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return connection.unwrap(iface);
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return connection.isWrapperFor(iface);
		}
	}

}
