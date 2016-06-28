package com.movierental.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.movierental.pojo.User;

public class UserDAOImpl implements UserDAO {

	private final HibernateTemplate hibernateTemplate;

	public UserDAOImpl() {
		SessionFactory sessionFactory = null;
		final StandardServiceRegistry regisrty = new StandardServiceRegistryBuilder().configure().build();
		sessionFactory = new MetadataSources(regisrty).buildMetadata().buildSessionFactory();
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void save(final User film) throws SQLException {
		this.hibernateTemplate.save(film);

	}

	@Override
	public void update(final User film) throws SQLException {
		this.hibernateTemplate.update(film);
	}

	@Override
	public void delete(final User film) throws SQLException {
		this.hibernateTemplate.delete(film);
	}

	@Override
	public User findById(final Integer id) throws SQLException {
		final DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("id", id));
		@SuppressWarnings("unchecked")
		final List<User> userList = (List<User>) this.hibernateTemplate.findByCriteria(criteria);
		return userList.get(0);
	}

	@Override
	public int getLastId() throws SQLException {
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> selectAll() throws SQLException {
		return (List<User>) this.hibernateTemplate.find(" from User ");
	}

	@Override
	public void renumber() throws SQLException {

	}

	@Override
	public User findByName(final String name) throws SQLException {
		// TODO: write it
		return null;
	}

	@Override
	public User findByEmail(final String email) throws SQLException {
		// TODO: write it
		return null;
	}

}
