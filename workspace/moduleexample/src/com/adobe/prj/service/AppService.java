package com.adobe.prj.service;

import com.adobe.prj.util.DateUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AppService {
    Logger logger = Logger.getLogger(AppService.class.getName());
    public void execute() {

        logger.log(Level.INFO,DateUtil.getDate().toString());
    }
}
