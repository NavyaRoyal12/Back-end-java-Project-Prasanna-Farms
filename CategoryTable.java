package in.prasannaFarm.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "CATEGORY" )
public class CategoryTable {
	
	 @Id
	    @Column(name = "ID")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int id;
       
	 @Column(name = "CATEGORYNAME")
	 private String category;
}