package project.gradproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.gradproject.domain.Keyword;
import project.gradproject.repository.KeywordRepository;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> splitKeyword(String keyword){
        String[] keywords = keyword.split(" ");
        List<String> keywordList=new ArrayList<String>();

        for (int i = 0; i < keywords.length; i++) {
            if(keywords[i].equals("")) continue;
            keywordList.add(keywords[i]);
        }

        return keywordList;
    }





}
