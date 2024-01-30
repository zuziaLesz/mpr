package com.pjatk.MPR;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatRepository extends JpaRepository<Cat, Integer> {
  Optional<Cat> getCatById(int id);
  Optional<Cat> getAllByName(String name);
}
