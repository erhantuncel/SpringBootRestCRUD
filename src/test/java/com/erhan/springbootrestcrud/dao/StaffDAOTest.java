package com.erhan.springbootrestcrud.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.erhan.springbootrestcrud.model.Department;
import com.erhan.springbootrestcrud.model.Staff;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StaffDAOTest {

	public static final Logger logger = LoggerFactory.getLogger(StaffDAOTest.class);
	
	@Autowired
	StaffDAO staffDAO;
	
	@Autowired
	DepartmentDAO departmentDAO;
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testSaveWithImage() {
		logger.info("testSaveImage is started.");
		
		Department department1 = departmentDAO.findById(1L).orElse(null);
		assertNotNull(department1);

		Staff staff = new Staff("Ahmet", "ÇALIŞKAN", "1231231231", "ahmet@abc.com", getByteArrayOfTestImage("man1.png"), new Date(), department1);
		staffDAO.save(staff);
		department1.getStaffList().add(staff);
		departmentDAO.save(department1);
		departmentDAO.flush();
		staffDAO.flush();
		assertNotNull(staff.getId());
		
		Staff staff7 = staffDAO.findById(7L).orElse(null);
		assertNotNull(staff7);
		assertNotNull(staff7.getImage());
		byte[] testImage = getByteArrayOfTestImage("man1.png");
		byte[] imageFromDb = staff7.getImage();
		String encodedTestImage = Base64.getEncoder().encodeToString(testImage);
		String encodedImageFromDb = Base64.getEncoder().encodeToString(imageFromDb);
		assertEquals(encodedTestImage, encodedImageFromDb);
		
		logger.info("testSaveImage is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindAll() {
		logger.info("testFindAll is started.");
		
		List<Staff> allStaffs = staffDAO.findAll();
		assertNotNull(allStaffs);
		assertEquals(allStaffs.size(), 6);
		
		logger.info("testFindAll is started.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindById() {
		logger.info("testFindById is started.");
		
		Staff staff1 = staffDAO.findById(1L).orElse(null);
		assertNotNull(staff1);
		assertEquals(staff1.getId(), Long.valueOf("1"));
		assertEquals(staff1.getFirstName(), "Simge");
		assertEquals(staff1.getLastName(), "CİĞERLİOĞLU");
		assertEquals(staff1.getPhone(), "6279548733");
		assertNull(staff1.getEmail());
		assertEquals(staff1.getDepartment().getId(), Long.valueOf("1"));
		
		logger.info("testFindById is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindAllPaginated() {
		logger.info("testFindAllPaginated is started.");
		
		List<Staff> allStaff = staffDAO.findAll(PageRequest.of(0, 2)).getContent();

		assertNotNull(allStaff);
		assertEquals(allStaff.size(), 2);
		assertEquals(allStaff.get(0).getFirstName(), "Simge");
		
		allStaff = staffDAO.findAll(PageRequest.of(1, 4)).getContent();
		assertNotNull(allStaff);
		assertEquals(allStaff.size(), 2);
		assertEquals(allStaff.get(0).getFirstName(), "Emre");
		
		logger.info("testFindAllPaginated is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testDelete() {
		logger.info("testDelete is started.");
		List<Staff> allStaffBeforeDelete = staffDAO.findAll();
		
		Staff staff1 = staffDAO.findById(1L).orElse(null);
		assertNotNull(staff1);
		Department department = departmentDAO.findById(staff1.getDepartment().getId()).orElse(null);
		department.getStaffList().remove(staff1);
		departmentDAO.save(department);		
		staffDAO.delete(staff1);
		staffDAO.flush();
		
		List<Staff> allStaffAfterDelete = staffDAO.findAll();
		assertEquals(allStaffAfterDelete.size(), allStaffBeforeDelete.size()-1);
		
		logger.info("testDelete is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByFirstName() {
		logger.info("testFindByFirstName is started.");
		
		List<Staff> findByFirstName = staffDAO.findByFirstName("Arzu");
		assertNotNull(findByFirstName);
		assertEquals(findByFirstName.size(), 1);
		assertEquals(findByFirstName.get(0).getFirstName(), "Arzu");
		
		logger.info("testFindByFirstName is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByLastName() {
		logger.info("testFindByLastName is started.");
		
		List<Staff> findByLastName = staffDAO.findByLastName("BULGUR");
		assertNotNull(findByLastName);
		assertEquals(findByLastName.size(), 1);
		assertEquals(findByLastName.get(0).getLastName(), "BULGUR");
		
		logger.info("testFindByLastName is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByPhone() {
		logger.info("testFindByPhone is started.");
		
		List<Staff> findByPhone = staffDAO.findByPhone("1283663610");
		assertNotNull(findByPhone);
		assertEquals(findByPhone.size(), 1);
		assertEquals(findByPhone.get(0).getPhone(), "1283663610");
		
		logger.info("testFindByPhone is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByEmail() {
		logger.info("testFindByEmail is started.");
		
		List<Staff> findByEmail = staffDAO.findByEmail("arzu@abc.com");
		assertNotNull(findByEmail);
		assertEquals(findByEmail.size(), 1);
		assertEquals(findByEmail.get(0).getEmail(), "arzu@abc.com");
		
		logger.info("testFindByEmail is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByCreateDate() throws ParseException {
		logger.info("testFindByCreateDate is started.");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateToCheck = df.parse("2019-06-20 18:35:52");
		List<Staff> findByRegisteredTime = staffDAO.findByCreateDate(dateToCheck);
		assertNotNull(findByRegisteredTime);
		assertEquals(findByRegisteredTime.size(), 1);
		assertEquals(findByRegisteredTime.get(0).getCreateDate().getTime(), dateToCheck.getTime());
		
		logger.info("testFindByCreateDate is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByDepartment() {
		logger.info("testFindByDepartment is started.");

		Department departmentId2 = departmentDAO.findById(2L).orElse(null);
		List<Staff> findByDepartment = staffDAO.findByDepartment(departmentId2);
		assertNotNull(findByDepartment);
		assertEquals(findByDepartment.size(), 4);
		assertEquals(findByDepartment.get(0).getDepartment().getDepartmentName(), departmentId2.getDepartmentName());
		
		logger.info("testFindByDepartment is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByDepartment_Id() {
		logger.info("testFindByDepartment_Id is started.");

		List<Staff> findByDepartment_Id = staffDAO.findByDepartment_Id(2L);
		assertNotNull(findByDepartment_Id);
		assertEquals(findByDepartment_Id.size(), 4);
		assertEquals(findByDepartment_Id.get(0).getDepartment().getId(), Long.valueOf("2"));
		assertEquals(findByDepartment_Id.get(0).getFirstName(), "Arzu");
		
		logger.info("testFindByDepartment_Id is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByIdAndDepartmentId() {
		logger.info("testFindByIdAndDepartmentId is started.");

		List<Staff> findByIdAndDepartmentId = staffDAO.findByIdAndDepartment_Id(3L, 2L);
		assertNotNull(findByIdAndDepartmentId);
		assertEquals(findByIdAndDepartmentId.size(), 1);
		assertEquals(findByIdAndDepartmentId.get(0).getDepartment().getId(), Long.valueOf("2"));
		assertEquals(findByIdAndDepartmentId.get(0).getFirstName(), "Emre");
		
		logger.info("testFindByIdAndDepartmentId is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByIdAndDepartmentIdReturnNoStaff() {
		logger.info("testFindByIdAndDepartmentIdReturnNoStaff is started.");

		List<Staff> findByIdAndDepartmentId = staffDAO.findByIdAndDepartment_Id(3L, 3L);
		assertNotNull(findByIdAndDepartmentId);
		assertEquals(findByIdAndDepartmentId.size(), 0);	
		
		logger.info("testFindByIdAndDepartmentIdReturnNoStaff is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByFirstNameAndDepartmentId() {
		logger.info("testFindByFirstNameAndDepartmentId is started.");

		List<Staff> findByFirstNameAndDepartmentId = staffDAO.findByFirstNameAndDepartment_Id("Emre", 2L);
		assertNotNull(findByFirstNameAndDepartmentId);
		assertEquals(findByFirstNameAndDepartmentId.size(), 3);
		assertEquals(findByFirstNameAndDepartmentId.get(0).getDepartment().getId(), Long.valueOf("2"));
		assertEquals(findByFirstNameAndDepartmentId.get(0).getFirstName(), "Emre");
		
		logger.info("testFindByFirstNameAndDepartmentId is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByFirstNameAndDepartmentIdPaginated() {
		logger.info("testFindByFirstNameAndDepartmentIdPaginated is started.");

		List<Staff> findByFirstNameAndDepartmentId = staffDAO.findByFirstNameAndDepartment_Id("Emre", 2L, PageRequest.of(0, 2));
		assertNotNull(findByFirstNameAndDepartmentId);
		assertEquals(findByFirstNameAndDepartmentId.size(), 2);
		assertEquals(findByFirstNameAndDepartmentId.get(0).getDepartment().getId(), Long.valueOf("2"));
		assertEquals(findByFirstNameAndDepartmentId.get(0).getFirstName(), "Emre");
		assertEquals(findByFirstNameAndDepartmentId.get(0).getLastName(), "BİNBAY");
		
		findByFirstNameAndDepartmentId = staffDAO.findByFirstNameAndDepartment_Id("Emre", 2L, PageRequest.of(1, 2));
		assertNotNull(findByFirstNameAndDepartmentId);
		assertEquals(findByFirstNameAndDepartmentId.size(), 1);
		assertEquals(findByFirstNameAndDepartmentId.get(0).getDepartment().getId(), Long.valueOf("2"));
		assertEquals(findByFirstNameAndDepartmentId.get(0).getFirstName(), "Emre");
		assertEquals(findByFirstNameAndDepartmentId.get(0).getLastName(), "YILMAZ");
		
		logger.info("testFindByFirstNameAndDepartmentIdPaginated is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByLastNameAndDepartmentId() {
		logger.info("testFindByLastNameAndDepartmentId is started.");

		List<Staff> findByLastNameAndDepartmentId = staffDAO.findByLastNameAndDepartment_Id("BİNBAY", 2L);
		assertNotNull(findByLastNameAndDepartmentId);
		assertEquals(findByLastNameAndDepartmentId.size(), 1);
		assertEquals(findByLastNameAndDepartmentId.get(0).getDepartment().getId(), Long.valueOf("2"));
		assertEquals(findByLastNameAndDepartmentId.get(0).getLastName(), "BİNBAY");
		
		logger.info("testFindByLastNameAndDepartmentId is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByLastNameAndDepartmentIdPaginated() {
		logger.info("testFindByLastNameAndDepartmentIdPaginated is started.");

		List<Staff> findByLastNameAndDepartmentId = staffDAO.findByLastNameAndDepartment_Id("CİĞERLİOĞLU", 1L, PageRequest.of(0, 1));
		assertNotNull(findByLastNameAndDepartmentId);
		assertEquals(findByLastNameAndDepartmentId.size(), 1);
		assertEquals(findByLastNameAndDepartmentId.get(0).getDepartment().getId(), Long.valueOf("1"));
		assertEquals(findByLastNameAndDepartmentId.get(0).getFirstName(), "Simge");
		assertEquals(findByLastNameAndDepartmentId.get(0).getLastName(), "CİĞERLİOĞLU");
		
		findByLastNameAndDepartmentId = staffDAO.findByLastNameAndDepartment_Id("CİĞERLİOĞLU", 1L, PageRequest.of(1, 1));
		assertNotNull(findByLastNameAndDepartmentId);
		assertEquals(findByLastNameAndDepartmentId.size(), 1);
		assertEquals(findByLastNameAndDepartmentId.get(0).getDepartment().getId(), Long.valueOf("1"));
		assertEquals(findByLastNameAndDepartmentId.get(0).getFirstName(), "Merve");
		assertEquals(findByLastNameAndDepartmentId.get(0).getLastName(), "CİĞERLİOĞLU");
		
		logger.info("testFindByLastNameAndDepartmentIdPaginated is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByPhoneAndDepartmentId() {
		logger.info("testFindByPhoneAndDepartmentId is started.");

		List<Staff> findByPhoneAndDepartmentId = staffDAO.findByPhoneAndDepartment_Id("7543118133", 2L);
		assertNotNull(findByPhoneAndDepartmentId);
		assertEquals(findByPhoneAndDepartmentId.size(), 1);
		assertEquals(findByPhoneAndDepartmentId.get(0).getDepartment().getId(), Long.valueOf("2"));
		assertEquals(findByPhoneAndDepartmentId.get(0).getPhone(), "7543118133");
		
		logger.info("testFindByPhoneAndDepartmentId is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByEmailAndDepartmentId() {
		logger.info("testFindByEmailAndDepartmentId is started.");

		List<Staff> findByPhoneAndDepartmentId = staffDAO.findByEmailAndDepartment_Id("arzu@abc.com", 2L);
		assertNotNull(findByPhoneAndDepartmentId);
		assertEquals(findByPhoneAndDepartmentId.size(), 1);
		assertEquals(findByPhoneAndDepartmentId.get(0).getDepartment().getId(), Long.valueOf("2"));
		assertEquals(findByPhoneAndDepartmentId.get(0).getEmail(), "arzu@abc.com");
		
		logger.info("testFindByEmailAndDepartmentId is successful.");
	}
	
	@Test
	@Sql(scripts = {"classpath:test.sql"})
	public void testFindByRegisteredTimeAndDepartmentId() throws ParseException {
		logger.info("testFindByRegisteredTimeAndDepartmentId is started.");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateToCheck = df.parse("2019-06-20 18:35:52");
		List<Staff> findByRegisteredTimeAndDepartmentId = staffDAO.findByCreateDateAndDepartment_Id(dateToCheck, 2L);
		assertNotNull(findByRegisteredTimeAndDepartmentId);
		assertEquals(findByRegisteredTimeAndDepartmentId.size(), 1);
		assertEquals(findByRegisteredTimeAndDepartmentId.get(0).getCreateDate().getTime(), dateToCheck.getTime());
		
		logger.info("testFindByRegisteredTimeAndDepartmentId is successful.");
	}
	
	private byte[] getByteArrayOfTestImage(String path) {
		byte[] imageArray = new byte[0];
		try {
			File imageFile = new ClassPathResource(path).getFile();
			imageArray = new byte[(int)imageFile.length()];;
			FileInputStream fileInputStream = new FileInputStream(imageFile);
			fileInputStream.read(imageArray);
			fileInputStream.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return imageArray;
	}
}
