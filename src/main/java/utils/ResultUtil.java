package utils;

import common.enums.ResultEnum;
import pojo.Result;

public class ResultUtil {

    public static Result success(Object object){
        Result resultVo = new Result();
        resultVo.setCode(ResultEnum.SUCCESS.getCode());
        resultVo.setMessage(ResultEnum.SUCCESS.getMessage());
        resultVo.setData(object);
        return resultVo;
    }

    /**
     * 无值成功返回
     * @return
     */
    public static Result success(){
        return success(null);
    }

    /**
     * 错误返回
     * @param resultEnum
     * @return
     */
    public static Result error(ResultEnum resultEnum){
        Result resultVo =  new Result();
        resultVo.setCode(resultEnum.getCode());
        resultVo.setMessage(resultEnum.getMessage());
        return resultVo;
    }

    public static Result error(){
        return error(ResultEnum.FAILD);
    }

}
