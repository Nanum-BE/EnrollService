package com.nanum.enrollservice.movein.application;

import com.nanum.enrollservice.movein.infrastructure.MoveInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoveInServiceImpl implements MoveInService{

    private final MoveInRepository moveInRepository;


}
