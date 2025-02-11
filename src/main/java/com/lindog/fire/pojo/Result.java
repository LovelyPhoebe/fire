package com.lindog.fire.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;//响应码，0 代表成功; 其余 代表失败
    private String msg;  //响应信息 描述字符串
    private Object data; //返回的数据

    public static Result success() {
        return new Result(0, "success", null);
    }

    public static Result success(Object data) {
        return new Result(0, "success", data);
    }

    public static Result error(String msg) {
        return new Result(1, msg, null);
    }
}
