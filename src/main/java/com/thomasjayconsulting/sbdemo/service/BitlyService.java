package com.thomasjayconsulting.sbdemo.service;

import com.thomasjayconsulting.sbdemo.dto.BitlyResponseDTO;

public interface BitlyService {

    public BitlyResponseDTO processBitly(String longURL);
}
