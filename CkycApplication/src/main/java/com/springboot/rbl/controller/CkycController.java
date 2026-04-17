package com.springboot.rbl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rbl.dto.CkycRequestDTO;
import com.springboot.rbl.service.CkycService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ckyc")
@RequiredArgsConstructor
public class CkycController {
	
	@Autowired
    private  CkycService ckycService;

    @PostMapping
    public String submit(@RequestBody CkycRequestDTO dto) {
        return ckycService.createCkyc(dto);
    }
}
