package com.maoding.v2.labor;

import com.maoding.core.bean.ResponseBean;
import com.maoding.labor.dto.QueryLaborHourDTO;
import com.maoding.labor.dto.SaveLaborHourDTO;
import com.maoding.labor.service.LaborHourService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.Query;

@Controller
@RequestMapping("/v2/labor/")
public class LaborHourController extends BaseWSController {

    @Autowired
    private LaborHourService laborHourService;

    @RequestMapping("/saveLaborHour")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean saveLaborHour(@RequestBody SaveLaborHourDTO dto) throws Exception {
        return laborHourService.saveLaborHour(dto);
    }

    @RequestMapping("/getLaborDate")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getLaborDate(@RequestBody QueryLaborHourDTO dto) throws Exception {
        return ResponseBean.responseSuccess().addData("dateList",laborHourService.getLaborDate(dto));
    }

    @RequestMapping("/getLaborHourByDate")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getLaborHourByDate(@RequestBody QueryLaborHourDTO dto) throws Exception {
        return ResponseBean.responseSuccess().addData("schedule",laborHourService.getLaborHourByDate(dto));
    }

    @RequestMapping("/deleteLaborHour")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean deleteLaborHour(@RequestBody SaveLaborHourDTO dto) throws Exception {
        return laborHourService.deleteLaborHour(dto);
    }


}
