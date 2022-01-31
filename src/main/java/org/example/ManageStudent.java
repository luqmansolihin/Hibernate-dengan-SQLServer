package org.example;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

public class ManageStudent {
    private static SessionFactory factory;

    public static void main(String[] args) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object : " + ex);
            throw new ExceptionInInitializerError(ex);
        }

        ManageStudent MS = new ManageStudent();

        /* Add few student record in database */
//        Integer stdID1 = MS.addStudent("Luqman Solihin Tiga", "1710501045", LocalDate.now(), LocalDate.now());
//        Integer stdID2 = MS.addStudent("Solihin", "1710501046", LocalDate.now(), LocalDate.now());
//        Integer stdID3 = MS.addStudent("Luqman Solihin", "1710501047", LocalDate.now(), LocalDate.now());

        /* List down all the employees */
//        MS.listStudents();

        /* Update student's records */
//        int stdID = 6;
//        MS.updateStudents(stdID, "Luqman Solihin Tiga Edit");

        /* Delete a student from the database */
//        int stdID = 6;
//        MS.deleteStudents(stdID);

        /* List down new list of the students */
        MS.listStudents();
    }

    /* Method to CREATE a students in the database */
    public Integer addStudent(String name, String nim, LocalDate created, LocalDate modified){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer studentID = null;

        try {
            tx = session.beginTransaction();
            Student student = new Student(name, nim, created, modified);
            studentID = (Integer) session.save(student);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return studentID;
    }

    /* Method to  READ all the students */
    public void listStudents( ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List students = session.createQuery("FROM students").list();
            for (Iterator iterator = students.iterator(); iterator.hasNext();){
                Student student = (Student) iterator.next();
                System.out.print("Name: " + student.getName() + " | ");
                System.out.print("NIM: " + student.getNim() + " | ");
                System.out.print("Created: " + student.getCreated() + " | ");
                System.out.println("Modified: " + student.getModified());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to UPDATE name for an student */
    public void updateStudents(Integer StudentID, String name ){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Student student = (Student) session.get(Student.class, StudentID);
            student.setName( name );
            session.update(student);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to DELETE a student from the records */
    public void deleteStudents(Integer StudentID){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Student student = (Student) session.get(Student.class, StudentID);
            session.delete(student);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
