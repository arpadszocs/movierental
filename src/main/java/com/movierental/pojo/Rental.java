package com.movierental.pojo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;

	@Column
	private Integer filmId;

	@Column
	private Integer userId;

	@Column
	private Date startDate;

	@Column
	private Date endDate;

	public Rental() {
		super();
	}

	public Rental(final Integer id, final Integer filmId, final Integer userId, final Date startDate,
			final Date endDate) {
		super();
		this.Id = id;
		this.filmId = filmId;
		this.userId = userId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Rental(final Integer filmId, final Integer userId, final Date startDate, final Date endDate) {
		super();
		this.filmId = filmId;
		this.userId = userId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Integer getId() {
		return this.Id;
	}

	public Integer getFilmId() {
		return this.filmId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setId(final Integer id) {
		this.Id = id;
	}

	public void setFilmId(final Integer filmId) {
		this.filmId = filmId;
	}

	public void setUserId(final Integer userId) {
		this.userId = userId;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.Id == null ? 0 : this.Id.hashCode());
		result = prime * result + (this.endDate == null ? 0 : this.endDate.hashCode());
		result = prime * result + (this.filmId == null ? 0 : this.filmId.hashCode());
		result = prime * result + (this.startDate == null ? 0 : this.startDate.hashCode());
		result = prime * result + (this.userId == null ? 0 : this.userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Rental other = (Rental) obj;
		if (this.Id == null) {
			if (other.Id != null) {
				return false;
			}
		} else if (!this.Id.equals(other.Id)) {
			return false;
		}
		if (this.endDate == null) {
			if (other.endDate != null) {
				return false;
			}
		} else if (!this.endDate.equals(other.endDate)) {
			return false;
		}
		if (this.filmId == null) {
			if (other.filmId != null) {
				return false;
			}
		} else if (!this.filmId.equals(other.filmId)) {
			return false;
		}
		if (this.startDate == null) {
			if (other.startDate != null) {
				return false;
			}
		} else if (!this.startDate.equals(other.startDate)) {
			return false;
		}
		if (this.userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!this.userId.equals(other.userId)) {
			return false;
		}
		return true;
	}
}
