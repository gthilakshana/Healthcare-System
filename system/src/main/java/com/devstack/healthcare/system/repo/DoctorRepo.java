package com.devstack.healthcare.system.repo;

import com.devstack.healthcare.system.entity.Doctor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface DoctorRepo extends JpaRepository<Doctor,Long> {
    public List<Doctor>findAllByName(String name);

    @Query(value = "SELECT * FROM doctor WHERE name LIKE ?1 OR address LIKE ?1",nativeQuery = true)
    public List<Doctor> searchDoctors(String searchText, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM doctor WHERE name LIKE ?1 OR address LIKE ?1",nativeQuery = true)
    public Long countDoctors(String searchText);

}
