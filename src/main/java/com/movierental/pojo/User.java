package com.movierental.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Comparable<User> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;

	@Column
	private String name;

	@Column
	private String email;

	@Column
	private String password;

	@Column
	private String role;

	public User() {
		super();
	}

	public User(final Integer id, final String name, final String email, final String password, final String role) {
		super();
		this.Id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public User(final String name, final String email, final String password, final String role) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Integer getId() {
		return this.Id;
	}

	public String getName() {
		return this.name;
	}

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}

	public String getRole() {
		return this.role;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setId(final Integer id) {
		this.Id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	@Override
	public int compareTo(final User user) {
		return this.getName().compareTo(user.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.Id == null ? 0 : this.Id.hashCode());
		result = prime * result + (this.email == null ? 0 : this.email.hashCode());
		result = prime * result + (this.name == null ? 0 : this.name.hashCode());
		result = prime * result + (this.password == null ? 0 : this.password.hashCode());
		result = prime * result + (this.role == null ? 0 : this.role.hashCode());
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
		final User other = (User) obj;
		if (this.Id == null) {
			if (other.Id != null) {
				return false;
			}
		} else if (!this.Id.equals(other.Id)) {
			return false;
		}
		if (this.email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!this.email.equals(other.email)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!this.password.equals(other.password)) {
			return false;
		}
		if (this.role == null) {
			if (other.role != null) {
				return false;
			}
		} else if (!this.role.equals(other.role)) {
			return false;
		}
		return true;
	}

}
