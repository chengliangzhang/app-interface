package com.maoding.commonModule.service;

import com.maoding.commonModule.dto.QueryRelationRecordDTO;
import com.maoding.commonModule.dto.RelationTypeDTO;
import com.maoding.commonModule.dto.SaveRelationRecordDTO;
import com.maoding.commonModule.entity.RelationRecordEntity;
import com.maoding.financial.dto.AuditDataDTO;

import java.util.List;

public interface RelationRecordService {

    void saveRelationRecord(SaveRelationRecordDTO dto) throws Exception;

    AuditDataDTO getRelationList(QueryRelationRecordDTO query) throws Exception;

    /**
     * 用于项目费用申请
     */
    RelationRecordEntity getRelationRecord(String mainId);

}
