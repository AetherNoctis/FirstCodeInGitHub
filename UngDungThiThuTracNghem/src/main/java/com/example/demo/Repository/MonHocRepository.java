package com.example.demo.Repository;

import com.example.demo.Model.MonHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonHocRepository extends JpaRepository<MonHoc, String> {
    List<MonHoc> findByTenMonContainingIgnoreCase(String keyword);
    List<MonHoc> findByMaMonContainingIgnoreCase(String keyword);
}
