package com.hy.mapper.ms;

import com.hy.model.Opinion;
import org.springframework.stereotype.Repository;

@Repository
public interface OpinionMapper {
    Integer insertOpinion(Opinion opinion);
}
