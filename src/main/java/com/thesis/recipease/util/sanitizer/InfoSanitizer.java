package com.thesis.recipease.util.sanitizer;

import com.thesis.recipease.model.web.recipe.WebInfo;
import com.thesis.recipease.model.web.recipe.WebRecipe;
import org.springframework.stereotype.Service;

@Service
public class InfoSanitizer implements Sanitizer<WebRecipe>{
    public WebRecipe sanitize(WebRecipe webRecipe){
        WebInfo webInfo = webRecipe.getInfo();
        if (webInfo.getPrepMin() == null) webInfo.setPrepMin(0);
        if (webInfo.getPrepHr() == null) webInfo.setPrepHr(0);
        if (webInfo.getProcessMin() == null) webInfo.setProcessMin(0);
        if (webInfo.getProcessHr() == null) webInfo.setProcessHr(0);
        if (webInfo.getYield() == null) webInfo.setYield(1.0);
        webInfo.setTotalMin((webInfo.getPrepMin() + webInfo.getProcessMin()) % 60);
        webInfo.setTotalHr(webInfo.getPrepHr() + webInfo.getProcessHr() + ((webInfo.getPrepMin() + webInfo.getProcessMin()) / 60));
        webRecipe.setInfo(webInfo);
        return webRecipe;
    }
}
