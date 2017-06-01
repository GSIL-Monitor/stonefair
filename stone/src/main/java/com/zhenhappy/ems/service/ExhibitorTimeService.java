package com.zhenhappy.ems.service;

import com.zhenhappy.ems.stonetime.TExhibitorTime;

public interface ExhibitorTimeService {
    public TExhibitorTime loadExhibitorTime();
    public TExhibitorTime loadExhibitorTimeByArea(int exhibitorArea);
}
