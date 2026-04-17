package com.springboot.rbl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rbl.dto.CkycSearchRequestDTO;
import com.springboot.rbl.service.CkycSearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ckyc/search")
@RequiredArgsConstructor
public class CkycSearchController {

	@Autowired
    private  CkycSearchService ckycSearchService;

    @PostMapping
    public String search(@RequestBody CkycSearchRequestDTO dto) {
        return ckycSearchService.search(dto.getPan());
    }
}

