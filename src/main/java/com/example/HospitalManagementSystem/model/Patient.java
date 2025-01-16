package com.example.HospitalManagementSystem.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "patients")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    
    @ManyToOne
    @JoinColumn(name = "userdetails_id")
    private UserDetails userDetails;
    

    @ManyToOne
    @Transient
    private Receptionist receptionist;
    

    @ManyToOne
    @JoinColumn(name = "nurse_id")  
    @JsonIgnore
    private Nurse nurse;

    public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<ConsultationFee> consultationFees;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    
    private boolean consultationFeePaid = false;

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Receptionist getReceptionist() {
		return receptionist;
	}

	public void setReceptionist(Receptionist receptionist) {
		this.receptionist = receptionist;
	}

	public List<ConsultationFee> getConsultationFees() {
		return consultationFees;
	}

	public void setConsultationFees(List<ConsultationFee> consultationFees) {
		this.consultationFees = consultationFees;
	}

	public boolean isConsultationFeePaid() {
		return consultationFeePaid;
	}

	public void setConsultationFeePaid(boolean consultationFeePaid) {
		this.consultationFeePaid = consultationFeePaid;
	}

	public Patient() {
        // Default constructor
    }

    public Patient(UserDetails userDetails, User createdBy) {
        this.userDetails = userDetails;
        this.createdBy = createdBy;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
