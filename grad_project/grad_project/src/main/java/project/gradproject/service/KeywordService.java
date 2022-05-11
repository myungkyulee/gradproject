package project.gradproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.gradproject.domain.Keyword;
import project.gradproject.repository.KeywordRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly =true)
public class KeywordService {

    private final KeywordRepository keywordRepository;

    @Transactional
    public Long saveKeyword(Keyword keyword) {
        keywordRepository.save(keyword);
        return keyword.getId();
    }





}
