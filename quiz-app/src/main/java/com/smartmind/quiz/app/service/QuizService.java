package com.smartmind.quiz.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smartmind.quiz.app.dao.QuizDao;
import com.smartmind.quiz.app.feign.FeignClientService;
import com.smartmind.quiz.app.model.QuestionWrapper;
import com.smartmind.quiz.app.model.Quiz;
import com.smartmind.quiz.app.model.Response;


@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    
@Autowired
FeignClientService feignClientService;

    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {

    	List<Integer> questionList=feignClientService.getQuestionsForQuiz(category, numQ).getBody();
         Quiz quiz=new Quiz();
         quiz.setQuestions(questionList);
         quiz.setTitle(title);
         quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
    	List<QuestionWrapper> questionsForUser = new ArrayList<>();
		
    	feignClientService.getQuestionsFromId(null).getBody();
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
    	int right = 0;
		right=feignClientService.getScore(responses).getBody();
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}