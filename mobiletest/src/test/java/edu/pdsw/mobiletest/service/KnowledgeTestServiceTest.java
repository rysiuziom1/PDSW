package edu.pdsw.mobiletest.service;

import edu.pdsw.mobiletest.MobiletestApplication;
import edu.pdsw.mobiletest.exceptions.DirectoryException;
import edu.pdsw.mobiletest.model.KnowledgeTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MobiletestApplication.class })
@SpringBootTest
class KnowledgeTestServiceTest {
	@TempDir
	static Path exercisesPath;

	@TempDir
	static Path solutionsPath;

	@Autowired
	private KnowledgeTestService knowledgeTestService;

	@BeforeAll
	static void setupDirectories() throws IOException {
		assertTrue(Files.isDirectory(exercisesPath));
		assertTrue(Files.isDirectory(solutionsPath));
		Files.createFile(exercisesPath.resolve("test_1.txt"));
		Files.createFile(exercisesPath.resolve("test_2.txt"));
		Files.createFile(exercisesPath.resolve("test_3.txt"));
	}

	@Test
	public void setValidTestTest() {
		KnowledgeTest knowledgeTest = new KnowledgeTest(90.0, solutionsPath.toAbsolutePath().toString(), exercisesPath.toAbsolutePath().toString());
		assertDoesNotThrow(() -> knowledgeTestService.setTest(knowledgeTest));
	}

	@Test
	public void setInvalidTestTest() {
		KnowledgeTest knowledgeTest = new KnowledgeTest(90.0, "xd", "xd");
		assertThrows(DirectoryException.class, () -> knowledgeTestService.setTest(knowledgeTest));
	}

}