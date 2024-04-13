package com.bit.envdev.dto;

import com.bit.envdev.constant.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ResponseDTO<T> {
    private T item;
    private List<T> items;
    private Page<T> pageItems;
    private int errorCode;
    private String errorMessage;
    private int statusCode;
    private Role role;
}
