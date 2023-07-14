package com.example.ATM.transection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transections")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transection {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long transection_id;
	private int date;
	private String name;
	private String accountNumber;
	private double remainingBalance;
	private double transectionStatement;
}
