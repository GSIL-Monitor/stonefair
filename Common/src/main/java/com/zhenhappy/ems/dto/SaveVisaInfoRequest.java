package com.zhenhappy.ems.dto;

import com.zhenhappy.ems.entity.TVisa;

/**
 * Created by wujianbin on 2014-05-11.
 */
public class SaveVisaInfoRequest extends TVisa {
    private String birthDay;
    private String expireDate;
    private String fromDate;
    private String toDate;
	private Integer joinerId;
    private String hotelAddress;

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

	@Override
	public Integer getJoinerId() {
		return joinerId;
	}

	@Override
	public void setJoinerId(Integer joinerId) {
		this.joinerId = joinerId;
	}

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }
}
