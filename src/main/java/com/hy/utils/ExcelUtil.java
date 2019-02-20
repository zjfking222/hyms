package com.hy.utils;

import com.hy.dto.ExcelHeadDto;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Auther: 钱敏杰
 * @Date: 2019/1/31 9:45
 * @Description:Excel操作工具类
 */
public class ExcelUtil {

    public static Workbook exportExcel(List<ExcelHeadDto> head, List<Object> body, CellStyle style) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Workbook workbook = new HSSFWorkbook();
        //sheet此处只设置一个
        Sheet sheet = workbook.createSheet();
        Row row = null;
        if(head != null && head.size() >0){
            //创建Excel标题
            row = sheet.createRow(0);
            Cell cell = null;
            for(ExcelHeadDto dto:head){//循环添加标题行
                cell = row.createCell(dto.getSort());
                if(style != null){
                    cell.setCellStyle(style);
                }
                cell.setCellValue(dto.getName());
            }
            if(body != null && body.size() >0){
                for(int i=0;i<body.size();i++){//循环添加数据行
                    row = sheet.createRow(i + 1);
                    Object obj = body.get(i);
                    for(ExcelHeadDto dto:head){
                        //根据标题行的排列利用反射获取相应数据并添加到当前标题列下
                        cell = row.createCell(dto.getSort());
                        String methodName = "get" + dto.getKey().substring(0,1).toUpperCase() + dto.getKey().substring(1);
                        Method method = obj.getClass().getMethod(methodName);
                        if(method != null){
                            Object value = method.invoke(obj);
                            if(style != null){
                                cell.setCellStyle(style);
                            }
                            //不同类型的数据需要转化
                            if(value instanceof String){
                                cell.setCellValue((String)value);
                            }else if(value instanceof Double){
                                cell.setCellValue((Double)value);
                            }else if(value instanceof Date){
                                cell.setCellValue((Date)value);
                            }else if(value instanceof Calendar){
                                cell.setCellValue((Calendar)value);
                            }else if(value instanceof Boolean){
                                cell.setCellValue((Boolean)value);
                            }else{
                                //不存在的类型，不处理
                            }
                        }
                    }
                }
            }
        }
        return workbook;
    }

}
