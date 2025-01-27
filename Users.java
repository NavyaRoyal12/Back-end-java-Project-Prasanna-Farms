package in.prasannaFarm.entity;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "userDetails" )
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @Column(name = "ID",unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
    @Column(name = "USERNAME",nullable = false)
	private String userName;
	
    @Column(name = "EMAIL",unique = true,nullable = false)
	private String email;
	
    @Column(name = "PASSWORD",unique = true,nullable = false)
	private String password;
	
    @Column(name = "PHONENUMBER",unique = true,nullable = false)
	private long phoneNumber;
	
    @Column(name = "ADDRESS",nullable = false)
	private String address;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Users [id=" + id + ", userName=" + userName + ", email=" + email + ", password=" + password
				+ ", phoneNumber=" + phoneNumber + ", address=" + address + "]";
	}
    
    
}