package edu.pdsw.mobiletest.dao;

import edu.pdsw.mobiletest.MobiletestApplication;
import edu.pdsw.mobiletest.model.KnowledgeTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MobiletestApplication.class })
@SpringBootTest
class KnowledgeTestDaoTest {
	@Autowired
	private KnowledgeTestDao knowledgeTestDao;

	@TempDir
	static Path tempExerciseDir;

	@TempDir
	static Path tempSolutionsDir;

	@BeforeAll
	static void setupDirectories() throws IOException {
		assertTrue(Files.isDirectory(tempExerciseDir));
		assertTrue(Files.isDirectory(tempSolutionsDir));
		Files.createFile(tempExerciseDir.resolve("test_1.txt"));
		Files.createFile(tempExerciseDir.resolve("test_2.txt"));
		Files.createFile(tempExerciseDir.resolve("test_3.txt"));
	}

	@AfterEach
	public void unsetTest() {
		knowledgeTestDao.deleteTest();
	}

	@Test
	void setKnowledgeTestTest() {
		KnowledgeTest knowledgeTest = new KnowledgeTest(90.0, tempSolutionsDir.toAbsolutePath().toString(), tempExerciseDir.toAbsolutePath().toString());
		knowledgeTestDao.setKnowledgeTest(knowledgeTest);
		assertNotNull(knowledgeTestDao.getKnowledgeTest());
	}

	@Test
	void getKnowledgeTestTest() {
		KnowledgeTest knowledgeTest = new KnowledgeTest(90.0, tempSolutionsDir.toAbsolutePath().toString(), tempExerciseDir.toAbsolutePath().toString());
		knowledgeTestDao.setKnowledgeTest(knowledgeTest);
		assertNotNull(knowledgeTestDao.getKnowledgeTest());
		assertNotNull(knowledgeTestDao.getKnowledgeTest().getSolutionsAbsolutePath());
		assertNotNull(knowledgeTestDao.getKnowledgeTest().getExercisesAbsolutePath());
		assertNotNull(knowledgeTestDao.getKnowledgeTest().getExercises());
	}

	@Test
	void getExerciseTest() {
		KnowledgeTest knowledgeTest = new KnowledgeTest(90.0, tempSolutionsDir.toAbsolutePath().toString(), tempExerciseDir.toAbsolutePath().toString());
		knowledgeTestDao.setKnowledgeTest(knowledgeTest);
		var firstExerciseUUID = Objects.requireNonNull(knowledgeTestDao.getAllExercises().stream().findFirst().orElse(null)).getExerciseID();
		assertNotNull(knowledgeTestDao.getExercise(firstExerciseUUID));
		assertEquals(tempExerciseDir.resolve("test_1.txt").toAbsolutePath().toString(), knowledgeTestDao.getExercise(firstExerciseUUID).getExerciseAbsolutePath());
	}

	@Test
	void getRandomExerciseTest() {
		KnowledgeTest knowledgeTest = new KnowledgeTest(90.0, tempSolutionsDir.toAbsolutePath().toString(), tempExerciseDir.toAbsolutePath().toString());
		knowledgeTestDao.setKnowledgeTest(knowledgeTest);
		var exercisesPaths = Arrays.stream(Objects.requireNonNull(tempExerciseDir.toFile().listFiles())).map(File::toString).collect(Collectors.toList());
		var randomExercisePath = knowledgeTestDao.getRandomExercise().getExerciseAbsolutePath();
		assertTrue(exercisesPaths.contains(randomExercisePath));
	}

	@Test
	void getTotalTimeTest() {
		KnowledgeTest knowledgeTest = new KnowledgeTest(90.0, tempSolutionsDir.toAbsolutePath().toString(), tempExerciseDir.toAbsolutePath().toString());
		knowledgeTestDao.setKnowledgeTest(knowledgeTest);
		assertEquals(90.0, knowledgeTestDao.getTotalTime());
		knowledgeTest = new KnowledgeTest(45.0, tempSolutionsDir.toAbsolutePath().toString(), tempExerciseDir.toAbsolutePath().toString());
		knowledgeTestDao.setKnowledgeTest(knowledgeTest);
		assertEquals(45.0, knowledgeTestDao.getTotalTime());
		knowledgeTest = new KnowledgeTest(20.0, tempSolutionsDir.toAbsolutePath().toString(), tempExerciseDir.toAbsolutePath().toString());
		knowledgeTestDao.setKnowledgeTest(knowledgeTest);
		assertEquals(20.0, knowledgeTestDao.getTotalTime());
	}

	@Test
	void getExercisesDirectoryPathTest() {
		KnowledgeTest knowledgeTest = new KnowledgeTest(90.0, tempSolutionsDir.toAbsolutePath().toString(), tempExerciseDir.toAbsolutePath().toString());
		knowledgeTestDao.setKnowledgeTest(knowledgeTest);
		assertEquals(tempExerciseDir.toAbsolutePath().toString(), knowledgeTestDao.getExercisesDirectoryPath());
	}

	@Test
	void getSolutionDirectoryPathTest() {
		KnowledgeTest knowledgeTest = new KnowledgeTest(90.0, tempSolutionsDir.toAbsolutePath().toString(), tempExerciseDir.toAbsolutePath().toString());
		knowledgeTestDao.setKnowledgeTest(knowledgeTest);
		assertEquals(tempSolutionsDir.toAbsolutePath().toString(), knowledgeTestDao.getSolutionDirectoryPath());
	}
}