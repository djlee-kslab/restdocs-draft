package com.example.demo.service;

import com.example.demo.model.TestXmlDto;
import org.springframework.stereotype.Service;

/**
 * payload를 그대로 return하게 작성해뒀지만, mocking해서
 */
@Service
public class TestService {

    public TestXmlDto testMethod(TestXmlDto xmlPayload) {
        return xmlPayload;
    }
}

/*
package longterm.service

import longterm.dto.request.AdminPttnCdRequestDTO
import org.springframework.stereotype.Service

@Service
class LongtermCaregiverService {
    // FIXME: 2022/05/23  response로 바꿔야함
    fun selectAdminPatternCode(condition: AdminPttnCdRequestDTO): AdminPttnCdRequestDTO {
        return condition
    }
}
 */