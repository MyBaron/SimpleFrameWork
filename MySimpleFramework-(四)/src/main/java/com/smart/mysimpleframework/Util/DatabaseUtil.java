package com.smart.mysimpleframework.Util;


import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class DatabaseUtil {

//    利用Apache的一个工具库DbUtils 对JDBC的封装
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    //    线程池
    private static final BasicDataSource DATA_SOURCE;
    // 用ThreadLoad 用来存放一个线程的Connection，保证线程隔离
    private  static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);


    static{
        System.out.println("配置加载");
        Properties conf = PropsUtil.loadProps("config.properties");
        String driver = conf.getProperty("jdbc.driver");
        String url = conf.getProperty("jdbc.url");
        String username = conf.getProperty("jdbc.username");
        String password = conf.getProperty("jdbc.password");


        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);

    }


    public static Connection getConnection() {
        Connection connection = null;
        connection=threadLocal.get();
        if (connection == null) {
            try {
                connection = DATA_SOURCE.getConnection();

            } catch (SQLException e) {
                logger.error("获取连接失败", e);
                e.printStackTrace();
            }finally {
                threadLocal.set(connection);
            }
        }
        return connection;
    }




    /**
     * 查询获取实体列表
     * @param entityClass 返回对象
     * @param sql    查询语句
     * @param params 查询条件
     *
     *
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass,String sql, Object... params) {
        List<T> entityList = null;
        Connection con = null;

        try {
            con = getConnection();
            entityList =
                    QUERY_RUNNER.query(con, sql, new BeanListHandler<T>(entityClass), params);

        } catch (SQLException e) {
            logger.error("查询失败",e);
            e.printStackTrace();
        }
        return entityList;
    }

    /**
     *  查询实体
     * @param tClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T queryEntity(Class<T> tClass, String sql, Object... params) {
        T entity = null;
        Connection con = null;

        try {
            con = getConnection();
            entity =
                    QUERY_RUNNER.query(con, sql, new BeanHandler<T>(tClass), params);

        } catch (SQLException e) {
            logger.error("查询失败",e);
            e.printStackTrace();
        }
        return entity;
    }

    /**
     *  执行查询语句
     *  返回一个List对象。Map 表示列名与列值得映射关系
     * @param sql
     * @param params
     * @return
     */

    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String,Object>> entity = null;
        Connection con = null;

        try {
            con = getConnection();
            entity =
                    QUERY_RUNNER.query(con, sql, new MapListHandler(), params);

        } catch (SQLException e) {
            logger.error("查询失败",e);
            e.printStackTrace();
        }
        return entity;
    }


    /**
     *  执行更新语句（update,insert,delete）
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdate(String sql, Object... params) {
        int rows=0;
        Connection con = null;

        try {
            con = getConnection();
            rows =
                    QUERY_RUNNER.update(con, sql, new MapListHandler(), params);

        } catch (SQLException e) {
            logger.error("查询失败",e);
            e.printStackTrace();
        }
        return rows;
    }

    /**
     *  插入实体
     * @param entityClass
     * @param filedMap
     * @param <T>
     * @return
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> filedMap) {
        /*
        *  1. 判断filedMap是否为空
        *  2. 对Map遍历，拼接sql语句
        *  3. 执行语句
        * */
        if (MapUtils.isEmpty(filedMap)) {
            logger.error("插入实体失败,实体Map为空");
            return false;
        }
        Iterator<String> iterator = filedMap.keySet().iterator();
        StringBuffer columns = new StringBuffer("(");
        StringBuffer values = new StringBuffer("(");
        String sql=("insert into" + entityClass.getSimpleName());
        while (iterator.hasNext()) {
            String key = iterator.next();
            columns.append(key).append(", ");
            values.append("?, ");
        }
//        删除最后一个 , 然后加上 )
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");

        sql += columns + " values " + values;

        Object[] params = filedMap.values().toArray();



        return executeUpdate(sql,params)==1;
    }

    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        //TODO
        return false;
    }

    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "Delete From " + entityClass.getSimpleName() + " Where id = ?";
        return executeUpdate(sql, id) == 1;
    }
}
