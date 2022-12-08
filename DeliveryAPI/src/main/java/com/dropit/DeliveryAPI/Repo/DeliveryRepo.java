package com.dropit.DeliveryAPI.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dropit.DeliveryAPI.Model.Delivery;

public interface DeliveryRepo extends JpaRepository<Delivery, Long>{

}
