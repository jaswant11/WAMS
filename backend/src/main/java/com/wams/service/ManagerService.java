package com.wams.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wams.dto.ManagerDetailDTO;
import com.wams.model.User;
import com.wams.repository.ManagerRepository;
import com.wams.repository.UserRepository;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ManagerDetailDTO> getAllManagerDetails() {
        return managerRepository.findAll().stream()
                .map(mgr -> {
                    Optional<User> userOpt = userRepository.findById(mgr.getId());
                    String username = userOpt.map(User::getUsername).orElse("unknown");
                    return new ManagerDetailDTO(mgr.getId(), username, mgr.getName(), mgr.getDepartment());
                }).collect(Collectors.toList());
    }
    


}
