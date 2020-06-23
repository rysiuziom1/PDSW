package edu.pdsw.mobiletest.dao;

import edu.pdsw.mobiletest.exceptions.NoTestException;
import edu.pdsw.mobiletest.model.Student;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Repository("studentRep")
public class StudentDataAccessService implements StudentDao {

    private static List<Student> DB = new ArrayList<>();

    @Override
    public int insertStudent(UUID id, Student student) {
        var newStudent = new Student(id, student.getStudentIndex(), student.getFirstName(), student.getLastName());
        newStudent.setExerciseID(student.getExerciseID());
        newStudent.setRemainingTime(student.getRemainingTime());
        DB.add(newStudent);
        return 1;
    }

    @Override
    public int increaseTime(UUID studentID) {
        var student = DB.stream().filter(s -> studentID.equals(s.getStudentID())).findAny().orElse(null);
        if (student == null) {
            return 0;
        } else {
            student.setRemainingTime(student.getRemainingTime() + 5.0);
        }
        return 1;
    }

    @Override
    public int decreaseTime(UUID studentID) {
        var student = DB.stream().filter(s -> studentID.equals(s.getStudentID())).findAny().orElse(null);
        if (student == null) {
            return 0;
        } else {
            student.setRemainingTime(student.getRemainingTime() - 5.0);
        }
        return 1;
    }

    @Override
    public int finishTest(UUID studentID) {
        var student = DB.stream().filter(s -> studentID.equals(s.getStudentID())).findAny().orElse(null);
        if (student == null) {
            return 0;
        } else {
            student.setRemainingTime(0.0);
            student.setFinishedTest(true);
        }
        return 1;
    }

    @Override
    public void finishAllStudentsTest(){
        for (Student student:DB
        ) {
            student.setRemainingTime(0.0);
            student.setFinishedTest(true);
        }
    }
    @Override
    public Student selectStudent(UUID uuid) {
        return DB.stream().filter(s -> s.getStudentID().equals(uuid)).findAny().orElse(null);
    }

    @Override
    public Student selectStudentByIndex(String studentIndex) {
        return DB.stream().filter(s -> s.getStudentIndex().equalsIgnoreCase(studentIndex)).findAny().orElse(null);
    }

    @Override
    public List<Student> selectAllStudents() {
        return DB;
    }

    @Override
    public int updateTime(double seconds) {
        DB.forEach(student -> {
            if (student.getRemainingTime() > 0) {
                student.setRemainingTime(student.getRemainingTime() + (seconds * (1. / 60.)));
                student.setFinishedTest(false);
            } else if (student.getRemainingTime() <= 0) {
                if (student.getRemainingTime() < 0) {
                    student.setRemainingTime(0.0);
                }
                student.setFinishedTest(true);
            }
        });
        return 1;
    }

    @Override
    public void saveStudentFile(String studentIndex, MultipartFile file, String solutionDirectoryPath) {
        var student = DB.stream().filter(s -> studentIndex.equals(s.getStudentIndex())).findAny().orElse(null);

        String fileName = student.getFirstName() + "_" + student.getLastName() + "_" + student.getStudentIndex();

        Path directoryPath = Paths.get(
                solutionDirectoryPath + "/" + student.getFirstName() + "_" +
                        student.getLastName() + "_" + student.getStudentIndex() + "/" + fileName + "." +
                        FilenameUtils.getExtension(file.getOriginalFilename())
        );

        try {
            Files.copy(file.getInputStream(), directoryPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public void createStudentsDirectory(Student student, String solutionDirectoryPath) {
        String directoryName = solutionDirectoryPath + "/" + student.getFirstName() + "_" +
                student.getLastName() + "_" + student.getStudentIndex();

        File directory = new File(directoryName);
        if (!directory.exists()){
            directory.mkdir();
        }
    }

    @Override
    public void deleteStudents() {
        DB.clear();
    }

    @Override
    public Map<String, String> getTestFile(String testFileDirectory) throws IOException, NoTestException {
        File directory = new File(testFileDirectory);

        File[] files = directory.listFiles(File::isFile);

        if (files == null) {
            throw new NoTestException("No test files in directory.");
        }

        long lastModifiedTime = Long.MIN_VALUE;
        File testFile = null;

        for (File file : files) {
            if (file.lastModified() > lastModifiedTime) {
                testFile = file;
                lastModifiedTime = file.lastModified();
            }
        }

        assert testFile != null;
        InputStream inputStream = new FileInputStream(testFile);
        byte[] fileBytes = IOUtils.toByteArray(inputStream);

        HashMap<String, String> objectMap = new HashMap<>();
        objectMap.put("filename", testFile.getName());
        objectMap.put("file", Base64.getEncoder().encodeToString(fileBytes));

        return objectMap;
    }

    @Override
    public UUID getExerciseID(String studentIndex) {
        return selectStudentByIndex(studentIndex).getExerciseID();
    }

    @Override
    public void logout(UUID studentID) {
        var student = DB.stream().filter(s -> studentID.equals(s.getStudentID())).findAny().orElse(null);
        if (student != null) {
            student.setLogged(false);
        }
    }
}
