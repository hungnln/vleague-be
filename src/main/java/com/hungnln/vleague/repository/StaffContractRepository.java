package com.hungnln.vleague.repository;

import com.hungnln.vleague.entity.PlayerContract;
import com.hungnln.vleague.entity.StaffContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StaffContractRepository extends JpaRepository<StaffContract, UUID>, JpaSpecificationExecutor<StaffContract> {
    Optional<StaffContract> findStaffContractById(UUID id);
//    Optional<StaffContract> findStaffContractByStaff(String name);
    @Query(nativeQuery = true,value = "SELECT * FROM staffcontracts p WHERE p.staffid = :staffId " +
            "AND (" +
            "    (p.start BETWEEN :start AND :end" +
            "    OR p.end BETWEEN :start AND :end)" +
            "    OR (:start BETWEEN p.start AND p.end)" +
            ")")
    List<StaffContract> findAllByStartGreaterThanEqualAndEndLessThanEqualAndStaffId(@Param("start") String start,@Param("end") String end,@Param("staffId") UUID staffId);
}

