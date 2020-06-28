package com.example.novopay.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSignUpDto {

	List<UserInfo> successfullyRegistered;

	List<UserInfo> alreadyExisting;

	public List<UserInfo> getSuccessfullyRegistered() {
		return successfullyRegistered;
	}

	public void setSuccessfullyRegistered(List<UserInfo> successfullyRegistered) {
		this.successfullyRegistered = successfullyRegistered;
	}

	public List<UserInfo> getAlreadyExisting() {
		return alreadyExisting;
	}

	public void setAlreadyExisting(List<UserInfo> alreadyExisting) {
		this.alreadyExisting = alreadyExisting;
	}

	public PostSignUpDto(List<UserInfo> successfullyRegistered, List<UserInfo> alreadyExisting) {
		super();
		this.successfullyRegistered = successfullyRegistered;
		this.alreadyExisting = alreadyExisting;
	}

}
