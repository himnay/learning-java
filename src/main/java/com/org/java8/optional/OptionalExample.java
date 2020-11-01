package com.org.java8.optional;

import com.org.java8.Student;
import com.sun.corba.se.impl.naming.cosnaming.InternalBindingKey;

import java.util.Optional;
import java.util.function.Supplier;

import static com.org.java8.StudentDataBase.getAllStudents;

public class OptionalExample {
    private static Supplier<Student> studentSupplier = () -> getAllStudents().get(0);

    public static void main(String[] args) {
        // 1.
        Optional<Student> student = Optional.ofNullable(getAllStudents().get(0));
        if (student.isPresent()) {
            Optional<String> name = student.map(Student::getName);
            System.out.println(name.get());
        }

        // 2. NPE
        // Optional<String> optional = Optional.of(null);
        // System.out.println(optional);

        // 3. orElse
        Optional<Student> optionalStudent = Optional.ofNullable(studentSupplier.get());
        String name = optionalStudent.map(Student::getName).orElse("Default");
        System.out.println(name);

        // 4. orElseGet
        Optional<Student> optionalStudent2 = Optional.ofNullable(studentSupplier.get());
        String name2 = optionalStudent.map(Student::getName).orElseGet(() -> "Default");
        System.out.println(name2);

        // 5. orElseThrow
        Optional<Student> optionalStudent3 = Optional.ofNullable(studentSupplier.get());
        String name3 = optionalStudent.map(Student::getName).orElseThrow(() -> new RuntimeException("Name not found"));
        System.out.println(name3);

        // 6. ifPresent
        Optional<String> optional = Optional.ofNullable("Himansu Nayak");
        optional.ifPresent(System.out::println);

        // 7. filter -> ifPresent
        Optional<Student> optionalStudent4 = Optional.ofNullable(studentSupplier.get());
        optionalStudent4.filter(studentTemp -> studentTemp.getGpa() >= 3.5).ifPresent(System.out::println);

        // 8. filter -> map
        Optional<Student> optionalStudent5 = Optional.ofNullable(studentSupplier.get());
        Optional<Double> gpa = optionalStudent4.filter(studentTemp -> studentTemp.getGpa() >= 3.5).map(Student::getGpa);
        System.out.println(gpa);

        // 9. flatmap -> map
        Optional<Student> optionalStudent6 = Optional.ofNullable(studentSupplier.get());
        Optional<String> bike = optionalStudent6.filter(studentTemp -> studentTemp.getGpa() >= 3.5).flatMap(Student::getBike).map(Student.Bike::getName);
        System.out.println(bike.get());
    }
}
