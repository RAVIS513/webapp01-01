package jp.ne.ravi.utils;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jp.ne.ravi.exception.WebException;

@Scope("prototype")
@Component
public class DbSessionUtil {

	private Logger logger = LogManager.getLogger(DbSessionUtil.class);

	private SqlSession session;

	public DbSessionUtil() {
		session = null;
	}

	/**
	 * DBセッションスタート
	 * @return
	 */
	public SqlSession open() {
		try {
			SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(this.getClass().getResourceAsStream("mybatis-config.xml"));
			session = ssf.openSession(false);
			logger.debug("DB Session Open : " + " / " + session);
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				session.close();
			}
			throw new WebException("DB Session Open Error", e);
		}
		return session;
	}

	/**
	 * DBコミット
	 * @param session
	 */
	public void commit(SqlSession session) {
		try {
			session.commit();
			logger.debug("DB Commit : " + session);
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			session.close();
			throw new WebException("DB Commit Error", e);
		}
	}

	/**
	 * DB異常終了処理
	 * @param session
	 */
	public void rollback(SqlSession session) {
		try {
			session.rollback();
			logger.debug("DB Rollback : " + session);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebException("DB Rollback Error", e);
		} finally {
			session.close();
		}
	}

	/**
	 * DB正常終了処理
	 * @param session
	 */
	public void close(SqlSession session) {
		try {
			session.commit();
			logger.debug("DB Commit : " + session);
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			throw new WebException("DB Close Error", e);
		} finally {
			session.close();
			logger.debug("DB Close : " + session);
		}
	}

}
