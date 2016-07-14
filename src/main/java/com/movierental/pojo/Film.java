package com.movierental.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Film implements Comparable<Film> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;

	@Column
	private String name;

	@Column
	private Integer length;

	@Column
	private String genre;

	@Column
	private Integer year;

	public Film() {
		super();
	}

	public Film(final Integer id, final String name, final Integer length, final String genre, final Integer year) {
		super();
		this.Id = id;
		this.name = name;
		this.length = length;
		this.genre = genre;
		this.year = year;
	}

	public Film(final String name, final Integer length, final String genre, final Integer year) {
		super();
		this.name = name;
		this.length = length;
		this.genre = genre;
		this.year = year;
	}

	public Integer getId() {
		return this.Id;
	}

	public String getName() {
		return this.name;
	}

	public Integer getLength() {
		return this.length;
	}

	public String getGenre() {
		return this.genre;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setId(final Integer id) {
		this.Id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setLength(final Integer length) {
		this.length = length;
	}

	public void setGenre(final String genre) {
		this.genre = genre;
	}

	public void setYear(final Integer year) {
		this.year = year;
	}

	@Override
	public int compareTo(final Film film) {
		if (film.getName() == null) {
			return 1;
		}
		final int compare = this.getName().compareTo(film.getName());
		if (compare != 0) {
			return compare;
		} else if (this.length > film.length) {
			return 1;
		} else if (this.length < film.length) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.Id == null ? 0 : this.Id.hashCode());
		result = prime * result + (this.genre == null ? 0 : this.genre.hashCode());
		result = prime * result + (this.length == null ? 0 : this.length.hashCode());
		result = prime * result + (this.name == null ? 0 : this.name.hashCode());
		result = prime * result + (this.year == null ? 0 : this.year.hashCode());
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
		final Film other = (Film) obj;
		if (this.Id == null) {
			if (other.Id != null) {
				return false;
			}
		} else if (!this.Id.equals(other.Id)) {
			return false;
		}
		if (this.genre == null) {
			if (other.genre != null) {
				return false;
			}
		} else if (!this.genre.equals(other.genre)) {
			return false;
		}
		if (this.length == null) {
			if (other.length != null) {
				return false;
			}
		} else if (!this.length.equals(other.length)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.year == null) {
			if (other.year != null) {
				return false;
			}
		} else if (!this.year.equals(other.year)) {
			return false;
		}
		return true;
	}

}
