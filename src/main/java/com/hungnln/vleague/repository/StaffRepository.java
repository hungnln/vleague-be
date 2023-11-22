package com.hungnln.vleague.repository;


import com.hungnln.vleague.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff, UUID> {
    List<Staff> findAll();
    Optional<Staff> findStaffById(UUID id);
    Optional<Staff> findStaffByName(String name);
}
