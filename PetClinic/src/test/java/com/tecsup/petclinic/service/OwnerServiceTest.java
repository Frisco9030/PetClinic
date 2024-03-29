package com.tecsup.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tecsup.petclinic.domain.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace= Replace.NONE)
public class OwnerServiceTest {
	
	private static final Logger logger=LoggerFactory.getLogger(OwnerServiceTest.class);
	
	@Autowired
	private OwnerService ownerService;
	
	/**
	 * 
	 * 
	 */
	@Test
	public void testFindOwnerById() {
		long id=1;
		String first_name="George";
		Owner owner=null;
		
		try {
			owner= ownerService.findById(id);
		}catch(OwnerNotFoundException e1) {
			fail(e1.getMessage());
		}
		logger.info(""+owner);
		
		assertEquals(first_name, owner.getFirst_name());
	}
	
	@Test
	public void testCreateOwner() {
		String first_name="Francisco";
		String last_name="Ramirez";
		String address="302 Patriotas, San Miguel";
		String city="Lima";
		String telephone="987535717";

		Owner owner=new Owner(first_name,last_name, address, city, telephone);
		owner= ownerService.create(owner);
		logger.info(""+owner);
		
		assertEquals(first_name,owner.getFirst_name());
		assertEquals(last_name,owner.getLast_name());
		assertEquals(address,owner.getAddress());
		assertEquals(city,owner.getCity());
		assertEquals(telephone,owner.getTelephone());
	}
	
	@Test
	public void testDelete() {
		String first_name="Francisco";
		String last_name="Ramirez";
		String address="302 Patriotas, San Miguel";
		String city="Lima";
		String telephone="987535717";
		
		Owner owner=new Owner(first_name, last_name, address, city, telephone);
		owner= ownerService.create(owner);
		logger.info(""+owner);
		
		try {
			ownerService.delete(owner.getId());
		}catch(OwnerNotFoundException e1) {
			fail(e1.getMessage());
		}
		try {
			ownerService.findById(owner.getId());
			assertTrue(false);
		}catch(OwnerNotFoundException e2) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testUpdateOwner() {
		// Lo que se va a sustituir
		String first_name="Francisco";
		String last_name="Ramirez";
		String address="302 Patriotas, San Miguel";
		String city="Lima";
		String telephone="987535717";
		long id=1;
		
		// Lo que se va a subir
		String up_first_name="Elmer";
		String up_last_name="Curio";
		String up_address="ciudad del deporte";
		String up_city="Ventanilla";
		String up_telephone="987456123";
		
		Owner owner=new Owner(first_name, last_name, address, city, telephone);
		
		// Crear record
		logger.info(">"+ owner);
		Owner leerOwner= ownerService.create(owner);
		logger.info(">>"+leerOwner);
		
		id=leerOwner.getId();
		
		// Preparar data para update
		leerOwner.setFirst_name(up_first_name);
		leerOwner.setLast_name(up_last_name);
		leerOwner.setAddress(up_address);
		leerOwner.setCity(up_city);
		leerOwner.setTelephone(up_telephone);
		
		// Ejecutar Update
		Owner upgradeOwner= ownerService.update(leerOwner);
		logger.info(">>>>"+upgradeOwner);
		
		assertThat(id).isNotNull();
		assertEquals(id,upgradeOwner.getId());
		assertEquals(up_first_name,upgradeOwner.getFirst_name());
		assertEquals(up_last_name,upgradeOwner.getLast_name());
		assertEquals(up_address,upgradeOwner.getAddress());
		assertEquals(up_city,upgradeOwner.getCity());
		assertEquals(up_telephone,upgradeOwner.getTelephone());
	}

}
