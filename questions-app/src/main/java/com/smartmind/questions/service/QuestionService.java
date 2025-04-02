package com.smartmind.questions.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smartmind.questions.dao.QuestionDao;
import com.smartmind.questions.models.Question;
import com.smartmind.questions.models.QuestionWrapper;
import com.smartmind.questions.models.Response;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

	public ResponseEntity<List<Integer>> genrateQuiz(String categoryName, Integer noOfQuestion) {
	List<Integer>questionList=	questionDao.findRandomQuestionsByCategory(categoryName,noOfQuestion);
		return new ResponseEntity<>(questionList,HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsByIds(List<Integer> ids) {
		List<QuestionWrapper>questionwrapperList=new ArrayList<>();
		List<Question>questionList=new ArrayList<>();
		for(int id:ids) {
			questionList.add(questionDao.findById(id).get());
		}
		for(Question ques:questionList) {
			QuestionWrapper wrapper=new QuestionWrapper();
			wrapper.setQuestionTitle(ques.getQuestionTitle());
			wrapper.setOption1(ques.getOption1());
			wrapper.setOption2(ques.getOption2());
			wrapper.setOption3(ques.getOption3());
			wrapper.setOption4(ques.getOption4());
			wrapper.setId(ques.getId());
			questionwrapperList.add(wrapper);
		}
		return new ResponseEntity<List<QuestionWrapper>>(questionwrapperList,HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {
		
	       
	        int right = 0;
	        int i = 0;
	        for(Response response : responses){
	        	Question question=questionDao.findById(response.getId()).get();
	            if(response.getResponse().equals(question.getRightAnswer()))
	                right++;

	            i++;
	        }
	        return new ResponseEntity<>(right, HttpStatus.OK);
		
	}
}