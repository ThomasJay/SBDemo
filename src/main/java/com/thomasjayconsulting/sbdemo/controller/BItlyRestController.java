package com.thomasjayconsulting.sbdemo.controller;

import com.thomasjayconsulting.sbdemo.dto.BitlyRequestDTO;
import com.thomasjayconsulting.sbdemo.dto.BitlyResponseDTO;
import com.thomasjayconsulting.sbdemo.service.BitlyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class BItlyRestController {

    private BitlyService bitlyService;

    public BItlyRestController(BitlyService bitlyService) {
        this.bitlyService = bitlyService;
    }

    @PostMapping("bitly")
    public BitlyResponseDTO processBitly(@RequestBody BitlyRequestDTO bitlyRequestDTO) {
        return bitlyService.processBitly(bitlyRequestDTO.getLongURL());
    }
}
