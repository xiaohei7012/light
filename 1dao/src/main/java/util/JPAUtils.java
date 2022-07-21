package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {
	// 同一个应用中，应该保证只有一个实例工厂。
	public static EntityManagerFactory emf = createEntityManagerFactory();

	// 1.获得实体管理工厂
	private static EntityManagerFactory createEntityManagerFactory() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql-jpa");
		return emf;
	}

	// 2.获得实体管理类对象
	public static EntityManager getEntityManger() {
		EntityManager entityManager = emf.createEntityManager();
		return entityManager;
	}

	// 3.关闭实体管理类对象
	public static void closeEntityManger() {
		if (emf != null)
			emf.close();
	}
}
