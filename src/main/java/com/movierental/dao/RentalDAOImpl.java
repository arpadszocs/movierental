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

import com.movierental.pojo.Rental;

public class RentalDAOImpl implements RentalDAO {

	private final HibernateTemplate hibernateTemplate;

	public RentalDAOImpl() {
		SessionFactory sessionFactory = null;
		final StandardServiceRegistry regisrty = new StandardServiceRegistryBuilder().configure().build();
		sessionFactory = new MetadataSources(regisrty).buildMetadata().buildSessionFactory();
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void save(final Rental rental) throws SQLException {
		this.hibernateTemplate.save(rental);
	}

	@Override
	public void update(final Rental rental) throws SQLException {
		this.hibernateTemplate.update(rental);
	}

	@Override
	public void delete(final Rental rental) throws SQLException {
		this.hibernateTemplate.delete(rental);
	}

	@Override
	public Rental findById(final Integer id) throws SQLException {
		final DetachedCriteria criteria = DetachedCriteria.forClass(Rental.class);
		criteria.add(Restrictions.eq("id", id));
		@SuppressWarnings("unchecked")
		final List<Rental> rentalList = (List<Rental>) this.hibernateTemplate.findByCriteria(criteria);
		return rentalList.get(0);
	}

	@Override
	public int getLastId() throws SQLException {
		return -1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rental> selectAll() throws SQLException {
		return (List<Rental>) this.hibernateTemplate.find(" from Rental ");
	}

	@Override
	public void renumber() throws SQLException {
	}

	@Override
	public List<Rental> findByFilmId(final Integer filmId) throws SQLException {
		final DetachedCriteria criteria = DetachedCriteria.forClass(Rental.class);
		criteria.add(Restrictions.eq("filmId", filmId));
		@SuppressWarnings("unchecked")
		final List<Rental> rentalList = (List<Rental>) this.hibernateTemplate.findByCriteria(criteria);
		return rentalList;
	}

	@Override
	public List<Rental> findByUserId(final Integer userId) throws SQLException {
		final DetachedCriteria criteria = DetachedCriteria.forClass(Rental.class);
		criteria.add(Restrictions.eq("userId", userId));
		@SuppressWarnings("unchecked")
		final List<Rental> rentalList = (List<Rental>) this.hibernateTemplate.findByCriteria(criteria);
		return rentalList;

	}

}
