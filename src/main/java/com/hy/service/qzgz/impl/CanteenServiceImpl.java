package com.hy.service.qzgz.impl;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.dto.CanteenDto;
import com.hy.dto.CanteenWithTotalPageDto;
import com.hy.mapper.ms.QzgzCanteenMapper;
import com.hy.model.QzgzCanteen;
import com.hy.service.qzgz.CanteenService;
import com.hy.utils.DTOUtil;
import com.hy.utils.DateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class CanteenServiceImpl implements CanteenService {

    private String[] titleRow = {"日期", "名称", "类型", "品种", "价格"};

    private boolean titleFlag;

    @Autowired
    private QzgzCanteenMapper canteenMapper;

    @Override
    public List<CanteenDto> getCanteenToday(int meal) {
        return DTOUtil.populateList(canteenMapper.selectCanteenToday(meal), CanteenDto.class);
    }

    @Override
    public boolean updateCanteenZan(int zan, int id) {
        QzgzCanteen qzgzCanteen = new QzgzCanteen();
        qzgzCanteen.setZan(zan);
        qzgzCanteen.setId(id);
        return canteenMapper.updateCanteenZan(qzgzCanteen) == 1;
    }

    @Override
    public List<CanteenDto> getCanteen(int pageNum, int pageSize, String value, String date, Integer meal, String sort, String dir) {
        PageHelper.startPage(pageNum, pageSize);
        return DTOUtil.populateList(canteenMapper.selectCanteen(value, date, meal, sort, dir), CanteenDto.class);
    }

    @Override
    public int getCanteenTotal(String value, String date, Integer meal) {
        return canteenMapper.selectCanteenTotal(value, date, meal);
    }

    @Override
    public boolean deleteCanteen(int id) {
        return canteenMapper.deleteCanteen(id) == 1;
    }

    private Object getCell(Row row, int index) {
        if (row.getCell(index) == null) {
            return "";
        } else {
            switch (row.getCell(index).getCellType()) {
                case STRING:
                    return row.getCell(index).getStringCellValue().trim();
                case BLANK:
                    return "";
                case NUMERIC:
                    return ((Double) row.getCell(index).getNumericCellValue()).longValue();
                case BOOLEAN:
                    return row.getCell(index).getBooleanCellValue();
                default:
                    return "";
            }
        }
    }

    @Override
    public Integer insertCanteenList(String filepath) {
        try {
            titleFlag = true;
            InputStream inputStream = new FileInputStream("files" + filepath);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet xssfSheet = workbook.getSheetAt(0);
            List<QzgzCanteen> qzgzCanteens = new ArrayList<>();
            HashMap<String,Integer> mealMap = new HashMap<>();
            mealMap.put("早餐",1);
            mealMap.put("午餐",2);
            mealMap.put("晚餐",3);
            mealMap.put("夜宵",4);
            List<CanteenDto> canteenDtos = DTOUtil.populateList(canteenMapper.selectCanteenAll(), CanteenDto.class);
            String[][] list = new String[canteenDtos.size()][3];
            for (int i = 0; i < canteenDtos.size(); i++) {
                list[i][0] = canteenDtos.get(i).getDate();
                list[i][1] = canteenDtos.get(i).getName();
                list[i][2] = String.valueOf(canteenDtos.get(i).getMeal());
            }
            IntStream.range(0, titleRow.length).forEach(i -> {
                try {
                    if (!xssfSheet.getRow(1).getCell(i).getStringCellValue().equals(titleRow[i])) {
                        titleFlag = false;
                    }
                } catch (NullPointerException e) {
                    titleFlag = false;
                }
            });
            System.out.println(titleFlag);
            if (titleFlag) {
                for (Row row : xssfSheet) {
                    if (row.getRowNum() > 1 && row.getRowNum() != xssfSheet.getLastRowNum()) {
                        Cell cell1 = row.getCell(0);
                        Cell cell2 = row.getCell(2);
                        Cell cell3 = row.getCell(4);
                        String d = String.valueOf(getCell(row, 0));
                        String name = String.valueOf(getCell(row, 1));
                        String meal = String.valueOf(getCell(row, 2));
                        String type = String.valueOf(getCell(row, 3));
                        String price = String.valueOf(getCell(row, 4));
                        if (d != null && !d.equals("") && name != null && !name.equals("") && meal != null && !meal.equals("")
                                && type != null && !type.equals("")) {
                            Date date;
                            String date1;
                            if (cell1.getCellType() == CellType.NUMERIC) {
                                date = cell1.getDateCellValue();
                                date1 = DateUtil.breviary(date);
                            } else {
                                date1 = null;
                            }
                            Integer meal1;
                            Float price1;
                            boolean judge = false;
                            if (meal == null || meal.equals("")) {
                                meal1 = null;
                            } else {
                                if (cell2.getCellType() == CellType.STRING && mealMap.containsKey(meal)) {
                                    meal1 = mealMap.get(meal);
                                } else {
                                    meal1 = null;
                                }
                            }
                            if (price == null || price.equals("")) {
                                price1 = Float.parseFloat("0");
                            } else {
                                if (cell3.getCellType() == CellType.NUMERIC) {
                                    price1 = Float.parseFloat(price);
                                } else {
                                    price1 = Float.parseFloat("0");
                                }
                            }
                            for (int x = 0; x < canteenDtos.size(); x++) {
                                if ((list[x][0].equals(date1) && list[x][1].equals(name) && list[x][2].equals(meal))) {
                                    judge = true;
                                    break;
                                }
                            }
                            if (!judge && (meal1 != null) && price != null) {
                                qzgzCanteens.add(new QzgzCanteen(date1, name, type, meal1, price1, SecurityUtil.getLoginid(), SecurityUtil.getLoginid()));

                            }
                        }
                    }
                }
                if (qzgzCanteens.size() != 0) {
                    return canteenMapper.insertCanteenList(qzgzCanteens);
                } else {
                    return 0;
                }
            }
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -2;
        }
    }
//    @Override
//    public CanteenWithTotalPageDto getCanteen(int page, int number, String state) {
//
//        //用户输入的页数转化为startRow
//        int startRow = (page - 1) * number;
//        List<CanteenDto> canteenDtos = DTOUtil.populateList(canteenMapper.selectCanteen(state, startRow, number),
//                new ArrayList<CanteenDto>(),CanteenDto.class);
//
//        return canteenDtos == null ?
//                null: new CanteenWithTotalPageDto(canteenDtos, getTotalPageOfCanteen(number, state));
//    }
//
//    @Override
//    public Integer getTotalPageOfCanteen(int number, String state) {
//        int countOfCanteen = canteenMapper.selectCountOfCanteen(state);
//        double totalPageD = (double)countOfCanteen/(double)number;
//        return (int)Math.ceil(totalPageD);
//    }
//
//    @Override
//    public boolean addCanteen(String name, String type) {
//        QzgzCanteen qzgzCanteen = new QzgzCanteen();
//        qzgzCanteen.setName(name);
//        qzgzCanteen.setType(type);
//        qzgzCanteen.setModifier(SecurityUtil.getLoginid());
//        qzgzCanteen.setCreater(SecurityUtil.getLoginid());
//        return canteenMapper.insertCanteen(qzgzCanteen) == 1;
//    }
//
//    @Override
//    public boolean updateCanteen(String name, String type, int id) {
//        QzgzCanteen qzgzCanteen = new QzgzCanteen();
//        qzgzCanteen.setId(id);
//        qzgzCanteen.setType(type);
//        qzgzCanteen.setName(name);
//        qzgzCanteen.setModifier(SecurityUtil.getLoginid());
//        return canteenMapper.updateCanteenById(qzgzCanteen) == 1;
//    }
//
//    @Override
//    public boolean updateCanteenState(String state, int id) {
//        QzgzCanteen qzgzCanteen = new QzgzCanteen();
//        qzgzCanteen.setId(id);
//        qzgzCanteen.setState(state);
//        qzgzCanteen.setModifier(SecurityUtil.getLoginid());
//        return canteenMapper.updateCanteenState(qzgzCanteen) == 1;
//    }
//
//    @Override
//    public List<CanteenDto> getCanteenBySearchName(String name, String state) {
//        return DTOUtil.populateList(canteenMapper.selectCanteenByName(name,state),
//                new ArrayList<CanteenDto>(),CanteenDto.class);
//    }
//
//    @Override
//    public CanteenDto getCanteenById(int id) {
//        return DTOUtil.populate(canteenMapper.selectCanteenById(id),CanteenDto.class);
//    }

}
