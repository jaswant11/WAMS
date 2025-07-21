package com.wams.service;

import com.wams.model.ShiftTemplate;
import com.wams.model.User;
import com.wams.repository.ShiftTemplateRepository;
import com.wams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftTemplateService {

    @Autowired
    private ShiftTemplateRepository shiftTemplateRepository;

    @Autowired
    private UserRepository userRepository;

    public ShiftTemplate createTemplate(Long managerId, ShiftTemplate template) {
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        template.setManager(manager);
        return shiftTemplateRepository.save(template);
    }

    public List<ShiftTemplate> getTemplatesByManager(Long managerId) {
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        return shiftTemplateRepository.findByManager(manager);
    }
}
