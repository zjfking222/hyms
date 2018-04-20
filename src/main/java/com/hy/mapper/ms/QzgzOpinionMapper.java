package com.hy.mapper.ms;

import com.hy.model.QzgzOpinion;
import org.springframework.stereotype.Repository;

@Repository
public interface QzgzOpinionMapper {
    Integer insertOpinion(QzgzOpinion opinion);
}
