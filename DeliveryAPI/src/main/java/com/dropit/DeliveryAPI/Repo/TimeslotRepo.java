package com.dropit.DeliveryAPI.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dropit.DeliveryAPI.Model.Timeslot;

public interface TimeslotRepo extends JpaRepository<Timeslot, Long>{

}
