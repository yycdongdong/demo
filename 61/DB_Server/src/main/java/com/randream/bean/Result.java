package com.randream.bean;


import com.randream.code.Code;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String state;
    private Long code;
    private Object data;

    public final static Result Common_OK(Object data) {

        return new Result(Code.Message_Ok, Code.Code_Common_OK, data);
    }

    public final static Result Common_Failure(Object data) {

        return new Result(Code.Message_Failure, Code.Code_Common_Failure, data);
    }

    public final static Result Common_Error(Object data) {

        return new Result(Code.Message_Error, Code.Code_Common_Error, data);
    }

    public final static Result OK(Long code, Object data) {

        return new Result(Code.Message_Ok, code, data);
    }

    public final static Result Failure(Long code, Object data) {

        return new Result(Code.Message_Failure, code, data);
    }

    public final static Result Error(Long code, Object data) {

        return new Result(Code.Message_Error, code, data);
    }

    public final static Result OK(String state, Long code, Object data) {

        return new Result(state, code, data);
    }

    public final static Result Failure(String state, Long code, Object data) {

        return new Result(state, code, data);
    }

    public final static Result Error(String state, Long code, Object data) {

        return new Result(state, code, data);
    }

}
