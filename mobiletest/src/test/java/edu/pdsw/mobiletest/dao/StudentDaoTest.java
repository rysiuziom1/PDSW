package edu.pdsw.mobiletest.dao;

import edu.pdsw.mobiletest.MobiletestApplication;
import edu.pdsw.mobiletest.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MobiletestApplication.class })
@SpringBootTest
class StudentDaoTest {
	@Autowired
	private StudentDao studentDao;

	@AfterEach
	public void clearStudentsList() {
		studentDao.deleteStudents();
	}

	@Test
	public void insertStudentWithRandomIdTest() {
		Student student = new Student(null, "215342", "Jan", "Kowalski");
		int errCode = studentDao.insertStudent(student);
		assertEquals(1, errCode);
		assertEquals(1, studentDao.selectAllStudents().size());
		Student dbStudent = studentDao.selectStudentByIndex(student.getStudentIndex());
		assertEquals(student.getStudentIndex(), dbStudent.getStudentIndex());
		assertEquals(student.getFirstName(), dbStudent.getFirstName());
		assertEquals(student.getLastName(), dbStudent.getLastName());
	}

	@Test
	public void insertStudentWithSpecifiedIdTest() {
		UUID uuid = UUID.randomUUID();
		Student student = new Student(uuid, "215342", "Jan", "Kowalski");
		int errCode = studentDao.insertStudent(uuid, student);
		assertEquals(1, errCode);
		assertEquals(1, studentDao.selectAllStudents().size());
		Student dbStudent = studentDao.selectStudent(uuid);
		assertNotNull(dbStudent);
		assertEquals(student.getStudentID(), dbStudent.getStudentID());
		assertEquals(student.getStudentIndex(), dbStudent.getStudentIndex());
		assertEquals(student.getFirstName(), dbStudent.getFirstName());
		assertEquals(student.getLastName(), dbStudent.getLastName());
	}

	@Test
	public void selectStudentTest() {
		assertNull(studentDao.selectStudent(UUID.randomUUID()));
		assertTrue(studentDao.selectAllStudents().isEmpty());
		Student student = new Student(null, "215342", "Jan", "Kowalski");
		studentDao.insertStudent(student);
		assertEquals(1, studentDao.selectAllStudents().size());
		Student dbStudent = studentDao.selectStudentByIndex(student.getStudentIndex());
		assertNotNull(dbStudent);
		assertEquals(student.getStudentIndex(), dbStudent.getStudentIndex());
		assertEquals(student.getFirstName(), dbStudent.getFirstName());
		assertEquals(student.getLastName(), dbStudent.getLastName());
	}

	@Test
	public void finishTestTest() {
		Student student = new Student(UUID.randomUUID(), "215342", "Jan", "Kowalski");
		Student student2 = new Student(UUID.randomUUID(), "215343", "Piotr", "Nowak");
		studentDao.insertStudent(student.getStudentID(), student);
		studentDao.insertStudent(student2.getStudentID(), student2);
		studentDao.finishTest(student.getStudentID());
		assertEquals(0.0, studentDao.selectStudent(student.getStudentID()).getRemainingTime());
		assertNotEquals(0.0, studentDao.selectStudent(student2.getStudentID()).getRemainingTime());
	}

	@Test
	public void finishAllStudentsTestTest() {
		addManyStudents();
		studentDao.finishAllStudentsTest();
		studentDao.selectAllStudents().forEach(student -> assertEquals(0.0, student.getRemainingTime()));
	}

	@Test
	public void increaseRemainingTimeTest() {
		UUID uuid = UUID.randomUUID();
		Student student = new Student(uuid, "215342", "Jan", "Kowalski");
		student.setRemainingTime(45.0);
		studentDao.insertStudent(uuid, student);
		studentDao.increaseTime(uuid);
		Student dbStudent = studentDao.selectStudent(uuid);
		assertEquals(45.083, dbStudent.getRemainingTime(), 0.001);
	}

	@Test
	public void decreaseRemainingTimeTest() {
		UUID uuid = UUID.randomUUID();
		Student student = new Student(uuid, "215342", "Jan", "Kowalski");
		student.setRemainingTime(45.0);
		studentDao.insertStudent(uuid, student);
		studentDao.decreaseTime(uuid);
		Student dbStudent = studentDao.selectStudent(uuid);
		assertEquals(44.916, dbStudent.getRemainingTime(), 0.001);
	}

	@Test
	public void updateTimeTest() {
		addManyStudents();
		studentDao.updateTime(-60);
		studentDao.selectAllStudents().forEach(student -> {
			assertEquals(44.0, student.getRemainingTime(), 0.001);
		});
		studentDao.updateTime(-1);
		studentDao.selectAllStudents().forEach(student -> {
			assertEquals(43.983, student.getRemainingTime(), 0.001);
		});
		studentDao.updateTime(20);
		studentDao.selectAllStudents().forEach(student -> {
			assertEquals(44.316, student.getRemainingTime(), 0.001);
		});
	}


	private void addManyStudents() {
		for (int i = 0; i < 10; i++) {
			var student = new Student(UUID.randomUUID(), "21534" + i, "Jan" + i, "Kowalski" + i);
			student.setRemainingTime(45.0);
			studentDao.insertStudent(student.getStudentID(), student);
		}
	}
}