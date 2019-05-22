package com.wmp.demo.service;

import org.springframework.stereotype.Service;

@Service
public interface Extract<R, P> {
    boolean validateParam(P p);
    R getOutputData(P p);
}
