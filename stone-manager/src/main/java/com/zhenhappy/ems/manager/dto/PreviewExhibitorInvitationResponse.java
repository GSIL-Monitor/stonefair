package com.zhenhappy.ems.manager.dto;

import com.zhenhappy.ems.dto.BaseResponse;
import com.zhenhappy.ems.manager.entity.PreviewExhibitorInvitation;

import java.util.List;

/**
 * 展商邀请涵响应数据
 * <p/>
 * Created by wangxd on 2016-11-24.
 */
public class PreviewExhibitorInvitationResponse extends BaseResponse {
    public List<PreviewExhibitorInvitation> previewExhibitorInvitationList;

    public PreviewExhibitorInvitationResponse() {
    }

    public PreviewExhibitorInvitationResponse(List<PreviewExhibitorInvitation> previewExhibitorInvitationList) {
        this.previewExhibitorInvitationList = previewExhibitorInvitationList;
    }

    public List<PreviewExhibitorInvitation> getPreviewExhibitorInvitationList() {
        return previewExhibitorInvitationList;
    }

    public void setPreviewExhibitorInvitationList(List<PreviewExhibitorInvitation> previewExhibitorInvitationList) {
        this.previewExhibitorInvitationList = previewExhibitorInvitationList;
    }
}
