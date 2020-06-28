package com.example.novopay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.novopay.model.Users;
import com.example.novopay.repository.UserRepository;

@Service
public class UserFetchService {

  /**
   * UserRepository.
   */
  @Autowired
  private UserRepository userRepository;

  public Users fetchUserById(long id) throws Exception {
    try {
      return userRepository.findByUserId(id);
    } catch (Exception ex) {
      throw ex;
    }
  }
}
