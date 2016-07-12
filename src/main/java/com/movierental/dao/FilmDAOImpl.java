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
import org.springframework.transaction.annotation.Transactional;

import com.movierental.pojo.Film;

public class FilmDAOImpl implements FilmDAO {

	public final HibernateTemplate hibernateTemplate;

	public FilmDAOImpl() {
		SessionFactory sessionFactory = null;
		final StandardServiceRegistry regisrty = new StandardServiceRegistryBuilder().configure().build();
		sessionFactory = new MetadataSources(regisrty).buildMetadata().buildSessionFactory();
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		this.hibernateTemplate.setCheckWriteOperations(false);
	}

	@Override
	@Transactional
	public void save(final Film film) throws SQLException {
		this.hibernateTemplate.save(film);
	}

	@Override
	@Transactional
	public void update(final Film film) throws SQLException {
		this.hibernateTemplate.update(film);
	}

	@Override
	@Transactional
	public void delete(final Film film) throws SQLException {
		this.hibernateTemplate.delete(film);
	}

	@Override
	@Transactional
	public Film findById(final Integer id) throws SQLException {
		final DetachedCriteria criteria = DetachedCriteria.forClass(Film.class);
		criteria.add(Restrictions.eq("id", id));
		@SuppressWarnings("unchecked")
		final List<Film> filmList = (List<Film>) this.hibernateTemplate.findByCriteria(criteria);
		return filmList.get(0);
	}

	@Override
	@Transactional
	public int getLastId() throws SQLException {
		return -1;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Film> selectAll() throws SQLException {
		return (List<Film>) this.hibernateTemplate.find(" from Film ");
	}

	@Override
	public void renumber() throws SQLException {
	}

	@Override
	@Transactional
	public List<Film> findByName(final String name) throws SQLException {
		final DetachedCriteria criteria = DetachedCriteria.forClass(Film.class);
		criteria.add(Restrictions.eq("name", name));
		@SuppressWarnings("unchecked")
		final List<Film> filmList = (List<Film>) this.hibernateTemplate.findByCriteria(criteria);
		return filmList;
	}

	@Override
	@Transactional
	public List<Film> findByGenre(final String genre) throws SQLException {
		final DetachedCriteria criteria = DetachedCriteria.forClass(Film.class);
		criteria.add(Restrictions.eq("genre", genre));
		@SuppressWarnings("unchecked")
		final List<Film> filmList = (List<Film>) this.hibernateTemplate.findByCriteria(criteria);
		return filmList;
	}

	@Override
	@Transactional
	public List<Film> findByYear(final Integer year) throws SQLException {
		final DetachedCriteria criteria = DetachedCriteria.forClass(Film.class);
		criteria.add(Restrictions.eq("year", year));
		@SuppressWarnings("unchecked")
		final List<Film> filmList = (List<Film>) this.hibernateTemplate.findByCriteria(criteria);
		return filmList;
	}

}
