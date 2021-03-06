package com.soen343.studentapp.service;

import com.soen343.studentapp.model.Course;
import com.soen343.studentapp.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("courseService")
public class CourseService {

    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> listAll(){
        List<Course> courses = new ArrayList<>();
        courseRepository.findAll().forEach(courses::add);
        return courses;
    }

    public Course findCourseById(int id){
        return courseRepository.findCourseById(id);
    }

    public Course saveCourse(Course course) {
        if(course == null || course.getTime() == null || course.getTime().length() == 0) {
            return null;
        }

        String time = "";
        String[] userSubmittedTime = course.getTime().split(",");
        if(userSubmittedTime.length==3){
            course.setTimeStart(userSubmittedTime[1].replaceAll("\\s+",""));
            course.setTimeEnd(userSubmittedTime[2].replaceAll("\\s+",""));

            if(userSubmittedTime[0].contains("Mo")){
                time+= "Mo";
            }
            if(userSubmittedTime[0].contains("Tu")){
                time += "Tu";
            }
            if(userSubmittedTime[0].contains("We")){
                time += "We";
            }
            if(userSubmittedTime[0].contains("Th")){
                time += "Th";
            }
            if(userSubmittedTime[0].contains("Fr")){
                time += "Fr";
            }

            time += " " + userSubmittedTime[1]+ " - " + userSubmittedTime[2];

        }

        else if(userSubmittedTime.length==4){
            course.setTimeStart(userSubmittedTime[2].replaceAll("\\s+",""));
            course.setTimeEnd(userSubmittedTime[3].replaceAll("\\s+",""));

            for (int i=0; i<2 ; i++){

                if(userSubmittedTime[i].contains("Mo")){
                    time+= "Mo";
                }
                if(userSubmittedTime[i].contains("Tu")){
                    time += "Tu";
                }
                if(userSubmittedTime[i].contains("We")){
                    time += "We";
                }
                if(userSubmittedTime[i].contains("Th")){
                    time += "Th";
                }
                if(userSubmittedTime[i].contains("Fr")){
                    time += "Fr";
                }
            }

            time += " " + userSubmittedTime[2]+ " - " + userSubmittedTime[3];

        }
        course.setTime(time);
        return courseRepository.save(course);
    }


    public Course saveEditedCourse(Course course) {
        return courseRepository.save(course);
    }

    public void removeCourses(String[] CoursesRequest) {
        List<Integer> courseIds = new ArrayList<>();

        for (int i = 0; i < CoursesRequest.length; i++) {
            courseRepository.deleteById(Integer.parseInt(CoursesRequest[i]));
        }
    }
}
