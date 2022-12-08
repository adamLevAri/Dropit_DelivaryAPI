package com.dropit.DeliveryAPI.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.dropit.DeliveryAPI.Model.Address;

public interface AddressRepo extends JpaRepository<Address, Long>{

}
