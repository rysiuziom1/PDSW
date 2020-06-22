package edu.pdsw.mobiletest.service;

import edu.pdsw.mobiletest.MobiletestApplication;
import edu.pdsw.mobiletest.exceptions.StudentAlreadyExistsException;
import edu.pdsw.mobiletest.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MobiletestApplication.class })
@SpringBootTest
class StudentServiceTest {
	@TempDir
	static Path tempSolutionsDir;

	@Autowired
	private StudentService studentService;

	@BeforeAll
	static void setupDirectories() {
		assertTrue(Files.isDirectory(tempSolutionsDir));
	}

	@AfterEach
	public void clearStudents() {
		studentService.deleteStudents();
	}

	@Test
	public void addNotExistingStudentTest() {
		Student student = new Student(UUID.randomUUID(), "215823", "Jan", "Kowalski");
		assertDoesNotThrow(() -> studentService.addStudent(student, tempSolutionsDir.toAbsolutePath().toString()));
	}

	@Test
	public void addExistingStudent() {
		Student student = new Student(UUID.randomUUID(), "215823", "Jan", "Kowalski");
		assertDoesNotThrow(() -> studentService.addStudent(student, tempSolutionsDir.toAbsolutePath().toString()));
		assertThrows(StudentAlreadyExistsException.class, () -> studentService.addStudent(student, tempSolutionsDir.toAbsolutePath().toString()));
	}
}