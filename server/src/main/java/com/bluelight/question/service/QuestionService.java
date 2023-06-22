package com.bluelight.question.service;

import com.bluelight.exception.BusinessLogicException;
import com.bluelight.exception.ExceptionCode;
import com.bluelight.member.entity.Member;
import com.bluelight.member.entity.Profile;
import com.bluelight.member.service.MemberService;
import com.bluelight.member.service.ProfileService;
import com.bluelight.question.dto.AskQuestionDto;
import com.bluelight.question.dto.AskQuestionDto.TagDto;
import com.bluelight.question.dto.QuestionDetailDto;
import com.bluelight.question.dto.QuestionMemberProfileDto;
import com.bluelight.question.dto.TopQuestionDto;
import com.bluelight.question.entity.Question;
import com.bluelight.question.mapper.QuestionMapper;
import com.bluelight.question.repository.QuestionRepository;
import com.bluelight.tag.entity.Tag;
import com.bluelight.tag.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final TagRepository tagRepository;

    private final QuestionMapper mapper;

    private final MemberService memberService;

    private final ProfileService profileService;

    private final QuestionTagService questionTagService;

    public QuestionService(QuestionRepository questionRepository, TagRepository tagRepository,
        QuestionMapper mapper, MemberService memberService, ProfileService profileService,
        QuestionTagService questionTagService) {
        this.questionRepository = questionRepository;
        this.tagRepository = tagRepository;
        this.mapper = mapper;
        this.memberService = memberService;
        this.profileService = profileService;
        this.questionTagService = questionTagService;
    }

    @Transactional
    public Question createQuestion(AskQuestionDto.Post requestBody) {

        // Dto로 받아온 데이터들을 Entity 객체로 만든 후
        // Repository를 통해 데이터를 데이터베이스에 추가해야함

        // Question Dto -> Question Entity 만듬
        Question question = mapper.askquestionPostToQuestion(requestBody);

        Member findMember = memberService.findMember(requestBody.getMemberId());

        question.addMember(findMember);

        // Tag id들을 검증하기 위한 작업 단계
        //[4, 5]
        List<Long> tagIds = requestBody.getQuestionTag().stream()
            .map(TagDto::getTagId)
            .distinct()
            .collect(Collectors.toList());

        // 태그 id를 통해 실제 태그 값이 데이터베이서 있는지 검증이 필요함
        List<Tag> actualTags = tagRepository.findAllById(tagIds);

        if (tagIds.size() != actualTags.size()) {
            throw new EntityNotFoundException("태그가 존재하지 않습니다.");
        }
        // 태그에 대한 검증완료

        //QuestionTag에 대한 Entity 객체를 만드는 작업
        question.addQuestionTags(actualTags);
        Question savedQuestion = questionRepository.save(question);
        return savedQuestion;
    }

    @Transactional(readOnly = true)
    public QuestionDetailDto findQuestionDetail(long questionId) {
        Question findQuestion = findVerifiedQuestionByQuery(questionId);
        Member findMember = memberService.findMember(findQuestion.getMember().getMemberId());
        Profile findProfile = profileService.findProfile(findMember);
        List<Tag> findTagList = questionTagService.getTagsByQuestionId(questionId);

        return mapper.questionToResponse(
            new QuestionMemberProfileDto(findQuestion, findMember, findProfile, findTagList));
    }

    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size,
            Sort.by("questionId").descending()));
    }

    @Transactional
    public void deleteQuestion(long questionId) {
        Question findQuestion = findVerifiedQuestionByQuery(questionId);
        questionRepository.delete(findQuestion);
    }

    @Transactional(readOnly = true)
    private Question findVerifiedQuestionByQuery(long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findByQuestion(questionId);
        Question findQuestion =
            optionalQuestion.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        return findQuestion;
    }

    //readOnly: true -> get, gets
    //readOnly: false -> post, put, patch, delete
    @Transactional(readOnly = true)
    public List<TopQuestionDto.Response> topQuestions(int size) {
        // 쿼리로 createdAt 최신순(내림차순)으로 size(50)개 가져온 후 리턴
        List<Question> questions = questionRepository.findAll(Sort.by("questionId").descending());
        List<TopQuestionDto.Response> topQuestions = new ArrayList<>();

        questions.stream()
            .limit(size)
            .forEach(question -> {
                Member findMember = question.getMember();
                Profile findProfile = findMember.getProfile();
                List<Tag> findTagList = questionTagService.getTagsByQuestionId(question.getQuestionId());
                topQuestions.add(mapper.questionToTopQuestion(
                    new QuestionMemberProfileDto(question, findMember, findProfile, findTagList)));
            });

        return topQuestions;
    }

    @Transactional(readOnly = true)
    public Question findQuestion(long questionId) {
        return findVerifiedQuestion(questionId);
    }

    @Transactional(readOnly = true)
    public Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(
            () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findQuestion;
    }
}

// questions.stream().map((question) -> new Dto(question))
//        Member member = ;
//        question.getMember().getProfile().getProfileImage()
//        question.getMember().getProfile().getNickName()

//        return Collections.emptyList();

/*
    public Question findTopQuestion() {
        Optional<Question> optionalQuestion = questionRepository.findByTopQuestion();
        Question findQuesion =
            optionalQuestion.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        return findQuesion;
    }*/
/*
    public Page<Question> findQuestions(int page, int size) {

        return questionRepository.findAll(
            PageRequest.of(page, size, Sort.by("questionId").descending()));
    }

    public Page<Question> findQuestions(int page, int size, String filters) {
        if(filters.equals("newest")){
            return questionRepository.findAll(
                PageRequest.of(page, size, Sort.by("questionId").descending()));
        } else if(filters.equals("noanswer")) {

        } else if (filters.equals("highestscore")) {
            
        }

        return questionRepository.findAll(
            PageRequest.of(page, size, Sort.by("questionId").descending()));
    }
*/

