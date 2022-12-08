package com.dropit.DeliveryAPI.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.PostConstruct;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.*;

import com.dropit.DeliveryAPI.Model.Address;
import com.dropit.DeliveryAPI.Model.Delivery;
import com.dropit.DeliveryAPI.Model.Timeslot;
import com.dropit.DeliveryAPI.Model.User;
import com.dropit.DeliveryAPI.Repo.AddressRepo;
import com.dropit.DeliveryAPI.Repo.DeliveryRepo;
import com.dropit.DeliveryAPI.Repo.TimeslotRepo;
import com.dropit.DeliveryAPI.Service.AddressDataService;
import com.dropit.DeliveryAPI.Service.TimeslotDataService;
import com.dropit.DeliveryAPI.Service.TimeslotJsonService;

@RestController
public class ApiController {
	
	@Autowired
	AddressDataService addressDataService;
	
	@Autowired
	TimeslotDataService timeslotDataService;
	
	@Autowired
	TimeslotJsonService timeslotJsonService;
	
	@Autowired
	AddressRepo addressRepo;
	
	@Autowired
	TimeslotRepo timeslotRepo;
	
	@Autowired
	DeliveryRepo deliveryRepo;
	
	@GetMapping("/")
	public String getHomePage() {
		return "";
	}
	
	@PostConstruct
	public List<Timeslot> populateTimeslots() {

		List<Timeslot> timeslots = new ArrayList<>();
		
		try {
			
			List<Map<String, String>> responseExcludeHoliday = (List<Map<String,String>>) timeslotDataService.getExcludedTimeslot().get().get("holidays");
			List<Map<String, String>> responseTimeslotJson = (List<Map<String, String>>) timeslotJsonService.getTimesoltByJson().get().get("dates");
		
			Date fromDate =textParseToDate(responseExcludeHoliday.get(0).get("date"));
			Date toDate =textParseToDate(responseTimeslotJson.get(0).get("date"));
			
			if (fromDate.compareTo(toDate) > 1) {
				timeslots = responseTimeslotJson.stream()
								.filter(date -> responseExcludeHoliday.stream()
												.filter(holidayEntry -> holidayEntry.get("date").equals(date.get("date")))
												.collect(Collectors.toList())
												.isEmpty())
								.map(date -> new Timeslot(textParseToDate(date.get("date"))))
								.collect(Collectors.toList());
			} else 
			
			timeslots = responseTimeslotJson.stream()
							.map(date -> new Timeslot(textParseToDate(date.get("date"))))
							.collect(Collectors.toList());
			
			
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}		
		
		return timeslotRepo.saveAll(timeslots);
	}
	
	@PostMapping("/resolve-address")
	public void getFormattedAddress(@RequestParam(name="searchTerm") String searchTerm) {
		
		Map<String, Object> responseAPI = addressDataService.getAddressByTerm(searchTerm);
		Map<String, String> parsedApiEntries = (Map<String, String>) responseAPI.get("parsed");
		
		Address newAddress = new Address();
		newAddress.setCountry(parsedApiEntries.get("country"));
		newAddress.setPostalCode(parsedApiEntries.get("postcode"));
		newAddress.setStreetLine1(parsedApiEntries.get("street"));
		newAddress.setFormattedAddress(responseAPI.get("text").toString());
		
		addressRepo.save(newAddress);
	}
	
	@PostMapping("/timeslots")
	public List<Timeslot> getAllAvailbleTimeslots(@RequestBody String formattedAddress){	
		return timeslotRepo.findAll().stream()
				.filter(timeslot -> timeslot.getSupportedAddresses().contains(formattedAddress))
				.collect(Collectors.toList());
		
	}
	
	@PostMapping("/deliveries/{timeslotId}")
	public Delivery bookDelivery(@PathVariable long timeslotId, @RequestBody User user) {		
		return deliveryRepo.save(new Delivery(user, timeslotId));
		
	}
	
	@PostMapping("/deliveris/{deliveryId}/complete")
	public void completedDelivery(@PathVariable("deliveryId") long deliveryId) {
		deliveryRepo.findById(deliveryId).get().setStatus(true);
	}
	
	@DeleteMapping("/deliveries/{deliveryId}")
	public void deleteDelivery(@PathVariable long deliveryId) {
		deliveryRepo.deleteById(deliveryRepo.findById(deliveryId).get().getId());
	}
	
	private Date textParseToDate(String text) {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(text);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
