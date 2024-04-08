package com.bit.envdev.service;

public interface BlockService {
    void block(String refType, Long refId, int period);

    void unblock(String refType, Long refId);
}
