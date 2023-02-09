package com.lperilla.projects.basfchallenge.service;

import com.lperilla.projects.basfchallenge.entity.Error;
import com.lperilla.projects.basfchallenge.repository.ErrorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ErrorServiceImpl implements ErrorService {

    private ErrorRepository errorRepository;

    @Override
    public List<Error> findAllByOrderByTimestampDesc() {
        return this.errorRepository.findAllByOrderByTimestampDesc();
    }

    @Override
    public Error save(Error error) {
        return this.errorRepository.save(error);
    }
}
