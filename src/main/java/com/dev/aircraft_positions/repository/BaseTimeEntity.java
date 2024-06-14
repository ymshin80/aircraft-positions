package com.dev.aircraft_positions.repository;

import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
	
	
	@CreatedDate
	@Column(updatable = false)
	private Instant createDate;
	
	@CreatedBy
	@Column(updatable = false)
	private Long createdId;
	
	
	@LastModifiedDate
	@Column(updatable = true)
	private Instant updateDate;
	
	
	@LastModifiedBy
	@Column(updatable = true)
	private Long updatedId;


	public Instant getCreateDate() {
		return createDate;
	}


	public Long getCreatedId() {
		return createdId;
	}


	public Instant getUpdateDate() {
		return updateDate;
	}


	public Long getUpdatedId() {
		return updatedId;
	}
	
	

}
